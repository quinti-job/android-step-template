package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun KyashTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    backgroundColor: Color = SocialNetworkTheme.colors.background,
    contentColor: Color = SocialNetworkTheme.colors.primary,
    indicator: @Composable
        (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        KyashTabRowDefaults.Indicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
        )
    },
    divider: @Composable () -> Unit = {},
    tabs: @Composable () -> Unit,
) = TabRow(
    selectedTabIndex = selectedTabIndex,
    modifier = modifier,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    indicator = indicator,
    divider = divider,
    tabs = tabs,
)

object KyashTabRowDefaults {

    @Composable
    fun Indicator(
        modifier: Modifier = Modifier,
        height: Dp = IndicatorHeight,
        color: Color = LocalContentColor.current,
    ) {
        // インジケータのバー部分を短くするためにBoxを入れ子にする
        Box(
            modifier = modifier,
            contentAlignment = Alignment.BottomCenter,
        ) {
            Box(
                modifier = Modifier
                    .widthIn(max = IndicatorMaxWidth)
                    .fillMaxWidth()
                    .height(height)
                    .background(color = color),
            )
        }
    }

    val IndicatorHeight = 2.dp
    val IndicatorMaxWidth = 120.dp
}

@Composable
fun KyashTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = SocialNetworkTheme.colors.primary,
    unselectedContentColor: Color = SocialNetworkTheme.colors.textColorTertiary,
) = Tab(
    selected = selected,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    text = text,
    icon = icon,
    interactionSource = interactionSource,
    selectedContentColor = selectedContentColor,
    unselectedContentColor = unselectedContentColor,
)

@Composable
fun KyashTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = SocialNetworkTheme.colors.primary,
    unselectedContentColor: Color = SocialNetworkTheme.colors.textColorTertiary,
) = KyashTab(
    selected = selected,
    onClick = onClick,
    modifier = modifier,
    enabled = enabled,
    text = {
        Text(
            text = text,
            // Typographyでテキストカラーを設定している都合で必要になる。
            color = if (selected) selectedContentColor else unselectedContentColor,
        )
    },
    icon = null,
    interactionSource = interactionSource,
    selectedContentColor = selectedContentColor,
    unselectedContentColor = unselectedContentColor,
)