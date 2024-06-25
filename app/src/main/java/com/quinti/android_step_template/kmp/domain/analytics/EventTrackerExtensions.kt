package com.quinti.android_step_template.kmp.domain.analytics

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver


/**
 * Composable関数のスクリーンイベントを記録するためのヘルパー関数。
 * 利用する際は必ずCompositionLocalProviderで[LocalEventTracker]を設定してください。
 * 画面に戻った際にも記録したい場合は[trackOnResume]をtrueに設定してください。
 *
 * @param screen 記録するスクリーンイベント
 * @param eventTracker [EventTracker]の実装
 * @param trackOnResume trueの場合、スクリーンが表示されるたびに記録します。falseの場合、スクリーンが初回表示された時点で記録します。
 */
@Composable
@Deprecated(
    "新しく設置する画面では TrackScreenEventV2 を使ってください。\n" +
            "https://github.com/Kyash/Kyash-Mobile-Log/issues/150",
)
fun TrackScreenEvent(
    screen: Tracking.Screen,
    eventTracker: EventTracker = LocalEventTracker.current,
    trackOnResume: Boolean = false,
) {
    if (trackOnResume) {
        val owner = LocalLifecycleOwner.current
        DisposableEffect(owner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    eventTracker.trackScreen(screen)
                }
            }

            owner.lifecycle.addObserver(observer)
            onDispose {
                owner.lifecycle.removeObserver(observer)
            }
        }
    } else {
        LaunchedEffect(Unit) {
            eventTracker.trackScreen(screen)
        }
    }
}

/**
 * Composable関数のスクリーンイベントを記録するためのヘルパー関数。
 * 利用する際は必ずCompositionLocalProviderで[LocalEventTracker]を設定してください。
 * 画面に戻った際にも記録したい場合は[trackOnResume]をtrueに設定してください。
 *
 * @param screen 記録するスクリーンイベント
 * @param eventTracker [EventTracker]の実装
 * @param trackOnResume trueの場合、スクリーンが表示されるたびに記録します。falseの場合、スクリーンが初回表示された時点で記録します。
 */
@Composable
fun TrackScreenEventV2(
    screen: Tracking.Screen,
    eventTracker: EventTracker = LocalEventTracker.current,
    trackOnResume: Boolean = true,
) {
    if (trackOnResume) {
        val owner = LocalLifecycleOwner.current
        DisposableEffect(owner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    eventTracker.trackScreenV2(screen)
                }
            }

            owner.lifecycle.addObserver(observer)
            onDispose {
                owner.lifecycle.removeObserver(observer)
            }
        }
    } else {
        LaunchedEffect(Unit) {
            eventTracker.trackScreenV2(screen)
        }
    }
}