package com.quinti.android_step_template.kmp.domain.reactor

import co.kyash.mobile.data.api.exception.ApiFatalError
import co.kyash.mobile.data.api.exception.ApiFatalErrorException
import co.kyash.mobile.model.reward.offerwall.OfferWallDisabledReason
import co.kyash.mobile.model.reward.offerwall.SkyFlagOfferWall
import co.kyash.mobile.model.reward.offerwall.SkyFlagOfferWallEngagementUri
import com.quinti.android_step_template.kmp.domain.reactor.SkyFlagOfferWallReactor.Action
import com.quinti.android_step_template.kmp.domain.reactor.SkyFlagOfferWallReactor.Event
import com.quinti.android_step_template.kmp.domain.reactor.SkyFlagOfferWallReactor.Mutation
import com.quinti.android_step_template.kmp.domain.reactor.SkyFlagOfferWallReactor.State
import co.kyash.mobile.ui.AbstractReactor
import co.kyash.mobile.ui.Reactor
import co.kyash.mobile.ui.exception.NonFatalError
import co.kyash.mobile.ui.exception.asNonFatalError
import co.kyash.mobile.usecase.reward.offerwall.AgreeToProvidePersonalInformationForSkyFlagUseCase
import co.kyash.mobile.usecase.reward.offerwall.ExtractSkyFlagOfferWallPromotionUrlUseCase
import co.kyash.mobile.usecase.reward.offerwall.GetSkyFlagOfferWallUseCase
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalError
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalErrorException
import com.quinti.android_step_template.kmp.data.api.exception.NonFatalError
import com.quinti.android_step_template.kmp.data.api.exception.asNonFatalError
import com.quinti.android_step_template.kmp.domain.reactor.base.AbstractReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.kmp.domain.usecase.AgreeToProvidePersonalInformationForSkyFlagUseCase
import com.quinti.android_step_template.kmp.domain.usecase.ExtractSkyFlagOfferWallPromotionUrlUseCase
import com.quinti.android_step_template.kmp.domain.usecase.GetSkyFlagOfferWallUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class SkyFlagOfferWallReactor(
    mainDispatcher: CoroutineDispatcher,
    private val extractSkyFlagOfferWallPromotionUrl: ExtractSkyFlagOfferWallPromotionUrlUseCase,
    private val getSkyFlagOfferWall: GetSkyFlagOfferWallUseCase,
    private val agreePersonalInformation: AgreeToProvidePersonalInformationForSkyFlagUseCase,
    private val isOfferWallEnabled: Boolean,
) : AbstractReactor<Reactor.LoadState<State>, Action, Mutation, Event>(
    mainDispatcher = mainDispatcher,
    initialState = Reactor.LoadState.Loading(),
) {
    override fun mutate(action: Action): Flow<Mutation> = flow {
        when (action) {
            Action.LoadInitially -> initialize()

            Action.TapAgreePersonalInformation -> agreeToProvidePersonalInformation()

            is Action.TapUri -> tapUri(action.uri)
        }
    }

    private suspend fun FlowCollector<Mutation>.initialize() {
        if (!isOfferWallEnabled) {
            emit(
                Mutation.SetLoaded(SkyFlagOfferWall.Disabled(OfferWallDisabledReason.NotAvailable)),
            )
            return
        }
        runCatching {
            val offerWall = getSkyFlagOfferWall()
            emit(Mutation.SetLoaded(offerWall))
        }.onFailure { throwable ->
            if (throwable is ApiFatalErrorException) {
                notify(Event.HandleFatalError(throwable.error))
            } else {
                notify(Event.ShowError(throwable.asNonFatalError()))
            }
            emit(Mutation.SetError)
        }
    }

    private suspend fun FlowCollector<Mutation>.agreeToProvidePersonalInformation() {
        runCatching {
            agreePersonalInformation()
            val offerWall = getSkyFlagOfferWall()
            emit(Mutation.SetLoaded(offerWall))
        }.onFailure { throwable ->
            if (throwable is ApiFatalErrorException) {
                notify(Event.HandleFatalError(throwable.error))
            } else {
                notify(Event.ShowError(throwable.asNonFatalError()))
            }
        }
    }

    private suspend fun tapUri(uri: SkyFlagOfferWallEngagementUri) {
        when (uri) {
            is SkyFlagOfferWallEngagementUri.Promotion -> {
                // URIから案件URLを取得してブラウザを開く
                val url = this.extractSkyFlagOfferWallPromotionUrl(uri)
                notify(Event.OpenBrowser(url))
            }

            is SkyFlagOfferWallEngagementUri.Inquiry -> {
                notify(Event.OpenBrowser(uri.inquiryUrl))
            }

            is SkyFlagOfferWallEngagementUri.Close -> {
                notify(Event.Close)
            }

            is SkyFlagOfferWallEngagementUri.Direct -> {
                // NOP
            }

            is SkyFlagOfferWallEngagementUri.FAQ -> {
                notify(Event.OpenBrowser(uri.faqUrl))
            }
        }
    }

    override fun reduce(
        state: Reactor.LoadState<State>,
        mutation: Mutation,
    ): Reactor.LoadState<State> {
        return when (mutation) {
            is Mutation.SetLoaded -> Reactor.LoadState.Loaded(
                State(mutation.offerWall),
            )

            Mutation.SetError -> Reactor.LoadState.Error()
            Mutation.SetLoading -> Reactor.LoadState.Loading()
        }
    }

    sealed class Mutation : AbstractReactor.Mutation {
        data class SetLoaded(val offerWall: SkyFlagOfferWall) : Mutation()
        data object SetLoading : Mutation()
        data object SetError : Mutation()
    }

    /**
     * State（Reactor → Client）
     * ・LoadState.Loading
     * ・LoadState.Loaded
     * 　・OfferWallUiState.Disabled
     * 　　・reason: OfferWallDisabledReason
     * 　・OfferWallUiState.Enabled
     * 　　・baseurl: string
     * 　　・params: Map<string, string>
     * 　・個人情報提供同意済みかどうか
     */
    data class State(
        val offerWall: OfferWallUiState,
        val remainingPointAmount: Long,
    ) : Reactor.State {

        companion object {
            operator fun invoke(offerWall: SkyFlagOfferWall) = when (offerWall) {
                is SkyFlagOfferWall.Disabled -> State(
                    offerWall = OfferWallUiState.Disabled(offerWall.reason),
                    remainingPointAmount = 0,
                )

                is SkyFlagOfferWall.Enabled -> State(
                    offerWall = if (offerWall.isPersonalInformationAgreed) {
                        OfferWallUiState.Enabled(offerWall.url.value)
                    } else {
                        OfferWallUiState.ServiceProviderAgreement
                    },
                    remainingPointAmount = offerWall.remainingPointAmount,
                )
            }
        }
    }

    sealed class OfferWallUiState {
        data object ServiceProviderAgreement : OfferWallUiState()
        data class Disabled(val reason: OfferWallDisabledReason) : OfferWallUiState()
        data class Enabled(val url: String) : OfferWallUiState()
    }

    /**
     * Action（Client→Reactor）
     * ・初期化処理
     * ・URIタップ
     * ・個人情報提供に同意するがクリックされた
     */
    sealed class Action : Reactor.Action {

        data object LoadInitially : Action()
        data class TapUri(val uri: SkyFlagOfferWallEngagementUri) : Action()
        data object TapAgreePersonalInformation : Action()
    }

    /**
     * Event（Reactor→Client）
     * ・閉じる
     * ・個人情報提供同意ダイアログ表示
     * ・ブラウザ遷移
     * ・エラー表示（２つ）
     * ・ローディング開始 / 終了
     */
    sealed class Event : Reactor.Event {
        data object Close : Event()
        data class OpenBrowser(val url: String) : Event()
        data class HandleFatalError(val error: ApiFatalError) : Event()
        data class ShowError(val error: NonFatalError) : Event()
        data object ShowProgress : Event()
        data object HideProgress : Event()
    }
}