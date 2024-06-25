package com.quinti.android_step_template.kmp.domain.analytics
//
//import android.os.Bundle
//import com.quinti.socialnetwork.kmp.api.KyashApi
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//
//@Suppress("LongParameterList")
//class EventTrackerImpl(
//    /**
//     * 非同期のイベント送信処理を実行する Coroutine Scope
//     * イベント送信は中断されたくないため
//     * GlobalScope のようなアプリ全体の Scope が設定される想定
//     */
//    private val scope: CoroutineScope,
//    private val firebaseAnalytics: FirebaseAnalytics,
//    private val pushwoosh: Pushwoosh,
//    private val pushwooshInAppManager: InAppManager,
//    private val appsFlyer: AppsFlyer,
//    private val playServices: PlayServices,
//    private val apiEndpointProvider: ApiEndpointProvider,
//    private val sardineTracker: SardineTracker,
//) : EventTracker {
//
//    /**
//     * ユーザーIDをセットする
//     */
//    override fun setUserId(publicId: Long?) {
//        FirebaseCrashlytics.getInstance().setUserId(publicId?.toString() ?: "")
//        firebaseAnalytics.setUserId(publicId?.toString() ?: "")
//        pushwoosh.setTags(
//            TagsBundle.Builder()
//                .putLong("PublicId", publicId ?: 0L)
//                .build(),
//        )
//        pushwoosh.setUserId(publicId?.toString() ?: "")
//        if (publicId == null) {
//            Sift.unsetUserId()
//        } else {
//            Sift.setUserId(publicId.toString())
//        }
//
//        appsFlyer.setCustomerUserId(publicId?.toString() ?: "")
//        sardineTracker.setUserId(publicId)
//    }
//
//    override fun setUserProperties(properties: UserProperties?) {
//        firebaseAnalytics.setUserProperty("account_type", properties?.accountType?.value)
//    }
//
//    /**
//     * プッシュ通知用のユーザータグを送信する
//     */
//    override fun setPushwooshTag(tag: Tracking.Tag) {
//        pushwoosh.setTags(
//            TagsBundle.Builder().apply {
//                tag.parameters.forEach { (key, value) ->
//                    when (value) {
//                        is String -> putString(key, value)
//                        is Int -> putInt(key, value)
//                        is Long -> putLong(key, value)
//                        is Boolean -> putBoolean(key, value)
//                        else -> error("未対応のPushwoosh Tag Type: $value")
//                    }
//                }
//            }.build(),
//        )
//    }
//
//    override fun trackScreen(screen: Tracking.Screen) {
//        // 移行期間中は以前の形式のイベントを送信した後に、新しい形式のイベントを送信する
//        logFirebaseEvent(
//            event = DEPRECATED_TRACK_SCREEN_PREFIX + screen.eventName,
//            parameters = null,
//        )
//        trackScreenV2(screen)
//    }
//
//    override fun trackScreenV2(screen: Tracking.Screen) {
//        logFirebaseEvent(
//            event = FirebaseAnalytics.Event.SCREEN_VIEW,
//            parameters = mapOf(FirebaseAnalytics.Param.SCREEN_NAME to screen.eventName),
//        )
//    }
//
//    override fun trackEvent(action: Tracking.Action) {
//        // 移行期間中は以前の形式のイベントを送信した後に、新しい形式のイベントを送信する
//        logFirebaseEvent(
//            event = DEPRECATED_TRACK_EVENT_PREFIX + action.eventName,
//            parameters = action.parameters,
//        )
//        trackEventV2(action)
//    }
//
//    override fun trackEventV2(action: Tracking.Action) {
//        logFirebaseEvent(
//            event = FirebaseAnalytics.Event.SELECT_CONTENT,
//            parameters = mapOf(
//                TRACK_ACTION_V2_EVENT_PARAM_KEY to action.eventName,
//            ) + action.parameters,
//        )
//    }
//
//    companion object {
//        const val APPSFLYER_EVENT_VALUE_KEY_ENV = "environment"
//        const val APPSFLYER_ENV_LOCAL = "local"
//        const val APPSFLYER_ENV_EXP = "experimental"
//        const val APPSFLYER_ENV_STG = "staging"
//        const val APPSFLYER_ENV_DEV = "development"
//        const val APPSFLYER_ENV = "production"
//        private const val DEPRECATED_TRACK_SCREEN_PREFIX = "SCREEN_"
//        private const val DEPRECATED_TRACK_EVENT_PREFIX = "EVENT_"
//        private const val TRACK_ACTION_V2_EVENT_PARAM_KEY = "kyash_action_name"
//    }
//
//    private val envName = when (apiEndpointProvider.provide().authHeader) {
//        KyashApi.AUTH_HEADER_LOCAL -> APPSFLYER_ENV_LOCAL
//        KyashApi.AUTH_HEADER_EXP -> APPSFLYER_ENV_EXP
//        KyashApi.AUTH_HEADER_STG -> APPSFLYER_ENV_STG
//        KyashApi.AUTH_HEADER_DEV -> APPSFLYER_ENV_DEV
//        KyashApi.AUTH_HEADER -> APPSFLYER_ENV
//        else -> APPSFLYER_ENV_EXP
//    }
//
//    override fun trackEvent(appsFlyer: Tracking.AppsFlyer) {
//        scope.launch {
//            // 非同期で Advertising ID を取得して AppsFlyer へイベントを送信する
//            val advertisingId = playServices.getAdvertisingId()
//            this@EventTrackerImpl.appsFlyer.logEvent(
//                eventName = appsFlyer.eventName,
//                eventValues = appsFlyer.parameters + mapOf(
//                    APPSFLYER_EVENT_VALUE_KEY_ENV to envName,
//                ),
//                advertisingId = advertisingId,
//            )
//        }
//    }
//
//    /**
//     * InAppMessage用のイベント送信
//     */
//    override fun trackEvent(event: Tracking.Event) {
//        pushwooshInAppManager.postEvent(
//            event.eventName,
//            TagsBundle.Builder().apply {
//                event.parameters.forEach { (key, value) ->
//                    when (value) {
//                        is String -> putString(key, value)
//                        is Int -> putInt(key, value)
//                        is Long -> putLong(key, value)
//                        is Boolean -> putBoolean(key, value)
//                        else -> error("未対応のPushwoosh Tag Type: $value")
//                    }
//                }
//            }.build(),
//        )
//    }
//
//    private fun logFirebaseEvent(event: String, parameters: Map<String, String>?) {
//        val params = parameters?.takeIf { it.isNotEmpty() }
//        val bundle = params?.let {
//            Bundle().apply {
//                it.forEach { putString(it.key, it.value) }
//            }
//        }
//        firebaseAnalytics.logEvent(event, bundle)
//
//        if (false) { // Change this value in debugging analytics
//            val tail = params?.let { " : $it" }.orEmpty()
//            Timber.i("[Firebase Event] $event$tail")
//        }
//    }
//}