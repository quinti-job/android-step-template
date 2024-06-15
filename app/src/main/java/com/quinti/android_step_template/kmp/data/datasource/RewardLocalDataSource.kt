package com.quinti.android_step_template.kmp.data.datasource

import co.kyash.mobile.data.file.platform.LocalKeyValueStore
import co.kyash.mobile.data.file.platform.LocalKeyValueStoreFactory
import com.quinti.android_step_template.kmp.file.platform.LocalKeyValueStore
import com.quinti.android_step_template.kmp.file.platform.LocalKeyValueStoreFactory
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onSubscription

interface RewardLocalDataSource {

    val requestUpdateRewardTabIndicatorFlow: SharedFlow<Unit>

    val isShownRewardTabCoachMarkFlow: SharedFlow<Boolean>
    suspend fun requestUpdateRewardTabIndicator()
    fun setRewardTopOnboardingShown(isShown: Boolean)
    fun isRewardTopOnboardingShown(): Boolean
    fun isRewardTabBalloonShown(): Boolean
    fun setRewardTabBalloonShown(isShown: Boolean)
    suspend fun setRewardTabCoachMarkShown()

    fun isNeedShowRewardStampOnboarding(): Boolean
    fun setNeedShowRewardStampOnboarding(needShow: Boolean)
}

class RewardPersistenceDataSource : RewardLocalDataSource {

    // テストする際に setUpTestLocalKeyValueStore  を呼ばないと、instance がnullとなる。
    // もし初期化で定義すると、Factoryを参照しているTest全てに setUpTestLocalKeyValueStore
    // を追加する必要があるので、遅延させて回避している。
    // 別途対応を検討したい。
    private val kvs: LocalKeyValueStore by lazy {
        LocalKeyValueStoreFactory.instance.create("kyash-reward")
    }

    private val _requestUpdateRewardTabIndicatorFlow = MutableSharedFlow<Unit>()
    override val requestUpdateRewardTabIndicatorFlow: SharedFlow<Unit> =
        _requestUpdateRewardTabIndicatorFlow.asSharedFlow()
            .onSubscription { emit(Unit) }

    private val _isShownRewardTabCoachMarkFlow = MutableSharedFlow<Boolean>()
    override val isShownRewardTabCoachMarkFlow: SharedFlow<Boolean> =
        _isShownRewardTabCoachMarkFlow.asSharedFlow().onSubscription {
            emit(isRewardTabCoachMarkShown())
        }

    private fun isRewardTabCoachMarkShown(): Boolean {
        return kvs.boolean(RewardTabCoachMarkShown) ?: false
    }

    override suspend fun setRewardTabCoachMarkShown() {
        if (isRewardTabCoachMarkShown()) {
            return
        }
        kvs.set(RewardTabCoachMarkShown, true)
        _isShownRewardTabCoachMarkFlow.emit(true)
    }

    override suspend fun requestUpdateRewardTabIndicator() {
        _requestUpdateRewardTabIndicatorFlow.emit(Unit)
    }

    override fun setRewardTopOnboardingShown(isShown: Boolean) {
        if (isRewardTopOnboardingShown()) {
            return
        }
        kvs.set(RewardTopOnboardingShown, true)
    }

    override fun isRewardTopOnboardingShown(): Boolean {
        return kvs.boolean(RewardTopOnboardingShown) ?: false
    }

    override fun isRewardTabBalloonShown(): Boolean {
        return kvs.boolean(RewardTabNewBalloonShown) ?: false
    }

    override fun setRewardTabBalloonShown(isShown: Boolean) {
        if (isRewardTabBalloonShown()) {
            return
        }
        kvs.set(RewardTabNewBalloonShown, true)
    }

    override fun isNeedShowRewardStampOnboarding(): Boolean {
        return kvs.boolean(NeedShowRewardStampOnboarding, false)
    }

    override fun setNeedShowRewardStampOnboarding(needShow: Boolean) {
        kvs.set(NeedShowRewardStampOnboarding, needShow)
    }

    companion object {
        private const val RewardTopOnboardingShown = "reward_top_onboarding_shown"
        private const val RewardTabNewBalloonShown = "reward_tab_new_balloon_shown"
        private const val RewardTabCoachMarkShown = "reward_tab_coach_mark_shown"
        private const val NeedShowRewardStampOnboarding = "need_show_reward_stamp_onboarding"
    }
}