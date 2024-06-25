package com.quinti.android_step_template.kmp.domain.analytics

//interface TrackableScreen {
//    val screenTracker: ScreenTracker
//
//    fun getScreen(): Tracking.Screen?
//}
//
//fun <T> T.setUp(screenTracker: ScreenTracker) where T : LifecycleOwner, T : TrackableScreen {
//    val screen = getScreen() ?: return
//    screenTracker.setScreen(screen)
//    lifecycle.addObserver(screenTracker)
//}