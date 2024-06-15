package com.quinti.android_step_template.ui.screen.chatlist

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel,
) {
    ChatListScreen(name = "ChatListScreen")
}

@Composable
private fun ChatListScreen(
    name: String,
) {
    Text(
        text = "Hello $name!",
    )
}

@Preview(showBackground = true)
@Composable
private fun ChatListScreenPreview() {
    SocialNetworkTheme {
        ChatListScreen("Android")
    }
}