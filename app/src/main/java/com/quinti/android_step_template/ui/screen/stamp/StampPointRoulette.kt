package com.quinti.android_step_template.ui.screen.stamp

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quinti.android_step_template.R
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Suppress("LongMethod")
@Composable
fun StampPointRoulette(
    rouletteState: RouletteState,
    amountOptions: List<Long>,
    displayText: String,
    shouldPointAnimate: Boolean,
) {
    val randomizeDegree = remember {
        val degree = (FullRotationAngle / amountOptions.size) - 1.0f
        (1..degree.toInt()).random() - degree / 2
    }

    // 停止角度の決定
    val degrees = remember(rouletteState) {
        when (rouletteState) {
            is RouletteState.Ready -> 0f
            is RouletteState.Start -> {
                val value = rouletteState.amountResult
                val index = amountOptions.indexOfFirst {
                    it == value
                }
                val degree = FullRotationAngle / rouletteState.amountOptions.size
                (FullRotationAngle * RouletteBoardRotateCount) - degree * index + randomizeDegree
            }
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

    Column(
        modifier = Modifier
            .padding(bottom = SocialNetworkTheme.spacing.l)
            .fillMaxWidth()
            .height(256.dp)
            .background(
                color = PointRouletteBgColor,
                shape = SocialNetworkTheme.shapes.large,
            )
            .verticalScroll(rememberScrollState()),
    ) {
        // ポイント表示エリア
        Box(
            modifier = Modifier
                .padding(
                    top = SocialNetworkTheme.spacing.m,
                    bottom = SocialNetworkTheme.spacing.m,
                )
                .width(112.dp)
                .height(48.dp)
                .align(Alignment.CenterHorizontally)
                .background(
                    color = SocialNetworkTheme.colors.onPrimary,
                    shape = SocialNetworkTheme.shapes.small,
                ),
        ) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.reward_ic_point),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = SocialNetworkTheme.spacing.xxs),
                )
                Text(
                    text = displayText,
                    style = SocialNetworkTheme.typography.title2Bold,
                    color = if (shouldPointAnimate) {
                        SocialNetworkTheme.colors.textColorSecondary
                    } else {
                        SocialNetworkTheme.colors.textColorExtraLight
                    },
                )
            }
        }
        // ルーレットと矢印のエリア
        Box(
            modifier = Modifier
                .width(256.dp)
                .height(256.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            // ルーレット盤面
            Surface(
                modifier = Modifier
                    .size(256.dp)
                    .offset(y = RouletteBoardBottomOffset.dp)
                    .shadow(1.dp, shape = SocialNetworkTheme.shapes.round),
                shape = SocialNetworkTheme.shapes.round,
                border = BorderStroke(5.dp, SocialNetworkTheme.colors.onPrimary),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    // ルーレット盤面の絵柄部分
                    RouletteBoard(
                        modifier = Modifier.rotate(rouletteRotateDegrees),
                        amountOptions = amountOptions,
                    )
                    // 中心の軸
                    Surface(
                        shape = SocialNetworkTheme.shapes.round,
                        modifier = Modifier
                            .shadow(1.dp, shape = SocialNetworkTheme.shapes.round)
                            .size(44.dp),
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(horizontal = 13.dp, vertical = 15.dp)
                                .width(18.dp)
                                .height(14.dp),
                            painter = painterResource(id = R.drawable.reward_img_kyash_icon),
                            contentDescription = null,
                        )
                    }
                }
            }
            // ルーレット盤面のインジケーター
            Image(
                modifier = Modifier
                    .size(
                        height = 24.dp,
                        width = 27.dp,
                    )
                    .align(Alignment.TopCenter),
                painter = painterResource(id = R.drawable.reward_img_roulette_indicator),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun RouletteBoard(
    modifier: Modifier,
    amountOptions: List<Long>,
) {
    val textMeasurer = rememberTextMeasurer()

    Spacer(
        modifier = modifier
            .fillMaxSize()
            .drawWithCache {
                onDrawBehind {
                    drawRouletteBoard(
                        amountOptions,
                        textMeasurer = textMeasurer,
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            lineHeight = 34.sp,
                            fontWeight = FontWeight.W700,
                        ),
                    )
                }
            },
    )
}

private fun DrawScope.drawRouletteBoard(
    amountOptions: List<Long>,
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
) {
    val sweepAngle = FullRotationAngle / amountOptions.size
    var startAngle = -90f - sweepAngle / 2
    var textStartAngle = -90f - sweepAngle / 2

    amountOptions.forEachIndexed { index, _ ->
        drawArc(
            color = RouletteColors[index],
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = true,
            topLeft = Offset(
                x = 0f,
                y = 0f,
            ),
            size = Size(RouletteRadius, RouletteRadius),
        )
        startAngle += sweepAngle
    }

    amountOptions.forEachIndexed { index, amountOption ->
        val text = amountOption.toString()
        val textSize = textMeasurer.measure(
            text = text,
            style = textStyle,
        ).size

        val x = -textSize.width / 2
        val y = -size.height / 2 + 32.dp.toPx()
        val translation = Offset(
            x = x + 128.dp.toPx(),
            y = y + 120.dp.toPx(),
        )

        withTransform(
            {
                rotate(sweepAngle * index)
            },
        ) {
            drawText(
                textMeasurer = textMeasurer,
                text = amountOption.toString(),
                topLeft = translation,
                style = textStyle,
            )
        }
        textStartAngle += sweepAngle
    }
}

sealed class RouletteState {

    abstract val amountOptions: List<Long>

    data class Ready(
        override val amountOptions: List<Long>,
    ) : RouletteState()

    data class Start(
        override val amountOptions: List<Long>,
        val amountResult: Long,
    ) : RouletteState()
}

private const val RouletteBoardBottomOffset = 13
private const val FullRotationAngle = 360f
private const val RouletteBoardRotateCount = 3
private const val RouletteAnimationDuration = 3000
private const val RouletteAnimationDelay = 200
private val PointRouletteBgColor = Color(0xFFDBF0FF)
private val DrawScope.RouletteRadius: Float get() = 256f.dp.toPx()

private val RouletteColors = listOf(
    Color(0xFF4DB2FF),
    Color(0xFF79C5FF),
    Color(0xFF4DB2FF),
    Color(0xFF79C5FF),
    Color(0xFF4DB2FF),
    Color(0xFF79C5FF),
    Color(0xFF4DB2FF),
    Color(0xFF79C5FF),
)

@Preview
@Composable
private fun RouletteSectionPreview() {
    val amountOptions: List<Long> = listOf(1, 100, 2, 6, 8, 9, 4, 5)
    val shouldPointAnimate = true
    StampPointRoulette(
        rouletteState = RouletteState.Ready(
            amountOptions = amountOptions,
        ),
        amountOptions = amountOptions,
        displayText = if (shouldPointAnimate) amountOptions.random().toString() else "---",
        shouldPointAnimate = shouldPointAnimate,
    )
}