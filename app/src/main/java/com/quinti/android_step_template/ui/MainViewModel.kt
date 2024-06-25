package com.quinti.android_step_template.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelDisposables
import androidx.lifecycle.viewModelScope
import co.kyash.log.Tracking
import co.kyash.mobile.usecase.reward.HideRewardTabCoachMarkUseCase
import co.kyash.mobile.usecase.reward.WatchNeedShowRewardTabCoachMarkUseCase
import co.kyash.model.analytics.EventTracker
import co.kyash.model.entity.KyashCardApplicationStatus
import co.kyash.model.entity.PreStartImportantNotice
import co.kyash.model.entity.PushNotificationSettings
import co.kyash.model.entity.User
import co.kyash.model.usecase.account.GetNotificationSettingUseCase
import co.kyash.model.usecase.appreview.AppReviewRequestedUseCase
import co.kyash.model.usecase.appreview.GetAppReviewRequiredUseCase
import co.kyash.model.usecase.appreview.SetAppReviewRequiredUseCase
import co.kyash.model.usecase.push.SendPushNotificationSettings
import co.kyash.model.usecase.user.GetPreStartImportantNoticeUseCase
import co.kyash.model.usecase.user.GetUserUseCase
import co.kyash.ui.util.NoCacheMutableLiveData
import co.kyash.ui.util.timberCoroutineExceptionHandler
import co.kyash.ui.util.zip
import co.kyash.ui.view.LoadState
import com.pushwoosh.notification.PushwooshNotificationSettings
import com.quinti.android_step_template.kmp.domain.analytics.Tracking
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlinx.coroutines.launch
import timber.log.Timber

@Suppress("LongParameterList")
class MainViewModel @Inject constructor(
    private val tracker: EventTracker,
    private val getUserUseCase: GetUserUseCase,
    private val getNotificationSettingUseCase: GetNotificationSettingUseCase,
    private val getPreStartImportantNoticeUseCase: GetPreStartImportantNoticeUseCase,
    private val getAppReviewRequiredUseCase: GetAppReviewRequiredUseCase,
    private val setAppReviewRequiredUseCase: SetAppReviewRequiredUseCase,
    private val appReviewRequestedUseCase: AppReviewRequestedUseCase,
    private val sendPushNotificationSettings: SendPushNotificationSettings,
    private val watchNeedShowRewardTabCoachMark: WatchNeedShowRewardTabCoachMarkUseCase,
    private val hideRewardTabCoachMarkUseCase: HideRewardTabCoachMarkUseCase,
) : ViewModel() {
    val loadState = MutableLiveData(LoadState.LOADING)
    private val isTabTopScreen = MutableLiveData(true)
    val isBottomNavigationViewVisible: LiveData<Boolean> =
        zip(loadState, isTabTopScreen) { loadState, isTabTopScreen ->
            isTabTopScreen && loadState == LoadState.LOADED
        }

    private val _isRewardTabCoachMarkVisible: MutableLiveData<Boolean> = MutableLiveData(false)
    val isRewardTabCoachMarkVisible: LiveData<Boolean> =
        zip(
            isBottomNavigationViewVisible,
            _isRewardTabCoachMarkVisible,
        ) { isBottomNavigationViewVisible, isRewardTabCoachMarkVisible ->
            isBottomNavigationViewVisible && isRewardTabCoachMarkVisible
        }

    val onEvent = NoCacheMutableLiveData<Event>()

    var isWalletTab = false
        set(value) {
            field = value
            if (value) {
                tracker.trackEvent(Tracking.Event.OpenFirstView)
            }
        }

    fun init(notificationSettings: PushNotificationSettings) {
        viewModelScope.launch(timberCoroutineExceptionHandler) {
            sendPushNotificationSettings(notificationSettings)
            observeNeedShowRewardTabCoachMark()
        }

        showImportantNoticeIfNeeded()
    }

    fun setIsTabTopScreen(isTabTop: Boolean) {
        isTabTopScreen.value = isTabTop
    }

    private fun observeNeedShowRewardTabCoachMark() {
        viewModelScope.launch {
            watchNeedShowRewardTabCoachMark().collect { showCoachMark ->
                _isRewardTabCoachMarkVisible.value = showCoachMark
            }
        }
    }

    fun hideRewardTabCoachMark() {
        viewModelScope.launch {
            runCatching {
                hideRewardTabCoachMarkUseCase.invoke()
            }.onFailure {
                // エラーはユーザーに報告しない
                Timber.e(it)
            }
        }
    }

    private fun showImportantNoticeIfNeeded() {
        loadState.value = LoadState.LOADING
        getPreStartImportantNoticeUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { notice ->
                    when (notice) {
                        is PreStartImportantNotice.ForKyashValueTerms -> {
                            onEvent.setValue(
                                Event.NavigateToImportantNoticeForKyashValueTerms(
                                    notice.status,
                                ),
                            )
                        }

                        is PreStartImportantNotice.ForIdentity -> {
                            onEvent.setValue(
                                Event.NavigateToImportantNoticeForIdentity,
                            )
                        }

                        is PreStartImportantNotice.None -> {
                            loadState.value = LoadState.LOADED
                        }
                    }
                },
                onError = {
                    Timber.w(it)
                    loadState.value = LoadState.ERROR
                },
            )
            .addTo(viewModelDisposables)
    }

    fun onRefreshNotificationSetting() {
        getNotificationSettingUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    enablePushwooshNotification(it.notifications.messageReceived.push)
                },
                {},
            )
            .addTo(viewModelDisposables)
    }

    fun onReloadClick() {
        showImportantNoticeIfNeeded()
    }

    @VisibleForTesting
    fun enablePushwooshNotification(enable: Boolean) {
        PushwooshNotificationSettings.enableNotifications(enable)
    }

    fun showUserDialog(publicId: Long) {
        getUserUseCase.execute(publicId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.isInformationRequiredForTransactionsFilled()) {
                        onEvent.setValue(Event.ShowUserDialog(it))
                    } else {
                        onEvent.setValue(
                            Event.ShowInformationRequiredForTransactionsIsNotFilledDialog,
                        )
                    }
                },
                {
                    Timber.w(it)
                },
            )
            .addTo(viewModelDisposables)
    }

    fun goToSendOrRequest(publicId: Long, action: String, amount: Long?, message: String?) {
        getUserUseCase.execute(publicId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.isInformationRequiredForTransactionsFilled()) {
                        when (action) {
                            "send" -> onEvent.setValue(Event.NavigateToSend(it, amount, message))
                            "request" -> onEvent.setValue(
                                Event.NavigateToRequest(
                                    it,
                                    amount,
                                    message,
                                ),
                            )

                            else -> Timber.e("Unknown action")
                        }
                    } else {
                        onEvent.setValue(
                            Event.ShowInformationRequiredForTransactionsIsNotFilledDialog,
                        )
                    }
                },
                {
                    Timber.w(it)
                },
            )
            .addTo(viewModelDisposables)
    }

    fun onCreateWithoutDeepLink() {
        Completable.timer(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                if (getAppReviewRequiredUseCase.execute()) {
                    appReviewRequestedUseCase.execute()
                    onEvent.setValue(Event.RequestAppReview)
                }
                setAppReviewRequiredUseCase.execute(false)
            }
            .addTo(viewModelDisposables)
    }

    sealed class Event {
        data object RequestAppReview : Event()
        class NavigateToImportantNoticeForKyashValueTerms(
            val status: KyashCardApplicationStatus.Status,
        ) :
            Event()

        data object NavigateToImportantNoticeForIdentity : Event()
        class FailedAuth(val throwable: Throwable) : Event()
        class ShowUserDialog(val user: User.PublicUser) : Event()
        class NavigateToSend(val user: User.PublicUser, val amount: Long?, val message: String?) :
            Event()

        class NavigateToRequest(
            val user: User.PublicUser,
            val amount: Long?,
            val message: String?,
        ) : Event()

        data object ShowInformationRequiredForTransactionsIsNotFilledDialog : Event()
    }
}