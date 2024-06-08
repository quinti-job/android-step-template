package com.quinti.android_step_template.kmp.api.entity

data class Login(
    val status: Boolean,
    val token: String,
    val userId: String,
)