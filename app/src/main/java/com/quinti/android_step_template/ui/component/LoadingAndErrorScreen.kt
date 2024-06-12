package com.quinti.android_step_template.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor.LoadState
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun <T : Reactor.State> LoadingAndErrorScreen(
    state: LoadState<T>,
    loadedContent: @Composable (T) -> Unit,
    onRetryClick: () -> Unit,
    backgroundColor: Color = SocialNetworkTheme.colors.background,
) {
    when (state) {
        is LoadState.Loading -> LoadingCircularProgressBar(
            backgroundColor = backgroundColor,
        )
        is LoadState.Loaded -> loadedContent(state.value)
        is LoadState.Error -> LoadErrorContent(
            errorMessage = state.message,
            onRetryClick = onRetryClick,
            backgroundColor = backgroundColor,
        )
    }
}

@Composable
fun <T : Reactor.State> LoadingAndErrorScreen(
    state: LoadState<T>,
    loadedContent: @Composable (T) -> Unit,
    loadingContent: @Composable () -> Unit,
    onRetryClick: () -> Unit,
    backgroundColor: Color = SocialNetworkTheme.colors.background,
) {
    when (state) {
        is LoadState.Loading -> loadingContent()
        is LoadState.Loaded -> loadedContent(state.value)
        is LoadState.Error -> LoadErrorContent(
            errorMessage = state.message,
            onRetryClick = onRetryClick,
            backgroundColor = backgroundColor,
        )
    }
}