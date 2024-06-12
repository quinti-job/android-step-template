package com.quinti.android_step_template.ui.theme

import androidx.compose.material.contentColorFor
import androidx.compose.ui.graphics.Color

// Brand Color
internal val kyashBrandBlue = Color(0xFF2AD1FF)
internal val kyashBrandGreen = Color(0xFF19E5B0)
internal val kyashBrandYellow = Color(0xFFFFC700)
internal val kyashBrandPink = Color(0xFFFF95C3)
internal val kyashBrandOrange = Color(0xFFFF6A56)
internal val kyashBrandNavy = Color(0xFF003AB8)

// UI Color
internal val kyashUiBlue = Color(0xFF0D97FF)
internal val kyashUiGreen = Color(0xFF00BD71)
internal val kyashBlack = Color(0xFF26262A)
internal val kyashGray700 = Color(0xFF565659)
internal val kyashGray500 = Color(0xFF808084)
internal val kyashGray400 = Color(0xFF9A9A9E)
internal val kyashGray300 = Color(0xFFB8B8BC)
internal val kyashGray200 = Color(0xFFD2D2D6)
internal val kyashGray100 = Color(0xFFEAEAEF)
internal val kyashGray50 = Color(0xFFF7F8F9)
internal val kyashWhite = Color(0xFFFFFFFF)

// New ui colors
internal val kyashBlue = Color(0xFF209FFF)
internal val kyashGreen = Color(0xFF00BF9D)
internal val kyashRed = Color(0xFFFF7978)
internal val kyashOrange = Color(0xFFFF945F)

// Functional Color
internal val kyashAttention = Color(0xFFFF910F)
internal val kyashSuccess1 = Color(0xFF00BD71)
internal val kyashSuccess2 = Color(0xFFD9F3E4)
internal val kyashCaution1 = Color(0xFFFF6A56)
internal val kyashCaution2 = Color(0xFFFDE9E8)

// System Background
internal val kyashLightBackground = Color(0xFFFFFFFF)
internal val kyashDarkBackground = Color(0xFF111111)

/**
 * Kyash KIG Light Color
 */
internal val kyashLightColors = object : Colors {
    // Material Colors
    override val materialColors: androidx.compose.material.Colors =
        androidx.compose.material.Colors(
            primary = kyashUiBlue,
            primaryVariant = kyashUiBlue,
            secondary = kyashUiGreen,
            secondaryVariant = kyashUiGreen,
            background = kyashLightBackground,
            surface = kyashLightBackground,
            error = kyashCaution1,
            onPrimary = kyashWhite,
            onSecondary = kyashWhite,
            onBackground = kyashBlack,
            onSurface = kyashBlack,
            onError = kyashWhite,
            isLight = true,
        )
    override val primary: Color = materialColors.primary
    override val primaryVariant: Color = materialColors.primaryVariant
    override val secondary: Color = materialColors.secondary
    override val secondaryVariant: Color = materialColors.secondaryVariant
    override val background: Color = materialColors.background
    override val surface: Color = materialColors.surface
    override val error: Color = materialColors.error
    override val onPrimary: Color = materialColors.onPrimary
    override val onSecondary: Color = materialColors.onSecondary
    override val onBackground: Color = materialColors.onBackground
    override val onSurface: Color = materialColors.onSurface
    override val onError: Color = materialColors.onError

    // Extended Colors
    override val attention: Color = kyashAttention
    override val success: Color = kyashSuccess1
    override val success2: Color = kyashSuccess2
    override val caution: Color = kyashCaution1
    override val caution2: Color = kyashCaution2
    override val border: Color = kyashGray200
    override val textColorPrimary: Color = kyashBlack
    override val textColorSecondary: Color = kyashGray700
    override val textColorTertiary: Color = kyashGray500
    override val textColorQuaternary: Color = kyashGray400
    override val textColorExtraLight: Color = kyashGray300
    override val textColorDisable: Color = kyashGray400
    override val backgroundVariant: Color = kyashGray50
    override val backgroundDark: Color = kyashGray100
    override val backgroundDarkVariant: Color = kyashGray200

    // Kyash Brand Colors
    override val brandKyash: Color = kyashBrandBlue
    override val brandKyashGreen: Color = kyashBrandGreen

    // Brand Colors
    override val brandFacebook: Color = Color(0xFF1877F2)
    override val brandApple: Color = Color.Black
    override val brandGoogle: Color = Color.White
}

/**
 * 新しいKIGのカラー定義
 *
 * 段階的に移行するため、利用は KyashTheme のパラメータに指定するか CompositionLocalProvider を通して行うこと。
 * 移行が完了したら KyashTheme のデフォルトを置き換える。
 */
val newKyashLightColors = object : Colors {
    // Material Colors
    override val materialColors: androidx.compose.material.Colors =
        androidx.compose.material.Colors(
            primary = kyashBlue,
            primaryVariant = kyashBlue,
            secondary = kyashGreen,
            secondaryVariant = kyashGreen,
            background = kyashLightBackground,
            surface = kyashLightBackground,
            error = kyashRed,
            onPrimary = kyashWhite,
            onSecondary = kyashWhite,
            onBackground = kyashBlack,
            onSurface = kyashBlack,
            onError = kyashWhite,
            isLight = true,
        )
    override val primary: Color = materialColors.primary
    override val primaryVariant: Color = materialColors.primaryVariant
    override val secondary: Color = materialColors.secondary
    override val secondaryVariant: Color = materialColors.secondaryVariant
    override val background: Color = materialColors.background
    override val surface: Color = materialColors.surface
    override val error: Color = materialColors.error
    override val onPrimary: Color = materialColors.onPrimary
    override val onSecondary: Color = materialColors.onSecondary
    override val onBackground: Color = materialColors.onBackground
    override val onSurface: Color = materialColors.onSurface
    override val onError: Color = materialColors.onError

    // Extended Colors
    override val attention: Color = kyashOrange
    override val success: Color = kyashSuccess1
    override val success2: Color = kyashSuccess2
    override val caution: Color = kyashRed
    override val caution2: Color = kyashCaution2
    override val border: Color = kyashGray200
    override val textColorPrimary: Color = kyashBlack
    override val textColorSecondary: Color = kyashGray700
    override val textColorTertiary: Color = kyashGray500
    override val textColorQuaternary: Color = kyashGray400
    override val textColorExtraLight: Color = kyashGray300
    override val textColorDisable: Color = kyashGray400
    override val backgroundVariant: Color = kyashGray50
    override val backgroundDark: Color = kyashGray100
    override val backgroundDarkVariant: Color = kyashGray200

    // Kyash Brand Colors
    override val brandKyash: Color = kyashBrandBlue
    override val brandKyashGreen: Color = kyashBrandGreen

    // Brand Colors
    override val brandFacebook: Color = Color(0xFF1877F2)
    override val brandApple: Color = Color.Black
    override val brandGoogle: Color = Color.White
}

/**
 * Kyash KIG Color パレット
 */
interface Colors {
    /**
     * Material Theme Color
     */
    val materialColors: androidx.compose.material.Colors

    // region material theme colors aliases
    val primary: Color
    val primaryVariant: Color
    val secondary: Color
    val secondaryVariant: Color
    val background: Color
    val surface: Color
    val error: Color
    val onPrimary: Color
    val onSecondary: Color
    val onBackground: Color
    val onSurface: Color
    val onError: Color

    // Extended Colors
    val attention: Color
    val success: Color
    val success2: Color
    val caution: Color
    val caution2: Color
    val border: Color
    val textColorPrimary: Color
    val textColorSecondary: Color
    val textColorTertiary: Color
    val textColorQuaternary: Color
    val textColorExtraLight: Color
    val textColorDisable: Color
    val backgroundVariant: Color
    val backgroundDark: Color
    val backgroundDarkVariant: Color

    // Kyash Brand Colors
    val brandKyash: Color
    val brandKyashGreen: Color

    // Brand Colors
    val brandFacebook: Color
    val brandApple: Color
    val brandGoogle: Color
}

val String.composeColor: Color?
    get() {
        return try {
            Color(android.graphics.Color.parseColor(this))
        } catch (e: Exception) {
            null
        }
    }

/**
 * [androidx.compose.material.Colors.contentColorFor]のKyashTheme拡張。
 */
fun Colors.contentColorFor(backgroundColor: Color): Color {
    return when (backgroundColor) {
        brandKyash,
        brandKyashGreen,
        -> kyashWhite
        /**
         * Use [androidx.compose.material.Colors.contentColorFor]
         */
        else -> materialColors.contentColorFor(backgroundColor)
    }
}