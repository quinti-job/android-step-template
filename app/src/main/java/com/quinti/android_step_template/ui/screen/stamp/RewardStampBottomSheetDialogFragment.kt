package com.quinti.android_step_template.ui.screen.stamp

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.quinti.android_step_template.kmp.domain.analytics.EventTracker
import com.quinti.android_step_template.kmp.domain.analytics.LocalEventTracker
import com.quinti.android_step_template.kmp.domain.analytics.Tracking
import com.quinti.android_step_template.ui.navigator.FragmentRouter
import com.quinti.android_step_template.ui.navigator.Router
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.util.KyashSupportUrls
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RewardStampBottomSheetDialogFragment : BottomSheetDialogFragment() {
    override fun getTheme(): Int = com.quinti.android_step_template.R.style.ThemeOverlay_KyashTheme_BottomSheetDialog

    private val stampCard: RewardStampBottomSheetDialogFragmentArgs by navArgs()

    @Inject
    lateinit var eventTracker: EventTracker

    @Inject
    @FragmentRouter
    lateinit var router: Router

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            val stampCardUiState = stampCard.stampCard.toEntity()
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CompositionLocalProvider(
                    LocalEventTracker provides eventTracker,
                ) {
                    SocialNetworkTheme {
                        RewardStampBottomSheetDialogScreen(
                            stampCardUiState = stampCardUiState,
                            onClose = { dismiss() },
                            onHelpClick = {
                                eventTracker.trackEvent(Tracking.Action.RewardStamp.ClickedHelp)
                                router.navigateToChromeCustomTab(
                                    KyashSupportUrls.ABOUT_REWARD_STAMP_CARD_URL,
                                    session = null,
                                )
                            },
                        )
                    }
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        setFragmentResult(REQUEST_KEY, Bundle())
    }

    companion object {
        const val REQUEST_KEY = "RewardStampBottomSheetDialogFragment_request"
    }
}