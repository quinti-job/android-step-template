package com.quinti.android_step_template.ui.screen.coin

import com.quinti.android_step_template.kmp.domain.analytics.EventTracker
import com.quinti.android_step_template.kmp.domain.analytics.Tracking
import com.quinti.android_step_template.kmp.domain.reactor.KyashCoinDetailReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.ReactorViewModel
import com.quinti.android_step_template.kmp.domain.reactor.di.ReactorDiContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class KyashCoinDetailViewModel @Inject constructor(
    private val tracker: EventTracker,
    reactorDiContainer: ReactorDiContainer,
) : ReactorViewModel<KyashCoinDetailReactor>(
    reactorDiContainer.createKyashCoinDetailReactor(),
) {
    fun initialize() {
        reactor.execute(KyashCoinDetailReactor.Action.LoadInitially)
    }

    fun refresh() {
        reactor.execute(KyashCoinDetailReactor.Action.Refresh)
    }

    fun onResume() {
        reactor.execute(KyashCoinDetailReactor.Action.SyncStatus)
    }

    fun onNavigationClick() {
        reactor.execute(KyashCoinDetailReactor.Action.TapBack)
    }

    fun onLoadMore() {
        reactor.execute(KyashCoinDetailReactor.Action.LoadMore)
    }

    fun onHelpClick() {
        tracker.trackEvent(Tracking.Action.RewardCoinDetail.ClickHelp)
        reactor.execute(KyashCoinDetailReactor.Action.TapInformation)
    }
}