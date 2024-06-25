package com.quinti.android_step_template.kmp.data.entity

import korlibs.time.DateTimeTz
import korlibs.time.millisecondsLong

/**
 * 指定日までの残り時間を表す
 *
 * [Over]: 期間が過ぎた
 * [InTime]: 時分秒での表現。期間が残っている場合で残り時間が1日以内
 * [InDays]: 日数での表現。期間が残っている場合で残り時間が1日以上
 * [Gone]: 残り時間がそもそも無い
 *
 * @property countDownAvailable カウントダウン表示が可能かどうか
 * @property inDays 日数での表現。
 * @property isToday 今日の日付かどうか
 * @property isTomorrow 明日の日付かどうか
 */
sealed class RemainingTimeType {

    abstract val countDownAvailable: Boolean
    abstract val inDays: Int
    abstract val isToday: Boolean
    abstract val isTomorrow: Boolean

    data object Gone : RemainingTimeType() {
        override val countDownAvailable: Boolean = false
        override val inDays: Int = 0
        override val isToday: Boolean = false
        override val isTomorrow: Boolean = false
    }
    data class InDays(val days: Int) : RemainingTimeType() {
        override val countDownAvailable: Boolean = true
        override val inDays: Int = days
        override val isToday: Boolean = false
        override val isTomorrow: Boolean = days == 1
    }
    data class InTime(val hours: Int, val minute: Int, val second: Int) : RemainingTimeType() {
        override val countDownAvailable: Boolean = true
        override val inDays: Int = 0
        override val isToday: Boolean = true
        override val isTomorrow: Boolean = false
    }

    // 期間が過ぎた
    data object Over : RemainingTimeType() {
        override val countDownAvailable: Boolean = false
        override val inDays: Int = 0
        override val isToday: Boolean = false
        override val isTomorrow: Boolean = false
    }

    companion object {
        private const val MILLIS_IN_DAY = 1000 * 60 * 60 * 24
        private const val MILLIS_IN_HOUR = 1000 * 60 * 60
        private const val MILLIS_IN_MINUTE = 1000 * 60
        private const val MILLIS_IN_SECOND = 1000
        private const val SECONDS_IN_MINUTE = 60

        /**
         * 指定日までの残り時間を[RemainingTimeType]として返す
         */
        fun differenceAsRemainingTime(
            targetTime: DateTimeTz,
            now: DateTimeTz = DateTimeTz.nowLocal(),
        ): RemainingTimeType {
            val remainingTimeMillis = (targetTime - now).millisecondsLong
            return when {
                remainingTimeMillis < 0 -> Over
                // 期間が残っている(24時間を超えている)
                remainingTimeMillis > MILLIS_IN_DAY ->
                    InDays((remainingTimeMillis / MILLIS_IN_DAY).toInt())

                // 期間が残っている(24時間以内)
                else -> {
                    val hours = (remainingTimeMillis / (MILLIS_IN_HOUR)).toInt()
                    val minutes =
                        (remainingTimeMillis / (MILLIS_IN_MINUTE) % SECONDS_IN_MINUTE).toInt()
                    val seconds =
                        (remainingTimeMillis / MILLIS_IN_SECOND % SECONDS_IN_MINUTE).toInt()
                    InTime(hours, minutes, seconds)
                }
            }
        }
    }
}