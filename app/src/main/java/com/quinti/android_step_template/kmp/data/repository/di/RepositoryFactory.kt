package com.quinti.android_step_template.kmp.data.repository.di

import com.quinti.android_step_template.kmp.data.repository.LoginRepository


interface RepositoryFactory {
    fun createLoginRepository(): LoginRepository
//    fun createBalanceRepository(): BalanceRepository
//    fun createBankRegistrationRepository(): BankRegistrationRepository
//    fun createBnplRepository(): BnplRepository
//    fun createKyashPrizeRepository(): PrizeRepository
//    fun createBudgetRepository(): BudgetRepository
//    fun createCouponRepository(): CouponRepository
//    fun createDepositBankAccountRepository(): DepositBankAccountRepository
//    fun createDepositRepository(): DepositRepository
//    fun createEKycRepository(): EKycRepository
//    fun createInvitationRepository(): InvitationRepository
//    fun createKyashCardRepository(): KyashCardRepository
//    fun createKyashCardVirtualRepository(): KyashCardVirtualRepository
//    fun createKyashCoinRepository(): KyashCoinRepository
//    fun createOfferWallRepository(): OfferWallRepository
//    fun createKycRepository(): KycRepository
//    fun createLawsonBankRepository(): LawsonBankRepository
//    fun createLiteRepository(): LiteRepository
//    fun createMauricioRepository(): MauricioRepository
//    fun createMeRepository(): MeRepository
//    fun createNewsRepository(): NewsRepository
//    fun createNotificationBannerRepository(): NotificationBannerRepository
//    fun createOtherOwnedBankTransferRepository(): OtherOwnedBankTransferRepository
//    fun createPayrollRepository(): PayrollRepository
//    fun createPersonalInformationRepository(): PersonalInformationRepository
//    fun createPointRepository(): PointRepository
//    fun createPostalRepository(): PostalRepository
//    fun createSecurityRepository(): SecurityRepository
//    fun createSendRequestRepository(): SendRequestRepository
//    fun createTimelineRepository(): TimelineRepository
//    fun createWalletRepository(): WalletRepository
//    fun createWithdrawalBankRepository(): WithdrawalBankRepository
//    fun createCategoryRepository(): CategoryRepository
//    fun createCustomCategoryDraftRepository(): CustomCategoryDraftRepository
//    fun createAdvertisementCampaignsRepository(): AdvertisementCampaignsRepository
}