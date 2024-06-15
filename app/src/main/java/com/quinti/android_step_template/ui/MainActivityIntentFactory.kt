package com.quinti.android_step_template.ui

import android.content.Context
import android.content.Intent
import co.kyash.model.entity.WalletId

interface MainActivityIntentFactory {
    fun create(
        context: Context,
        route: Route = Route.OpenWallet(),
        selectedWalletId: WalletId? = null,
    ): Intent
}

