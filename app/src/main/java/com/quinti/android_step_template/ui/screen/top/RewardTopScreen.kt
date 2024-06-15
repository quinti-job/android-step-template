package com.quinti.android_step_template.ui.screen.top

import android.graphics.RectF
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.kyash.mobile.model.point.PointBalance
import co.kyash.mobile.model.reward.DailyRouletteStatus
import co.kyash.mobile.reward.RemainingTimeType
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.domain.reactor.RewardTopReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.ui.component.KyashScaffold
import com.quinti.android_step_template.ui.component.KyashTopAppBar
import com.quinti.android_step_template.ui.component.LoadingAndErrorScreen
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun RewardTopScreen(viewModel: RewardTopViewModel) {
    val loadState by viewModel.reactor.state.collectAsStateWithLifecycle()

//    TrackScreenEvent(screen = Tracking.Screen.RewardTop, trackOnResume = true)
    RewardTopScreen(
        loadState = loadState,
        onClickDailyRoulette = viewModel::onClickDailyRoulette,
        onRefresh = viewModel::onRefresh,
        onClickKyashCoin = viewModel::onClickKyashCoin,
        onClickPrize = viewModel::onClickPrize,
        onClickStampBanner = viewModel::onClickStampBanner,
        onAppliedPrizeListClick = viewModel::onAppliedPrizeListClick,
        onAboutRewardClick = viewModel::onAboutRewardClick,
        onClickPointBalance = viewModel::onClickPointBalance,
        setRouletteRectF = viewModel::setRouletteRect,
        setWelcomeChallengeRectF = viewModel::setWelcomeChallengeRect,
        onTapOfferWallBanner = viewModel::onTapOfferWallBanner,
    )
}

@Composable
private fun RewardTopScreen(
    loadState: Reactor.LoadState<RewardTopReactor.State>,
    onClickKyashCoin: () -> Unit,
    onClickDailyRoulette: () -> Unit,
    onClickPrize: (RewardTopReactor.State.PrizeUiState) -> Unit,
    onClickStampBanner: () -> Unit,
    onRefresh: () -> Unit,
    onAppliedPrizeListClick: () -> Unit,
    onAboutRewardClick: () -> Unit,
    onClickPointBalance: () -> Unit,
    setRouletteRectF: (RectF) -> Unit,
    setWelcomeChallengeRectF: (RectF) -> Unit,
    onTapOfferWallBanner: () -> Unit,
) {
    KyashScaffold(
        topBar = {
            KyashTopAppBar(
                title = stringResource(id = R.string.reward_title),
                elevation = 0.dp,
                actions = {
                    KyashPointButton(
                        pointBalance = (
                                loadState as?
                                        Reactor.LoadState.Loaded<RewardTopReactor.State>
                                )?.value?.pointBalance,
                        onClick = onClickPointBalance,
                    )
                    KyashCoinButton(
                        kyashCoinAmount = (
                                loadState as?
                                        Reactor.LoadState.Loaded<RewardTopReactor.State>
                                )?.value?.kyashCoinAmount,
                        onClick = onClickKyashCoin,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                },
            )
        },
    ) {
        LoadingAndErrorScreen(
            state = loadState,
            loadedContent = { state ->
                RewardTopSections(
                    weeklyPrizes = state.weeklyPrizes,
                    dailyPrizes = state.dailyPrizes,
                    comingSoonPrizes = state.weeklyUpcomingPrizes,
                    welcomePrizes = state.welcomePrizes,
                    welcomeRemainingTimeType = state.welcomeRemainingTimeType,
                    isRefreshing = state.isRefreshing,
                    onRefresh = onRefresh,
                    onClickPrize = onClickPrize,
                    onClickStampBanner = onClickStampBanner,
                    onDailyRouletteClick = onClickDailyRoulette,
                    weeklyRemainingTimeType = state.weeklyRemainingTimeType,
                    dailyRemainingTimeType = state.dailyRemainingTimeType,
                    onAppliedPrizeListClick = onAppliedPrizeListClick,
                    needShowRewardHistoryIndicator = state.needShowRewardHistoryIndicator,
                    rewardHistoryIndicatorCount = state.rewardHistoryIndicatorCount,
                    setRouletteRectF = setRouletteRectF,
                    setWelcomeChallengeRectF = setWelcomeChallengeRectF,
                    onTapOfferWallBanner = onTapOfferWallBanner,
                    onAboutRewardClick = onAboutRewardClick,
                )
            },
            onRetryClick = onRefresh,
        )
    }
}

@Preview
@Composable
private fun RewardTopScreenPreview() {
    SocialNetworkTheme {
        RewardTopScreen(
            loadState = Reactor.LoadState.Loaded(
                RewardTopReactor.State(
                    dailyRouletteStatus = DailyRouletteStatus.Available,
                    kyashCoinAmount = 1000,
                    weeklyPrizes = emptyList(),
                    dailyPrizes = emptyList(),
                    weeklyUpcomingPrizes = emptyList(),
                    welcomePrizes = emptyList(),
                    isRefreshing = false,
                    weeklyRemainingTimeType = RemainingTimeType.Gone,
                    dailyRemainingTimeType = RemainingTimeType.Gone,
                    pointBalance = PointBalance(
                        availableAmount = 1000,
                        pendingAmount = 0,
                        expiresAt = null,
                    ),
                    uncheckRewardCount = 0,
                ),
            ),
            onClickDailyRoulette = {},
            onClickKyashCoin = {},
            onClickPrize = {},
            onClickStampBanner = {},
            onRefresh = {},
            onAppliedPrizeListClick = {},
            onAboutRewardClick = {},
            onClickPointBalance = {},
            setRouletteRectF = { _ -> },
            setWelcomeChallengeRectF = { _ -> },
            onTapOfferWallBanner = { },
        )
    }
}

