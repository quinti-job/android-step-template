package com.quinti.android_step_template.ui.screen.setting

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
) {
    SettingScreen(name = "SettingScreen")
}

@Composable
private fun SettingScreen(
    name: String,
) {
    Text(
        text = "Hello $name!",
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingScreenPreview() {
    SocialNetworkTheme {
        SettingScreen("Android")
    }
}