package com.quinti.android_step_template.ui.screen.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withResumed
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import co.kyash.KyashSupportUrls
import co.kyash.analytics.LocalEventTracker
import co.kyash.kig.compose.theme.SocialNetworkTheme
import co.kyash.mobile.core.featureflag.ReleaseFlag
import co.kyash.mobile.reward.top.RewardTopReactor
import co.kyash.model.analytics.EventTracker
import co.kyash.reward.R
import co.kyash.ui.core.compose.LocalOfferWallEnabled
import co.kyash.ui.navigator.Router
import co.kyash.ui.reward.spotlight.SpotlightFragment
import co.kyash.ui.reward.top.onboarding.RewardOnboardingFragment
import co.kyash.ui.reward.top.stamp.RewardStampBottomSheetDialogFragment
import co.kyash.ui.reward.transfer.CoinPrizeTransfer
import co.kyash.ui.reward.transfer.StampCardUiStateTransfer
import co.kyash.ui.util.collectWithLifecycle
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.domain.reactor.RewardTopReactor
import com.quinti.android_step_template.kmp.domain.util.collectWithLifecycle
import com.quinti.android_step_template.ui.navigator.Router
import com.quinti.android_step_template.ui.screen.spotlight.SpotlightFragment
import com.quinti.android_step_template.ui.screen.stamp.RewardStampBottomSheetDialogFragment
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.ui.transfer.CoinPrizeTransfer
import com.quinti.android_step_template.ui.transfer.StampCardUiStateTransfer
import com.quinti.android_step_template.util.KyashSupportUrls
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RewardTopFragment : Fragment() {

    private val viewModel: RewardTopViewModel by viewModels()

    @Inject
    lateinit var releaseFlag: ReleaseFlag

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var eventTracker: EventTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(RewardStampBottomSheetDialogFragment.REQUEST_KEY) { _, _ ->
            viewModel.onResume()
        }
        setFragmentResultListener(RewardOnboardingFragment.REQUEST_KEY) { _, _ ->
            viewModel.onResume()
        }
        setFragmentResultListener(SpotlightFragment.REQUEST_KEY_TAP_CONTENT) { _, bundle ->
            val type = bundle.getString(SpotlightFragment.KEY_TYPE)?.let {
                SpotlightFragment.Type.valueOf(it)
            } ?: SpotlightFragment.Type.Roulette
            when (type) {
                SpotlightFragment.Type.Roulette -> viewModel.onClickDailyRoulette()
                SpotlightFragment.Type.WelcomeChallenge -> viewModel.clickWelcomePrize()
            }
        }
        setFragmentResultListener(SpotlightFragment.REQUEST_KEY_TAP_OVERLAY) { _, _ ->
            viewModel.onTapSpotlightScrim()
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
                    LocalEventTracker provides eventTracker,
                    LocalOfferWallEnabled provides releaseFlag.isOfferwallEnabled,
                ) {
                    SocialNetworkTheme {
                        RewardTopScreen(viewModel)
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

    @Suppress("CyclomaticComplexMethod", "LongMethod")
    private fun handleEvent(event: RewardTopReactor.Event) {
        when (event) {
            is RewardTopReactor.Event.NavigateToPrizeDetail -> {
                // リワードトップが表示されているときのみ遷移する。
                // それ以外の場合はエラーになるため遷移しない。
                findNavController().runInRewardTop {
                    navigate(
                        RewardTopFragmentDirections.toPrizeDetail(
                            CoinPrizeTransfer.from(event.prize),
                            prizeId = event.prize.id,
                        ),
                    )
                }
            }

            is RewardTopReactor.Event.HandleFatalError -> {
                router.showApiFatalErrorToast(event.error)
            }

            is RewardTopReactor.Event.ShowError -> {
                router.showNonFatalErrorToast(event.error)
            }

            RewardTopReactor.Event.NavigateToDailyRoulette ->
                findNavController().runInRewardTop {
                    navigate(
                        RewardTopFragmentDirections.toDailyRoulette(),
                    )
                }

            RewardTopReactor.Event.NavigateToAppliedPrizeList ->
                findNavController().runInRewardTop {
                    navigate(
                        RewardTopFragmentDirections.toAppliedPrizeList(),
                    )
                }

            is RewardTopReactor.Event.ShowUncheckedPendingApplicationResult -> {
                // TODO: この機能はDay1では提供しないことになったのでNOPとする
            }

            RewardTopReactor.Event.NavigateToAboutReward -> router.navigateToChromeCustomTab(
                url = KyashSupportUrls.SHARE_REWARD_SECTION_URL,
                session = null,
            )

            RewardTopReactor.Event.ShowRewardOnboarding ->
                findNavController().runInRewardTop {
                    navigate(
                        RewardTopFragmentDirections.toRewardOnboarding(),
                    )
                }

            RewardTopReactor.Event.NavigateToKyashCoinDetail ->
                findNavController().runInRewardTop {
                    navigate(
                        RewardTopFragmentDirections.toKyashCoinDetail(),
                    )
                }

            is RewardTopReactor.Event.ShowStampCardDetail -> {
                findNavController().runInRewardTop {
                    navigate(
                        RewardTopFragmentDirections.toRewardStampBottomSheetDialog(
                            stampCard = StampCardUiStateTransfer.from(event.stampCard),
                        ),
                    )
                }
            }

            RewardTopReactor.Event.NavigateToPointHistory -> {
                router.navigateToPointHistory()
            }

            RewardTopReactor.Event.ShowSpotlight.Roulette -> {
                SpotlightFragment
                    .newInstance(
                        type = SpotlightFragment.Type.Roulette,
                        rectF = viewModel.rouletteRect.value,
                    )
                    .show(parentFragmentManager, "SpotlightFragmentTypeRoulette")
            }

            RewardTopReactor.Event.ShowSpotlight.WelcomeChallenge -> {
                SpotlightFragment
                    .newInstance(
                        type = SpotlightFragment.Type.WelcomeChallenge,
                        rectF = viewModel.welcomeChallengeRect.value,
                    )
                    .show(parentFragmentManager, "SpotlightFragmentTypeWelcomeChallenge")
            }

            RewardTopReactor.Event.ShowStampCardOnboarding ->
                findNavController().runInRewardTop {
                    navigate(
                        RewardTopFragmentDirections.toRewardStampOnboardingDialogFragment(),
                    )
                }

            RewardTopReactor.Event.NavigateToOfferWall ->
                findNavController().runInRewardTop {
                    navigate(
                        RewardTopFragmentDirections.toOfferWallFragment(),
                    )
                }
        }
    }
}

private fun NavController.runInRewardTop(block: NavController.() -> Unit) {
    currentDestination?.let { destination ->
        if (destination.id == R.id.coupon) {
            block(this)
        }
    }
}

