package com.quinti.android_step_template.ui.screen.coin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quinti.android_step_template.kmp.domain.analytics.EventTracker
import com.quinti.android_step_template.util.KyashSupportUrls
import com.quinti.android_step_template.kmp.domain.reactor.KyashCoinDetailReactor
import com.quinti.android_step_template.kmp.domain.util.collectWithLifecycle
import com.quinti.android_step_template.ui.navigator.FragmentRouter
import com.quinti.android_step_template.ui.navigator.Router
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.kmp.domain.analytics.LocalEventTracker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class KyashCoinDetailFragment : Fragment() {

    private val viewModel: KyashCoinDetailViewModel by viewModels()

    @FragmentRouter
    @Inject
    lateinit var router: Router

    @Inject
    lateinit var eventTracker: EventTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.onNavigationClick()
        }
        viewModel.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CompositionLocalProvider(
                    LocalEventTracker provides eventTracker,
                ) {
                    SocialNetworkTheme {
                        KyashCoinDetailScreen(
                            viewModel = viewModel,
                        )
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.reactor.event.collectWithLifecycle(this, ::handleEvent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    private fun handleEvent(event: KyashCoinDetailReactor.Event) {
        when (event) {
            KyashCoinDetailReactor.Event.Back -> findNavController().popBackStack()

            KyashCoinDetailReactor.Event.ShowInformation -> router.navigateToChromeCustomTab(
                KyashSupportUrls.ABOUT_REWARD_COIN_URL,
                session = null,
            )

            is KyashCoinDetailReactor.Event.ShowError ->
                router.showNonFatalErrorDialog(event.error)

            is KyashCoinDetailReactor.Event.HandleFatalError ->
                router.showApiFatalErrorToast(event.error)
        }
    }
}