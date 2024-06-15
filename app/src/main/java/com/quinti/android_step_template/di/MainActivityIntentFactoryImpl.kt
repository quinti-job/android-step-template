package com.quinti.android_step_template.di

import android.content.Context
import android.content.Intent
import co.kyash.MainActivity
import co.kyash.model.entity.WalletId
import co.kyash.ui.main.MainActivityIntentFactory
import co.kyash.ui.main.Route
import com.quinti.android_step_template.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

private class MainActivityIntentFactoryImpl : MainActivityIntentFactory {
    override fun create(
        context: Context,
        route: Route,
        selectedWalletId: WalletId?,
    ): Intent {
        return MainActivity.createIntent(
            context = context,
            route = route,
            selectedWalletId = selectedWalletId,
        )
    }
}

@Module
@InstallIn(SingletonComponent::class)
object MainActivityFactoryModule {

    @Provides
    fun providesMainActivityFactory(): MainActivityIntentFactory {
        return MainActivityIntentFactoryImpl()
    }
}

