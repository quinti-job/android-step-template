package com.quinti.android_step_template.ui.screen.top

import android.graphics.RectF
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.entity.Prize
import com.quinti.android_step_template.kmp.data.entity.PrizeApplicableStatus
import com.quinti.android_step_template.kmp.data.entity.RemainingTimeType
import com.quinti.android_step_template.kmp.domain.analytics.LocalEventTracker
import com.quinti.android_step_template.kmp.domain.analytics.Tracking
import com.quinti.android_step_template.kmp.domain.reactor.RewardTopReactor
import com.quinti.android_step_template.ui.component.DisabledKyashCoinIcon
import com.quinti.android_step_template.ui.component.DisabledPointIcon
import com.quinti.android_step_template.ui.component.KyashCoinIcon
import com.quinti.android_step_template.ui.component.KyashCoinWithIcon
import com.quinti.android_step_template.ui.component.KyashPointWithIcon
import com.quinti.android_step_template.ui.component.KyashSwipeRefresh
import com.quinti.android_step_template.ui.component.PointBalance
import com.quinti.android_step_template.ui.component.PrizeApplicationStatusBadge
import com.quinti.android_step_template.ui.component.clickableWithoutDoubleTap
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.util.PreviewFontScales
import com.quinti.android_step_template.util.getRemainingTimeText
import korlibs.time.DateTime
import korlibs.time.DateTimeTz
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.take

@Composable
fun KyashCoinButton(
    kyashCoinAmount: Long?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .clip(RoundedCornerShape(SocialNetworkTheme.spacing.xxl))
            .clickableWithoutDoubleTap(
                onClick = onClick,
            )
            .background(
                color = SocialNetworkTheme.colors.backgroundVariant,
                shape = RoundedCornerShape(SocialNetworkTheme.spacing.xxl),
            ),
    ) {
        KyashCoinWithIcon(
            amount = kyashCoinAmount,
            modifier = Modifier
                .padding(
                    horizontal = SocialNetworkTheme.spacing.m,
                    vertical = SocialNetworkTheme.spacing.xs
                ),
        )
    }
}

@Composable
fun KyashPointButton(
    pointBalance: PointBalance?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .clip(RoundedCornerShape(SocialNetworkTheme.spacing.xxl))
            .clickableWithoutDoubleTap(
                onClick = onClick,
            )
            .background(
                color = SocialNetworkTheme.colors.backgroundVariant,
                shape = RoundedCornerShape(SocialNetworkTheme.spacing.xxl),
            ),
    ) {
        KyashPointWithIcon(
            amount = pointBalance,
            modifier = Modifier
                .padding(
                    horizontal = SocialNetworkTheme.spacing.m,
                    vertical = SocialNetworkTheme.spacing.xs
                ),
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Suppress("LongMethod")
@Composable
fun RewardTopSections(
    weeklyPrizes: List<RewardTopReactor.State.PrizeUiState>,
    dailyPrizes: List<RewardTopReactor.State.PrizeUiState>,
    comingSoonPrizes: List<RewardTopReactor.State.PrizeUiState>,
    welcomePrizes: List<RewardTopReactor.State.PrizeUiState>,
    welcomeRemainingTimeType: RemainingTimeType,
    weeklyRemainingTimeType: RemainingTimeType,
    dailyRemainingTimeType: RemainingTimeType,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onDailyRouletteClick: () -> Unit,
    onAppliedPrizeListClick: () -> Unit,
    onClickPrize: (prize: RewardTopReactor.State.PrizeUiState) -> Unit,
    onClickStampBanner: () -> Unit,
    needShowRewardHistoryIndicator: Boolean,
    rewardHistoryIndicatorCount: Int,
    setRouletteRectF: (RectF) -> Unit,
    setWelcomeChallengeRectF: (RectF) -> Unit,
    onTapOfferWallBanner: () -> Unit,
    onAboutRewardClick: () -> Unit,
) {
    KyashSwipeRefresh(
        state = rememberPullRefreshState(refreshing = isRefreshing, onRefresh),
        refreshing = isRefreshing,
    ) {
        val carouselItems = buildList {
            add(
                RewardTopCarouselItem(
                    image = painterResource(
                        id = R.drawable.reward_img_daily_roulette_banner,
                    ),
                    onClick = onDailyRouletteClick,
                    needGetBannerRectF = true,
                ),
            )
            add(
                RewardTopCarouselItem(
                    image = painterResource(
                        id = R.drawable.reward_img_applied_reward_banner,
                    ),
                    onClick = onAppliedPrizeListClick,
                    needShowRewardHistoryIndicator = needShowRewardHistoryIndicator,
                    rewardHistoryIndicatorCount = rewardHistoryIndicatorCount,
                    needGetBannerRectF = false,
                ),
            )
        }

        LazyColumn(
            horizontalAlignment = Alignment.Start,
            contentPadding = PaddingValues(vertical = SocialNetworkTheme.spacing.m),
        ) {
            if (carouselItems.isNotEmpty()) {
                item {
                    TopCarouselSection(
                        items = carouselItems,
                        modifier = Modifier.fillMaxWidth(),
                        setRouletteRect = setRouletteRectF,
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(SocialNetworkTheme.spacing.xs)) }
            item {
                OfferWallBanner(
                    modifier = Modifier,
                    onClick = onTapOfferWallBanner,
                )
            }
            item { Spacer(modifier = Modifier.height(SocialNetworkTheme.spacing.xl)) }

            if (welcomePrizes.isNotEmpty()) {
                item {
                    WelcomePrizesSection(
                        prizes = welcomePrizes,
                        remainingTimeType = welcomeRemainingTimeType,
                        onPrizeClick = onClickPrize,
                        modifier = Modifier.fillMaxWidth(),
                        setWelcomeChallengeRectF = setWelcomeChallengeRectF,
                    )
                }
                item { Spacer(modifier = Modifier.height(SocialNetworkTheme.spacing.xl)) }
            }

            if (weeklyPrizes.isNotEmpty()) {
                item {
                    WeeklyPrizesSection(
                        prizes = weeklyPrizes,
                        remainingTimeType = weeklyRemainingTimeType,
                        onPrizeClick = onClickPrize,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                item { Spacer(modifier = Modifier.height(SocialNetworkTheme.spacing.xl)) }
            }

            if (dailyPrizes.isNotEmpty()) {
                item {
                    DailyPrizesSection(
                        prizes = dailyPrizes,
                        remainingTimeType = dailyRemainingTimeType,
                        onPrizeClick = onClickPrize,
                        onStampBannerClick = onClickStampBanner,
                    )
                }
                item { Spacer(modifier = Modifier.height(SocialNetworkTheme.spacing.xl)) }
            }

            if (comingSoonPrizes.isNotEmpty()) {
                item {
                    ComingSoonPrizesSection(
                        prizes = comingSoonPrizes,
                        onPrizeClick = onClickPrize,
                    )
                }
            }

            item {
                AboutRewardSection(
                    onAboutRewardClick = onAboutRewardClick,
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun TopCarouselSection(
    items: List<RewardTopCarouselItem>,
    modifier: Modifier = Modifier,
    setRouletteRect: (RectF) -> Unit,
) {
    Row(
        modifier = modifier.padding(
            horizontal = SocialNetworkTheme.spacing.m,
        ),
        horizontalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
    ) {
        val statusBarHeight = WindowInsets
            .statusBars
            .asPaddingValues()
            .calculateTopPadding() * LocalDensity.current.density

        for (item in items) {
            Box(
                modifier = Modifier
                    .weight(1f),
            ) {
                Image(
                    painter = item.image,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableWithoutDoubleTap(onClick = item.onClick)
                        .onGloballyPositioned {
                            if (item.needGetBannerRectF) {
                                val left = it.positionInWindow().x
                                val top = it.positionInWindow().y - statusBarHeight.value
                                val right = left + it.size.width
                                val bottom = top + it.size.height
                                setRouletteRect(
                                    RectF(left, top, right, bottom),
                                )
                            }
                        },
                )
                if (item.needShowRewardHistoryIndicator) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(
                                x = SocialNetworkTheme.spacing.xs,
                                y = -SocialNetworkTheme.spacing.xs
                            )
                            .background(
                                color = SocialNetworkTheme.colors.caution,
                                shape = CircleShape,
                            )
                            .size(24.dp),
                    ) {
                        Text(
                            text = "${item.rewardHistoryIndicatorCount}",
                            style = SocialNetworkTheme.typography.captionBold,
                            color = SocialNetworkTheme.colors.onPrimary,
                            modifier = Modifier
                                .align(Alignment.Center),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OfferWallBanner(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Image(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = SocialNetworkTheme.spacing.m,
            )
            .clickableWithoutDoubleTap(onClick = onClick),
        painter = painterResource(
            id = R.drawable.reward_img_offerwall_banner,
        ),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
    )
}

private const val TrackingHorizontalScrollThreshold = 200

@Composable
private fun WeeklyPrizesSection(
    prizes: List<RewardTopReactor.State.PrizeUiState>,
    remainingTimeType: RemainingTimeType,
    onPrizeClick: (RewardTopReactor.State.PrizeUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberScrollState()
    val tracker = LocalEventTracker.current

    // ウィークリーチャレンジの横スクロールを一定DP以上した場合、1回のみイベントを送信する
    LaunchedEffect(state) {
        snapshotFlow { state.value }
            .filter { it >= TrackingHorizontalScrollThreshold }
            .take(1)
            .collect {
                tracker.trackEvent(Tracking.Action.RewardTop.SwipeWeeklyChallenge)
            }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
        modifier = modifier,
    ) {
        PrizesHeaderSectionLabel(
            modifier = Modifier
                .padding(horizontal = SocialNetworkTheme.spacing.m),
            title = stringResource(id = R.string.reward_weekly_prizes_label),
            remainingTimeType = remainingTimeType,
            description = stringResource(id = R.string.reward_weekly_prizes_description),
        )

        Row(
            modifier = modifier
                .horizontalScroll(state)
                .padding(SocialNetworkTheme.spacing.m),
            horizontalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
        ) {
            for (prize in prizes) {
                WeeklyPrizeItem(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickableWithoutDoubleTap {
                            onPrizeClick(prize)
                        },
                    isUpcoming = false,
                    prize = prize,
                )
            }
        }
    }
}

@Composable
private fun DailyPrizesSection(
    prizes: List<RewardTopReactor.State.PrizeUiState>,
    remainingTimeType: RemainingTimeType,
    onPrizeClick: (RewardTopReactor.State.PrizeUiState) -> Unit,
    onStampBannerClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
        modifier = modifier,
    ) {
        PrizesHeaderSectionLabel(
            modifier = Modifier
                .padding(horizontal = SocialNetworkTheme.spacing.m),
            title = stringResource(id = R.string.reward_daily_prizes_label),
            remainingTimeType = remainingTimeType,
            description = stringResource(id = R.string.reward_daily_prizes_description),
        )

        Column(
            modifier = modifier.padding(vertical = SocialNetworkTheme.spacing.xs),
            verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
        ) {
            for (prize in prizes) {
                DailyPrizeItem(
                    modifier = Modifier
                        .padding(horizontal = SocialNetworkTheme.spacing.m)
                        .clickableWithoutDoubleTap {
                            onPrizeClick(prize)
                        },
                    prize = prize,
                )
            }
        }
        StampBanner(onClickStampBanner = onStampBannerClick)
    }
}

@Composable
private fun WelcomePrizesSection(
    prizes: List<RewardTopReactor.State.PrizeUiState>,
    remainingTimeType: RemainingTimeType,
    onPrizeClick: (RewardTopReactor.State.PrizeUiState) -> Unit,
    modifier: Modifier = Modifier,
    setWelcomeChallengeRectF: (RectF) -> Unit,
) {
    val statusBarHeight = WindowInsets
        .statusBars.asPaddingValues()
        .calculateTopPadding() * LocalDensity.current.density

    Column(
        verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
        modifier = modifier
            .onGloballyPositioned {
                val left = it.positionInWindow().x
                val top = it.positionInWindow().y - statusBarHeight.value
                val right = left + it.size.width
                val bottom = top + it.size.height
                setWelcomeChallengeRectF(
                    RectF(left, top, right, bottom),
                )
            },
    ) {
        PrizesHeaderSectionLabel(
            modifier = Modifier
                .padding(horizontal = SocialNetworkTheme.spacing.m),
            title = stringResource(id = R.string.reward_welcome_prizes_label),
            remainingTimeType = remainingTimeType,
            description = stringResource(id = R.string.reward_welcome_prizes_description),
        )

        Column(
            modifier = modifier.padding(vertical = SocialNetworkTheme.spacing.xs),
            verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
        ) {
            for (prize in prizes) {
                DailyPrizeItem(
                    modifier = Modifier
                        .padding(horizontal = SocialNetworkTheme.spacing.m)
                        .clickableWithoutDoubleTap {
                            onPrizeClick(prize)
                        },
                    prize = prize,
                )
            }
        }
    }
}

@Composable
private fun StampBanner(
    onClickStampBanner: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .padding(horizontal = SocialNetworkTheme.spacing.m)
            .padding(top = SocialNetworkTheme.spacing.m)
            .background(
                color = StampBannerBgColor,
                shape = SocialNetworkTheme.shapes.medium,
            )
            .clickable(onClick = onClickStampBanner),
    ) {
        Column(
            modifier = Modifier
                .padding(start = SocialNetworkTheme.spacing.m)
                .padding(end = SocialNetworkTheme.spacing.s),
        ) {
            Text(
                text = stringResource(id = R.string.reward_stamp_banner_title),
                style = SocialNetworkTheme.typography.bodyBold,
                color = SocialNetworkTheme.colors.textColorPrimary,
            )
            Text(
                text = stringResource(id = R.string.reward_stamp_banner_description),
                style = SocialNetworkTheme.typography.caption1,
                color = SocialNetworkTheme.colors.textColorSecondary,
            )
        }
        StampBannerStampIconRow()
    }
}

@Composable
private fun StampBannerStampIconRow() {
    Box {
        // 4つ目のアイコン
        DisabledPointCircleIcon()
        // 3つ目のアイコン
        DisabledKyashCoinIconCircle(offset = SocialNetworkTheme.spacing.xxxl)
        // 2つ目のアイコン
        DisabledKyashCoinIconCircle(offset = SocialNetworkTheme.spacing.l)
        // 1つ目のアイコン
        KyashCoinIconCircle()
    }
}

@Composable
private fun KyashCoinIconCircle() {
    Box(
        modifier = Modifier
            .size(StampIconCircleSize)
            .background(SocialNetworkTheme.colors.primary, CircleShape)
            .padding(SocialNetworkTheme.spacing.xxxs)
            .border(
                StampIconCircleInnerBorderSize,
                SocialNetworkTheme.colors.onPrimary,
                shape = CircleShape,
            ),
    ) {
        KyashCoinIcon(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun DisabledKyashCoinIconCircle(
    offset: Dp,
) {
    Box(
        modifier = Modifier
            .offset(x = offset)
            .size(SocialNetworkTheme.spacing.xxl)
            .background(
                SocialNetworkTheme.colors.onPrimary,
                shape = CircleShape,
            )
            .border(
                StampIconCircleBorderSize,
                SocialNetworkTheme.colors.backgroundDark,
                shape = CircleShape,
            ),
    ) {
        Surface(
            modifier = Modifier
                .padding(SocialNetworkTheme.spacing.xxxs)
                .size(StampIconCircleInnerBorderWidth),
            shape = CircleShape,
        ) {
            val pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(DashLength, SpaceLength),
                0f,
            )
            val dottedLineColor = SocialNetworkTheme.colors.backgroundDark
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .background(SocialNetworkTheme.colors.onPrimary),
            ) {
                drawCircle(
                    color = dottedLineColor,
                    radius = size.minDimension / 2,
                    style = Stroke(
                        width = StampIconCircleBorderSize.toPx(),
                        pathEffect = pathEffect,
                    ),
                )
            }
        }
        DisabledKyashCoinIcon(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun DisabledPointCircleIcon() {
    Box(
        modifier = Modifier
            .offset(x = DisabledPointCircleIconOffset)
            .size(SocialNetworkTheme.spacing.xxl)
            .background(
                SocialNetworkTheme.colors.onPrimary,
                shape = CircleShape,
            )
            .border(
                StampIconCircleBorderSize,
                SocialNetworkTheme.colors.backgroundDark,
                shape = CircleShape,
            ),
    ) {
        Surface(
            modifier = Modifier
                .padding(SocialNetworkTheme.spacing.xxxs)
                .size(StampIconCircleInnerBorderWidth),
            shape = CircleShape,
        ) {
            val pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(DashLength, SpaceLength),
                0f,
            )
            val dottedLineColor = SocialNetworkTheme.colors.backgroundDark
            SocialNetworkTheme.shapes.medium
            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .background(SocialNetworkTheme.colors.onPrimary),
            ) {
                drawCircle(
                    color = dottedLineColor,
                    radius = size.minDimension / 2,
                    style = Stroke(
                        width = StampIconCircleBorderSize.toPx(),
                        pathEffect = pathEffect,
                    ),
                )
            }
        }
        DisabledPointIcon(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
private fun PrizesHeaderSectionLabel(
    title: String,
    remainingTimeType: RemainingTimeType,
    description: String?,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
        modifier = modifier,
    ) {
        Row {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = title,
                style = SocialNetworkTheme.typography.title3Bold,
            )
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painterResource(id = R.drawable.reward_ic_remain_timer),
                    contentDescription = null,
                    tint = SocialNetworkTheme.colors.caution,
                )
                Text(
                    text = remainingTimeType.getRemainingTimeText(context),
                    style = SocialNetworkTheme.typography.bodyBold,
                    color = SocialNetworkTheme.colors.caution,
                )
            }
        }
        if (description != null) {
            Text(
                description,
                style = SocialNetworkTheme.typography.paragraph2,
                color = SocialNetworkTheme.colors.textColorSecondary,
            )
        }
    }
}

@Composable
private fun ComingSoonPrizesSection(
    prizes: List<RewardTopReactor.State.PrizeUiState>,
    onPrizeClick: (RewardTopReactor.State.PrizeUiState) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.m),
        modifier = modifier,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SocialNetworkTheme.spacing.m),
            text = stringResource(id = R.string.reward_coming_soon_prizes_label),
            style = SocialNetworkTheme.typography.title3Bold,
        )

        Row(
            modifier = modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = SocialNetworkTheme.spacing.m)
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
        ) {
            for (prize in prizes) {
                WeeklyPrizeItem(
                    modifier = Modifier
                        .fillMaxHeight()
                        .clickableWithoutDoubleTap {
                            onPrizeClick(prize)
                        },
                    isUpcoming = true,
                    prize = prize,
                )
            }
        }
    }
}

private val prizeImageWidth = 280.dp

@Composable
private fun WeeklyPrizeItem(
    prize: RewardTopReactor.State.PrizeUiState,
    isUpcoming: Boolean,
    modifier: Modifier = Modifier,
) {
    WeeklyPrizeItem(
        imagePainter = rememberAsyncImagePainter(model = prize.imageUrl),
        title = prize.title,
        maxWinners = prize.maxWinners,
        entryCoinRatio = prize.possessionToEntryCoinRatio,
        needShowEntryCoinRatio = prize.needShowPossessionToEntryCoinRatio,
        entryCoinAmount = prize.entryCoinAmount,
        applicableStatus = prize.applicableStatus,
        isUpcoming = isUpcoming,
        modifier = modifier,
    )
}

@Suppress("LongMethod")
@Composable
private fun WeeklyPrizeItem(
    imagePainter: Painter,
    title: String,
    maxWinners: Prize.MaxWinners,
    entryCoinRatio: Float,
    needShowEntryCoinRatio: Boolean,
    applicableStatus: PrizeApplicableStatus,
    entryCoinAmount: Long,
    isUpcoming: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.width(prizeImageWidth),
        elevation = 0.dp,
        shape = SocialNetworkTheme.shapes.large,
        border = BorderStroke(width = 1.dp, color = SocialNetworkTheme.colors.backgroundDark),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(PrizeCardImageAspectRatio),
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SocialNetworkTheme.spacing.s),
                color = SocialNetworkTheme.colors.surface,
            ) {
                Column(
                    modifier = Modifier.wrapContentHeight(),
                ) {
                    Text(
                        text = title,
                        style = SocialNetworkTheme.typography.headlineBold,
                        maxLines = 2,
                        minLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )

                    if (maxWinners is Prize.MaxWinners.Limited) {
                        Text(
                            text = stringResource(
                                id = R.string.reward_prize_detail_sub_title_max_winners,
                                maxWinners.count,
                            ),
                            style = SocialNetworkTheme.typography.caption1,
                            color = SocialNetworkTheme.colors.textColorSecondary,
                        )
                    }

                    if (needShowEntryCoinRatio) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .padding(
                                    top = SocialNetworkTheme.spacing.m,
                                    bottom = SocialNetworkTheme.spacing.xs,
                                )
                                .fillMaxWidth()
                                .height(SocialNetworkTheme.spacing.xs),
                            progress = entryCoinRatio,
                            strokeCap = StrokeCap.Round,
                            color = SocialNetworkTheme.colors.primary,
                            backgroundColor = SocialNetworkTheme.colors.backgroundVariant,
                        )
                    } else if (isUpcoming) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(SocialNetworkTheme.spacing.xxs),
                        )
                    } else {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = SocialNetworkTheme.spacing.m,
                                    bottom = SocialNetworkTheme.spacing.xs,
                                )
                                .height(SocialNetworkTheme.spacing.xs),
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = SocialNetworkTheme.spacing.xs)
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xs),
                    ) {
                        PrizeApplicationStatusBadge(
                            modifier = Modifier.weight(1f),
                            prizeStatusLabel = applicableStatus,
                        )
                        KyashCoinWithIcon(entryCoinAmount)
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyPrizeItem(
    prize: RewardTopReactor.State.PrizeUiState,
    modifier: Modifier = Modifier,
) {
    DailyPrizeItem(
        imagePainter = rememberAsyncImagePainter(model = prize.imageUrl),
        title = prize.title,
        maxWinners = prize.maxWinners,
        entryCoinRatio = prize.possessionToEntryCoinRatio,
        entryCoinAmount = prize.entryCoinAmount,
        needShowEntryCoinRatio = prize.needShowPossessionToEntryCoinRatio,
        applicableStatus = prize.applicableStatus,
        modifier = modifier,
    )
}

@Suppress("LongMethod")
@Composable
private fun DailyPrizeItem(
    imagePainter: Painter,
    title: String,
    maxWinners: Prize.MaxWinners,
    entryCoinRatio: Float,
    needShowEntryCoinRatio: Boolean,
    applicableStatus: PrizeApplicableStatus,
    entryCoinAmount: Long,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        elevation = 0.dp,
        shape = SocialNetworkTheme.shapes.medium,
        border = BorderStroke(width = 1.dp, color = SocialNetworkTheme.colors.backgroundDark),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
        ) {
            Image(
                modifier = Modifier
                    .weight(1.25f)
                    .aspectRatio(1f)
                    .fillMaxHeight(),
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .padding(SocialNetworkTheme.spacing.s),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = title,
                    style = SocialNetworkTheme.typography.bodyBold,
                    maxLines = 2,
                    minLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .fillMaxHeight(),
                ) {
                    if (maxWinners is Prize.MaxWinners.Limited) {
                        Text(
                            text = stringResource(
                                id = R.string.reward_prize_detail_sub_title_max_winners,
                                maxWinners.count,
                            ),
                            style = SocialNetworkTheme.typography.caption1,
                            color = SocialNetworkTheme.colors.textColorSecondary,
                        )
                    }
                    if (needShowEntryCoinRatio) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .padding(
                                    top = SocialNetworkTheme.spacing.m,
                                    bottom = SocialNetworkTheme.spacing.m,
                                )
                                .fillMaxWidth()
                                .height(6.dp),
                            progress = entryCoinRatio,
                            strokeCap = StrokeCap.Round,
                            color = SocialNetworkTheme.colors.primary,
                            backgroundColor = SocialNetworkTheme.colors.backgroundVariant,
                        )
                    } else {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = SocialNetworkTheme.spacing.m,
                                    bottom = SocialNetworkTheme.spacing.m,
                                )
                                .height(6.dp),
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        PrizeApplicationStatusBadge(
                            modifier = Modifier.weight(1f),
                            prizeStatusLabel = applicableStatus,
                        )
                        Box(
                            modifier = Modifier.padding(horizontal = SocialNetworkTheme.spacing.xs),
                        ) {
                            KyashCoinWithIcon(
                                amount = entryCoinAmount,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AboutRewardSection(
    onAboutRewardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = SocialNetworkTheme.spacing.m,
            )
            .padding(
                top = SocialNetworkTheme.spacing.xxl,
            )
            .padding(
                bottom = SocialNetworkTheme.spacing.xxl,
            ),
    ) {
        Text(
            text = stringResource(
                id = R.string.reward_about_reward_label,
            ),
            style = SocialNetworkTheme.typography.title3Bold,
            color = SocialNetworkTheme.colors.textColorPrimary,
            modifier = modifier
                .padding(
                    bottom = SocialNetworkTheme.spacing.m,
                ),
        )
        Image(
            painter = painterResource(
                id = R.drawable.reward_img_about_reward_banner,
            ),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = modifier
                .clickableWithoutDoubleTap(
                    onClick = onAboutRewardClick,
                ),
        )
    }
}

private const val PrizeCardImageAspectRatio = 1f

data class RewardTopCarouselItem(
    val image: Painter,
    val onClick: () -> Unit,
    val needShowRewardHistoryIndicator: Boolean = false,
    val rewardHistoryIndicatorCount: Int = 0,
    val needGetBannerRectF: Boolean = false,
)

private const val DashLength = 10f
private const val SpaceLength = 10f
private val StampBannerBgColor = Color(0xFFDBF0FF)
private val DisabledPointCircleIconOffset = 72.dp
private val StampIconCircleSize = 40.dp
private val StampIconCircleBorderSize = 1.dp
private val StampIconCircleInnerBorderSize = 1.dp
private val StampIconCircleInnerBorderWidth = 36.dp

@Suppress("LongMethod")
@Composable
@Preview
private fun RewardTopSectionsPreview() {
    SocialNetworkTheme {
        RewardTopSections(
            weeklyPrizes = listOf(
                RewardTopReactor.State.PrizeUiState(
                    Prize(
                        id = "1",
                        title = "ウィークリー懸賞１",
                        description = "詳細",
                        imageUrl = "https://placehold.jp/375x294.png",
                        entryCoinAmount = 2000,
                        applicationStatus = Prize.ApplicationStatus.NotApplied,
                        appliedCount = 15,
                        maxWinnersCount = 15,
                        type = Prize.Type.Weekly,
                        activationStatus = Prize.ActivationStatus.Active,
                        startAt = DateTimeTz.nowLocal(),
                        announceAt = DateTimeTz.nowLocal(),
                        endAt = DateTime.fromUnixMillis(
                            System.currentTimeMillis() + 25 * 60 * 60 * 1000,
                        ).local,
                        winnersCount = 0,
                        lotteryResultChecked = false,
                        giftType = Prize.GiftType.Redirect,
                    ),
                    kyashCoinAmount = 100L,
                ),
                RewardTopReactor.State.PrizeUiState(
                    Prize(
                        id = "2",
                        title = "ウィークリー懸賞２",
                        description = "詳細",
                        imageUrl = "https://placehold.jp/375x294.png",
                        entryCoinAmount = 1000,
                        applicationStatus = Prize.ApplicationStatus.NotApplied,
                        appliedCount = 15,
                        maxWinnersCount = 10,
                        type = Prize.Type.Weekly,
                        activationStatus = Prize.ActivationStatus.Active,
                        startAt = DateTimeTz.nowLocal(),
                        endAt = DateTimeTz.nowLocal(),
                        announceAt = DateTimeTz.nowLocal(),
                        winnersCount = 0,
                        lotteryResultChecked = false,
                        giftType = Prize.GiftType.Redirect,
                    ),
                    kyashCoinAmount = 100L,
                ),
            ),
            dailyPrizes = listOf(
                RewardTopReactor.State.PrizeUiState(
                    Prize(
                        id = "1",
                        title = "デイリー懸賞１",
                        description = "詳細",
                        imageUrl = "https://placehold.jp/375x294.png",
                        entryCoinAmount = 1000,
                        applicationStatus = Prize.ApplicationStatus.NotApplied,
                        appliedCount = 15,
                        maxWinnersCount = 20,
                        type = Prize.Type.Daily,
                        activationStatus = Prize.ActivationStatus.Active,
                        startAt = DateTimeTz.nowLocal(),
                        endAt = DateTime.fromUnixMillis(
                            System.currentTimeMillis() + 2 * 60 * 60 * 1000,
                        ).local,
                        announceAt = DateTimeTz.nowLocal(),
                        winnersCount = 0,
                        lotteryResultChecked = false,
                        giftType = Prize.GiftType.Redirect,
                    ),
                    kyashCoinAmount = 100L,
                ),
                RewardTopReactor.State.PrizeUiState(
                    Prize(
                        id = "2",
                        title = "デイリー懸賞２",
                        description = "詳細",
                        imageUrl = "https://placehold.jp/375x294.png",
                        entryCoinAmount = 2000,
                        applicationStatus = Prize.ApplicationStatus.NotApplied,
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
                    ),
                    kyashCoinAmount = 100L,
                ),
            ),
            comingSoonPrizes = listOf(
                RewardTopReactor.State.PrizeUiState(
                    Prize(
                        id = "1",
                        title = "次週の懸賞１",
                        description = "詳細",
                        imageUrl = "https://placehold.jp/375x294.png",
                        entryCoinAmount = 5000,
                        applicationStatus = Prize.ApplicationStatus.NotApplied,
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
                    ),
                    kyashCoinAmount = 100L,
                ),
                RewardTopReactor.State.PrizeUiState(
                    Prize(
                        id = "1",
                        title = "次週の懸賞２",
                        description = "詳細",
                        imageUrl = "https://placehold.jp/375x294.png",
                        entryCoinAmount = 10000,
                        applicationStatus = Prize.ApplicationStatus.NotApplied,
                        appliedCount = 15,
                        maxWinnersCount = 20,
                        type = Prize.Type.Weekly,
                        activationStatus = Prize.ActivationStatus.Inactive,
                        startAt = DateTimeTz.nowLocal(),
                        endAt = DateTimeTz.nowLocal(),
                        announceAt = DateTimeTz.nowLocal(),
                        winnersCount = 0,
                        lotteryResultChecked = false,
                        giftType = Prize.GiftType.Redirect,
                    ),
                    kyashCoinAmount = 100L,
                ),
            ),
            isRefreshing = false,
            onRefresh = {},
            onClickPrize = {},
            onClickStampBanner = {},
            onDailyRouletteClick = {},
            weeklyRemainingTimeType = RemainingTimeType.Gone,
            dailyRemainingTimeType = RemainingTimeType.Gone,
            onAppliedPrizeListClick = {},
            needShowRewardHistoryIndicator = true,
            rewardHistoryIndicatorCount = 2,
            welcomeRemainingTimeType = RemainingTimeType.Gone,
            welcomePrizes = listOf(
                RewardTopReactor.State.PrizeUiState(
                    Prize(
                        id = "1",
                        title = "ウェルカム懸賞１",
                        description = "詳細",
                        imageUrl = "https://placehold.jp/375x294.png",
                        entryCoinAmount = 1000,
                        applicationStatus = Prize.ApplicationStatus.NotApplied,
                        appliedCount = 15,
                        maxWinnersCount = 20,
                        type = Prize.Type.Welcome,
                        activationStatus = Prize.ActivationStatus.Active,
                        startAt = DateTimeTz.nowLocal(),
                        endAt = DateTimeTz.nowLocal(),
                        announceAt = DateTimeTz.nowLocal(),
                        winnersCount = 0,
                        lotteryResultChecked = false,
                        giftType = Prize.GiftType.Redirect,
                    ),
                    kyashCoinAmount = 1000L,
                ),
            ),
            setRouletteRectF = { _ -> },
            setWelcomeChallengeRectF = { _ -> },
            onTapOfferWallBanner = { },
            onAboutRewardClick = { },
        )
    }
}

@PreviewFontScales
@Composable
private fun PrizeItemPreview_NotYetApplicable() {
    SocialNetworkTheme {
        Surface {
            WeeklyPrizeItem(
                imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
                title = "AirPods Pro",
                maxWinners = Prize.MaxWinners.Limited(10),
                entryCoinRatio = 0.6f,
                entryCoinAmount = 6000,
                needShowEntryCoinRatio = true,
                isUpcoming = false,
                applicableStatus = PrizeApplicableStatus.NotYetApplicable,
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@PreviewFontScales
@Composable
private fun PrizeItemPreview_Applicable() {
    SocialNetworkTheme {
        Surface {
            WeeklyPrizeItem(
                imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
                title = "AirPods Pro",
                maxWinners = Prize.MaxWinners.Limited(10),
                entryCoinRatio = 0.6f,
                entryCoinAmount = 6000,
                needShowEntryCoinRatio = false,
                isUpcoming = false,
                applicableStatus = PrizeApplicableStatus.Applicable,
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@PreviewFontScales
@Composable
private fun PrizeItemPreview_Applied() {
    SocialNetworkTheme {
        Surface {
            WeeklyPrizeItem(
                imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
                title = "AirPods Pro",
                maxWinners = Prize.MaxWinners.Limited(10),
                entryCoinRatio = 0.6f,
                entryCoinAmount = 6000,
                needShowEntryCoinRatio = false,
                isUpcoming = false,
                applicableStatus = PrizeApplicableStatus.Applied,
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@PreviewFontScales
@Composable
private fun PrizeItemPreview_Closed() {
    SocialNetworkTheme {
        Surface {
            WeeklyPrizeItem(
                imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
                title = "AirPods Pro",
                maxWinners = Prize.MaxWinners.Limited(10),
                entryCoinRatio = 0.6f,
                entryCoinAmount = 6000,
                needShowEntryCoinRatio = false,
                isUpcoming = false,
                applicableStatus = PrizeApplicableStatus.Ended(
                    reason = PrizeApplicableStatus.Ended.Reason.LotteryPeriodEnded,
                ),
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@PreviewFontScales
@Composable
private fun PrizeItemPreview_Shortage() {
    SocialNetworkTheme {
        Surface {
            WeeklyPrizeItem(
                imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
                title = "AirPods Pro",
                maxWinners = Prize.MaxWinners.Limited(10),
                entryCoinRatio = 0.6f,
                entryCoinAmount = 6000,
                needShowEntryCoinRatio = false,
                isUpcoming = false,
                applicableStatus = PrizeApplicableStatus.Shortage(200, 0.6f),
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@PreviewFontScales
@Composable
private fun DailyPrizeItemPreview_Shortage() {
    SocialNetworkTheme {
        Surface {
            DailyPrizeItem(
                imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
                title = "AirPods ProAirPods ProAirPods ProAirPods Pro",
                maxWinners = Prize.MaxWinners.Limited(10),
                entryCoinRatio = 0.6f,
                entryCoinAmount = 6000,
                needShowEntryCoinRatio = true,
                applicableStatus = PrizeApplicableStatus.Shortage(200, 0.6f),
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@PreviewFontScales
@Composable
private fun DailyPrizeItemPreview_Unlimited() {
    SocialNetworkTheme {
        Surface {
            DailyPrizeItem(
                imagePainter = painterResource(id = R.drawable.img_walkthrough_01),
                title = "AirPods ProAirPods ProAirPods ProAirPods Pro",
                maxWinners = Prize.MaxWinners.Unlimited,
                entryCoinRatio = 0.6f,
                entryCoinAmount = 6000,
                needShowEntryCoinRatio = true,
                applicableStatus = PrizeApplicableStatus.Shortage(200, 0.6f),
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@Preview
@Composable
fun StampBannerPreview() {
    StampBanner(onClickStampBanner = {})
}
