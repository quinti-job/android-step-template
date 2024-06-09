package com.quinti.android_step_template.kmp.repository

import com.quinti.android_step_template.kmp.api.entity.Login


interface LoginRepository {
    suspend fun postLogin(
        email: String,
        password: String,
    ): Login
//    suspend fun sendVerificationCodeViaSms(phoneNumber: String)
//
//    suspend fun sendVerificationCodeViaVoiceCall(phoneNumber: String)
//
//    suspend fun verifyPhoneNumber(
//        phoneNumber: String,
//        verificationCode: String,
//    ): PhoneNumberVerifiedToken
//
//    suspend fun registerWithEmailV1(
//        email: String,
//        password: String,
//        phoneNumberVerificationToken: String,
//        userName: String,
//        firstNameReal: String,
//        lastNameReal: String,
//        firstNameKana: String,
//        lastNameKana: String,
//        birthYear: String,
//    ): UserSession
//
//    suspend fun registerWithAppleV1(
//        sessionId: String,
//        phoneNumberVerificationToken: String,
//        userName: String,
//        firstNameReal: String,
//        lastNameReal: String,
//        firstNameKana: String,
//        lastNameKana: String,
//        birthYear: String,
//    ): UserSession
//
//    suspend fun registerWithGoogleV1(
//        sessionId: String,
//        phoneNumberVerificationToken: String,
//        userName: String,
//        firstNameReal: String,
//        lastNameReal: String,
//        firstNameKana: String,
//        lastNameKana: String,
//        birthYear: String,
//    ): UserSession
//
//    suspend fun registerWithEmailV2(
//        email: String,
//        password: String,
//        phoneNumberVerificationToken: String,
//    ): UserSession
//
//    suspend fun registerWithAppleV2(
//        sessionId: String,
//        phoneNumberVerificationToken: String,
//    ): UserSession
//
//    suspend fun registerWithGoogleV2(
//        sessionId: String,
//        phoneNumberVerificationToken: String,
//    ): UserSession
//
//    suspend fun checkEmailAddress(
//        email: String,
//    )
//
//    suspend fun checkUserName(
//        userName: String,
//    )
}
