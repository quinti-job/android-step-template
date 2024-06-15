package com.quinti.android_step_template.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.R

@Composable
fun LoginSections(
    loginBtnDisable: Boolean,
    emailErrorMsgVisibility: Boolean,
    passwordErrorMsgVisibility: Boolean,
    loginErrorMsgVisibility: Boolean,
    netWorkErrorPopUpVisibility: Boolean,
    onClickLogin: () -> Unit,
    onClickChangeEmail: () -> Unit,
    onClickChangePassword: () -> Unit,
    onClickCreateAccount: () -> Unit,
    onClickTerms: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
//    var isLoginBtnDisable by remember { mutableStateOf(true) }
//    var isEmailErrorMsgVisible by remember { mutableStateOf(false) }
//    var isPasswordErrorMsgVisible by remember { mutableStateOf(false) }
//    var loginErrorMsgVisibility by remember { mutableStateOf(false) }
    var loginSuccess by remember { mutableStateOf(false) }
//    var netWorkErrorPopUpVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
//        BasicTextField(
//            value = email,
//            onValueChange = {
//                email = it
//                isLoginBtnDisable = email.isBlank() || password.isBlank()
//            },
//            modifier = Modifier.fillMaxWidth(),
//            decorationBox = { innerTextField ->
//                Box(
//                    Modifier
//                        .background(Color.Gray, shape = MaterialTheme.shapes.small)
//                        .padding(8.dp)
//                ) {
//                    if (email.isEmpty()) Text("メールアドレス", color = Color.Gray)
//                    innerTextField()
//                }
//            }
//        )
//        if (isEmailErrorMsgVisible) {
//            Text(
//                "有効なメールアドレスを入力してください",
//                color = Color.Red,
//                style = MaterialTheme.typography.caption
//            )
//        }

//        Spacer(modifier = Modifier.height(16.dp))

//        BasicTextField(
//            value = password,
//            onValueChange = {
//                password = it
//                isLoginBtnDisable = email.isBlank() || password.isBlank()
//            },
//            modifier = Modifier.fillMaxWidth(),
//            visualTransformation = PasswordVisualTransformation(),
//            decorationBox = { innerTextField ->
//                Box(
//                    Modifier
//                        .background(Color.Gray, shape = MaterialTheme.shapes.small)
//                        .padding(8.dp)
//                ) {
//                    if (password.isEmpty()) Text("パスワード", color = Color.Gray)
//                    innerTextField()
//                }
//            }
//        )
//        if (isPasswordErrorMsgVisible) {
//            Text(
//                "パスワードが正しくありません。入力し直してください。\n「パスワードをお忘れの場合」から再設定できます。",
//                color = Color.Red,
//                style = MaterialTheme.typography.caption
//            )
//        }

//        Spacer(modifier = Modifier.height(16.dp))

//        if (loginErrorMsgVisibility) {
//            Text(
//                "アカウントが見つかりませんでした",
//                color = Color.Red,
//                style = MaterialTheme.typography.caption
//            )
//        }

//        Button(
//            onClick = onClickLogin,
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            enabled = !isLoginBtnDisable,
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = if (isLoginBtnDisable) Color.Gray else MaterialTheme.colors.primary,
//                contentColor = Color.White
//            )
//        ) {
//            Text("ログイン")
//        }

//        Spacer(modifier = Modifier.height(50.dp))

//        TextButton(
//            onClick = onClickChangeEmail,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("メールアドレスを忘れた場合", color = MaterialTheme.colors.primary)
//        }
//
//        TextButton(
//            onClick = onClickChangePassword,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("パスワードを忘れた場合", color = MaterialTheme.colors.primary)
//        }

//        Spacer(modifier = Modifier.height(50.dp))

//        SocialLoginButton("Facebookでログイン", R.drawable.facebook_logo)
//        SocialLoginButton("Googleでログイン", R.drawable.google_logo)
//        SocialLoginButton("Twitterでログイン", R.drawable.twitter_logo)

//        Spacer(modifier = Modifier.height(30.dp))
//
//        TextButton(
//            onClick = onClickCreateAccount,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("アカウント作成", color = MaterialTheme.colors.primary)
//        }

//        Spacer(modifier = Modifier.height(20.dp))
//
//        Row(
//            horizontalArrangement = Arrangement.spacedBy(50.dp),
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            TextButton(
//                onClick = onClickTerms
//            ) {
//                Text("利用規約", color = Color.Magenta)
//            }
//            TextButton(
//                onClick = onClickPrivacyPolicy
//            ) {
//                Text("プライバシーポリシー", color = Color.Magenta)
//            }
//        }

//        if (netWorkErrorPopUpVisibility) {
//            AlertDialog(
//                onDismissRequest = { netWorkErrorPopUpVisibility = false },
//                title = { Text("ネットワークが繋がっていません") },
//                text = {
//                    Text("ネットワークが繋がっていない為、ログインできません。ネットワークが繋がっているかご確認の上、もう一度実行してください。")
//                },
//                confirmButton = {
//                    TextButton(onClick = {
//                        netWorkErrorPopUpVisibility = false
//                        // login()
//                    }) {
//                        Text("再実行")
//                    }
//                },
//                dismissButton = {
//                    TextButton(onClick = { netWorkErrorPopUpVisibility = false }) {
//                        Text("キャンセル")
//                    }
//                }
//            )
//        }
    }
}