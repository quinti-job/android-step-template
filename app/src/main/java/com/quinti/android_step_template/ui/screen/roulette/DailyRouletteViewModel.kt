package com.quinti.android_step_template.ui.screen.roulette

import com.quinti.android_step_template.kmp.domain.analytics.EventTracker
import com.quinti.android_step_template.kmp.domain.analytics.Tracking
import com.quinti.android_step_template.kmp.domain.reactor.DailyRouletteReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.ReactorViewModel
import com.quinti.android_step_template.kmp.di.ReactorDiContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyRouletteViewModel @Inject constructor(
    private val tracker: EventTracker,
    container: ReactorDiContainer,
) : ReactorViewModel<DailyRouletteReactor>(
    container.createDailyRouletteReactor(),
) {

    fun initialize() {
        reactor.execute(DailyRouletteReactor.Action.LoadInitially)
    }

    fun onExecuteClick() {
        tracker.trackEventV2(Tracking.Action.RewardDailyRoulette.ClickStart)
        reactor.execute(DailyRouletteReactor.Action.TapApply)
    }

    fun onAnimationCompleted() {
        reactor.execute(DailyRouletteReactor.Action.RouletteAnimationCompleted)
    }

    fun onNavigationClick() {
        reactor.execute(DailyRouletteReactor.Action.TapNavigation)
    }

    fun onCloseClick() {
        reactor.execute(DailyRouletteReactor.Action.TapClose)
    }
}