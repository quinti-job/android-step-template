package com.quinti.android_step_template.ui.screen.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    HomeScreen(name = "HomeScreen")
}

@Composable
private fun HomeScreen(
    name: String,
) {
    Text(
        text = "Hello $name!",
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    SocialNetworkTheme {
        HomeScreen("Android")
    }
}