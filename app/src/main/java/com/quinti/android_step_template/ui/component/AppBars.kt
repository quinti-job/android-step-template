package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.R
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.ui.theme.contentColorFor

@Composable
fun KyashTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = SocialNetworkTheme.colors.background,
    contentColor: Color = SocialNetworkTheme.colors.contentColorFor(backgroundColor),
    windowInsets: WindowInsets = KyashTopAppBarDefaults.TopAppBarWindowInsets,
    elevation: Dp = KyashTopAppBarDefaults.Elevation,
) {
    Surface(
        modifier = modifier,
        elevation = elevation,
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        TopAppBar(
            title = title,
            modifier = Modifier.windowInsetsPadding(windowInsets),
            navigationIcon = navigationIcon,
            actions = actions,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            elevation = 0.dp,
        )
    }
}

@Composable
fun KyashTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = null,
    contentDescription: String? = null,
    onNavigationClick: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = SocialNetworkTheme.colors.background,
    contentColor: Color = SocialNetworkTheme.colors.contentColorFor(backgroundColor),
    windowInsets: WindowInsets = KyashTopAppBarDefaults.TopAppBarWindowInsets,
    elevation: Dp = KyashTopAppBarDefaults.Elevation,
) {
    KyashTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon?.let { icon ->
            {
                IconButton(onClick = onNavigationClick) {
                    Icon(
                        imageVector = icon,
                        contentDescription = contentDescription,
                        tint = contentColor,
                    )
                }
            }
        },
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        windowInsets = windowInsets,
        elevation = elevation,
    )
}

@Composable
fun KyashBackTopAppBar(
    title: String,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = SocialNetworkTheme.colors.background,
    contentColor: Color = SocialNetworkTheme.colors.contentColorFor(backgroundColor),
    windowInsets: WindowInsets = KyashTopAppBarDefaults.TopAppBarWindowInsets,
    elevation: Dp = KyashTopAppBarDefaults.Elevation,
) {
    KyashTopAppBar(
        title = title,
        navigationIcon = Icons.Filled.ArrowBack,
        contentDescription = stringResource(id = R.string.sn_content_description_back),
        onNavigationClick = onNavigationClick,
        modifier = modifier,
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        windowInsets = windowInsets,
        elevation = elevation,
    )
}

@Composable
fun KyashCloseTopAppBar(
    title: String,
    onNavigationClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = SocialNetworkTheme.colors.background,
    contentColor: Color = SocialNetworkTheme.colors.contentColorFor(backgroundColor),
    windowInsets: WindowInsets = KyashTopAppBarDefaults.TopAppBarWindowInsets,
    elevation: Dp = KyashTopAppBarDefaults.Elevation,
) {
    KyashTopAppBar(
        title = title,
        navigationIcon = Icons.Filled.Close,
        contentDescription = stringResource(id = R.string.sn_content_description_close),
        onNavigationClick = onNavigationClick,
        modifier = modifier,
        actions = actions,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        windowInsets = windowInsets,
        elevation = elevation,
    )
}

object KyashTopAppBarDefaults {

    val Elevation = 4.dp

    val TopAppBarWindowInsets: WindowInsets
        @Composable
        get() = WindowInsets.systemBars
            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
}