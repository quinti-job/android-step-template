package com.quinti.android_step_template.ui.screen.coin

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.domain.analytics.TrackScreenEventV2
import com.quinti.android_step_template.kmp.domain.analytics.Tracking
import com.quinti.android_step_template.kmp.domain.reactor.KyashCoinDetailReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.ui.component.KyashBackTopAppBar
import com.quinti.android_step_template.ui.component.KyashScaffold
import com.quinti.android_step_template.ui.component.LoadingAndErrorScreen

@Composable
fun KyashCoinDetailScreen(
    viewModel: KyashCoinDetailViewModel,
) {
    val state by viewModel.reactor.state.collectAsStateWithLifecycle()

    TrackScreenEventV2(screen = Tracking.Screen.CoinDetail, trackOnResume = true)
    KyashCoinDetailScreen(
        state = state,
        onNavigationClick = viewModel::onNavigationClick,
        onHelpClick = viewModel::onHelpClick,
        onRefresh = viewModel::refresh,
        onLoadMore = viewModel::onLoadMore,
    )
}

@Composable
private fun KyashCoinDetailScreen(
    state: Reactor.LoadState<KyashCoinDetailReactor.State>,
    onNavigationClick: () -> Unit,
    onHelpClick: () -> Unit,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
) {
    KyashScaffold(
        topBar = {
            KyashBackTopAppBar(
                title = stringResource(id = R.string.reward_kyash_coin_detail_title),
                elevation = 0.dp,
                onNavigationClick = onNavigationClick,
                actions = {
                    IconButton(onClick = onHelpClick) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) {
        LoadingAndErrorScreen(
            state = state,
            loadedContent = {
                KyashCoinDetailSection(
                    availableCoinAmount = it.coinStatus.availableCoinAmount,
                    needShowExpiryCoin = it.needShowExpiryCoin,
                    expiryCoinAmount = it.coinStatus.expiryCoinAmount,
                    isRefreshing = it.isRefreshing,
                    histories = it.histories,
                    hasMore = it.hasMore,
                    onRefresh = onRefresh,
                    onLoadMore = onLoadMore,
                )
            },
            onRetryClick = onRefresh,
        )
    }
}

@Preview
@Composable
fun KyashCoinDetailScreenPreview() {
    KyashCoinDetailScreen(
        state = Reactor.LoadState.Loaded(
            KyashCoinDetailReactor.State(
                coinStatus = KyashCoinDetailReactor.State.CoinStatus(
                    availableCoinAmount = 11100,
                    expiryCoinAmount = 1275,
                ),
                histories = listOf(),
                nextLastCreatedAt = null,
            ),
        ),
        onNavigationClick = {},
        onHelpClick = {},
        onRefresh = {},
        onLoadMore = {},
    )
}