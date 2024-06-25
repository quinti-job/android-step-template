package com.quinti.android_step_template.ui.screen.prize

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeStatus
import com.quinti.android_step_template.kmp.data.entity.Prize
import com.quinti.android_step_template.kmp.data.entity.RemainingTimeType
import com.quinti.android_step_template.ui.component.KyashCoinWithIcon
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.util.DateFormatter
import com.quinti.android_step_template.util.toDate
import korlibs.time.DateTimeTz

private const val RewardImageAspectRatio = 1f
private val CornerOverlayLabelWonBackgroundColor = Color(0xFFE3F2FF)
private val CornerOverlayLabelLostBackgroundColor = Color(0xFF808084)
private val CornerOverlayLabelLostForegroundColor = Color(0xFFFFFFFF)

private val ImageOverlayLabelAnnounceBackgroundColor = Color(0xFFF7F8F9)
private val ImageOverlayLabelAnnounceForegroundColor = Color(0xFF808084)

private val ImageOverlayLabelReceiveBackgroundColor = Color(0xFFFDE9E8)
private val ImageOverlayLabelReceiveForegroundColor = Color(0xFFFF6A56)

/**
 * 応募済みウィークリーチャレンジ
 */

@Suppress("LongMethod")
@Composable
fun AppliedWeeklyPrizeListItem(
    formatter: DateFormatter,
    title: String,
    imagePainter: Painter,
    entryCoinAmount: Long,
    appliedPrizeStatus: AppliedPrizeStatus,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = SocialNetworkTheme.shapes.medium,
        border = BorderStroke(1.dp, SocialNetworkTheme.colors.backgroundDark),
        modifier = modifier,
    ) {
        when (appliedPrizeStatus) {
            is AppliedPrizeStatus.Applied -> AppliedWeeklyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    WeeklyAppliedDateText(
                        modifier = Modifier,
                        date = appliedPrizeStatus.appliedAt,
                        formatter = formatter,
                    )
                },
                topLabel = {
                    ApplicationResultLabel(
                        modifier = Modifier,
                        text = remainingText(
                            remainingDays = appliedPrizeStatus.remainingAnnounceAt,
                            remainingDaysTextRes =
                            R.string.reward_applied_prize_list_announce_expire_remaining_days,
                            remainingDaysCurrentTextRes =
                            R.string.reward_applied_prize_list_announce_expire_remaining_current,
                        ),
                        backgroundColor = ImageOverlayLabelAnnounceBackgroundColor,
                        color = ImageOverlayLabelAnnounceForegroundColor,
                    )
                },
            )

            is AppliedPrizeStatus.CheckExpired -> AppliedWeeklyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    WeeklyAppliedDateText(
                        modifier = Modifier,
                        date = appliedPrizeStatus.appliedAt,
                        formatter = formatter,
                    )
                },
            )

            is AppliedPrizeStatus.Lost -> AppliedWeeklyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    WeeklyAppliedDateText(
                        modifier = Modifier,
                        date = appliedPrizeStatus.appliedAt,
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

            is AppliedPrizeStatus.ReceiveExpired -> AppliedWeeklyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    WeeklyAppliedDateText(
                        modifier = Modifier,
                        date = appliedPrizeStatus.appliedAt,
                        formatter = formatter,
                    )
                },
            )

            is AppliedPrizeStatus.Received -> AppliedWeeklyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    WeeklyAppliedDateText(
                        modifier = Modifier,
                        date = appliedPrizeStatus.appliedAt,
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

            is AppliedPrizeStatus.ResultNotChecked -> AppliedWeeklyPrizeListItemCard(
                modifier = Modifier,
                title = title,
                imagePainter = imagePainter,
                entryCoinAmount = entryCoinAmount,
                appliedDateText = {
                    WeeklyAppliedDateText(
                        modifier = Modifier,
                        date = appliedPrizeStatus.appliedAt,
                        formatter = formatter,
                    )
                },
                bottomSection = {
                    AnnounceLabel(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = remainingText(
                            remainingDays = appliedPrizeStatus.remainingCheckExpireAt,
                            remainingDaysTextRes =
                            R.string.reward_applied_prize_list_check_expire_remaining_days,
                            remainingDaysCurrentTextRes =
                            R.string.reward_applied_prize_list_check_expire_remaining_current,
                        ),
                        backgroundColor = ImageOverlayLabelReceiveBackgroundColor,
                        color = ImageOverlayLabelReceiveForegroundColor,
                    )
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = stringResource(id = R.string.reward_applied_prize_list_check_result),
                        style = SocialNetworkTheme.typography.captionBold,
                        color = SocialNetworkTheme.colors.primary,
                    )
                },
            )

            is AppliedPrizeStatus.Won -> AppliedWeeklyPrizeListItemCard(
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
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        text = remainingText(
                            remainingDays = appliedPrizeStatus.remainingReceiveExpireAt,
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
                    WeeklyAppliedDateText(
                        modifier = Modifier,
                        date = appliedPrizeStatus.appliedAt,
                        formatter = formatter,
                    )
                },
            )

            AppliedPrizeStatus.NotApplied -> {
                // NOP: 応募済み一覧では表示しない
            }
        }
    }
}

/**
 * 応募日テキスト
 */
@Composable
private fun WeeklyAppliedDateText(
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
 * ウィークリーチャレンジアイテムのカード要素
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
private fun AppliedWeeklyPrizeListItemCard(
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

                        Box {
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
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        bottomSection()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AppliedWeeklyPrizeListItemPreview() {
    val appliedPrizeStatus: AppliedPrizeStatus = AppliedPrizeStatus.NotApplied
    SocialNetworkTheme {
        AppliedWeeklyPrizeListItem(
            modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            formatter = DateFormatter(LocalContext.current),
            title = DummyPrize.title,
            imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
            appliedPrizeStatus = appliedPrizeStatus,
            entryCoinAmount = DummyPrize.entryCoinAmount,
        )
    }
}

@Suppress("LongMethod")
@Preview
@Composable
private fun AppliedWeeklyPrizeListItemCardPreview() {
    val formatter = DateFormatter(LocalContext.current)
    SocialNetworkTheme {
        AppliedWeeklyPrizeListItemCard(
            title = "TEST",
            imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
            entryCoinAmount = 1000L,
            appliedDateText = {
                WeeklyAppliedDateText(
                    date = DateTimeTz.nowLocal(),
                    formatter = formatter,
                )
            },
            bottomSection = {
                AnnounceLabel(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
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
    title = "デイリー懸賞２デイリー懸賞２デイリー懸賞２デイリー懸賞２デイリー懸賞２デイリー懸賞２",
    description = "詳細",
    imageUrl = "https://placehold.jp/375x294.png",
    entryCoinAmount = 10000,
    applicationStatus = Prize.ApplicationStatus.Applied(
        appliedAt = DateTimeTz.nowLocal(),
        applicationId = "applicationId",
    ),
    appliedCount = 15,
    maxWinnersCount = 20,
    type = Prize.Type.Weekly,
    activationStatus = Prize.ActivationStatus.Active,
    startAt = DateTimeTz.nowLocal(),
    endAt = DateTimeTz.nowLocal(),
    announceAt = DateTimeTz.nowLocal(),
    winnersCount = 0,
    lotteryResultChecked = false,
    giftType = Prize.GiftType.Redirect,
)