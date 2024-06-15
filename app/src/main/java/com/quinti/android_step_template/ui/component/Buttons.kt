package com.quinti.android_step_template.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.R
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.ui.theme.contentColorFor
import kotlinx.coroutines.delay

@Composable
fun KyashButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = SocialNetworkTheme.shapes.round,
    border: BorderStroke? = null,
    colors: ButtonColors = KyashButtonDefault.buttonPrimaryColors(),
    contentPadding: PaddingValues = KyashButtonDefault.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    // 2重タップ制御
    var clickEnabled by remember { mutableStateOf(true) }
    LaunchedEffect(
        key1 = clickEnabled,
        block = {
            if (clickEnabled) return@LaunchedEffect
            delay(KyashButtonDefault.ClickThresholdMills)
            clickEnabled = true
        },
    )
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun KyashButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = SocialNetworkTheme.shapes.round,
    border: BorderStroke? = null,
    colors: ButtonColors = KyashButtonDefault.buttonPrimaryColors(),
    contentPadding: PaddingValues = KyashButtonDefault.ContentPadding,
) {
    KyashButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = elevation,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
    ) {
        Text(
            text = text,
            // Typographyでテキストカラーを設定している都合で必要になる。
            color = colors.contentColor(enabled = enabled).value,
        )
    }
}

@Composable
fun KyashOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = SocialNetworkTheme.shapes.round,
    border: BorderStroke? = KyashButtonDefault.outlinedPrimaryBorder(enabled = enabled),
    colors: ButtonColors = KyashButtonDefault.outlinedButtonPrimaryColors(),
    contentPadding: PaddingValues = KyashButtonDefault.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) = KyashButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    content = content,
)

@Composable
fun KyashOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = SocialNetworkTheme.shapes.round,
    border: BorderStroke? = KyashButtonDefault.outlinedPrimaryBorder(enabled = enabled),
    colors: ButtonColors = KyashButtonDefault.outlinedButtonPrimaryColors(),
    contentPadding: PaddingValues = KyashButtonDefault.ContentPadding,
) = KyashButton(
    text = text,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
)

@Composable
fun KyashTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = SocialNetworkTheme.shapes.round,
    border: BorderStroke? = null,
    colors: ButtonColors = KyashButtonDefault.textButtonPrimaryColors(),
    contentPadding: PaddingValues = KyashButtonDefault.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) = KyashButton(
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
    content = content,
)

@Composable
fun KyashTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    elevation: ButtonElevation? = null,
    shape: Shape = SocialNetworkTheme.shapes.round,
    border: BorderStroke? = null,
    colors: ButtonColors = KyashButtonDefault.textButtonPrimaryColors(),
    contentPadding: PaddingValues = KyashButtonDefault.ContentPadding,
) = KyashButton(
    text = text,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    interactionSource = interactionSource,
    elevation = elevation,
    shape = shape,
    border = border,
    colors = colors,
    contentPadding = contentPadding,
)

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
private fun UnelevatedButtonContent(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    clickThresholdMills: Long = 500L,
    content: @Composable RowScope.() -> Unit,
) {
    var clickEnabled by remember { mutableStateOf(true) }
    LaunchedEffect(
        key1 = clickEnabled,
        block = {
            if (clickEnabled) return@LaunchedEffect
            delay(clickThresholdMills)
            clickEnabled = true
        },
    )
    Button(
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        elevation = null,
        shape = shape,
        border = border,
        colors = colors,
        contentPadding = contentPadding,
        onClick = {
            if (clickEnabled) {
                clickEnabled = false
                onClick()
            }
        },
    ) {
        content.invoke(this)
    }
}

/**
 * ButtonFrameのみ既存styleを適用したButton
 */
@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
private fun KyashUnelevatedButtonContent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke? = null,
    content: @Composable RowScope.() -> Unit,
) {
    UnelevatedButtonContent(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        enabled = enabled,
        interactionSource = remember { MutableInteractionSource() },
        shape = shape,
        border = border,
        onClick = { onClick.invoke() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
        ),
        contentPadding = PaddingValues(
            horizontal = SocialNetworkTheme.spacing.m,
            vertical = SocialNetworkTheme.spacing.s,
        ),
        content = content,
    )
}

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
fun KyashMaterialButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape,
    backgroundColor: Color,
    text: String,
    border: BorderStroke? = null,
    textColor: Color = Color.White,
    maxLines: Int = 1,
) {
    KyashUnelevatedButtonContent(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = shape,
        border = border,
        backgroundColor = backgroundColor,
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = SocialNetworkTheme.typography.caption1.copy(
                color = if (enabled) textColor else SocialNetworkTheme.colors.textColorExtraLight,
            ),
            maxLines = maxLines,
        )
    }
}

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
fun KyashCornerRoundedMaterialBlueButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    KyashMaterialButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50),
        backgroundColor = SocialNetworkTheme.colors.primary,
        text = text,
    )
}

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
fun KyashCornerRoundedMaterialGreenButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    KyashMaterialButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50),
        backgroundColor = SocialNetworkTheme.colors.secondary,
        text = text,
    )
}

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
fun KyashCornerRound8dpMaterialSaxButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    KyashMaterialButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(SocialNetworkTheme.spacing.xs),
        backgroundColor = SocialNetworkTheme.colors.backgroundVariant,
        text = text,
        textColor = SocialNetworkTheme.colors.primary,
    )
}

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
fun KyashCornerRound8dpMaterialKyashGreenButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    KyashMaterialButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(SocialNetworkTheme.spacing.xs),
        backgroundColor = SocialNetworkTheme.colors.secondary,
        text = text,
        textColor = Color.White,
    )
}

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
fun KyashCornerRound8dpOutlinedKyashGreenButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    KyashMaterialButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(SocialNetworkTheme.spacing.xs),
        backgroundColor = SocialNetworkTheme.colors.background,
        border = BorderStroke(width = 1.dp, color = SocialNetworkTheme.colors.secondary),
        text = text,
        textColor = SocialNetworkTheme.colors.secondary,
    )
}

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
fun KyashCornerRounded8dpOutlinedKyashBlueButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    KyashMaterialButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(SocialNetworkTheme.spacing.xs),
        backgroundColor = SocialNetworkTheme.colors.background,
        border = BorderStroke(width = 1.dp, color = SocialNetworkTheme.colors.primary),
        text = text,
        textColor = SocialNetworkTheme.colors.primary,
    )
}

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
fun KyashCornerRounded8dpOutlinedKyashBlueButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    KyashUnelevatedButtonContent(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(SocialNetworkTheme.spacing.xs),
        backgroundColor = SocialNetworkTheme.colors.background,
        content = content,
        border = BorderStroke(width = 1.dp, color = SocialNetworkTheme.colors.primary),
        enabled = enabled,
    )
}

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
fun KyashCornerRound8dpMaterialKyashBlueButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    KyashMaterialButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(SocialNetworkTheme.spacing.xs),
        backgroundColor = SocialNetworkTheme.colors.primary,
        text = text,
        textColor = Color.White,
    )
}

@Composable
fun SignInWithAppleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes iconResId: Int,
    text: String,
) {
    KyashUnelevatedButtonContent(
        modifier = modifier,
        onClick = onClick,
        enabled = true,
        shape = RoundedCornerShape(50),
        backgroundColor = SocialNetworkTheme.colors.brandApple,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = Color.White,
            )
            Spacer(modifier = Modifier.size(SocialNetworkTheme.spacing.xs))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                style = SocialNetworkTheme.typography.caption1.copy(color = Color.White),
                maxLines = 1,
            )
        }
    }
}

@Composable
fun FacebookButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes iconResId: Int,
    text: String,
) {
    KyashUnelevatedButtonContent(
        modifier = modifier,
        onClick = onClick,
        enabled = true,
        shape = RoundedCornerShape(50),
        backgroundColor = SocialNetworkTheme.colors.brandFacebook,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = Color.White,
            )
            Spacer(modifier = Modifier.size(SocialNetworkTheme.spacing.xs))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                style = SocialNetworkTheme.typography.caption1.copy(color = Color.White),
                maxLines = 1,
            )
        }
    }
}

@Composable
fun SignInWithGoogleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @DrawableRes iconResId: Int,
    text: String,
) {
    KyashUnelevatedButtonContent(
        modifier = modifier,
        onClick = onClick,
        enabled = true,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, color = SocialNetworkTheme.colors.backgroundDark),
        backgroundColor = SocialNetworkTheme.colors.brandGoogle,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.size(SocialNetworkTheme.spacing.xs))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                style = SocialNetworkTheme.typography.caption1,
                maxLines = 1,
            )
        }
    }
}

@Deprecated("KyashButton / KyashOutlinedButton / KyashTextButton のいずれかを利用してください")
@Composable
fun KyashCornerRoundedMaterialSaxButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: String,
) {
    KyashUnelevatedButtonContent(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(50),
        backgroundColor = SocialNetworkTheme.colors.backgroundVariant,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(24.dp)) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                style = SocialNetworkTheme.typography.caption1.copy(
                    color = SocialNetworkTheme.colors.primary,
                ),
                maxLines = 1,
            )
        }
    }
}

object KyashButtonDefault {
    const val ClickThresholdMills = 500L
    private val ButtonHorizontalPadding = 16.dp
    private val ButtonVerticalPadding = 12.dp
    private val ButtonVerticalPaddingSmall = 8.dp

    val ContentPadding = PaddingValues(
        start = ButtonHorizontalPadding,
        top = ButtonVerticalPadding,
        end = ButtonHorizontalPadding,
        bottom = ButtonVerticalPadding,
    )

    val ContentPaddingSmall = PaddingValues(
        start = ButtonHorizontalPadding,
        top = ButtonVerticalPaddingSmall,
        end = ButtonHorizontalPadding,
        bottom = ButtonVerticalPaddingSmall,
    )

    @Composable
    fun buttonPrimaryColors(
        backgroundColor: Color = SocialNetworkTheme.colors.primary,
        contentColor: Color = SocialNetworkTheme.colors.contentColorFor(backgroundColor),
        disabledBackgroundColor: Color = SocialNetworkTheme.colors.backgroundDark,
        disabledContentColor: Color = SocialNetworkTheme.colors.textColorExtraLight,
    ): ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun buttonSecondaryColors(
        backgroundColor: Color = SocialNetworkTheme.colors.secondary,
        contentColor: Color = SocialNetworkTheme.colors.contentColorFor(backgroundColor),
        disabledBackgroundColor: Color = SocialNetworkTheme.colors.backgroundDark,
        disabledContentColor: Color = SocialNetworkTheme.colors.textColorExtraLight,
    ): ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun buttonDangerColors(
        backgroundColor: Color = SocialNetworkTheme.colors.error,
        contentColor: Color = SocialNetworkTheme.colors.contentColorFor(backgroundColor),
        disabledBackgroundColor: Color = SocialNetworkTheme.colors.backgroundDark,
        disabledContentColor: Color = SocialNetworkTheme.colors.textColorExtraLight,
    ): ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun textButtonPrimaryColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = SocialNetworkTheme.colors.primary,
        disabledContentColor: Color = SocialNetworkTheme.colors.textColorExtraLight,
    ): ButtonColors = ButtonDefaults.textButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun textButtonSecondaryColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = SocialNetworkTheme.colors.secondary,
        disabledContentColor: Color = SocialNetworkTheme.colors.textColorExtraLight,
    ): ButtonColors = ButtonDefaults.textButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun textButtonDangerColors(
        backgroundColor: Color = Color.Transparent,
        contentColor: Color = SocialNetworkTheme.colors.error,
        disabledContentColor: Color = SocialNetworkTheme.colors.textColorExtraLight,
    ): ButtonColors = ButtonDefaults.textButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedButtonPrimaryColors(
        backgroundColor: Color = SocialNetworkTheme.colors.background,
        contentColor: Color = SocialNetworkTheme.colors.primary,
        disabledContentColor: Color = SocialNetworkTheme.colors.textColorExtraLight,
    ): ButtonColors = ButtonDefaults.outlinedButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedButtonSecondaryColors(
        backgroundColor: Color = SocialNetworkTheme.colors.background,
        contentColor: Color = SocialNetworkTheme.colors.secondary,
        disabledContentColor: Color = SocialNetworkTheme.colors.textColorExtraLight,
    ): ButtonColors = ButtonDefaults.outlinedButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )

    @Composable
    fun outlinedButtonDangerColors(
        backgroundColor: Color = SocialNetworkTheme.colors.background,
        contentColor: Color = SocialNetworkTheme.colors.error,
        disabledContentColor: Color = SocialNetworkTheme.colors.textColorExtraLight,
    ): ButtonColors = ButtonDefaults.outlinedButtonColors(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
    )

    val OutlinedBorderSize = 1.dp

    @Composable
    fun outlinedPrimaryBorder(enabled: Boolean = true): BorderStroke = BorderStroke(
        width = OutlinedBorderSize,
        color = if (enabled) SocialNetworkTheme.colors.primary else SocialNetworkTheme.colors.textColorExtraLight,
    )

    @Composable
    fun outlinedSecondaryBorder(enabled: Boolean = true): BorderStroke = BorderStroke(
        width = OutlinedBorderSize,
        color = if (enabled) SocialNetworkTheme.colors.secondary else SocialNetworkTheme.colors.textColorExtraLight,
    )

    @Composable
    fun outlinedDangerBorder(enabled: Boolean = true): BorderStroke = BorderStroke(
        width = OutlinedBorderSize,
        color = if (enabled) SocialNetworkTheme.colors.error else SocialNetworkTheme.colors.textColorExtraLight,
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
private fun FacebookButtonPreview() {
    FacebookButton(
        onClick = {},
        iconResId = R.drawable.ic_facebook,
        text = "Facebookで始める",
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
private fun SignInWithGoogleButtonPreview() {
    SignInWithGoogleButton(
        onClick = {},
        iconResId = R.drawable.ic_google,
        text = "Googleで始める",
    )
}