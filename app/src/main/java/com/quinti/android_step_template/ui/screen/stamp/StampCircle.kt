package com.quinti.android_step_template.ui.screen.stamp

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.entity.Stamp
import com.quinti.android_step_template.ui.component.DisabledKyashCoinIcon
import com.quinti.android_step_template.ui.component.DisabledPointIcon
import com.quinti.android_step_template.ui.component.KyashCoinIcon
import com.quinti.android_step_template.ui.component.PointIcon
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Suppress("LongMethod")
@Composable
fun StampCircleNoStamped(reward: Stamp.Reward) {
    val borderColor = SocialNetworkTheme.colors.backgroundDark
    Column {
        Box(
            modifier = Modifier
                .padding(bottom = SocialNetworkTheme.spacing.xxs)
                .size(StampIconSize)
                .background(
                    color = SocialNetworkTheme.colors.onPrimary,
                    shape = CircleShape,
                )
                .border(
                    StampIconBorderSize,
                    borderColor,
                    shape = CircleShape,
                ),
        ) {
            Surface(
                modifier = Modifier
                    .padding(SocialNetworkTheme.spacing.xxs)
                    .size(StampIconInnerBorderWidth),
                shape = CircleShape,
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val pathEffect = PathEffect.dashPathEffect(
                        floatArrayOf(DashLength, SpaceLength),
                        0f,
                    )
                    drawCircle(
                        color = borderColor,
                        radius = size.minDimension / 2,
                        style = Stroke(
                            width = StampIconInnerBorderSize.toPx(),
                            pathEffect = pathEffect,
                        ),
                    )
                }
            }
            when (reward) {
                is Stamp.Reward.Coin -> {
                    val fontScale = LocalDensity.current.fontScale
                    Row(
                        modifier = Modifier.align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        DisabledKyashCoinIcon(
                            modifier = Modifier.size(18.dp * fontScale),
                        )
                        Text(
                            text = stringResource(
                                id = R.string.reward_kyach_coin_balance,
                                reward.amount,
                            ),
                            style = SocialNetworkTheme.typography.headlineBold,
                            color = SocialNetworkTheme.colors.textColorExtraLight,
                        )
                    }
                }

                is Stamp.Reward.Point -> {
                    val fontScale = LocalDensity.current.fontScale
                    Row(
                        modifier = Modifier.align(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                    ) {
                        DisabledPointIcon(
                            modifier = Modifier.size(18.dp * fontScale),
                        )
                        Text(
                            text = "???",
                            style = SocialNetworkTheme.typography.headlineBold,
                            color = SocialNetworkTheme.colors.textColorExtraLight,
                        )
                    }
                }

                Stamp.Reward.Unknown -> {}
            }
        }
    }
}

@Composable
fun KyashCoinStampCircle(
    amount: Long?,
    scale: Animatable<Float, AnimationVector1D>,
    opacity: Animatable<Float, AnimationVector1D>,
) {
    Column(
        modifier = Modifier.graphicsLayer(
            alpha = opacity.value,
            scaleX = scale.value,
            scaleY = scale.value,
        ),
    ) {
        Box(
            modifier = Modifier
                .size(StampIconSize)
                .background(
                    color = SocialNetworkTheme.colors.primary,
                    shape = CircleShape,
                )
                .padding(SocialNetworkTheme.spacing.xxs)
                .border(
                    StampIconInnerBorderSize,
                    SocialNetworkTheme.colors.onPrimary,
                    shape = CircleShape,
                ),
        ) {
            val fontScale = LocalDensity.current.fontScale
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                KyashCoinIcon(
                    modifier = Modifier.size(18.dp * fontScale),
                )
                if (amount != null) {
                    Text(
                        text = stringResource(
                            id = R.string.reward_kyach_coin_balance,
                            amount,
                        ),
                        style = SocialNetworkTheme.typography.headlineBold,
                        color = SocialNetworkTheme.colors.onPrimary,
                    )
                } else {
                    Text(
                        text = "-",
                        style = SocialNetworkTheme.typography.headlineBold,
                        color = SocialNetworkTheme.colors.onPrimary,
                    )
                }
            }
        }
    }
}

@Composable
fun PointStampCircle(
    amount: Long?,
    scale: Animatable<Float, AnimationVector1D>,
    opacity: Animatable<Float, AnimationVector1D>,
) {
    Column(
        modifier = Modifier.graphicsLayer(
            alpha = opacity.value,
            scaleX = scale.value,
            scaleY = scale.value,
        ),
    ) {
        Box(
            modifier = Modifier
                .size(StampIconSize)
                .background(
                    color = SocialNetworkTheme.colors.attention,
                    shape = CircleShape,
                )
                .padding(SocialNetworkTheme.spacing.xxs)
                .border(
                    StampIconInnerBorderSize,
                    SocialNetworkTheme.colors.onPrimary,
                    shape = CircleShape,
                ),
        ) {
            val fontScale = LocalDensity.current.fontScale
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                PointIcon(
                    modifier = Modifier.size(18.dp * fontScale),
                )
                if (amount != null) {
                    Text(
                        text = stringResource(
                            id = R.string.reward_kyach_coin_balance,
                            amount,
                        ),
                        style = SocialNetworkTheme.typography.headlineBold,
                        color = SocialNetworkTheme.colors.onPrimary,
                    )
                } else {
                    Text(
                        text = "-",
                        style = SocialNetworkTheme.typography.headlineBold,
                        color = SocialNetworkTheme.colors.onPrimary,
                    )
                }
            }
        }
    }
}

private val StampIconSize = 80.dp
private val StampIconBorderSize = 1.dp
private val StampIconInnerBorderWidth = 72.dp
private val StampIconInnerBorderSize = 1.5.dp
private const val DashLength = 10f
private const val SpaceLength = 10f
private const val DefaultScale: Float = 1f
private const val DefaultOpacity: Float = 1f

@Preview(showBackground = true)
@Composable
private fun KyashCoinNoStampedPreview() {
    StampCircleNoStamped(
        reward = Stamp.Reward.Coin(amount = 100),
    )
}

@Preview(showBackground = true)
@Composable
private fun PointNoStampedPreview() {
    StampCircleNoStamped(
        reward = Stamp.Reward.Point(
            amount = 100,
            options = listOf(1, 2, 3),
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun KyashCoinStampCirclePreview() {
    val scale = remember {
        Animatable(DefaultScale)
    }
    val opacity = remember {
        Animatable(DefaultOpacity)
    }
    KyashCoinStampCircle(
        amount = 100,
        scale = scale,
        opacity = opacity,
    )
}

@Preview(showBackground = true)
@Composable
private fun PointStampCirclePreview() {
    val scale = remember {
        Animatable(DefaultScale)
    }
    val opacity = remember {
        Animatable(DefaultOpacity)
    }
    PointStampCircle(
        amount = 1,
        scale = scale,
        opacity = opacity,
    )
}