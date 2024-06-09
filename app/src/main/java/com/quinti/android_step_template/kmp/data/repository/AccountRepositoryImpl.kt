package com.quinti.android_step_template.kmp.data.repository

import com.quinti.android_step_template.kmp.data.api.LoginApi
import com.quinti.android_step_template.kmp.data.api.entity.Login
import com.quinti.android_step_template.kmp.data.datasource.AccountLocalDataSource
import kotlinx.coroutines.flow.SharedFlow

class AccountRepositoryImpl(
    private val loginApi: LoginApi,
    private val localDataSource: AccountLocalDataSource,
) : AccountRepository {

    override suspend fun login(
        email: String,
        password: String,
    ): Login {
        return loginApi.postLogin(
            email = email,
            password = password,
        ).result.data.toEntity()
    }

    override val requestUpdateRewardTabIndicatorFlow: SharedFlow<Unit> =
        localDataSource.requestUpdateRewardTabIndicatorFlow

    override suspend fun requestUpdateRewardTabIndicator() {
        localDataSource.requestUpdateRewardTabIndicator()
    }

    override suspend fun hideRewardTabCoachMark() {
        localDataSource.setRewardTabCoachMarkShown()
    }

    override suspend fun getRewardTopOnboardingShown(): Boolean {
        return localDataSource.isRewardTopOnboardingShown()
    }

    override suspend fun setRewardTopOnboardingShown() {
        localDataSource.setRewardTopOnboardingShown(true)
    }

    override suspend fun getRewardTabNewBalloonShown(): Boolean {
        return localDataSource.isRewardTabBalloonShown()
    }

    override suspend fun setRewardTabNewBalloonShown() {
        return localDataSource.setRewardTabBalloonShown(true)
    }

    override suspend fun isNeedShowRewardStampOnboarding(): Boolean {
        return localDataSource.isNeedShowRewardStampOnboarding()
    }

    override suspend fun setNeedShowRewardStampOnboarding(needShow: Boolean) {
        return localDataSource.setNeedShowRewardStampOnboarding(needShow)
    }

//    override suspend fun registerWithEmailV1(
//        email: String,
//        password: String,
//        phoneNumberVerificationToken: String,
//        userName: String,
//        firstNameReal: String,
//        lastNameReal: String,
//        firstNameKana: String,
//        lastNameKana: String,
//        birthYear: String,
//    ): UserSession {
//        return accountApi.registerV1(
//            EmailAccountRegistrationV1Body(
//                email = email,
//                password = password,
//                numberVerificationToken = phoneNumberVerificationToken,
//                userName = userName,
//                firstNameReal = firstNameReal,
//                lastNameReal = lastNameReal,
//                firstNameKana = firstNameKana,
//                lastNameKana = lastNameKana,
//                birthYear = birthYear,
//            ),
//        ).result.data.toDomain()
//    }
//
//    override suspend fun registerWithAppleV1(
//        sessionId: String,
//        phoneNumberVerificationToken: String,
//        userName: String,
//        firstNameReal: String,
//        lastNameReal: String,
//        firstNameKana: String,
//        lastNameKana: String,
//        birthYear: String,
//    ): UserSession {
//        return accountApi.registerByAppleV1(
//            AppleAccountRegistrationV1Body(
//                sessionId = sessionId,
//                numberVerificationToken = phoneNumberVerificationToken,
//                userName = userName,
//                firstNameReal = firstNameReal,
//                lastNameReal = lastNameReal,
//                firstNameKana = firstNameKana,
//                lastNameKana = lastNameKana,
//                birthYear = birthYear,
//            ),
//        ).result.data.toDomain()
//    }
//
//    override suspend fun registerWithGoogleV1(
//        sessionId: String,
//        phoneNumberVerificationToken: String,
//        userName: String,
//        firstNameReal: String,
//        lastNameReal: String,
//        firstNameKana: String,
//        lastNameKana: String,
//        birthYear: String,
//    ): UserSession {
//        return accountApi.registerByGoogleV1(
//            GoogleAccountRegistrationV1Body(
//                sessionId = sessionId,
//                numberVerificationToken = phoneNumberVerificationToken,
//                userName = userName,
//                firstNameReal = firstNameReal,
//                lastNameReal = lastNameReal,
//                firstNameKana = firstNameKana,
//                lastNameKana = lastNameKana,
//                birthYear = birthYear,
//            ),
//        ).result.data.toDomain()
//    }
//
//    override suspend fun registerWithEmailV2(
//        email: String,
//        password: String,
//        phoneNumberVerificationToken: String,
//    ): UserSession {
//        return accountApi.registerV2(
//            EmailAccountRegistrationV2Body(
//                email = email,
//                password = password,
//                numberVerificationToken = phoneNumberVerificationToken,
//            ),
//        ).result.data.toDomain()
//    }
//
//    override suspend fun registerWithAppleV2(
//        sessionId: String,
//        phoneNumberVerificationToken: String,
//    ): UserSession {
//        return accountApi.registerByAppleV2(
//            AppleAccountRegistrationV2Body(
//                sessionId = sessionId,
//                numberVerificationToken = phoneNumberVerificationToken,
//            ),
//        ).result.data.toDomain()
//    }
//
//    override suspend fun registerWithGoogleV2(
//        sessionId: String,
//        phoneNumberVerificationToken: String,
//    ): UserSession {
//        return accountApi.registerByGoogleV2(
//            GoogleAccountRegistrationV2Body(
//                sessionId = sessionId,
//                numberVerificationToken = phoneNumberVerificationToken,
//            ),
//        ).result.data.toDomain()
//    }
//
//    override suspend fun checkEmailAddress(email: String) {
//        accountApi.checkEmailAddress(EmailAddressBody(email))
//    }
//
//    override suspend fun checkUserName(userName: String) {
//        accountApi.checkUserName(UserNameBody(userName))
//    }
//
//    override suspend fun verifyPhoneNumber(
//        phoneNumber: String,
//        verificationCode: String,
//    ): PhoneNumberVerifiedToken {
//        val token = accountApi.verifyPhoneNumberVerificationCode(
//            PhoneNumberVerificationCodeBody(
//                phoneNumber = phoneNumber,
//                verificationCode = verificationCode,
//            ),
//        ).result.data.token
//        return PhoneNumberVerifiedToken(token)
//    }
//
//    override suspend fun sendVerificationCodeViaSms(phoneNumber: String) {
//        accountApi.sendVerificationCodeViaSms(RegisterPhoneNumberBody(phoneNumber))
//    }
//
//    override suspend fun sendVerificationCodeViaVoiceCall(phoneNumber: String) {
//        accountApi.sendVerificationCodeViaVoiceCall(RegisterPhoneNumberBody(phoneNumber))
//    }
}