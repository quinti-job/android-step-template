package com.quinti.android_step_template.ui.screen.roulette

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.EaseOutElastic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.domain.reactor.DailyRouletteReactor
import com.quinti.android_step_template.ui.component.KyashButton
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

/**
 * ルーレット盤面の描画とアニメーションを行う
 *
 * 各アニメーションのタイムラインは以下
 *
 * | start millis |  duration | what |
 * |--------|--------|--------|
 * | 0 | 300 | ラベル・下部ボタンを隠す |
 * | 300 | 5000 | ルーレット回す |
 * | 5000 | 100 | 結果表示スロットの色を変える(下より300ms早く) |
 * | 5300 | 100 | PlaceHolderテキストを隠す |
 * | 5300 | 1000 | 結果コイン数を表示する |
 * | 6300 | 300 | ラベル・下部ボタンを隠す（Reactor.Action経由） |
 */
@Composable
fun DailyRouletteSection(
    actionButtonType: DailyRouletteReactor.State.ActionButtonType,
    bottomButtonVisibility: Boolean,
    bottomButtonEnabled: Boolean,
    messageLabelType: DailyRouletteReactor.State.MessageLabelType,
    labelDisplayVisibility: Boolean,
    rouletteState: DailyRouletteReactor.State.RouletteState,
    resultSlotState: DailyRouletteReactor.State.RouletteResultSlotState,
    onExecuteClick: () -> Unit,
    onCloseClick: () -> Unit,
    onAnimationCompleted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // ルーレット選択肢を色付きの値に変換
    val lotteryCandidates = rouletteState.lotteryCandidates.zip(RouletteColors) { value, color ->
        LotteryCandidate(value, color)
    }.toList()

    // AnimatedVisibilityだとGone状態となるのでAlphaで制御する
    val labelAnimationAlpha by animateFloatAsState(
        targetValue = if (labelDisplayVisibility) 1f else 0f,
        animationSpec = tween(
            durationMillis = MessageLabelAnimationDuration,
            delayMillis = MessageLabelAnimationDelay,
            easing = Ease,
        ),
        label = "labelAlphaAnimation",
    )

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            LabelSection(
                modifier = Modifier
                    .height(60.dp)
                    .alpha(labelAnimationAlpha),
                messageLabelType = messageLabelType,
            )

            LotteryResultSlotSection(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(SocialNetworkTheme.spacing.m),
                resultSlotState = resultSlotState,
                values = lotteryCandidates,
                onAnimationCompleted = onAnimationCompleted,
            )

            AnimatedRouletteSection(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(SocialNetworkTheme.spacing.m),
                rouletteState = rouletteState,
                valueAndColors = lotteryCandidates,
            )
        }

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            visible = bottomButtonVisibility,
            enter = slideIn(
                animationSpec = tween(
                    durationMillis = ActionButtonAnimationDuration,
                    delayMillis = ActionButtonAnimationDelay,
                    easing = Ease,
                ),
                initialOffset = { IntOffset(0, ActionButtonPosition) },
            ),
            exit = slideOut(
                animationSpec = tween(
                    durationMillis = ActionButtonAnimationDuration,
                    delayMillis = ActionButtonAnimationDelay,
                    easing = Ease,
                ),
                targetOffset = { IntOffset(0, ActionButtonPosition) },
            ),
        ) {
            BottomButtonSection(
                actionButtonType = actionButtonType,
                bottomButtonEnabled = bottomButtonEnabled,
                onCloseClick = onCloseClick,
                onExecuteClick = onExecuteClick,
            )
        }
    }
}

@Composable
private fun BottomButtonSection(
    actionButtonType: DailyRouletteReactor.State.ActionButtonType,
    bottomButtonEnabled: Boolean,
    onCloseClick: () -> Unit,
    onExecuteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = DailyRouletteBackgroundColor,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(
                    WindowInsets.systemBars.only(
                        WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom,
                    ),
                ),
        ) {
            when (actionButtonType) {
                DailyRouletteReactor.State.ActionButtonType.Playable -> KyashButton(
                    modifier = Modifier
                        .padding(SocialNetworkTheme.spacing.m)
                        .fillMaxWidth(),
                    onClick = onExecuteClick,
                    enabled = bottomButtonEnabled,
                    text = stringResource(id = R.string.reward_daily_roulette_draw_button),
                )

                DailyRouletteReactor.State.ActionButtonType.Close -> KyashButton(
                    modifier = Modifier
                        .padding(SocialNetworkTheme.spacing.m)
                        .fillMaxWidth(),
                    onClick = onCloseClick,
                    enabled = bottomButtonEnabled,
                    text = stringResource(id = R.string.close),
                )
            }
        }
    }
}

@Composable
private fun AnimatedRouletteSection(
    modifier: Modifier,
    rouletteState: DailyRouletteReactor.State.RouletteState,
    valueAndColors: List<LotteryCandidate>,
) {
    // 停止時の位置をランダムにずらす
    // 下記停止角度の決定ロジックに入れると、毎回停止位置が変わってしまうため、別で計算する。
    // セクション間に止まらないように、左右1度ずつ余裕をもたせる
    val randomizeDegree = remember {
        val degree = (FullRotationAngle / valueAndColors.size) - 1.0f
        (1..degree.toInt()).random() - degree / 2
    }

    // 停止角度の決定
    val degrees = remember(rouletteState) {
        when (rouletteState) {
            is DailyRouletteReactor.State.RouletteState.Ready -> 0f
            is DailyRouletteReactor.State.RouletteState.Start -> {
                val value = rouletteState.lotteryResult
                val index = valueAndColors.indexOfFirst {
                    it.value == value
                }
                val degree = FullRotationAngle / rouletteState.lotteryCandidates.size
                (FullRotationAngle * RouletteBoardRotateCount) - degree * index + randomizeDegree
            }

            is DailyRouletteReactor.State.RouletteState.Challenged -> 0f
        }
    }

    // 回転アニメーション
    val rouletteRotateDegrees by animateFloatAsState(
        targetValue = degrees,
        animationSpec = tween(
            durationMillis = RouletteAnimationDuration,
            delayMillis = RouletteAnimationDelay,
            easing = Ease,
        ),
        label = "degrees",
    )

    Box(
        modifier = modifier,
    ) {
        // ルーレット盤面
        Surface(
            modifier = Modifier
                .size(RouletteBoardOriginSize.dp)
                .scale(RouletteBoardScale)
                .offset(y = RouletteBoardBottomOffset.dp)
                .shadow(1.dp, shape = SocialNetworkTheme.shapes.round),
            color = Color.White,
            shape = SocialNetworkTheme.shapes.round,
            border = BorderStroke(5.dp, Color.White),
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                // ルーレット盤面の絵柄部分
                RouletteBoard(
                    modifier = Modifier.rotate(rouletteRotateDegrees),
                    valueAndColors = valueAndColors,
                )
                // 中心の軸
                Surface(
                    shape = SocialNetworkTheme.shapes.round,
                    modifier = Modifier.shadow(1.dp, shape = SocialNetworkTheme.shapes.round),
                ) {
                    Image(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(20.dp),
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                    )
                }
            }
        }

        // ルーレット盤面のインジケーター
        Image(
            modifier = Modifier
                .size(
                    height = 66.dp,
                    width = 57.dp,
                )
                .scale(RouletteBoardIndicatorScale)
                .align(Alignment.TopCenter),
            painter = painterResource(id = android.R.drawable.button_onoff_indicator_on),
            contentDescription = null,
        )
    }
}

@Composable
private fun RouletteBoard(
    modifier: Modifier,
    valueAndColors: List<LotteryCandidate>,
) {
    val textMeasurer = rememberTextMeasurer()

    Spacer(
        modifier = modifier
            .fillMaxSize()
            .drawWithCache {
                onDrawBehind {
                    drawRouletteBoard(
                        valueAndColors,
                        textMeasurer = textMeasurer,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            lineHeight = 30.sp,
                            fontWeight = FontWeight.W700,
                        ),
                    )
                }
            },
    )
}

private val DrawScope.RouletteRadius: Float get() = size.width / 2
private fun DrawScope.drawRouletteBoard(
    valueAndColors: List<LotteryCandidate>,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
) {
    val sweepAngle = FullRotationAngle / valueAndColors.size
    var startAngle = -90f - sweepAngle / 2
    var textStartAngle = -90f - sweepAngle / 2

    valueAndColors.forEach {
        drawArc(
            color = it.color,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = true,
        )
        startAngle += sweepAngle
    }

    valueAndColors.forEachIndexed { index, lotteryCandidate ->
        val text = lotteryCandidate.value.toString()
        val textSize = textMeasurer.measure(
            text = text,
            style = textStyle,
        ).size

        val x = -textSize.width / 2
        val y = -size.height / 2 + 32.dp.toPx()
        val translation = Offset(
            x = x + RouletteRadius,
            y = y + RouletteRadius,
        )

        withTransform(
            {
                rotate(sweepAngle * index)
            },
        ) {
            drawText(
                textMeasurer = textMeasurer,
                text = lotteryCandidate.value.toString(),
                topLeft = translation,
                style = textStyle,
            )
        }
        textStartAngle += sweepAngle
    }
}

@Composable
private fun LotteryResultSlotSection(
    resultSlotState: DailyRouletteReactor.State.RouletteResultSlotState,
    values: List<LotteryCandidate>,
    onAnimationCompleted: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundAnimationColor by animateColorAsState(
        targetValue = when (resultSlotState) {
            DailyRouletteReactor.State.RouletteResultSlotState.Ready -> Color.White
            is DailyRouletteReactor.State.RouletteResultSlotState.Start ->
                values.first { it.value == resultSlotState.lotteryResult }.color
        },
        animationSpec = tween(
            // 少し早めに色を変える
            delayMillis = LotteryResultAnimationDelay - LotteryResultColorAnimationDelayOffset,
            durationMillis = 100,
            easing = LinearEasing,
        ),
        label = "backgroundColorAnimation",
    )

    // 確定した値を表示するアニメーション
    // 表示領域外（下部、テキストサイズ分）から表示領域内へ移動させる
    val valueTextTransitionAnimationRatio by animateFloatAsState(
        targetValue = when (resultSlotState) {
            DailyRouletteReactor.State.RouletteResultSlotState.Ready -> 1f
            is DailyRouletteReactor.State.RouletteResultSlotState.Start -> 0f
        },
        animationSpec = tween(
            delayMillis = LotteryResultAnimationDelay,
            durationMillis = LotteryResultAnimationDuration,
            easing = EaseOutElastic,
        ),
        label = "valueTextAnimation",
        finishedListener = {
            onAnimationCompleted()
        },
    )

    // 横幅が先に決まってしまうので、Delayを入れるためにアニメーションを利用する。
    val valueTextAnimationInt by animateIntAsState(
        targetValue = when (resultSlotState) {
            DailyRouletteReactor.State.RouletteResultSlotState.Ready -> 0
            is DailyRouletteReactor.State.RouletteResultSlotState.Start ->
                resultSlotState.lotteryResult.toInt()
        },
        label = "valueTextXScaleAnimation",
        animationSpec = tween(
            delayMillis = LotteryResultAnimationDelay,
            durationMillis = 1, // 0だとアニメーションが走らないので1msにする
            easing = Ease,
        ),
    )

    Surface(
        color = backgroundAnimationColor,
        modifier = modifier
            .wrapContentSize()
            .padding(SocialNetworkTheme.spacing.m)
            .border(
                width = 1.dp,
                color = SocialNetworkTheme.colors.backgroundDark,
                shape = SocialNetworkTheme.shapes.medium,
            )
            .clip(SocialNetworkTheme.shapes.medium),
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = SocialNetworkTheme.spacing.m,
                    vertical = SocialNetworkTheme.spacing.xxs,
                )
                .wrapContentSize()
                .height(80.dp)
                .width(191.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                SocialNetworkTheme.spacing.xs,
                Alignment.CenterHorizontally,
            ),
        ) {
//            KyashCoinIcon(
//                modifier = Modifier.size(40.dp),
//            )
            Box(
                modifier = Modifier.height(LotteryResultSlotTextHeight.dp),
                contentAlignment = Alignment.Center,
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = resultSlotState is
                            DailyRouletteReactor.State.RouletteResultSlotState.Ready,
                    exit = slideOut(
                        animationSpec = tween(
                            delayMillis = LotteryResultAnimationDelay,
                            durationMillis = 100,
                            easing = LinearEasing,
                        ),
                        targetOffset = { IntOffset(0, y = LotteryResultSlotTextHeight) },
                    ),
                ) {
                    Text(
                        modifier = Modifier
                            .height(LotteryResultSlotTextHeight.dp),
                        text = stringResource(
                            id = R.string.reward_daily_roulette_lottery_result_placeholder,
                        ),
                        style = TextStyle(
                            color = LotteryResultSlotPlaceHolderTextColor,
                            fontSize = LotteryResultSlotTextFontSize.sp,
                            lineHeight = LotteryResultSlotTextHeight.sp,
                            letterSpacing = 4.sp,
                            fontWeight = FontWeight.W500,
                        ),
                        textAlign = TextAlign.Center,
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(top = SocialNetworkTheme.spacing.xxxs)
                        .height(LotteryResultSlotTextHeight.dp)
                        .offset(
                            y = valueTextTransitionAnimationRatio *
                                    LotteryResultSlotTextHeight.dp,
                        ),
                    text = valueTextAnimationInt.toString(),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = LotteryResultSlotTextFontSize.sp,
                        lineHeight = LotteryResultSlotTextHeight.sp,
                        fontWeight = FontWeight.W700,
                    ),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun LabelSection(
    messageLabelType: DailyRouletteReactor.State.MessageLabelType,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (messageLabelType) {
            DailyRouletteReactor.State.MessageLabelType.Challenged -> Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.reward_daily_roulette_message_done),
                style = SocialNetworkTheme.typography.bodyBold,
                textAlign = TextAlign.Center,
                color = SocialNetworkTheme.colors.textColorSecondary,
            )

            DailyRouletteReactor.State.MessageLabelType.Determined -> Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.reward_daily_roulette_message_completed),
                style = SocialNetworkTheme.typography.title2Bold,
                textAlign = TextAlign.Center,
                color = SocialNetworkTheme.colors.textColorSecondary,
            )

            DailyRouletteReactor.State.MessageLabelType.Playable -> Text(
                modifier = Modifier
                    .padding(SocialNetworkTheme.spacing.m)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.reward_daily_roulette_message_yet),
                style = SocialNetworkTheme.typography.bodyBold,
                textAlign = TextAlign.Center,
                color = SocialNetworkTheme.colors.textColorSecondary,
            )
        }
    }
}

private val RouletteColors = listOf(
    Color(0xFF00BF9D),
    Color(0xFFFF945F),
    Color(0xFFFFAEAE),
    Color(0xFFFF7978),
    Color(0xFF9C92FF),
    Color(0xFF1F60E6),
    Color(0xFF14C2E9),
    Color(0xFF209FFF),
    Color(0xFF00BF9D),
)

private const val MessageLabelAnimationDelay = 0
private const val MessageLabelAnimationDuration = 300

private const val ActionButtonAnimationDelay = MessageLabelAnimationDelay
private const val ActionButtonAnimationDuration = MessageLabelAnimationDuration
private const val ActionButtonPosition = 400

private const val RouletteAnimationDelay =
    ActionButtonAnimationDelay + ActionButtonAnimationDuration
private const val RouletteAnimationDuration = 5000

private const val LotteryResultAnimationDelay = RouletteAnimationDelay + RouletteAnimationDuration
private const val LotteryResultAnimationDuration = 1000
private const val LotteryResultColorAnimationDelayOffset = 300
private const val LotteryResultSlotTextHeight = 66
private const val LotteryResultSlotTextFontSize = 44
private val LotteryResultSlotPlaceHolderTextColor = Color(0x59222222)

private const val RouletteBoardScale = 2.3f
private const val RouletteBoardOriginSize = 315
private const val RouletteBoardBottomOffset = 100
private const val RouletteBoardRotateCount = 5
private const val RouletteBoardIndicatorScale = 1.5f

private const val FullRotationAngle = 360f

// region preview

private val DefaultValuesForPreview = listOf<Long>(
    600,
    800,
    1000,
    1500,
    2000,
    700,
    500,
    1800,
).shuffled()

@Preview
@Composable
private fun DailyRouletteLoadedContentPreview() {
    SocialNetworkTheme {
        Surface(
            color = DailyRouletteBackgroundColor,
        ) {
            DailyRouletteSection(
                actionButtonType = DailyRouletteReactor.State.ActionButtonType.Playable,
                bottomButtonVisibility = true,
                bottomButtonEnabled = true,
                messageLabelType = DailyRouletteReactor.State.MessageLabelType.Playable,
                labelDisplayVisibility = true,
                rouletteState = DailyRouletteReactor.State.RouletteState.Ready(
                    DefaultValuesForPreview,
                ),
                resultSlotState = DailyRouletteReactor.State.RouletteResultSlotState.Ready,
                onExecuteClick = {},
                onCloseClick = {},
                onAnimationCompleted = {},
            )
        }
    }
}

@Preview
@Composable
private fun DailyRouletteLoadedContentPreview_Challenged() {
    SocialNetworkTheme {
        Surface(
            color = DailyRouletteBackgroundColor,
        ) {
            DailyRouletteSection(
                actionButtonType = DailyRouletteReactor.State.ActionButtonType.Playable,
                bottomButtonVisibility = true,
                bottomButtonEnabled = false,
                messageLabelType = DailyRouletteReactor.State.MessageLabelType.Playable,
                labelDisplayVisibility = true,
                rouletteState = DailyRouletteReactor.State.RouletteState.Ready(
                    DefaultValuesForPreview,
                ),
                resultSlotState = DailyRouletteReactor.State.RouletteResultSlotState.Ready,
                onExecuteClick = {},
                onCloseClick = {},
                onAnimationCompleted = {},
            )
        }
    }
}

private data class LotteryCandidate(
    val value: Long,
    val color: Color,
)

@Preview
@Composable
private fun DailyRouletteLoadedContentPreview_Playing() {
    SocialNetworkTheme {
        Surface(
            color = DailyRouletteBackgroundColor,
        ) {
            DailyRouletteSection(
                actionButtonType = DailyRouletteReactor.State.ActionButtonType.Playable,
                bottomButtonVisibility = false,
                bottomButtonEnabled = false,
                messageLabelType = DailyRouletteReactor.State.MessageLabelType.Playable,
                labelDisplayVisibility = false,
                rouletteState = DailyRouletteReactor.State.RouletteState.Start(
                    lotteryCandidates = listOf(100, 200, 300, 400, 500, 600, 700, 800),
                    100L,
                ),
                resultSlotState = DailyRouletteReactor.State.RouletteResultSlotState.Start(600L),
                onExecuteClick = {},
                onCloseClick = {},
                onAnimationCompleted = {},
            )
        }
    }
}

private val LotteryCandidates =
    DefaultValuesForPreview.zip(RouletteColors) { candidateValue, color ->
        LotteryCandidate(candidateValue, color)
    }.toList()

@Preview
@Composable
private fun RouletteResultSlotSection_Ready() {
    SocialNetworkTheme {
        Surface(
            color = SocialNetworkTheme.colors.surface,
        ) {
            LotteryResultSlotSection(
                resultSlotState = DailyRouletteReactor.State.RouletteResultSlotState.Ready,
                values = LotteryCandidates,
                onAnimationCompleted = {},
            )
        }
    }
}

@Preview
@Composable
private fun RouletteResultSlotSection_Determined() {
    SocialNetworkTheme {
        Surface(
            color = SocialNetworkTheme.colors.surface,
        ) {
            LotteryResultSlotSection(
                resultSlotState = DailyRouletteReactor.State.RouletteResultSlotState.Start(
                    lotteryResult = 600L,
                ),
                values = LotteryCandidates,
                onAnimationCompleted = {},
            )
        }
    }
}

@Preview
@Composable
private fun RouletteResultSlotSection_Determined_1000() {
    SocialNetworkTheme {
        Surface(
            color = SocialNetworkTheme.colors.surface,
        ) {
            LotteryResultSlotSection(
                resultSlotState = DailyRouletteReactor.State.RouletteResultSlotState.Start(
                    lotteryResult = 2000L,
                ),
                values = LotteryCandidates,
                onAnimationCompleted = {},
            )
        }
    }
}

@Preview
@Composable
private fun RouletteSectionPreview_Ready() {
    SocialNetworkTheme {
        Surface(
            color = SocialNetworkTheme.colors.surface,
        ) {
            AnimatedRouletteSection(
                modifier = Modifier,
                rouletteState = DailyRouletteReactor.State.RouletteState.Ready(
                    DefaultValuesForPreview,
                ),
                valueAndColors = LotteryCandidates,
            )
        }
    }
}