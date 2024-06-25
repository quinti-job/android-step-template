package com.quinti.android_step_template.kmp.data.entity

sealed class ReceivePrizeType {
    data class Redirect(val url: String) : ReceivePrizeType()
    data object Unknown : ReceivePrizeType()
}