package com.quinti.android_step_template.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Kyash KIG Theme の提供
 *
 * * KyashTheme.colors
 * * KyashTheme.typography
 */
@Composable
fun SocialNetworkTheme(
    colors: Colors = kyashLightColors,
    typography: Typography = Typography(),
    shapes: Shapes = Shapes(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalThemeColors provides colors,
        LocalThemeTypography provides typography,
        LocalThemeShapes provides shapes,
    ) {
        MaterialTheme(
            colors = colors.materialColors,
            typography = MaterialTypography,
        ) {
            content()
        }
    }
}

/**
 * KyashTheme Composable 内でアクセスできる CompositionLocal
 */
object SocialNetworkTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeTypography.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeShapes.current

    val spacing: Spacing
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeSpacing.current
}

/**
 * [MaterialTheme]のtypographyを上書きするための変数。
 * マテリアルコンポーネントの内部では[MaterialTheme.typography]を参照することがあるため、
 * その内容を恒久的に変えたいもののみをここで設定する。
 */
private val MaterialTypography: androidx.compose.material.Typography
    @Composable
    get() = androidx.compose.material.Typography(
        // ボタンの TextStyle は KIG で bodyBold となっている
        button = SocialNetworkTheme.typography.bodyBold,
    )

val LocalThemeColors = staticCompositionLocalOf { kyashLightColors }
val LocalThemeTypography = staticCompositionLocalOf { Typography() }
val LocalThemeShapes = staticCompositionLocalOf { Shapes() }
val LocalThemeSpacing = staticCompositionLocalOf { Spacing() }