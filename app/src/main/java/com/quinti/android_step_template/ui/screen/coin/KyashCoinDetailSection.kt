package com.quinti.android_step_template.ui.screen.coin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.api.entity.KyashCoinHistory
import com.quinti.android_step_template.ui.component.KyashCoinIcon
import com.quinti.android_step_template.ui.component.KyashSwipeRefresh
import com.quinti.android_step_template.ui.component.OnScrollEnd
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.util.formatLocaleNumericTwoDigitYearDate
import korlibs.time.DateTimeTz
import kotlin.math.absoluteValue

private val ConsumedColor = Color(0xFF0D97FF)
private val ExpiredColor = Color(0xFFFF6A56)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KyashCoinDetailSection(
    availableCoinAmount: Long,
    needShowExpiryCoin: Boolean,
    expiryCoinAmount: Long,
    histories: List<KyashCoinHistory.Available>,
    hasMore: Boolean,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
) {
    KyashSwipeRefresh(
        state = rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = onRefresh,
        ),
        refreshing = isRefreshing,
    ) {
        val lazyListState = rememberLazyListState()
        if (hasMore) {
            OnScrollEnd(
                lazyListState = lazyListState,
                onAppearLastItem = onLoadMore,
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xxs),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        ) {
            item {
                KyashCoinDetailAvailableCoinSection(
                    availableCoinAmount = availableCoinAmount,
                    needShowExpiryCoin = needShowExpiryCoin,
                    expiryCoinAmount = expiryCoinAmount,
                )
            }
            item {
                Spacer(
                    modifier = Modifier.height(SocialNetworkTheme.spacing.xxl),
                )
            }

            if (histories.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = SocialNetworkTheme.spacing.m)
                            .padding(
                                top = SocialNetworkTheme.spacing.xxl,
                                bottom = SocialNetworkTheme.spacing.m,
                            ),
                        text = stringResource(
                            id = R.string.reward_kyash_coin_detail_history_title,
                        ),
                        style = SocialNetworkTheme.typography.headlineBold,
                    )
                }
            }

            items(histories) { history ->
                KyashCoinHistoryItem(
                    modifier = Modifier.fillMaxWidth(),
                    history = history,
                )
            }
        }
    }
}

@Composable
private fun KyashCoinHistoryItem(
    history: KyashCoinHistory.Available,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.padding(
            vertical = SocialNetworkTheme.spacing.s,
            horizontal = SocialNetworkTheme.spacing.m,
        ),
    ) {
        when (history) {
            is KyashCoinHistory.Available.Consumed -> KyashCoinHistoryItemConsumed(
                history = history,
            )

            is KyashCoinHistory.Available.Expired -> KyashCoinHistoryItemExpired(
                history = history,
            )

            is KyashCoinHistory.Available.Received -> KyashCoinHistoryItemReceived(
                history = history,
            )
        }
    }
}

@Composable
private fun KyashCoinHistoryItemConsumed(
    history: KyashCoinHistory.Available.Consumed,
    modifier: Modifier = Modifier,
) {
    val dateText = formatLocaleNumericTwoDigitYearDate(history.createdAt)
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xxs),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = history.title,
                style = SocialNetworkTheme.typography.bodyBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.width(SocialNetworkTheme.spacing.m))
            Text(
                text = stringResource(
                    id = R.string.reward_kyash_coin_detail_history_amount_minus,
                    history.amount.absoluteValue,
                ),
                style = SocialNetworkTheme.typography.bodyBold,
                color = ConsumedColor,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = dateText,
                style = SocialNetworkTheme.typography.caption2,
                color = SocialNetworkTheme.colors.textColorTertiary,
            )
            Text(
                text = stringResource(
                    id = R.string.reward_kyash_coin_detail_history_consumed_subtitle,
                    history.amount.absoluteValue,
                ),
                style = SocialNetworkTheme.typography.caption2,
                color = ConsumedColor,
            )
        }
    }
}

@Composable
private fun KyashCoinHistoryItemExpired(
    history: KyashCoinHistory.Available.Expired,
    modifier: Modifier = Modifier,
) {
    val dateText = formatLocaleNumericTwoDigitYearDate(history.createdAt)
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xxs),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = history.title,
                style = SocialNetworkTheme.typography.bodyBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.width(SocialNetworkTheme.spacing.m))
            Text(
                text = stringResource(
                    id = R.string.reward_kyash_coin_detail_history_amount_minus,
                    history.amount.absoluteValue,
                ),
                style = SocialNetworkTheme.typography.bodyBold,
                color = ExpiredColor,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = dateText,
                style = SocialNetworkTheme.typography.caption2,
                color = SocialNetworkTheme.colors.textColorTertiary,
            )
            Text(
                text = stringResource(
                    id = R.string.reward_kyash_coin_detail_history_expired_subtitle,
                    history.amount.absoluteValue,
                ),
                style = SocialNetworkTheme.typography.caption2,
                color = ExpiredColor,
            )
        }
    }
}

@Composable
private fun KyashCoinHistoryItemReceived(
    history: KyashCoinHistory.Available.Received,
    modifier: Modifier = Modifier,
) {
    val dateText = formatLocaleNumericTwoDigitYearDate(history.createdAt)
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xxs),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = history.title,
                style = SocialNetworkTheme.typography.bodyBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.width(SocialNetworkTheme.spacing.m))
            Text(
                text = stringResource(
                    id = R.string.reward_kyash_coin_detail_history_amount_plus,
                    history.amount.absoluteValue,
                ),
                style = SocialNetworkTheme.typography.bodyBold,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = dateText,
                style = SocialNetworkTheme.typography.caption2,
                color = SocialNetworkTheme.colors.textColorTertiary,
            )
            Text(
                text = stringResource(
                    id = R.string.reward_kyash_coin_detail_history_received_subtitle,
                    history.amount.absoluteValue,
                ),
                style = SocialNetworkTheme.typography.caption2,
                color = SocialNetworkTheme.colors.textColorTertiary,
            )
        }
    }
}

private val RewardHistoryIconColor = Color(0xFFE3F2FF)

@Composable
private fun KyashCoinDetailAvailableCoinSection(
    availableCoinAmount: Long,
    needShowExpiryCoin: Boolean,
    expiryCoinAmount: Long,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xxs),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
        ) {
            KyashCoinIcon(
                modifier = Modifier.size(32.dp),
            )
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = stringResource(
                    id = R.string.reward_prize_detail_entry_coin_value,
                    availableCoinAmount,
                ),
                style = SocialNetworkTheme.typography.largeTitleBold,
            )
        }

        if (needShowExpiryCoin) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = stringResource(
                        id = R.string.reward_kyash_coin_detail_expiry_coin_title,
                    ),
                    style = SocialNetworkTheme.typography.body,
                    color = SocialNetworkTheme.colors.textColorSecondary,
                )
                Text(
                    text = stringResource(
                        id = R.string.reward_prize_detail_entry_coin_value,
                        expiryCoinAmount,
                    ),
                    style = SocialNetworkTheme.typography.bodyBold,
                    color = SocialNetworkTheme.colors.caution,
                )
            }
        }
    }
}

@Preview
@Composable
private fun KyashCoinDetailSectionPreview_Normal() {
    SocialNetworkTheme {
        Surface {
            KyashCoinDetailSection(
                availableCoinAmount = 1000,
                needShowExpiryCoin = true,
                expiryCoinAmount = 2050,
                isRefreshing = false,
                histories = listOf(
                    KyashCoinHistory.Available.Received(
                        amount = 1000,
                        createdAt = DateTimeTz.nowLocal(),
                        title = "タイトル",
                    ),
                ),
                hasMore = false,
                onLoadMore = {},
                onRefresh = {},
            )
        }
    }
}

@Preview
@Composable
fun KyashCoinHistoryItemConsumedPreview() {
    SocialNetworkTheme {
        Surface {
            KyashCoinHistoryItem(
                history = KyashCoinHistory.Available.Consumed(
                    amount = 1000,
                    createdAt = DateTimeTz.nowLocal(),
                    title = "タイトル",
                ),
            )
        }
    }
}

@Preview
@Composable
fun KyashCoinHistoryItemExpiredPreview() {
    SocialNetworkTheme {
        Surface {
            KyashCoinHistoryItem(
                history = KyashCoinHistory.Available.Expired(
                    amount = 1000,
                    createdAt = DateTimeTz.nowLocal(),
                    title = "タイトル",
                ),
            )
        }
    }
}

@Preview
@Composable
fun KyashCoinHistoryItemReceivedPreview() {
    SocialNetworkTheme {
        Surface {
            KyashCoinHistoryItem(
                history = KyashCoinHistory.Available.Received(
                    amount = 1000,
                    createdAt = DateTimeTz.nowLocal(),
                    title = "タイトル",
                ),
            )
        }
    }
}