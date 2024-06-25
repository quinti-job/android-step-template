package com.quinti.android_step_template.ui.screen.home

import androidx.lifecycle.ViewModel
import com.quinti.android_step_template.kmp.domain.reactor.LoginReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.ReactorViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    reactor: LoginReactor,
) : ReactorViewModel<LoginReactor>(reactor) {

    fun loadInitially() {
        reactor.execute(LoginReactor.Action.LoadInitially)
    }

    fun onResume() {
        loadInitially()
    }

    fun onPause() {

    }

    fun onClickLogin() {
        reactor.execute(LoginReactor.Action.TapLogin)
    }

    fun onClickChangeEmail() {
        reactor.execute(LoginReactor.Action.TapChangeEmail)
    }

    fun onClickChangePassword() {
        reactor.execute(LoginReactor.Action.TapChangePassword)
    }

    fun onClickCreateAccount() {
        reactor.execute(LoginReactor.Action.TapCreateAccount)
    }

    fun onClickTerms() {
        reactor.execute(LoginReactor.Action.TapTerms)
    }

    fun onClickPrivacyPolicy() {
        reactor.execute(LoginReactor.Action.TapPrivacyPolicy)
    }
}