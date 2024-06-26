package com.quinti.android_step_template.util

import android.content.Context
import android.graphics.Bitmap

/**
 * Utility for notification
 */
interface NotificationUtility {

    enum class MessageType {
        REQUEST,
        RECEIVE,
        DECLINE,
        MESSAGE,
        GIFT,
        USER_RECEIVED_KYASH_INFO,
        OTHER,
    }

    companion object {
        const val KEY_NOTIFICATION_ID = "notification_id"
    }

    /**
     * 通知チャンネル
     * @note PushWooshの通知チャンネル仕様が微妙なため、PushWoosh用のチャンネルは自分で作らない。
     *       PushWoosh SDKがプッシュ受信時に自動的に作る。
     */
    enum class Channel(val id: String) {
        // push
        TX("channel_0001"),
        CONTACT("channel_0002"),
        MESSAGE("channel_0003"), // platform push

        // local
        UPLOAD("channel_1000"),
        DOWNLOAD("channel_1001"),

        // widget
        WIDGET("channel_2000"),
    }

    enum class NotificationId(val id: Int) {
        // widget
        WIDGET(2000),
    }

    /**
     * Create notification channels for Android O
     */
    fun createChannels(context: Context)

    fun showNotification(
        context: Context,
        channel: Channel,
        type: MessageType,
        title: String,
        body: String,
        largeIcon: Bitmap?,
        url: String,
        txUUID: String?,
        txGroupUUID: String?,
    )

    fun showUploading(context: Context)

    fun showUploaded(context: Context)

    fun showDownloading(context: Context)

    fun showDownloaded(context: Context)

    fun showRepliedNotification(
        context: Context,
        notificationId: Int,
        channelID: String,
        success: Boolean,
    )

    fun cancel(context: Context, notificationId: Int)
}