package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimatedGiftBox(
    onAnimationEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset(
            "lottie/LottieResultGiftBoxAnimation.json",
        ),
    )
    var called by remember { mutableStateOf(false) }
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1,
    )

    // 完了を検知する
    if (progress >= ContentCrossFadeStartProgressThreshold && !called) {
        called = true
        onAnimationEnd()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AnimatedGiftBoxBackgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        BoxWithConstraints {
            val thin = min(maxWidth, this.maxHeight)

            val wide = max(maxWidth, this.maxHeight)

            val scale = wide / thin

            LottieAnimation(
                modifier = Modifier
                    .size(thin)
                    .aspectRatio(1f)
                    .scale(scale),
                composition = composition,
                progress = { progress },
            )
        }
    }
}
private val AnimatedGiftBoxBackgroundColor = Color(0xFFBCE2FF)
private const val ContentCrossFadeStartProgressThreshold = 0.7f