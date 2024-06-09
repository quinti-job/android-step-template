package com.quinti.android_step_template.kmp.data.repository.di

import com.quinti.android_step_template.kmp.data.api.di.ApiFactory
import com.quinti.android_step_template.kmp.data.datasource.di.LocalDataSourceFactory
import com.quinti.android_step_template.kmp.debug.DevelopModeFactory
import com.quinti.android_step_template.kmp.data.repository.AccountRepository
import com.quinti.android_step_template.kmp.data.repository.AccountRepositoryImpl

fun provideRepositoryFactory(
    apiFactory: ApiFactory,
    localDataSourceFactory: LocalDataSourceFactory,
): RepositoryFactory {
    return RepositoryFactoryImpl(
        apiFactory = apiFactory,
        localDataSourceFactory = localDataSourceFactory,
    )
}

internal class RepositoryFactoryImpl(
    private val apiFactory: ApiFactory,
    private val localDataSourceFactory: LocalDataSourceFactory,
) : RepositoryFactory, DevelopModeFactory {

//    override fun createWithdrawalBankRepository(): WithdrawalBankRepository =
//        WithdrawalBankRepositoryImpl(
//            api = apiFactory.createWithdrawalBankApi(),
//        )
//
//    override fun createOtherOwnedBankTransferRepository(): OtherOwnedBankTransferRepository {
//        return OtherOwnedBankTransferRepositoryImpl(
//            localDataSource = localDataSourceFactory.createOtherOwnedBankTransferLocalDataSource(),
//        )
//    }
//
//    override fun createPayrollRepository(): PayrollRepository {
//        return PayrollRepositoryImpl(
//            api = apiFactory.createPayrollApi(),
//        )
//    }
//
//    override fun createPersonalInformationRepository(): PersonalInformationRepository {
//        return PersonalInformationRepositoryImpl(
//            personalInformationApi = apiFactory.createPersonalInformationApi(),
//        )
//    }
//
//    override fun createPointRepository(): PointRepository {
//        return PointRepositoryImpl(
//            api = apiFactory.createPointApi(),
//        )
//    }
//
//    override fun createPostalRepository(): PostalRepository {
//        return PostalRepositoryImpl(
//            postalApi = apiFactory.createPostalApi(),
//        )
//    }
//
//    override fun createSecurityRepository(): SecurityRepository {
//        return SecurityRepositoryImpl(
//            securityApi = apiFactory.createSecurityApi(),
//        )
//    }
//
//    override fun createSendRequestRepository(): SendRequestRepository {
//        return SendRequestRepositoryImpl(
//            api = apiFactory.createSendRequestApi(),
//        )
//    }
//
//    override fun createTimelineRepository(): TimelineRepository {
//        return TimelineRepositoryImpl(
//            timelineApi = apiFactory.createTimelineApi(),
//        )
//    }
//
//    override fun createKyashCoinRepository(): KyashCoinRepository {
//        return KyashCoinRepositoryImpl(
//            kyashCoinApi = apiFactory.createKyashCoinApi(),
//            localDataSource = localDataSourceFactory.createRewardLocalDataSource(),
//            isRewardOnboarding202404Enabled = releaseFlag.isRewardOnboarding202404Enabled,
//        )
//    }
//
//    override fun createOfferWallRepository(): OfferWallRepository {
//        return OfferWallRepositoryImpl(
//            api = apiFactory.createOfferWallApi(),
//        )
//    }
//
//    override fun createLawsonBankRepository(): LawsonBankRepository {
//        return LawsonBankRepositoryImpl(
//            lawsonBankApi = apiFactory.createLawsonBankApi(),
//        )
//    }
//
//    override fun createLiteRepository(): LiteRepository {
//        return LiteRepositoryImpl(
//            liteApi = apiFactory.createLiteApi(),
//        )
//    }
//
//    override fun createMauricioRepository(): MauricioRepository {
//        return MauricioRepositoryImpl(
//            mauricioApi = apiFactory.createMauricioApi(),
//        )
//    }
//
//    override fun createMeRepository(): MeRepository {
//        return MeRepositoryImpl(
//            meApi = apiFactory.createMeApi(),
//            localDataSource = localDataSourceFactory.createMeLocalDataSource(),
//        )
//    }
//
//    override fun createNewsRepository(): NewsRepository {
//        return NewsRepositoryImpl(
//            newsApi = apiFactory.createNewsApi(),
//        )
//    }
//
//    override fun createNotificationBannerRepository(): NotificationBannerRepository {
//        return NotificationBannerRepositoryImpl(
//            api = apiFactory.createNotificationBannerApi(),
//        )
//    }
//
//    override fun createBalanceRepository(): BalanceRepository {
//        return BalanceRepositoryImpl(
//            meApi = apiFactory.createMeApi(),
//        )
//    }
//
//    override fun createBankRegistrationRepository(): BankRegistrationRepository {
//        return BankRegistrationRepositoryImpl(
//            bankRegistrationApi = apiFactory.createBankRegistrationApi(),
//        )
//    }
//
//    override fun createBnplRepository(): BnplRepository {
//        return BnplRepositoryImpl(
//            bnplApi = apiFactory.createBnplApi(),
//            meApi = apiFactory.createMeApi(),
//        )
//    }
//
//    override fun createKyashPrizeRepository(): PrizeRepository {
//        return PrizeRepositoryImpl(
//            prizeLocalDataSource = localDataSourceFactory.createCoinPrizeLocalDataSource(),
//            prizePageLocalDataSource = localDataSourceFactory
//                .createCoinPrizePaginationLocalDataSource(),
//            prizeApi = apiFactory.createKyashCoinApi(),
//        )
//    }
//
//    override fun createBudgetRepository(): BudgetRepository {
//        return BudgetRepositoryImpl(
//            localDataSource = localDataSourceFactory.createBudgetSettingLocalDataSource(),
//        )
//    }
//
//    override fun createCouponRepository(): CouponRepository {
//        return CouponRepositoryImpl(
//            couponApi = apiFactory.createCouponApi(),
//        )
//    }
//
//    override fun createDepositBankAccountRepository(): DepositBankAccountRepository {
//        return DepositBankAccountRepositoryImpl(
//            virtualBankApi = apiFactory.createVirtualBankApi(),
//        )
//    }
//
//    override fun createDepositRepository(): DepositRepository {
//        return DepositRepositoryImpl(
//            api = apiFactory.createDepositApi(),
//            userJourneyDataSource = localDataSourceFactory.createUserJourneyLocalDataSource(),
//        )
//    }
//
//    override fun createEKycRepository(): EKycRepository {
//        return EKycRepositoryImpl(
//            eKycApi = apiFactory.createEKycApi(),
//        )
//    }
//
//    override fun createInvitationRepository(): InvitationRepository {
//        return InvitationRepositoryImpl(
//            developMode = createDevelopMode(),
//            promotionApi = apiFactory.createPromotionApi(),
//        )
//    }
//
//    override fun createKyashCardRepository(): KyashCardRepository {
//        return KyashCardRepositoryImpl(
//            decryptor = Decryptor(),
//            meApi = apiFactory.createMeApi(),
//        )
//    }
//
//    override fun createKyashCardVirtualRepository(): KyashCardVirtualRepository {
//        return KyashCardVirtualRepositoryImpl(
//            decryptor = Decryptor(),
//            kyashCardVirtualApi = apiFactory.createKyashCardVirtualApi(),
//            meApi = apiFactory.createMeApi(),
//        )
//    }
//
//    override fun createWalletRepository(): WalletRepository {
//        return WalletRepositoryImpl(
//            walletApi = apiFactory.createWalletApi(),
//        )
//    }
//
//    override fun createCategoryRepository(): CategoryRepository {
//        return CategoryRepositoryImpl(
//            meApi = apiFactory.createMeApi(),
//            timelineApi = apiFactory.createTimelineApi(),
//        )
//    }
//
//    override fun createCustomCategoryDraftRepository(): CustomCategoryDraftRepository {
//        return CustomCategoryDraftRepositoryImpl(
//            dataSource = localDataSourceFactory.createCustomCategoryDraftLocalDataSource(),
//        )
//    }
//
//    override fun createAdvertisementCampaignsRepository(): AdvertisementCampaignsRepository {
//        return AdvertisementCampaignsRepositoryImpl(
//            promotionApi = apiFactory.createPromotionApi(),
//        )
//    }

    override fun createAccountRepository(): AccountRepository {
        return AccountRepositoryImpl(
            loginApi = apiFactory.createLoginApi(),
            localDataSource = localDataSourceFactory.createAccountDataSource(),
        )
    }

//    override fun createKycRepository(): KycRepository {
//        return KycRepositoryImpl(
//            kycApi = apiFactory.createKycApi(),
//        )
//    }
}