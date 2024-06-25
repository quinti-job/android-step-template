package com.quinti.android_step_template.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

/**
 * Kyash Typography
 */
@Immutable
data class Typography internal constructor(
    val largeTitle: TextStyle = TextStyle(
        fontSize = 40.sp,
        lineHeight = DefaultLineHeight,
    ),
    val largeTitleBold: TextStyle = largeTitle.copy(
        fontWeight = FontWeight.Bold,
    ),
    val title1: TextStyle = TextStyle(
        fontSize = 36.sp,
        lineHeight = DefaultLineHeight,
    ),
    val title1Bold: TextStyle = title1.copy(
        fontWeight = FontWeight.Bold,
    ),
    val title2: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = DefaultLineHeight,
    ),
    val title2Bold: TextStyle = title2.copy(
        fontWeight = FontWeight.Bold,
    ),
    val title3: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = DefaultLineHeight,
    ),
    val title3Bold: TextStyle = title3.copy(
        fontWeight = FontWeight.Bold,
    ),
    val headline: TextStyle = TextStyle(
        fontSize = 17.sp,
        lineHeight = DefaultLineHeight,
    ),
    val headlineBold: TextStyle = headline.copy(
        fontWeight = FontWeight.Bold,
    ),
    val paragraph1: TextStyle = TextStyle(
        fontSize = 17.sp,
        lineHeight = 1.72.em,
    ),
    val paragraph1Bold: TextStyle = paragraph1.copy(
        fontWeight = FontWeight.Bold,
    ),
    val body: TextStyle = TextStyle(
        fontSize = 15.sp,
        lineHeight = DefaultLineHeight,
    ),
    val bodyBold: TextStyle = body.copy(
        fontWeight = FontWeight.Bold,
    ),
    val paragraph2: TextStyle = TextStyle(
        fontSize = 15.sp,
        lineHeight = 1.7.em,
    ),
    val paragraph2Bold: TextStyle = paragraph2.copy(
        fontWeight = FontWeight.Bold,
    ),
    val caption1: TextStyle = TextStyle(
        fontSize = 13.sp,
        lineHeight = DefaultLineHeight,
    ),
    val captionBold: TextStyle = caption1.copy(
        fontWeight = FontWeight.Bold,
    ),
    val caption2: TextStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = DefaultLineHeight,
    ),
    val caption2Bold: TextStyle = caption2.copy(
        fontWeight = FontWeight.Bold,
    ),
    val link: TextStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = DefaultLineHeight,
    ),
    val linkBold: TextStyle = link.copy(
        fontWeight = FontWeight.Bold,
    ),
)

private val DefaultLineHeight: TextUnit = 1.5.em