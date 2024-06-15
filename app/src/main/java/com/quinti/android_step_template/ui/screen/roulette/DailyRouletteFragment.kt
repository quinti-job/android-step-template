package com.quinti.android_step_template.ui.screen.roulette

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.quinti.android_step_template.kmp.domain.util.collectWithLifecycle
import com.quinti.android_step_template.kmp.domain.reactor.DailyRouletteReactor.Event
import com.quinti.android_step_template.ui.navigator.FragmentRouter
import com.quinti.android_step_template.ui.navigator.Router
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DailyRouletteFragment : Fragment() {
    private val viewModel: DailyRouletteViewModel by viewModels()

    @Inject
    @FragmentRouter
    lateinit var router: Router

//    @Inject
//    lateinit var eventTracker: EventTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.onNavigationClick()
        }
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
//                    LocalEventTracker provides eventTracker,
                ) {
                    SocialNetworkTheme {
                        DailyRouletteScreen(viewModel)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.reactor.event.collectWithLifecycle(this, ::handleEvent)
        viewModel.initialize()
    }

    private fun handleEvent(event: Event) {
        when (event) {
            Event.Close -> findNavController().popBackStack()
            is Event.HandleFatalError -> router.showApiFatalErrorToast(event.error)
            is Event.ShowError -> router.showNonFatalErrorDialog(event.error)
            Event.ShowProgress -> router.showProgressDialog()
            Event.HideProgress -> router.dismissProgressDialog()
        }
    }
}