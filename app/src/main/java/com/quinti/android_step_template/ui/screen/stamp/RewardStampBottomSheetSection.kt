package com.quinti.android_step_template.ui.screen.stamp

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.entity.Stamp
import com.quinti.android_step_template.kmp.data.entity.StampCard
import com.quinti.android_step_template.kmp.data.entity.StampCardUiState
import com.quinti.android_step_template.kmp.data.entity.StampUiState
import com.quinti.android_step_template.kmp.data.entity.needRouletteAnimation
import com.quinti.android_step_template.ui.component.HalfModalBottomSheetHandle
import com.quinti.android_step_template.ui.component.KyashOutlinedButton
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import korlibs.time.DateTimeTz
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RewardStampBottomSheetSection(
    stampCardUiState: StampCardUiState,
    onClose: () -> Unit,
    onHelpClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = SocialNetworkTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = SocialNetworkTheme.spacing.m),
        ) {
            HalfModalBottomSheetHandle(
                modifier = Modifier
                    .padding(
                        top = SocialNetworkTheme.spacing.xs,
                        bottom = SocialNetworkTheme.spacing.xs,
                    )
                    .align(Alignment.CenterHorizontally),
            )
            TitleAndDescriptionSection(
                modifier = Modifier
                    .padding(
                        bottom = SocialNetworkTheme.spacing.l,
                    ),
                onHelpClick = onHelpClick,
            )
            AnimatedStampAndRouletteSection(
                stamps = stampCardUiState.stamps,
                hasNeedRouletteAnimation = stampCardUiState.hasNeedRouletteAnimation,
            )
            CloseButton(
                onClose = onClose,
            )
        }
    }
}

@Composable
private fun TitleAndDescriptionSection(
    modifier: Modifier,
    onHelpClick: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    bottom = SocialNetworkTheme.spacing.xs,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SocialNetworkTheme.spacing.xxxs),
        ) {
            Text(
                text = stringResource(id = R.string.reward_stamp_bottom_sheet_title),
                style = SocialNetworkTheme.typography.title3Bold,
                color = SocialNetworkTheme.colors.textColorPrimary,
                modifier = Modifier.padding(end = SocialNetworkTheme.spacing.xxs),
            )
            IconButton(
                onClick = onHelpClick,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.reward_ic_question_mark_circle),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
        }
        Text(
            text = stringResource(id = R.string.reward_stamp_bottom_sheet_description),
            style = SocialNetworkTheme.typography.paragraph2,
            color = SocialNetworkTheme.colors.textColorSecondary,
            modifier = Modifier.padding(bottom = SocialNetworkTheme.spacing.m),
        )
    }
}

@Suppress("LongMethod")
@Composable
private fun AnimatedStampAndRouletteSection(
    stamps: List<StampUiState>,
    hasNeedRouletteAnimation: Boolean,
) {
    val stampParameters = remember(stamps) {
        stamps.map { stamp ->
            val scale = Animatable(
                if (stamp.needAnimation) MaxScale else DefaultScale,
            )
            val opacity =
                Animatable(
                    if (stamp.needAnimation) MinimOpacity else DefaultOpacity,
                )
            val checkIconVisibleState =
                mutableStateOf(stamp.isExchanged)
            val rouletteEnabled = stamp.stamp.reward.needRouletteAnimation
            StampParameter(
                scale = scale,
                opacity = opacity,
                checkIconVisible = checkIconVisibleState,
                currentCount = stamp.currentCount,
                needAnimation = stamp.needAnimation,
                rouletteEnabled = rouletteEnabled,
            )
        }
    }
    val animationSequence =
        remember(stampParameters) {
            stampParameters.filter { it.needAnimation }.map { it }
        }
    val amountOptions: List<Long> = stamps
        .mapNotNull { it.reward as? Stamp.Reward.Point }.flatMap { it.options }
    val pointAmount: Long? = stamps
        .mapNotNull { it.reward as? Stamp.Reward.Point }.firstOrNull()?.amount
    val rouletteState = remember {
        mutableStateOf<RouletteState>(RouletteState.Ready(amountOptions = amountOptions))
    }
    val startPointAnimate = remember { mutableStateOf(false) }
    val displayText = remember { mutableStateOf("---") }
    LaunchedEffect(animationSequence) {
        animationSequence.forEach { state ->
            delay(StampAnimationDelayTimeMillis)
            if (state.rouletteEnabled) {
                val amountResult = pointAmount ?: 0
                rouletteState.value = RouletteState.Start(
                    amountOptions = amountOptions,
                    amountResult = amountResult,
                )
                delay(RouletteAnimationDurationMillis)
                startPointAnimate.value = true
                displayText.value = amountResult.toString()
                delay(PointAnimationDelayTime)
            }
            launch {
                state.scale.animateTo(
                    targetValue = 0.95f,
                    animationSpec = tween(
                        durationMillis = 333,
                        easing = FastOutSlowInEasing,
                    ),
                )
                state.scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 83, easing = Ease),
                )
                state.checkIconVisible.value = true
            }
            launch {
                state.opacity.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 167),
                )
            }
        }
    }
    StampCardSection(
        stamps = stamps,
        stampParameters = stampParameters,
    )
    if (hasNeedRouletteAnimation) {
        StampPointRoulette(
            rouletteState = rouletteState.value,
            amountOptions = amountOptions,
            displayText = displayText.value,
            shouldPointAnimate = startPointAnimate.value,
        )
    }
}

@Composable
private fun StampCardSection(
    stamps: List<StampUiState>,
    stampParameters: List<StampParameter>,
) {
    val enableBonusText = stamps.any { it.stamp.type is Stamp.Type.Bonus }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = SocialNetworkTheme.spacing.l),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        stamps.forEachIndexed { index, stamp ->
            val hasBonus = stamp.stamp.type is Stamp.Type.Bonus
            val bonusText = (stamp.stamp.type as? Stamp.Type.Bonus)?.title ?: ""
            val isExchanged = stampParameters[index].checkIconVisible.value
            val stampParameter = stampParameters[index]
            Column {
                when (stamp.reward) {
                    is Stamp.Reward.Coin -> {
                        CoinStamp(
                            hasBonus = hasBonus,
                            bonusText = bonusText,
                            isExchanged = isExchanged,
                            reward = stamp.reward,
                            stampParameter = stampParameter,
                            isStamped = stamp.isStamped,
                            enableBonusText = enableBonusText,
                        )
                    }

                    is Stamp.Reward.Point -> {
                        PointStamp(
                            hasBonus = hasBonus,
                            bonusText = bonusText,
                            isExchanged = isExchanged,
                            reward = stamp.reward,
                            stampParameter = stampParameter,
                            isStamped = stamp.isStamped,
                            enableBonusText = enableBonusText,
                        )
                    }

                    Stamp.Reward.Unknown -> {}
                }
                CheckIconWithCountText(
                    isChecked = isExchanged,
                    currentCount = stamp.currentCount,
                )
            }
        }
    }
}

@Composable
private fun CheckIconWithCountText(
    isChecked: Boolean,
    currentCount: Int,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.width(CountTextSectionWidth),
    ) {
        if (isChecked) {
            Image(
                painter = painterResource(id = R.drawable.reward_ic_checked),
                contentDescription = null,
                modifier = Modifier.padding(end = SocialNetworkTheme.spacing.xxxs),
            )
        }
        Text(
            text = stringResource(
                id = R.string.reward_stamp_bottom_sheet_count,
                currentCount,
            ),
            style = SocialNetworkTheme.typography.caption2,
            color = SocialNetworkTheme.colors.textColorSecondary,
        )
    }
}

@Composable
private fun CoinStamp(
    enableBonusText: Boolean,
    hasBonus: Boolean,
    bonusText: String,
    isExchanged: Boolean,
    reward: Stamp.Reward.Coin,
    stampParameter: StampParameter,
    isStamped: Boolean,
) {
    if (enableBonusText) {
        Box(
            modifier = Modifier
                .height(20.dp)
                .padding(bottom = SocialNetworkTheme.spacing.xxxs),
        ) {
            if (hasBonus) {
                Text(
                    text = bonusText,
                    style = SocialNetworkTheme.typography.caption2Bold,
                    color = if (isExchanged) {
                        SocialNetworkTheme.colors.primary
                    } else {
                        SocialNetworkTheme.colors.textColorExtraLight
                    },
                )
            }
        }
    }
    Box {
        StampCircleNoStamped(reward = reward)
        if (isStamped) {
            KyashCoinStampCircle(
                amount = reward.amount,
                scale = stampParameter.scale,
                opacity = stampParameter.opacity,
            )
        }
    }
}

@Composable
private fun PointStamp(
    enableBonusText: Boolean,
    hasBonus: Boolean,
    bonusText: String,
    isExchanged: Boolean,
    reward: Stamp.Reward.Point,
    stampParameter: StampParameter,
    isStamped: Boolean,
) {
    if (enableBonusText) {
        Box(
            modifier = Modifier
                .height(20.dp)
                .padding(bottom = SocialNetworkTheme.spacing.xxxs),
        ) {
            if (hasBonus) {
                Text(
                    text = bonusText,
                    style = SocialNetworkTheme.typography.caption2Bold,
                    color = if (isExchanged) {
                        SocialNetworkTheme.colors.attention
                    } else {
                        SocialNetworkTheme.colors.textColorExtraLight
                    },
                )
            }
        }
    }
    Box {
        StampCircleNoStamped(reward = reward)
        if (isStamped) {
            PointStampCircle(
                amount = reward.amount,
                scale = stampParameter.scale,
                opacity = stampParameter.opacity,
            )
        }
    }
}

@Composable
private fun CloseButton(
    onClose: () -> Unit,
) {
    KyashOutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = SocialNetworkTheme.spacing.l),
        text = stringResource(
            id = R.string.reward_stamp_bottom_sheet_close_button,
        ),
        onClick = onClose,
    )
}

private const val MaxScale = 1.2f
private const val DefaultScale = 1f
private const val MinimOpacity = 0f
private const val DefaultOpacity = 1f
private const val StampAnimationDelayTimeMillis: Long = 600
private const val RouletteAnimationDurationMillis: Long = 3500
private const val PointAnimationDelayTime = 600L
private val CountTextSectionWidth = 80.dp

data class StampParameter(
    val scale: Animatable<Float, AnimationVector1D>,
    val opacity: Animatable<Float, AnimationVector1D>,
    val checkIconVisible: MutableState<Boolean>,
    val currentCount: Int,
    val needAnimation: Boolean,
    val rouletteEnabled: Boolean,
)

private fun mockStampCardUiState(): StampCardUiState {
    val stamps = listOf(
        Stamp(
            type = Stamp.Type.Normal,
            stampedAt = DateTimeTz.nowLocal(),
            exchangedAt = DateTimeTz.nowLocal(),
            reward = Stamp.Reward.Coin(amount = 100),
        ),
        Stamp(
            type = Stamp.Type.Normal,
            stampedAt = DateTimeTz.nowLocal(),
            exchangedAt = DateTimeTz.nowLocal(),
            reward = Stamp.Reward.Coin(amount = 200),
        ),
        Stamp(
            type = Stamp.Type.Normal,
            stampedAt = DateTimeTz.nowLocal(),
            exchangedAt = DateTimeTz.nowLocal(),
            reward = Stamp.Reward.Coin(amount = 300),
        ),
        Stamp(
            type = Stamp.Type.Bonus("初回ボーナス"),
            stampedAt = DateTimeTz.nowLocal(),
            exchangedAt = null,
            reward = Stamp.Reward.Point(
                amount = 250L,
                options = listOf(1, 100, 2, 4, 5, 1, 350, 250),
            ),
        ),
    )
    val stampCard = StampCard(
        id = "1",
        stamps = stamps,
        isCompleted = false,
    )
    return StampCardUiState(stampCard = stampCard)
}

@Preview(showBackground = true)
@Composable
private fun RewardStampBottomSheetSectionPreview() {
    SocialNetworkTheme {
        RewardStampBottomSheetSection(
            stampCardUiState = mockStampCardUiState(),
            onClose = {},
            onHelpClick = {},
        )
    }
}