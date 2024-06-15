package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.kyash.kig.compose.clickableWithoutDoubleTap
import com.quinti.android_step_template.R
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
@Suppress("LongMethod", "MagicNumber")
fun CoachMark(
    onClose: () -> Unit,
) {
    val screenWidthDpInt = LocalConfiguration.current.screenWidthDp
    val triangleOffsetX = -(screenWidthDpInt * 0.25).dp + ((TriangleWidth + 8.dp) / 2)
    Column(
        modifier = Modifier
            .fillMaxWidth().padding(
                bottom = TriangleHeight + SocialNetworkTheme.spacing.xxxs,
            ),
    ) {
        Box(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { },
                )
                .padding(start = SocialNetworkTheme.spacing.xs)
                .shadow(
                    elevation = SocialNetworkTheme.spacing.l,
                )
                .background(
                    SocialNetworkTheme.colors.surface,
                    SocialNetworkTheme.shapes.medium,
                ),
        ) {
            Row(
                modifier = Modifier.padding(
                    start = SocialNetworkTheme.spacing.s,
                    bottom = SocialNetworkTheme.spacing.s,
                ),
                verticalAlignment = Alignment.Top,
            ) {
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            top = SocialNetworkTheme.spacing.s,
                        ),
                ) {
                    Text(
                        text = stringResource(id = R.string.reward_coach_mark_bold_description),
                        color = SocialNetworkTheme.colors.onBackground,
                        style = SocialNetworkTheme.typography.bodyBold,
                    )
                }
                Box(
                    modifier = Modifier
                        .clickableWithoutDoubleTap { onClose() }
                        .align(Alignment.Top),
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(SocialNetworkTheme.spacing.xs),

                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = co.kyash.core.R.string.close),
                    )
                }
            }
        }
        InvertedTriangle(
            modifier = Modifier
                .offset(x = triangleOffsetX)
                .align(Alignment.CenterHorizontally),
        )
    }
}

@Composable
private fun InvertedTriangle(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(x = TriangleWidth.toPx() / 2, y = TriangleHeight.toPx())
            lineTo(x = TriangleWidth.toPx(), y = 0f)
            lineTo(x = 0f, y = 0f)
            close()
        }
        drawPath(path = path, color = TriangleColor)
    }
}

private val TriangleColor = Color(0xFFFFFFFF)
private val TriangleWidth = 20.dp
private val TriangleHeight = 12.dp

@Preview(showBackground = true)
@Composable
fun CoachMarkPreview() {
    SocialNetworkTheme {
        CoachMark {
        }
    }
}