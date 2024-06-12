package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.R
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

sealed class LoadState<out T> {
    data object Loading : LoadState<Nothing>()
    data class Loaded<out T>(val value: T) : LoadState<T>()
    data class Error(val throwable: Throwable) : LoadState<Nothing>()
}

@Composable
fun <T> LoadingAndErrorScreen(
    state: LoadState<T>,
    loadedContent: @Composable (T) -> Unit,
    onRetryClick: () -> Unit,
) {
    when (state) {
        is LoadState.Loading -> LoadingCircularProgressBar()
        is LoadState.Loaded -> loadedContent.invoke(state.value)
        is LoadState.Error -> LoadErrorContent(onRetryClick = onRetryClick)
    }
}

@Composable
fun <T> LoadingAndErrorScreen(
    state: LoadState<T>,
    loadingContent: @Composable () -> Unit,
    loadedContent: @Composable (T) -> Unit,
    onRetryClick: () -> Unit,
) {
    when (state) {
        is LoadState.Loading -> loadingContent()
        is LoadState.Loaded -> loadedContent.invoke(state.value)
        is LoadState.Error -> LoadErrorContent(onRetryClick = onRetryClick)
    }
}

@Composable
fun LoadingCircularProgressBar(
    backgroundColor: Color = SocialNetworkTheme.colors.background,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = SocialNetworkTheme.colors.primary,
        )
    }
}

@Composable
fun LoadErrorContent(
    errorMessage: String? = null,
    onRetryClick: () -> Unit,
    backgroundColor: Color = SocialNetworkTheme.colors.background,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = errorMessage ?: stringResource(id = R.string.sn_load_state_error_title),
                style = SocialNetworkTheme.typography.body,
            )
            OutlinedButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = SocialNetworkTheme.spacing.l),
                onClick = onRetryClick,
                border = BorderStroke(1.dp, SocialNetworkTheme.colors.primary),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.sn_load_state_error_retry),
                    style = SocialNetworkTheme.typography.body.copy(
                        color = SocialNetworkTheme.colors.primary,
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }
        }
    }
}

@Composable
@Preview
private fun LoadingCircularProgressBarPreview() {
    LoadingCircularProgressBar()
}

@Composable
@Preview
private fun LoadErrorContentPreview() {
}