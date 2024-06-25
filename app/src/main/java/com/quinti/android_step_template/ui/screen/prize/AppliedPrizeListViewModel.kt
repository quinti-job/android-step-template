package com.quinti.android_step_template.ui.screen.prize

import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeFilter
import com.quinti.android_step_template.kmp.data.entity.Prize
import com.quinti.android_step_template.kmp.domain.analytics.EventTracker
import com.quinti.android_step_template.kmp.domain.analytics.Tracking
import com.quinti.android_step_template.kmp.domain.reactor.AppliedPrizeListReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.ReactorViewModel
import com.quinti.android_step_template.kmp.di.ReactorDiContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppliedPrizeListViewModel @Inject constructor(
    reactorDiContainer: ReactorDiContainer,
    private val tracker: EventTracker,
) : ReactorViewModel<AppliedPrizeListReactor>(
    reactorDiContainer.createAppliedPrizeListReactor(),
) {
    fun initialize() {
        reactor.execute(AppliedPrizeListReactor.Action.LoadInitially)
    }

    fun onNavigationClick() {
        reactor.execute(AppliedPrizeListReactor.Action.TapNavigation)
    }

    fun onTapPrize(prize: AppliedPrizeListReactor.State.AppliedPrizeItem) {
        tracker.trackEvent(
            Tracking.Action.RewardHistory.ClickedReward(
                prizeId = prize.prizeId,
                prizeType = prize.type.name,
            ),
        )
        reactor.execute(AppliedPrizeListReactor.Action.TapAppliedPrize(prize))
    }

    fun onRefresh() {
        reactor.execute(AppliedPrizeListReactor.Action.Refresh)
    }

    fun onLoadNextWeekly() {
        reactor.execute(AppliedPrizeListReactor.Action.LoadNext.Weekly)
    }

    fun onLoadNextDaily() {
        reactor.execute(AppliedPrizeListReactor.Action.LoadNext.Daily)
    }

    fun onResume() {
        reactor.execute(AppliedPrizeListReactor.Action.RefreshStatus)
    }

    fun onFilterChange(
        appliedPrizeFilter: AppliedPrizeFilter,
        prizeType: Prize.Type,
    ) {
        reactor.execute(
            AppliedPrizeListReactor.Action.ChangeFilter(
                appliedPrizeFilter = appliedPrizeFilter,
                prizeType = prizeType,
            ),
        )
    }
}