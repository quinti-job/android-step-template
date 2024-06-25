package com.quinti.android_step_template.ui.screen.prize

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeFilter
import com.quinti.android_step_template.kmp.data.entity.Prize
import com.quinti.android_step_template.kmp.domain.analytics.LocalEventTracker
import com.quinti.android_step_template.kmp.domain.analytics.TrackScreenEvent
import com.quinti.android_step_template.kmp.domain.analytics.TrackScreenEventV2
import com.quinti.android_step_template.kmp.domain.analytics.Tracking
import com.quinti.android_step_template.kmp.domain.reactor.AppliedPrizeListReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.ui.component.KyashBackTopAppBar
import com.quinti.android_step_template.ui.component.KyashScaffold
import com.quinti.android_step_template.ui.component.KyashSwipeRefresh
import com.quinti.android_step_template.ui.component.LoadingAndErrorScreen
import com.quinti.android_step_template.util.DateFormatter
import kotlinx.coroutines.launch

@Composable
fun AppliedPrizeListScreen(
    formatter: DateFormatter,
    viewModel: AppliedPrizeListViewModel,
) {
    val state by viewModel.reactor.state.collectAsStateWithLifecycle()
    AppliedPrizeListScreen(
        state = state,
        formatter = formatter,
        onTapItem = viewModel::onTapPrize,
        onRefresh = viewModel::onRefresh,
        onNextWeekly = viewModel::onLoadNextWeekly,
        onNextDaily = viewModel::onLoadNextDaily,
        onNavigationClick = viewModel::onNavigationClick,
        onFilterClick = viewModel::onFilterChange,
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun AppliedPrizeListScreen(
    state: Reactor.LoadState<AppliedPrizeListReactor.State>,
    formatter: DateFormatter,
    onTapItem: (AppliedPrizeListReactor.State.AppliedPrizeItem) -> Unit,
    onRefresh: () -> Unit,
    onNextWeekly: () -> Unit,
    onNextDaily: () -> Unit,
    onNavigationClick: () -> Unit,
    onFilterClick: (AppliedPrizeFilter, Prize.Type) -> Unit,
) {
    TrackScreenEvent(Tracking.Screen.CoinPrizeHistory)
    val scope = rememberCoroutineScope()
    val isRefresh =
        (state as? Reactor.LoadState.Loaded<AppliedPrizeListReactor.State>)?.value?.isRefreshing
            ?: false

    KyashScaffold(
        topBar = {
            KyashBackTopAppBar(
                title = stringResource(id = R.string.reward_prize_application_history_title),
                elevation = 0.dp,
                onNavigationClick = onNavigationClick,
            )
        },
    ) {
        KyashSwipeRefresh(
            state = rememberPullRefreshState(
                refreshing = isRefresh,
                onRefresh,
            ),
            refreshing = state is Reactor.LoadState.Loading,
        ) {
            LoadingAndErrorScreen(
                state = state,
                loadedContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .windowInsetsPadding(
                                WindowInsets.systemBars.only(
                                    WindowInsetsSides.Horizontal,
                                ),
                            ),
                    ) {
                        val tabs = listOf(
                            it.weeklyTab,
                            it.dailyTab,
                        )
                        val pagerState = rememberPagerState { tabs.size }
                        val tracker = LocalEventTracker.current
                        AppliedPrizeListSection(
                            formatter,
                            pagerState,
                            tabs = tabs,
                            onPrizeTap = onTapItem,
                            onNextWeekly = onNextWeekly,
                            onNextDaily = onNextDaily,
                            onTabChanged = { index ->
                                when (index) {
                                    0 -> tracker.trackEvent(
                                        Tracking.Action.RewardHistory.ClickedTab(
                                            "Weekly",
                                        ),
                                    )

                                    1 -> tracker.trackEvent(
                                        Tracking.Action.RewardHistory.ClickedTab(
                                            "Daily",
                                        ),
                                    )
                                }
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            onFilterClick = onFilterClick,
                        )
                    }
                },
                onRetryClick = onRefresh,
            )
        }
    }
}