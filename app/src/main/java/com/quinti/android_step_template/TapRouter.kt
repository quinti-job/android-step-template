package com.quinti.android_step_template

import android.os.Parcelable
import androidx.annotation.IdRes
import kotlinx.parcelize.Parcelize

sealed class TabRoute : Parcelable {
    @Parcelize
    data class OpenWallet : TabRoute()

    @Parcelize
    data object OpenCoupon : TabRoute()

    @Parcelize
    data object OpenCategory : TabRoute()

    @Parcelize
    data object OpenSendRequest : TabRoute()

    @Parcelize
    data object OpenAccount : TabRoute()

    @get:IdRes
    val tabId: Int
        get() = when (this) {
            is OpenWallet -> R.id.wallet_nav_graph
            OpenCoupon -> R.id.coupon_nav_graph
            OpenCategory -> R.id.household_nav_graph
            OpenSendRequest -> R.id.send_request_nav_graph
            OpenAccount -> R.id.account_nav_graph
        }
}