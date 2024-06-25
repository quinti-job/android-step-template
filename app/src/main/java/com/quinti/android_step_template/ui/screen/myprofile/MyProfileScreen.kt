package com.quinti.android_step_template.ui.screen.myprofile

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun MyProfileScreen(
    viewModel: MyProfileViewModel,
) {
    MyProfileScreen(name = "MyProfileScreen")
}

@Composable
private fun MyProfileScreen(
    name: String,
) {
    Text(
        text = "Hello $name!",
    )
}

@Preview(showBackground = true)
@Composable
private fun MyProfileScreenPreview() {
    SocialNetworkTheme {
        MyProfileScreen("Android")
    }
}