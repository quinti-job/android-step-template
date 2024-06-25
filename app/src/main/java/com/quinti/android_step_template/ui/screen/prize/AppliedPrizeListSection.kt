@file:OptIn(ExperimentalFoundationApi::class)

package com.quinti.android_step_template.ui.screen.prize

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.entity.Prize
import com.quinti.android_step_template.kmp.domain.reactor.AppliedPrizeListReactor.State
import com.quinti.android_step_template.ui.component.KyashButton
import com.quinti.android_step_template.ui.component.KyashTab
import com.quinti.android_step_template.ui.component.KyashTabRow
import com.quinti.android_step_template.ui.component.clickableWithoutDoubleTap
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.util.DateFormatter
import korlibs.time.DateTimeTz
import kotlinx.coroutines.launch
import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeFilter
import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeStatus

@OptIn(ExperimentalFoundationApi::class)
@Suppress("LongMethod")
@Composable
fun AppliedPrizeListSection(
    formatter: DateFormatter,
    pagerState: PagerState,
    tabs: List<State.Tab>,
    onPrizeTap: (State.AppliedPrizeItem) -> Unit,
    onNextWeekly: () -> Unit,
    onNextDaily: () -> Unit,
    onTabChanged: (Int) -> Unit,
    onFilterClick: (AppliedPrizeFilter, Prize.Type) -> Unit,
) {
    // tabの表示
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        KyashTabRow(
            selectedTabIndex = pagerState.currentPage,
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = pagerState.currentPage == index
                KyashTab(
                    selected = isSelected,
                    onClick = {
                        onTabChanged(index)
                    },
                    text = {
                        TabText(
                            prizeType = tab.prizeType,
                            uncheckedCount = tab.uncheckedCount,
                            isSelected = isSelected,
                            showUncheckedIndicator = tab.showTabIndicator,
                        )
                    },
                )
            }
        }

        // スクロール部分
        HorizontalPager(
            state = pagerState,
        ) {
            val page = tabs[it]

            when (val loadableList = page.prizeList) {
                State.LoadableList.Loading -> LoadingContent()
                is State.LoadableList.Loaded -> {
                    if (loadableList.list.isEmpty()) {
                        EmptyContent(
                            modifier = Modifier.fillMaxSize(),
                        )
                    } else {
                        AppliedPrizeListContent(
                            modifier = Modifier.fillMaxSize(),
                            formatter = formatter,
                            items = loadableList.list,
                            type = page.prizeType,
                            hasMore = page.hasMore,
                            onPrizeTap = onPrizeTap,
                            onNext = {
                                when (page.prizeType) {
                                    Prize.Type.Weekly -> onNextWeekly()
                                    Prize.Type.Daily -> onNextDaily()
                                    Prize.Type.Welcome -> {
                                        // NOP(Welcomeは無限スクロールしない)
                                    }
                                }
                            },
                            filter = page.filter,
                            onFilterClick = onFilterClick,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(
    text: String,
    isSelected: Boolean,
    appliedPrizeFilter: AppliedPrizeFilter,
    prizeType: Prize.Type,
    onClick: (AppliedPrizeFilter, Prize.Type) -> Unit,
    modifier: Modifier = Modifier,
) {
    KyashButton(
        modifier = modifier,
        onClick = {
            if (!isSelected) {
                onClick(
                    appliedPrizeFilter,
                    prizeType,
                )
            }
        },
        border = if (isSelected) {
            null
        } else {
            BorderStroke(
                1.dp,
                SocialNetworkTheme.colors.textColorExtraLight,
            )
        },
        colors = if (isSelected) {
            ButtonDefaults.buttonColors(
                backgroundColor = SocialNetworkTheme.colors.primary,
                contentColor = SocialNetworkTheme.colors.textColorPrimary,
            )
        } else {
            ButtonDefaults.buttonColors(
                backgroundColor = SocialNetworkTheme.colors.background,
                contentColor = SocialNetworkTheme.colors.textColorExtraLight,
            )
        },
    ) {
        if (isSelected) {
            Text(
                text = text,
                style = SocialNetworkTheme.typography.captionBold,
                color = SocialNetworkTheme.colors.onPrimary,
            )
        } else {
            Text(
                text = text,
                style = SocialNetworkTheme.typography.caption1,
                color = SocialNetworkTheme.colors.textColorTertiary,
            )
        }
    }
}

@Composable
private fun EmptyContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.reward_prize_application_history_empty),
        )
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = SocialNetworkTheme.colors.primary,
        )
    }
}

@Composable
private fun TabText(
    prizeType: Prize.Type,
    uncheckedCount: Int,
    isSelected: Boolean,
    showUncheckedIndicator: Boolean,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // タブタイトル
        Text(
            modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            text = stringResource(id = prizeType.getText()),
            style = if (isSelected) {
                SocialNetworkTheme.typography.captionBold
            } else {
                SocialNetworkTheme.typography.captionBold
            },
            color = if (isSelected) {
                SocialNetworkTheme.colors.primary
            } else {
                SocialNetworkTheme.colors.onBackground
            },
        )
        // ウィークリータブのみ未読数を表示する
        if (prizeType == Prize.Type.Weekly && showUncheckedIndicator) {
            Box(
                modifier = Modifier
                    .background(SocialNetworkTheme.colors.caution, CircleShape)
                    .size(20.dp),
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 1.dp),
                    style = SocialNetworkTheme.typography.caption2,
                    text = uncheckedCount.toString(),
                    color = SocialNetworkTheme.colors.onError,
                )
            }
        }
    }
}

@Composable
private fun AppliedPrizeListContent(
    formatter: DateFormatter,
    type: Prize.Type,
    items: List<State.AppliedPrizeItem>,
    hasMore: Boolean,
    onPrizeTap: (State.AppliedPrizeItem) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
    filter: AppliedPrizeFilter,
    onFilterClick: (AppliedPrizeFilter, Prize.Type) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(SocialNetworkTheme.spacing.m),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            FilterButtonSection(
                modifier = Modifier
                    .padding(
                        bottom = SocialNetworkTheme.spacing.m,
                    )
                    .fillMaxWidth(),
                filter = filter,
                prizeType = type,
                onFilterClick = onFilterClick,
            )
        }
        itemsIndexed(
            items = items,
        ) { index, item ->

            // アイテム間のスペース
            if (index != 0) {
                Spacer(modifier = Modifier.size(SocialNetworkTheme.spacing.xs))
            }
            AppliedPrizeListItem(
                formatter = formatter,
                type = type,
                title = item.title,
                imagePainter = rememberAsyncImagePainter(model = item.imageUrl),
                appliedPrizeStatus = item.displayType,
                entryCoinAmount = item.entryCoinAmount,
                modifier = Modifier.clickableWithoutDoubleTap {
                    onPrizeTap(item)
                },
            )
        }

        // 追加読み込み
        if (hasMore) {
            item {
                LaunchedEffect(key1 = items.lastOrNull()?.prizeId) {
                    onNext()
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SocialNetworkTheme.spacing.m),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = SocialNetworkTheme.colors.primary,
                    )
                }
            }
        } else {
            // 下部のSystemBarのPadding
            item {
                Spacer(
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.systemBars.only(
                            WindowInsetsSides.Bottom,
                        ),
                    ),
                )
            }
        }
    }
}

@Composable
private fun FilterButtonSection(
    modifier: Modifier = Modifier,
    filter: AppliedPrizeFilter,
    prizeType: Prize.Type,
    onFilterClick: (AppliedPrizeFilter, Prize.Type) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            SocialNetworkTheme.spacing.xs,
        ),
    ) {
        FilterButton(
            text = stringResource(
                id = R.string.reward_prize_application_history_button_all,
            ),
            isSelected = filter == AppliedPrizeFilter.All,
            appliedPrizeFilter = AppliedPrizeFilter.All,
            prizeType = prizeType,
            onClick = onFilterClick,
            modifier = Modifier,
        )
        FilterButton(
            text = stringResource(
                id = R.string.reward_prize_application_history_button_won,
            ),
            isSelected = filter == AppliedPrizeFilter.Won,
            appliedPrizeFilter = AppliedPrizeFilter.Won,
            prizeType = prizeType,
            onClick = onFilterClick,
            modifier = Modifier,
        )
        Spacer(modifier = Modifier.weight(1.0f))
    }
}

@Composable
private fun AppliedPrizeListItem(
    formatter: DateFormatter,
    type: Prize.Type,
    title: String,
    imagePainter: Painter,
    entryCoinAmount: Long,
    appliedPrizeStatus: AppliedPrizeStatus,
    modifier: Modifier = Modifier,
) {
    when (type) {
        Prize.Type.Weekly -> AppliedWeeklyPrizeListItem(
            modifier = modifier,
            formatter = formatter,
            title = title,
            imagePainter = imagePainter,
            appliedPrizeStatus = appliedPrizeStatus,
            entryCoinAmount = entryCoinAmount,
        )

        Prize.Type.Daily -> AppliedDailyPrizeListItem(
            modifier = modifier,
            formatter = formatter,
            title = title,
            imagePainter = imagePainter,
            displayType = appliedPrizeStatus,
            entryCoinAmount = entryCoinAmount,
        )

        Prize.Type.Welcome -> {
            // NOP(Welcomeは表示されない)
        }
    }
}

@StringRes
private fun Prize.Type.getText(): Int {
    return when (this) {
        Prize.Type.Weekly -> R.string.reward_prize_application_history_tab_weekly
        Prize.Type.Daily -> R.string.reward_prize_application_history_tab_daily
        Prize.Type.Welcome -> R.string.reward_prize_application_history_tab_daily // Welcomeは表示されない
    }
}

// region Preview

@Suppress("LongMethod")
@Preview
@Composable
private fun AppliedPrizeListPreview() {
    SocialNetworkTheme {
        Surface {
            val pagerState = rememberPagerState { 2 }
            val scope = rememberCoroutineScope()
            AppliedPrizeListSection(
                formatter = DateFormatter(LocalContext.current),
                pagerState = pagerState,
                tabs = listOf(
                    State.Tab(
                        prizeList = State.LoadableList.Loaded(
                            listOf(
                                State.AppliedPrizeItem(DummyPrize.copy(id = "1")),
                                State.AppliedPrizeItem(DummyPrize.copy(id = "2")),
                                State.AppliedPrizeItem(DummyPrize.copy(id = "3")),
                                State.AppliedPrizeItem(DummyPrize.copy(id = "4")),
                                State.AppliedPrizeItem(DummyPrize.copy(id = "5")),
                                State.AppliedPrizeItem(DummyPrize.copy(id = "6")),
                                State.AppliedPrizeItem(DummyPrize.copy(id = "7")),
                                State.AppliedPrizeItem(DummyPrize.copy(id = "8")),
                                State.AppliedPrizeItem(DummyPrize.copy(id = "9")),
                                State.AppliedPrizeItem(DummyPrize.copy(id = "10")),
                            ),
                        ),
                        prizeType = Prize.Type.Weekly,
                        hasMore = false,
                        uncheckedCount = 1,
                        filter = AppliedPrizeFilter.All,
                    ),
                    State.Tab(
                        prizeList = State.LoadableList.Loaded(
                            listOf(
                                State.AppliedPrizeItem(
                                    DummyPrize.copy(
                                        id = "1",
                                        type = Prize.Type.Daily,
                                    ),
                                ),
                                State.AppliedPrizeItem(
                                    DummyPrize.copy(
                                        id = "2",
                                        type = Prize.Type.Daily,
                                    ),
                                ),
                                State.AppliedPrizeItem(
                                    DummyPrize.copy(
                                        id = "3",
                                        type = Prize.Type.Daily,
                                    ),
                                ),
                            ),
                        ),
                        prizeType = Prize.Type.Daily,
                        hasMore = false,
                        uncheckedCount = 0,
                        filter = AppliedPrizeFilter.All,
                    ),
                ),
                onPrizeTap = {},
                onNextWeekly = {},
                onNextDaily = {},
                onTabChanged = {
                    scope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
                onFilterClick = { _, _ -> },
            )
        }
    }
}

@Suppress("LongMethod")
@Preview
@Composable
private fun AppliedPrizeListPreview_Empty() {
    SocialNetworkTheme {
        Surface {
            val pagerState = rememberPagerState { 2 }
            val scope = rememberCoroutineScope()
            AppliedPrizeListSection(
                formatter = DateFormatter(LocalContext.current),
                pagerState = pagerState,
                tabs = listOf(
                    State.Tab(
                        prizeList = State.LoadableList.Loaded(
                            listOf(),
                        ),
                        prizeType = Prize.Type.Weekly,
                        hasMore = false,
                        uncheckedCount = 1,
                        filter = AppliedPrizeFilter.All,
                    ),
                    State.Tab(
                        prizeList = State.LoadableList.Loaded(
                            listOf(),
                        ),
                        prizeType = Prize.Type.Daily,
                        hasMore = false,
                        uncheckedCount = 0,
                        filter = AppliedPrizeFilter.All,
                    ),
                ),
                onPrizeTap = {},
                onNextWeekly = {},
                onNextDaily = {},
                onTabChanged = {
                    scope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                },
                onFilterClick = { _, _ -> },
            )
        }
    }
}

private val DummyPrize = Prize(
    id = "asopfjqohqwjfgawjfiowfa2w",
    title = "デイリー懸賞２",
    description = "詳細",
    imageUrl = "https://placehold.jp/375x294.png",
    entryCoinAmount = 10000,
    applicationStatus = Prize.ApplicationStatus.Applied(
        appliedAt = DateTimeTz.nowLocal(),
        applicationId = "applicationId",
    ),
    appliedCount = 15,
    maxWinnersCount = 20,
    type = Prize.Type.Daily,
    activationStatus = Prize.ActivationStatus.Active,
    startAt = DateTimeTz.nowLocal(),
    endAt = DateTimeTz.nowLocal(),
    announceAt = DateTimeTz.nowLocal(),
    winnersCount = 0,
    lotteryResultChecked = false,
    giftType = Prize.GiftType.Redirect,
)