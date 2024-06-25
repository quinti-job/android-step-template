package com.quinti.android_step_template.ui.screen.myprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withResumed
import com.quinti.android_step_template.kmp.domain.reactor.LoginReactor
import com.quinti.android_step_template.util.collectWithLifecycle
import com.quinti.android_step_template.ui.navigator.FragmentRouter
import com.quinti.android_step_template.ui.navigator.Router
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance() = MyProfileFragment()
    }

    private val viewModel: MyProfileViewModel by viewModels()

    @Inject
    @FragmentRouter
    lateinit var router: Router

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                CompositionLocalProvider {
                    SocialNetworkTheme {
                        MyProfileScreen(viewModel)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.reactor.event.collectWithLifecycle(this, ::handleEvent)
        lifecycleScope.launch {
            withResumed {
                viewModel.loadInitially()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    private fun handleEvent(
        event: LoginReactor.Event,
    ) {
        when (event) {
            LoginReactor.Event.DisableLoginBtn -> TODO()
            LoginReactor.Event.Login -> TODO()
            LoginReactor.Event.NavigateToChangeEmail -> TODO()
            LoginReactor.Event.NavigateToChangePassword -> TODO()
            LoginReactor.Event.NavigateToCreateAccount -> TODO()
            LoginReactor.Event.NavigateToPrivacyPolicy -> TODO()
            LoginReactor.Event.NavigateToTerms -> TODO()
            is LoginReactor.Event.ShowEmailError -> TODO()
            is LoginReactor.Event.ShowLoginError -> TODO()
            is LoginReactor.Event.ShowNetWorkError -> TODO()
            is LoginReactor.Event.ShowPasswordError -> TODO()
        }
    }
}