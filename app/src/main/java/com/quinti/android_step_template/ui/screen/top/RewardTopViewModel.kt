package com.quinti.android_step_template.ui.screen.top

import android.graphics.RectF
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.quinti.android_step_template.kmp.domain.reactor.RewardTopReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.kmp.domain.reactor.base.ReactorViewModel
import com.quinti.android_step_template.kmp.domain.reactor.di.ReactorDiContainer
import com.quinti.android_step_template.ui.config.RewardConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Suppress("TooManyFunctions")
@HiltViewModel
class RewardTopViewModel @Inject constructor(
    reactorDiContainer: ReactorDiContainer,
//    private val tracker: EventTracker,
    rewardConfig: RewardConfig,
) : ReactorViewModel<RewardTopReactor>(
    reactorDiContainer.createRewardTopReactor(
        isAutoNavigateToRouletteEnabled = rewardConfig.rewardTopAutoNavigateToRouletteEnabled,
    ),
) {
    private var loaded = false

    private val _rouletteRect: MutableStateFlow<RectF> = MutableStateFlow(RectF())
    val rouletteRect: StateFlow<RectF> = _rouletteRect.asStateFlow()

    private val _welcomeChallengeRect: MutableStateFlow<RectF> = MutableStateFlow(RectF())
    val welcomeChallengeRect: StateFlow<RectF> = _welcomeChallengeRect.asStateFlow()

    fun setRouletteRect(rectF: RectF) {
        _rouletteRect.value = rectF
    }

    fun setWelcomeChallengeRect(rectF: RectF) {
        _welcomeChallengeRect.value = rectF
    }

    fun loadInitially() {
        // タブ切り替えで再読込が行われることを防ぐ
        if (!loaded) {
            reactor.execute(RewardTopReactor.Action.LoadInitially)
//            tracker.trackEvent(Tracking.AppsFlyer.Reward.OpenReward)
            loaded = true
        }
    }

    fun onClickDailyRoulette() {
//        tracker.trackEvent(Tracking.Action.RewardTop.ClickRoulette)
        reactor.execute(RewardTopReactor.Action.TapRoulette)
    }

    fun onRefresh() {
        reactor.execute(RewardTopReactor.Action.Refresh)
    }

    fun onClickPrize(prize: RewardTopReactor.State.PrizeUiState) {
//        tracker.trackEvent(
//            Tracking.Action.RewardTop.ClickChallenge(
//                prizeId = prize.prizeId,
//                prizeType = prize.prizeType.name,
//            ),
//        )
        reactor.execute(RewardTopReactor.Action.TapPrize(prize))
    }

    fun clickWelcomePrize() {
        viewModelScope.launch {
            (reactor.state.value as? Reactor.LoadState.Loaded<RewardTopReactor.State>)?.let {
                onClickPrize(prize = it.value.welcomePrizes.firstOrNull() ?: return@launch)
            }
        }
    }

    fun onAppliedPrizeListClick() {
        reactor.execute(RewardTopReactor.Action.TapAppliedPrizeList)
    }

    fun onAboutRewardClick() {
//        tracker.trackEvent(Tracking.Action.RewardTop.ClickAboutReward)
        reactor.execute(RewardTopReactor.Action.TapAboutReward)
    }

    fun onClickKyashCoin() {
//        tracker.trackEvent(Tracking.Action.RewardTop.ClickCoinDetail)
        reactor.execute(RewardTopReactor.Action.TapKyashCoinDetail)
    }

    fun onClickStampBanner() {
        reactor.execute(RewardTopReactor.Action.TapChallengeStamp)
    }

    private val onResumeAvailable = MutableLiveData(false)
    fun onResume() {
        if (onResumeAvailable.value == true) {
            reactor.execute(RewardTopReactor.Action.OnResume)
        } else {
            onResumeAvailable.value = true
        }
    }

    fun onPause() {
        reactor.execute(RewardTopReactor.Action.OnStop)
    }

    fun onClickPointBalance() {
        reactor.execute(RewardTopReactor.Action.TapPointDetail)
    }

    fun onTapSpotlightScrim() {
        reactor.execute(RewardTopReactor.Action.TapSpotlightScrim)
    }

    fun onTapOfferWallBanner() {
//        tracker.trackEvent(Tracking.Action.RewardTop.ClickOfferWall)
        reactor.execute(RewardTopReactor.Action.TapOfferWall)
    }
}

