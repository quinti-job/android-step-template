package com.quinti.android_step_template.ui.screen.roulette

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.domain.reactor.DailyRouletteReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.ui.component.KyashCloseTopAppBar
import com.quinti.android_step_template.ui.component.KyashScaffold
import com.quinti.android_step_template.ui.component.LoadingAndErrorScreen
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

internal val DailyRouletteBackgroundColor = Color(0xFFbce2ff)

@Composable
fun DailyRouletteScreen(viewModel: DailyRouletteViewModel) {
    val state by viewModel.reactor.state.collectAsStateWithLifecycle()

    DailyRouletteScreen(
        loadState = state,
        onExecuteClick = viewModel::onExecuteClick,
        onNavigationClick = viewModel::onNavigationClick,
        onCloseClick = viewModel::onCloseClick,
        onRefresh = viewModel::initialize,
        onAnimationCompleted = viewModel::onAnimationCompleted,
    )
}

@Composable
private fun DailyRouletteScreen(
    loadState: Reactor.LoadState<DailyRouletteReactor.State>,
    onExecuteClick: () -> Unit,
    onNavigationClick: () -> Unit,
    onCloseClick: () -> Unit,
    onRefresh: () -> Unit,
    onAnimationCompleted: () -> Unit,
) {
    KyashScaffold(
        backgroundColor = DailyRouletteBackgroundColor,
        topBar = {
            KyashCloseTopAppBar(
                title = stringResource(id = R.string.reward_daily_roulette_title),
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                onNavigationClick = onNavigationClick,
            )
        },
    ) { padding ->

        LoadingAndErrorScreen(
            state = loadState,
            backgroundColor = DailyRouletteBackgroundColor,
            loadedContent = {
                when (it.resultSlotState) {
                    DailyRouletteReactor.State.RouletteResultSlotState.Ready -> {
//                        TrackScreenEvent(Tracking.Screen.RewardDailyRouletteReady)
                    }

                    is DailyRouletteReactor.State.RouletteResultSlotState.Start -> {
//                        TrackScreenEvent(Tracking.Screen.RewardDailyRouletteCompletion)
                    }
                }

                DailyRouletteSection(
                    actionButtonType = it.actionButtonType,
                    bottomButtonVisibility = it.actionButtonVisibility,
                    bottomButtonEnabled = it.actionButtonEnabled,
                    messageLabelType = it.messageLabelType,
                    labelDisplayVisibility = it.messageLabelVisibility,
                    rouletteState = it.rouletteState,
                    resultSlotState = it.resultSlotState,
                    onExecuteClick = onExecuteClick,
                    onCloseClick = onCloseClick,
                    onAnimationCompleted = onAnimationCompleted,
                    modifier = Modifier.padding(padding),
                )
            },
            onRetryClick = onRefresh,
        )
    }
}

@Preview
@Composable
fun DailyRouletteScreenPreview() {
    SocialNetworkTheme {
        DailyRouletteScreen(
            loadState = Reactor.LoadState.Loaded(
                DailyRouletteReactor.State.initialized(
                    lotteryCandidates = listOf<Long>(100, 200, 300, 400, 500, 600, 700, 800, 900)
                        .shuffled(),
                ),
            ),
            onExecuteClick = {},
            onNavigationClick = {},
            onCloseClick = {},
            onRefresh = {},
            onAnimationCompleted = {},
        )
    }
}

@Preview
@Composable
fun DailyRouletteScreenPreview_Drew() {
    SocialNetworkTheme {
        DailyRouletteScreen(
            loadState = Reactor.LoadState.Loaded(
                DailyRouletteReactor.State.challenged(
                    lotteryCandidates = listOf<Long>(100, 200, 300, 400, 500, 600, 700, 800, 900)
                        .shuffled(),
                ),
            ),
            onExecuteClick = {},
            onNavigationClick = {},
            onCloseClick = {},
            onRefresh = {},
            onAnimationCompleted = {},
        )
    }
}

@Preview
@Composable
fun DailyRouletteScreenPreview_Determined() {
    SocialNetworkTheme {
        DailyRouletteScreen(
            loadState = Reactor.LoadState.Loaded(
                DailyRouletteReactor.State.initialized(
                    lotteryCandidates = listOf<Long>(100, 200, 300, 400, 500, 600, 700, 800, 900)
                        .shuffled(),
                ),
            ),
            onExecuteClick = {},
            onNavigationClick = {},
            onCloseClick = {},
            onRefresh = {},
            onAnimationCompleted = {},
        )
    }
}