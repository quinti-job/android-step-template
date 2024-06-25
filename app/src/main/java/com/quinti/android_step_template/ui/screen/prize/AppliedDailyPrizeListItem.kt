@file:OptIn(ExperimentalFoundationApi::class)

package com.quinti.android_step_template.ui.screen.prize

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeFilter
import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeStatus
import com.quinti.android_step_template.kmp.data.entity.Prize
import com.quinti.android_step_template.kmp.data.entity.RemainingTimeType
import com.quinti.android_step_template.kmp.domain.reactor.AppliedPrizeListReactor.State
import com.quinti.android_step_template.ui.component.KyashCoinWithIcon
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.util.DateFormatter
import com.quinti.android_step_template.util.toDate
import korlibs.time.DateTimeTz
import kotlinx.coroutines.launch

private const val RewardImageAspectRatio = 1f
private val CornerOverlayLabelWonBackgroundColor = Color(0xFFE3F2FF)
private val CornerOverlayLabelLostBackgroundColor = Color(0xFF808084)
private val CornerOverlayLabelLostForegroundColor = Color(0xFFFFFFFF)

private val ImageOverlayLabelAnnounceBackgroundColor = Color(0xFFF7F8F9)
private val ImageOverlayLabelAnnounceForegroundColor = Color(0xFF808084)

private val ImageOverlayLabelReceiveBackgroundColor = Color(0xFFFDE9E8)
private val ImageOverlayLabelReceiveForegroundColor = Color(0xFFFF6A56)

/**
 * 応募済みデイリーチャレンジ
 */
@Suppress("LongMethod")
@Composable
fun AppliedDailyPrizeListItem(
    formatter: DateFormatter,
    title: String,
    imagePainter: Painter,
    entryCoinAmount: Long,
    displayType: AppliedPrizeStatus,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = SocialNetworkTheme.shapes.medium,
        border = BorderStroke(1.dp, SocialNetworkTheme.colors.backgroundDark),
        modifier = modifier,
    ) {
        when (displayType) {
            is AppliedPrizeStatus.Won -> AppliedDailyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                topLabel = {
                    ApplicationResultLabel(
                        modifier = Modifier,
                        text = stringResource(id = R.string.reward_applied_prize_list_won),
                        backgroundColor = CornerOverlayLabelWonBackgroundColor,
                        color = SocialNetworkTheme.colors.primary,
                    )
                },
                bottomSection = {
                    AnnounceLabel(
                        modifier = Modifier,
                        text = remainingText(
                            remainingDays = displayType.remainingReceiveExpireAt,
                            remainingDaysTextRes =
                            R.string.reward_applied_prize_list_receive_expire_remaining_days,
                            remainingDaysCurrentTextRes =
                            R.string.reward_applied_prize_list_receive_expire_remaining_current,
                        ),
                        backgroundColor = ImageOverlayLabelReceiveBackgroundColor,
                        color = ImageOverlayLabelReceiveForegroundColor,
                    )
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.reward_applied_prize_list_receive_flow),
                        style = SocialNetworkTheme.typography.captionBold,
                        color = SocialNetworkTheme.colors.primary,
                    )
                },
                appliedDateText = {
                    DailyAppliedDateText(
                        modifier = Modifier,
                        date = displayType.appliedAt,
                        formatter = formatter,
                    )
                },
            )

            is AppliedPrizeStatus.Received -> AppliedDailyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    DailyAppliedDateText(
                        modifier = Modifier,
                        date = displayType.appliedAt,
                        formatter = formatter,
                    )
                },
                topLabel = {
                    ApplicationResultLabel(
                        modifier = Modifier,
                        text = stringResource(id = R.string.reward_applied_prize_list_won),
                        backgroundColor = CornerOverlayLabelWonBackgroundColor,
                        color = SocialNetworkTheme.colors.primary,
                    )
                },
            )

            is AppliedPrizeStatus.Lost -> AppliedDailyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    DailyAppliedDateText(
                        modifier = Modifier,
                        date = displayType.appliedAt,
                        formatter = formatter,
                    )
                },
                topLabel = {
                    ApplicationResultLabel(
                        modifier = Modifier,
                        text = stringResource(id = R.string.reward_applied_prize_list_lost),
                        backgroundColor = CornerOverlayLabelLostBackgroundColor,
                        color = CornerOverlayLabelLostForegroundColor,
                    )
                },
            )

            // ここ以下は同じレイアウトのアイテムだが、appliedAtを利用しているため個別に定義する
            is AppliedPrizeStatus.Applied -> AppliedDailyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    DailyAppliedDateText(
                        modifier = Modifier,
                        date = displayType.appliedAt,
                        formatter = formatter,
                    )
                },
            )

            is AppliedPrizeStatus.CheckExpired -> AppliedDailyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    DailyAppliedDateText(
                        modifier = Modifier,
                        date = displayType.appliedAt,
                        formatter = formatter,
                    )
                },
            )

            is AppliedPrizeStatus.ReceiveExpired -> AppliedDailyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    DailyAppliedDateText(
                        modifier = Modifier,
                        date = displayType.appliedAt,
                        formatter = formatter,
                    )
                },
            )

            is AppliedPrizeStatus.ResultNotChecked -> AppliedDailyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    DailyAppliedDateText(
                        modifier = Modifier,
                        date = displayType.appliedAt,
                        formatter = formatter,
                    )
                },
            )

            AppliedPrizeStatus.NotApplied -> {
                // NOP: 応募済み一覧ではここには来ない
            }
        }
    }
}

/**
 * 応募日テキスト
 */
@Composable
private fun DailyAppliedDateText(
    date: DateTimeTz,
    formatter: DateFormatter,
    modifier: Modifier = Modifier,
) {
    val dateText = formatter.toDateText(date.toDate())
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.reward_applied_prize_list_applied_at, dateText),
        style = SocialNetworkTheme.typography.caption1,
        color = SocialNetworkTheme.colors.textColorTertiary,
    )
}

/**
 * 残り日数を取得する
 */
@Composable
private fun remainingText(
    remainingDays: RemainingTimeType,
    @StringRes remainingDaysTextRes: Int,
    @StringRes remainingDaysCurrentTextRes: Int,
): String {
    return if (remainingDays.isToday) {
        stringResource(
            remainingDaysCurrentTextRes,
        )
    } else {
        stringResource(
            remainingDaysTextRes,
            remainingDays.inDays,
        )
    }
}

/**
 * デイリーチャレンジアイテムのカード要素
 *
 *  SLOTパターンを使って以下要素を差し込み可能としている。
 *  * 画像上の左上の要素
 *  * 画像上の左下の要素
 *  * コイン数の左側
 *
 * 各要素にはカード上の場所指定などを行うため、Modifierを引数として渡している。
 */
@Suppress("LongMethod")
@Composable
private fun AppliedDailyPrizeListItemCard(
    title: String,
    imagePainter: Painter,
    entryCoinAmount: Long,
    appliedDateText: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    topLabel: @Composable (BoxScope.() -> Unit)? = null,
    bottomSection: @Composable (RowScope.() -> Unit)? = null,
) {
    Card(
        shape = SocialNetworkTheme.shapes.medium,
        border = BorderStroke(1.dp, SocialNetworkTheme.colors.border),
        modifier = modifier,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier.size(144.dp),
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(RewardImageAspectRatio),
                        painter = imagePainter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 144.dp)
                        .padding(SocialNetworkTheme.spacing.s),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xxs),
                    ) {
                        if (topLabel != null) {
                            Box(
                                modifier = Modifier.align(Alignment.Start),
                            ) {
                                topLabel()
                            }
                        }

                        Text(
                            text = title,
                            style = SocialNetworkTheme.typography.bodyBold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Box() {
                            KyashCoinWithIcon(
                                amount = entryCoinAmount,
                            )
                        }
                    }
                    appliedDateText()
                }
            }

            if (bottomSection != null) {
                Column {
                    Divider(
                        modifier = Modifier,
                    )

                    Row(
                        Modifier
                            .align(Alignment.Start)
                            .padding(SocialNetworkTheme.spacing.s)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        bottomSection()
                    }
                }
            }
        }
    }
}

/**
 * タイトル上のラベル
 */
@Composable
private fun ApplicationResultLabel(
    text: String,
    backgroundColor: Color,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = backgroundColor,
        shape = SocialNetworkTheme.shapes.small,
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier.padding(
                vertical = SocialNetworkTheme.spacing.xxs,
                horizontal = SocialNetworkTheme.spacing.xs,
            ),
            text = text,
            style = SocialNetworkTheme.typography.caption2Bold,
            color = color,
        )
    }
}

/**
 * 左下のラベル
 */
@Composable
private fun AnnounceLabel(
    text: String,
    backgroundColor: Color,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = backgroundColor,
        modifier = modifier.clip(SocialNetworkTheme.shapes.small),
    ) {
        Text(
            modifier = Modifier.padding(
                vertical = SocialNetworkTheme.spacing.xxs,
                horizontal = SocialNetworkTheme.spacing.xs,
            ),
            text = text,
            style = SocialNetworkTheme.typography.caption2Bold,
            color = color,
            textAlign = TextAlign.Center,
        )
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
                                State.AppliedPrizeItem(
                                    DummyPrize.copy(
                                        id = "4",
                                        type = Prize.Type.Daily,
                                    ),
                                ),
                            ),
                        ),
                        prizeType = Prize.Type.Daily,
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
                        filter = AppliedPrizeFilter.Won,
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

@Preview
@Composable
private fun AppliedDailyPrizeListItemPreview() {
    val appliedPrizeStatus: AppliedPrizeStatus = AppliedPrizeStatus.NotApplied
    SocialNetworkTheme {
        AppliedDailyPrizeListItem(
            modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            formatter = DateFormatter(LocalContext.current),
            title = DummyPrize.title,
            imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
            displayType = appliedPrizeStatus,
            entryCoinAmount = DummyPrize.entryCoinAmount,
        )
    }
}

@Suppress("LongMethod")
@Preview
@Composable
private fun AppliedDailyPrizeListItemCardPreview() {
    val formatter = DateFormatter(LocalContext.current)
    SocialNetworkTheme {
        AppliedDailyPrizeListItemCard(
            title = "TEST",
            imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
            entryCoinAmount = 1000L,
            appliedDateText = {
                DailyAppliedDateText(
                    date = DateTimeTz.nowLocal(),
                    formatter = formatter,
                )
            },
            bottomSection = {
                AnnounceLabel(
                    modifier = Modifier,
                    text = remainingText(
                        remainingDays = RemainingTimeType.InDays(2),
                        remainingDaysTextRes =
                        R.string.reward_applied_prize_list_announce_expire_remaining_days,
                        remainingDaysCurrentTextRes =
                        R.string.reward_applied_prize_list_announce_expire_remaining_current,
                    ),
                    backgroundColor = ImageOverlayLabelAnnounceBackgroundColor,
                    color = ImageOverlayLabelAnnounceForegroundColor,
                )
            },
            topLabel = {
                ApplicationResultLabel(
                    modifier = Modifier,
                    text = stringResource(id = R.string.reward_applied_prize_list_won),
                    backgroundColor = CornerOverlayLabelWonBackgroundColor,
                    color = SocialNetworkTheme.colors.primary,
                )
            },
        )
    }
}

private val DummyPrize = Prize(
    id = "s",
    title = "デイリー懸賞２デイリー懸賞２デイリー懸賞２デイリー懸賞２",
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