package com.quinti.android_step_template.ui.screen.spotlight

import android.app.Dialog
import android.graphics.Color
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SpotlightFragment : DialogFragment() {

//    @Inject
//    lateinit var eventTracker: EventTracker

    private val RouletteRadius = 19.dp
    private val WelcomeChallengeRadius = 0.dp

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setDimAmount(0f)
        dialog.setOnShowListener {
            val window = dialog.window
            val layoutParams = window!!.attributes
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            window.attributes = layoutParams
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(true)
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val type = arguments?.getString(KEY_TYPE)?.let {
            Type.valueOf(it)
        } ?: Type.Roulette
        val rect: RectF = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(KEY_RECT, RectF::class.java) ?: RectF()
        } else {
            arguments?.getParcelable(KEY_RECT) ?: RectF()
        }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CompositionLocalProvider(
//                    LocalEventTracker provides eventTracker,
                ) {
                    SocialNetworkTheme {
                        SpotlightScreen(
                            type = type,
                            customPath = { density, rect ->
                                spotlightPath(
                                    rect = rect,
                                    radius = when (type) {
                                        Type.Roulette -> with(density) {
                                            RouletteRadius.toPx()
                                        }

                                        Type.WelcomeChallenge -> with(density) {
                                            WelcomeChallengeRadius.toPx()
                                        }
                                    },
                                )
                            },
                            spotlightAreaRectF = rect,
                            coachMarkPosition = Offset(rect.left, rect.bottom),
                            onTapContent = {
                                val bundle = Bundle().apply {
                                    putString(KEY_TYPE, type.name)
                                }
                                setFragmentResult(REQUEST_KEY_TAP_CONTENT, bundle)
                                dismiss()
                            },
                            onTapOverlay = {
                                setFragmentResult(REQUEST_KEY_TAP_OVERLAY, Bundle())
                                dismiss()
                            },
                        )
                    }
                }
            }
        }
    }

    enum class Type {
        Roulette,
        WelcomeChallenge,
    }

    companion object {
        fun newInstance(
            type: Type,
            rectF: RectF,
        ): SpotlightFragment {
            val bundle = Bundle().apply {
                putParcelable(KEY_RECT, rectF)
                putString(KEY_TYPE, type.name)
            }
            return SpotlightFragment().apply {
                arguments = bundle
            }
        }

        const val REQUEST_KEY_TAP_CONTENT = "SpotlightFragment_TapContent_request"
        const val REQUEST_KEY_TAP_OVERLAY = "SpotlightFragment_TapOverlay_request"
        const val KEY_RECT = "SpotlightFragment_Key_rect"
        const val KEY_TYPE = "SpotlightFragment_Key_type"
    }
}
