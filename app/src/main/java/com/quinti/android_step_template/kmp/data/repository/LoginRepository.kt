package com.quinti.android_step_template.kmp.data.repository

import com.quinti.android_step_template.kmp.data.api.entity.Login
import kotlinx.coroutines.flow.SharedFlow


interface LoginRepository {
    suspend fun postLogin(
        email: String,
        password: String,
    ): Login

    val requestUpdateRewardTabIndicatorFlow: SharedFlow<Unit>

    suspend fun requestUpdateRewardTabIndicator()

    suspend fun hideRewardTabCoachMark()

    suspend fun getRewardTopOnboardingShown(): Boolean

    suspend fun setRewardTopOnboardingShown()


    suspend fun getRewardTabNewBalloonShown(): Boolean

    suspend fun setRewardTabNewBalloonShown()

    suspend fun isNeedShowRewardStampOnboarding(): Boolean

    suspend fun setNeedShowRewardStampOnboarding(needShow: Boolean)

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