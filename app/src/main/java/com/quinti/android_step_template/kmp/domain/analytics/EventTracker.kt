package com.quinti.android_step_template.kmp.domain.analytics

import com.quinti.android_step_template.kmp.domain.analytics.Tracking

/**
 * イベント送信
 */
interface EventTracker {
    /**
     * Set user ID (Kyash public ID) to all analytics SDKs
     * If user does not have public ID (not signed in), arg may be null.
     */
    fun setUserId(publicId: Long?)

    /**
     * Set user properties to Firebase analytics.
     */
    fun setUserProperties(properties: UserProperties?)

    /**
     * Send user tags for push notification.
     */
    fun setPushwooshTag(tag: Tracking.Tag)

    @Deprecated(
        "新しく設置する画面では trackScreenV2 を使ってください。\n" +
                "https://github.com/Kyash/Kyash-Mobile-Log/issues/150",
    )
    fun trackScreen(screen: Tracking.Screen)

    fun trackScreenV2(screen: Tracking.Screen)

//    @Deprecated(
//        "新しく設置する画面では trackEventV2 を使ってください。\n" +
//                "https://github.com/Kyash/Kyash-Mobile-Log/issues/150",
//    )
    fun trackEvent(action: Tracking.Action)

    fun trackEventV2(action: Tracking.Action)

    /**
     * AppsFlyer イベント送信
     */
//    fun trackEvent(appsFlyer: Tracking.AppsFlyer)

    /**
     * Track UI event for in-app message
     */
    fun trackEvent(event: Tracking.Event)
}