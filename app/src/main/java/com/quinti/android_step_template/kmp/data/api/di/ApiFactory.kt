package com.quinti.android_step_template.kmp.data.api.di

import com.quinti.android_step_template.kmp.data.api.KyashCoinApi
import com.quinti.android_step_template.kmp.data.api.KyashCoinApiImpl
import com.quinti.android_step_template.kmp.data.api.LoginApi
import com.quinti.android_step_template.kmp.data.api.LoginApiImpl
import com.quinti.android_step_template.kmp.data.api.PointApi


interface ApiFactory {
//    fun createAccountRegisterApi(): AccountRegisterApi
//    fun createBankRegistrationApi(): BankRegistrationApi
//    fun createCouponApi(): CouponApi
//    fun createDepositApi(): DepositApi
    fun createKyashCoinApi(): KyashCoinApi
//    fun createOfferWallApi(): OfferWallApi
//    fun createLawsonBankApi(): LawsonBankApi
//    fun createMauricioApi(): MauricioApi
//    fun createMeApi(): MeApi
//    fun createNewsApi(): NewsApi
//    fun createNotificationBannerApi(): NotificationBannerApi
//    fun createPayrollApi(): PayrollApi
//    fun createPersonalInformationApi(): PersonalInformationApi
    fun createPointApi(): PointApi
//    fun createPostalApi(): PostalApi
//    fun createPromotionApi(): PromotionApi
//    fun createSecurityApi(): SecurityApi
//    fun createSendRequestApi(): SendRequestApi
//    fun createTimelineApi(): TimelineApi
//    fun createWalletApi(): WalletApi
//    fun createWithdrawalBankApi(): WithdrawalBankApi
    fun createLoginApi(): LoginApi
//    fun createKycApi(): KycApi
//    fun createEKycApi(): EKycApi
//    fun createLiteApi(): LiteApi
//    fun createVirtualBankApi(): VirtualBankApi
//    fun createBnplApi(): BnplApi
//    fun createKyashCardVirtualApi(): KyashCardVirtualApi

    companion object {
        fun provide(kyashApiFactory: KyashApiFactory): ApiFactory {
            return ApiFactoryImpl(kyashApiFactory = kyashApiFactory)
        }
    }
}

internal class ApiFactoryImpl(
    private val kyashApiFactory: KyashApiFactory,
) : ApiFactory {

//    override fun createWithdrawalBankApi(): WithdrawalBankApi {
//        return WithdrawalBankApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createTimelineApi(): TimelineApi {
//        return TimelineApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createAccountRegisterApi(): AccountRegisterApi {
//        return AccountRegisterApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createBankRegistrationApi(): BankRegistrationApi {
//        return BankRegistrationApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createCouponApi(): CouponApi {
//        return CouponApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createDepositApi(): DepositApi {
//        return DepositApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }

    override fun createKyashCoinApi(): KyashCoinApi {
        return KyashCoinApiImpl(
            httpClient = kyashApiFactory.httpClient,
            endpoint = kyashApiFactory.endpoint,
            session = kyashApiFactory.session,
        )
    }

//    override fun createOfferWallApi(): OfferWallApi {
//        return OfferWallApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createLawsonBankApi(): LawsonBankApi {
//        return LawsonBankApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createMauricioApi(): MauricioApi {
//        return MauricioApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createNewsApi(): NewsApi {
//        return NewsApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createNotificationBannerApi(): NotificationBannerApi {
//        return NotificationBannerApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createPayrollApi(): PayrollApi {
//        return PayrollApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createPersonalInformationApi(): PersonalInformationApi {
//        return PersonalInformationApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }

    override fun createPointApi(): PointApi {
        return PointApi(
            httpClient = kyashApiFactory.httpClient,
            endpoint = kyashApiFactory.endpoint,
            session = kyashApiFactory.session,
        )
    }

//    override fun createPostalApi(): PostalApi {
//        return PostalApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createPromotionApi(): PromotionApi {
//        return PromotionApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createSecurityApi(): SecurityApi {
//        return SecurityApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createSendRequestApi(): SendRequestApi {
//        return SendRequestApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createWalletApi(): WalletApi {
//        return WalletApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createMeApi(): MeApi {
//        return MeApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }

    override fun createLoginApi(): LoginApi {
        return LoginApiImpl(
            httpClient = kyashApiFactory.httpClient,
            endpoint = kyashApiFactory.endpoint,
            session = kyashApiFactory.session,
        )
    }

//    override fun createKycApi(): KycApi {
//        return KycApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createEKycApi(): EKycApi {
//        return EKycApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createLiteApi(): LiteApi {
//        return LiteApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createVirtualBankApi(): VirtualBankApi {
//        return VirtualBankApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createBnplApi(): BnplApi {
//        return BnplApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
//
//    override fun createKyashCardVirtualApi(): KyashCardVirtualApi {
//        return KyashCardVirtualApi(
//            httpClient = kyashApiFactory.httpClient,
//            endpoint = kyashApiFactory.endpoint,
//            session = kyashApiFactory.session,
//        )
//    }
}