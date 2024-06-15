package com.quinti.android_step_template.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.quinti.android_step_template.kmp.domain.reactor.LoginReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.ui.component.KyashScaffold
import com.quinti.android_step_template.ui.component.LoadingAndErrorScreen
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme


@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
) {
    val state by viewModel.reactor.state.collectAsStateWithLifecycle()
    LoginScreen(
        loadState = state,
        onClickLogin = viewModel::onClickLogin,
        onClickChangeEmail = viewModel::onClickChangeEmail,
        onClickChangePassword = viewModel::onClickChangePassword,
        onClickCreateAccount = viewModel::onClickCreateAccount,
        onClickTerms = viewModel::onClickTerms,
        onClickPrivacyPolicy = viewModel::onClickPrivacyPolicy,
    )
}


@Composable
private fun LoginScreen(
    loadState: Reactor.LoadState<LoginReactor.State>,
    onClickLogin: () -> Unit,
    onClickChangeEmail: () -> Unit,
    onClickChangePassword: () -> Unit,
    onClickCreateAccount: () -> Unit,
    onClickTerms: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
) {

//    if (loginSuccess) {
//        // Navigate to MainView
//        navController.navigate("mainView")
//    }

    KyashScaffold(
        topBar = {
            TopAppBar(
                title = { Text("ログイン画面") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            )
        }
    ) { padding ->
        LoadingAndErrorScreen(
            state = loadState,
            loadedContent = {
//                LoginSections(
//                    loginBtnDisable = it.loginBtnDisable,
//                    emailErrorMsgVisibility = it.emailErrorMsgVisibility,
//                    passwordErrorMsgVisibility = it.passwordErrorMsgVisibility,
//                    loginErrorMsgVisibility = it.loginErrorMsgVisibility,
//                    netWorkErrorPopUpVisibility = it.netWorkErrorPopUpVisibility,
//                    onClickLogin = onClickLogin,
//                    onClickChangeEmail = onClickChangeEmail,
//                    onClickChangePassword = onClickChangePassword,
//                    onClickCreateAccount = onClickCreateAccount,
//                    onClickTerms = onClickTerms,
//                    onClickPrivacyPolicy = onClickPrivacyPolicy,
//                    modifier = Modifier.padding(paddingValues = padding)
//                )
                Text(text = "aaaa")
            },
            onRetryClick = {}
        )
    }
}

@Composable
fun SocialLoginButton(text: String, iconRes: Int) {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text, color = Color.White)
        }
    }
}

// Mock functions to replace ValidationUtil methods
fun checkMatchesWithEmail(email: String): Boolean {
    // Mock validation logic
    return email.contains("@")
}

fun checkMatchesWithPassword(email: String, password: String): Boolean {
    // Mock validation logic
    return password.length >= 8
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    SocialNetworkTheme {
        LoginScreen(
            loadState = Reactor.LoadState.Loaded(
                LoginReactor.State.LoginSuccessed(
                    loginSuccess = false
                )
            ),
            onClickLogin = { },
            onClickChangeEmail = { },
            onClickChangePassword = { },
            onClickCreateAccount = { },
            onClickTerms = { },
            onClickPrivacyPolicy = { },
        )
    }
}
