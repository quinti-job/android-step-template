package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun KyashSwipeRefresh(
    state: PullRefreshState,
    refreshing: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier.pullRefresh(state)) {
        content()
        PullRefreshIndicator(
            refreshing = refreshing,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = SocialNetworkTheme.colors.primary,
        )
    }
}
