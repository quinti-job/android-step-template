package com.quinti.android_step_template.ui.screen.spotlight

import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.R
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SpotlightScreen(
    type: SpotlightFragment.Type,
    customPath: (Density, RectF) -> Path,
    spotlightAreaRectF: RectF,
    coachMarkPosition: Offset,
    onTapContent: () -> Unit,
    onTapOverlay: () -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        delay(OverlayShowDelayTimeMillis)
        isVisible = true
    }
    val density = LocalDensity.current

    val rouletteCoachMarkRectF: MutableStateFlow<RectF> = MutableStateFlow(RectF())
    val welcomeChallengeCoachMarkRectF: MutableStateFlow<RectF> = MutableStateFlow(RectF())

    if (isVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { offset ->
                                if (isInsideSpotlightArea(offset, spotlightAreaRectF)) {
                                    onTapContent()
                                } else if (!isInsideCoachMarkArea(
                                        type,
                                        offset,
                                        rouletteCoachMarkRectF.value,
                                        welcomeChallengeCoachMarkRectF.value,
                                    )
                                ) {
                                    onTapOverlay()
                                }
                            },
                        )
                    },
            ) {
                clipPath(
                    path = customPath(density, spotlightAreaRectF),
                    clipOp = ClipOp.Difference,
                ) {
                    drawRect(SolidColor(OverlayColor))
                }
            }
            if (type == SpotlightFragment.Type.Roulette) {
                RouletteCoachMark(
                    modifier = Modifier
                        .offset(
                            x = with(density) { coachMarkPosition.x.toDp() },
                            y = with(density) { coachMarkPosition.y.toDp() },
                        )
                        .onGloballyPositioned {
                            val left = it.positionInWindow().x
                            val top = it.positionInWindow().y
                            val right = left + it.size.width
                            val bottom = top + it.size.height
                            rouletteCoachMarkRectF.value = RectF(left, top, right, bottom)
                        },
                )
            } else if (type == SpotlightFragment.Type.WelcomeChallenge) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    WelcomeChallengeCoachMark(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .offset(
                                y = with(density) { coachMarkPosition.y.toDp() },
                            )
                            .onGloballyPositioned {
                                val left = it.positionInWindow().x
                                val top = it.positionInWindow().y
                                val right = left + it.size.width
                                val bottom = top + it.size.height
                                welcomeChallengeCoachMarkRectF.value =
                                    RectF(left, top, right, bottom)
                            },
                    )
                }
            }
        }
    }
}

fun spotlightPath(
    rect: RectF,
    radius: Float,
): Path {
    val path = Path().apply {
        addRoundRect(
            RoundRect(
                rect = Rect(
                    offset = Offset(
                        x = rect.left,
                        y = rect.top,
                    ),
                    size = Size(
                        width = rect.width(),
                        height = rect.height(),
                    ),
                ),
                cornerRadius = CornerRadius(
                    x = radius,
                    y = radius,
                ),
            ),
        )
    }
    return path
}

@Composable
private fun RouletteCoachMark(modifier: Modifier) {
    Column(
        modifier = modifier,
    ) {
        CoachMarkTriangle(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = TrianglePosition)
                .padding(end = LocalConfiguration.current.screenWidthDp.dp / 4),
        )
        Box(
            modifier = Modifier
                .shadow(elevation = SocialNetworkTheme.spacing.l)
                .background(
                    SocialNetworkTheme.colors.surface,
                    SocialNetworkTheme.shapes.medium,
                ),
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = SocialNetworkTheme.spacing.s)
                    .padding(vertical = SocialNetworkTheme.spacing.m),
                text = stringResource(
                    id = R.string.reward_onboarding_tooltip_reward_top_daily_roulette,
                ),
                color = SocialNetworkTheme.colors.onBackground,
                style = SocialNetworkTheme.typography.bodyBold,
            )
        }
    }
}

@Composable
private fun WelcomeChallengeCoachMark(
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        CoachMarkTriangle(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = TrianglePosition),
        )
        Box(
            modifier = Modifier
                .shadow(elevation = SocialNetworkTheme.spacing.l)
                .background(
                    SocialNetworkTheme.colors.surface,
                    SocialNetworkTheme.shapes.medium,
                ),
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = SocialNetworkTheme.spacing.s)
                    .padding(vertical = SocialNetworkTheme.spacing.m),
                text = stringResource(
                    id = R.string.reward_onboarding_tooltip_reward_top_first_challenge,
                ),
                color = SocialNetworkTheme.colors.onBackground,
                style = SocialNetworkTheme.typography.bodyBold,
            )
        }
    }
}

@Composable
private fun CoachMarkTriangle(
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier,
    ) {
        val path = Path().apply {
            moveTo(
                x = 0f,
                y = TriangleHeight.toPx(),
            )
            lineTo(
                x = TriangleWidth.toPx() / 2,
                y = 0f,
            )
            lineTo(
                x = TriangleWidth.toPx(),
                y = TriangleHeight.toPx(),
            )
            close()
        }
        drawPath(
            path = path,
            color = TriangleColor,
        )
    }
}

private fun isInsideSpotlightArea(
    offset: Offset,
    rect: RectF,
): Boolean {
    return offset.x > rect.left &&
            offset.x < rect.right &&
            offset.y > rect.top &&
            offset.y < rect.bottom
}

private fun isInsideCoachMarkArea(
    type: SpotlightFragment.Type,
    offset: Offset,
    rouletteCoachMarkRectF: RectF,
    welcomeChallengeCoachMarkRectF: RectF,
): Boolean {
    return when (type) {
        SpotlightFragment.Type.Roulette -> {
            offset.x > rouletteCoachMarkRectF.left &&
                    offset.x < rouletteCoachMarkRectF.right &&
                    offset.y > rouletteCoachMarkRectF.top &&
                    offset.y < rouletteCoachMarkRectF.bottom
        }

        SpotlightFragment.Type.WelcomeChallenge -> {
            offset.x > welcomeChallengeCoachMarkRectF.left &&
                    offset.x < welcomeChallengeCoachMarkRectF.right &&
                    offset.y > welcomeChallengeCoachMarkRectF.top &&
                    offset.y < welcomeChallengeCoachMarkRectF.bottom
        }
    }
}

private val TriangleColor = Color(0xFFFFFFFF)
private val TrianglePosition = 12.dp
private val TriangleWidth = 20.dp
private val TriangleHeight = 12.dp
private val OverlayColor = Color(0x4D000000)
private const val OverlayShowDelayTimeMillis: Long = 200