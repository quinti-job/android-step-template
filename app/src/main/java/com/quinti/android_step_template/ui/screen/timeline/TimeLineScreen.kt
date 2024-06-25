package com.quinti.android_step_template.ui.screen.timeline

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun TimeLineScreen(
    viewModel: TimeLineViewModel,
) {
    TimeLineScreen(name = "TimeLineScreen")
}

@Composable
private fun TimeLineScreen(
    name: String,
) {
    Text(
        text = "Hello $name!",
    )
}

@Preview(showBackground = true)
@Composable
private fun TimeLineScreenPreview() {
    SocialNetworkTheme {
        TimeLineScreen("Android")
    }
}