package com.quinti.android_step_template.kmp.data.api.entity

import korlibs.time.DateTimeTz

data class KyashCoinHistoryList(
    val transactions: List<KyashCoinHistory>,
    val nextLastCreatedAt: String?,
)

sealed class KyashCoinHistory {
    sealed class Available : KyashCoinHistory() {

        /**
         * 獲得・消費コイン数
         *
         * 獲得は値が正、消費または失効は値が負となる。
         */
        abstract val amount: Long

        /**
         * 履歴タイトル
         */
        abstract val title: String

        /**
         * 履歴作成日時
         */
        abstract val createdAt: DateTimeTz

        data class Received(
            override val amount: Long,
            override val createdAt: DateTimeTz,
            override val title: String,
        ) : Available()

        data class Consumed(
            override val amount: Long,
            override val createdAt: DateTimeTz,
            override val title: String,
        ) : Available()

        data class Expired(
            override val amount: Long,
            override val createdAt: DateTimeTz,
            override val title: String,
        ) : Available()
    }

    data object Unknown : KyashCoinHistory()
}

