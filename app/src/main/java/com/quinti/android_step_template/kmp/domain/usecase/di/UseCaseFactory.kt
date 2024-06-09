package com.quinti.android_step_template.kmp.domain.usecase.di

import com.quinti.android_step_template.kmp.data.repository.di.RepositoryFactory
import com.quinti.android_step_template.kmp.domain.usecase.account.LoginUseCase
import com.quinti.android_step_template.kmp.domain.usecase.account.LoginUseCaseImpl

@Suppress("TooManyFunctions")
interface UseCaseFactory {

    fun createLoginUseCase(): LoginUseCase

//    fun createActivateKyashCardUseCase(): ActivateKyashCardUseCase
//    fun createCloseNotificationBannerUseCase(): CloseNotificationBannerUseCase
//    fun createExpirationDateRenewalActivateKyashCardUseCase():
//            ExpirationDateRenewalActivateKyashCardUseCase
//
//    fun createGetAppCheckUseCase(): GetAppCheckUseCase
//    fun createGetCouponDetailUseCase(): GetCouponDetailUseCase
//    fun createGetCouponsUseCase(): GetCouponsUseCase
//    fun createGetKyashCardUseCase(): GetKyashCardUseCase
//    fun createGetKycStatusUseCase(): GetKycStatusUseCase
//    fun createGetNotificationBannersUseCase(
//        releaseFlag: ReleaseFlag,
//    ): GetNotificationBannersUseCase
//
//    fun createGetTopCouponsUseCase(): GetTopCouponsUseCase
//    fun createGetWalletBalanceUseCase(): GetWalletBalanceUseCase
//    fun createSendVerificationCodeViaSmsUseCase(): SendVerificationCodeViaSmsUseCase
//    fun createSendVerificationCodeViaVoiceCallUseCase(): SendVerificationCodeViaVoiceCallUseCase
//    fun createVerifyPhoneNumberUseCase(): VerifyPhoneNumberUseCase
//    fun createGetBnplDebtDetailUseCase(): GetBnplDebtDetailUseCase
//    fun createRequestKyashMoneyRepayUseCase(): RequestKyashMoneyRepayUseCase
//    fun createApplyMauricioCardUseCase(): ApplyMauricioCardUseCase
//    fun createGetMauricioApplicationInfoUseCase(): GetMauricioApplicationInfoUseCase
//    fun createGetMauricioApplicationStatusUseCase(): GetMauricioApplicationStatusUseCase
//    fun createSetMauricioApplicationInfoUseCase(): SetMauricioApplicationInfoUseCase
//    fun createAgreeCouponPersonalInformationUseCase(): AgreeCouponPersonalInformationUseCase
//    fun createGetCouponPersonalInformationAgreementUseCase():
//            GetCouponPersonalInformationAgreementUseCase
//
//    fun createGetLawsonBankAvailableAmountUseCase(): GetLawsonBankAvailableAmountUseCase
//    fun createRegisterLawsonBankAtmDepositUseCase(): RegisterLawsonBankAtmDepositUseCase
//    fun createRegisterLawsonBankAtmWithdrawalUseCase(): RegisterLawsonBankAtmWithdrawalUseCase
//    fun createApplyForBankRegistrationUseCase(): ApplyForBankRegistrationUseCase
//    fun createApplyForJnbUseCase(): ApplyForJnbUseCase
//    fun createApplyForMoneyTapUseCase(): ApplyForMoneyTapUseCase
//    fun createApplyForRakutenUseCase(): ApplyForRakutenUseCase
//    fun createFindBankUseCase(): FindBankUseCase
//    fun createGetBankBranchesUseCase(): GetBankBranchesUseCase
//    fun createGetBankWithdrawalFeeUseCase(): GetBankWithdrawalFeeUseCase
//    fun createGetFeaturedBanksUseCase(): GetFeaturedBanksUseCase
//    fun createGetOtherOwnedBankTransferRequestDraftAsFlowUseCase():
//            GetOtherOwnedBankTransferRequestDraftAsFlowUseCase
//
//    fun createRequestBankWithdrawalUseCase(): RequestBankWithdrawalUseCase
//    fun createSaveOtherOwnedBankTransferRequestDraftUseCase():
//            SaveOtherOwnedBankTransferRequestDraftUseCase
//
//    fun createGetBirthDateRangeUseCase(): GetBirthDateRangeUseCase
//    fun createGetEKycNfcSignTargetDataUseCase(): GetEKycNfcSignTargetDataUseCase
//    fun createGetKycInfoUseCase(): GetKycInfoUseCase
//    fun createPreApplyForEKycOcrUseCase(): PreApplyForEKycOcrUseCase
//    fun createStructuralizeEKycNfcFamilyNameUseCase(): StructuralizeEKycNfcFamilyNameUseCase
//    fun createApplyLiteUseCase(): ApplyLiteUseCase
//    fun createGetAndCreateLiteApplicationExpirationDateRenewalInfoUseCase():
//            GetAndCreateLiteApplicationExpirationDateRenewalInfoUseCase
//
//    fun createGetLiteApplicationExpirationDateRenewalInfoUseCase():
//            GetLiteApplicationExpirationDateRenewalInfoUseCase
//
//    fun createGetLiteIssuanceFeeInfoUseCase(): GetLiteIssuanceFeeInfoUseCase
//    fun createUpdateLiteApplicationExpirationDateRenewalInfoUseCase():
//            UpdateLiteApplicationExpirationDateRenewalInfoUseCase
//
//    fun createGetAccountLinkNonceUseCase(): GetAccountLinkNonceUseCase
//    fun createGetActiveCardUseCase(): GetActiveCardUseCase
//    fun createGetContactsUseCase(): GetContactsUseCase
//    fun createGetIsPublicProfileUseCase(): GetIsPublicProfileUseCase
//    fun createGetMeAccountsUseCase(): GetMeAccountsUseCase
//    fun createGetMeUseCase(): GetMeUseCase
//    fun createUpdateMeUseCase(): UpdateMeUseCase
//    fun createGetUserSettingsUseCase(): GetUserSettingsUseCase
//    fun createLinkAppleAccountUseCase(): LinkAppleAccountUseCase
//    fun createLinkFacebookAccountUseCase(): LinkFacebookAccountUseCase
//    fun createLinkGoogleAccountUseCase(): LinkGoogleAccountUseCase
//    fun createUnlinkAppleAccountUseCase(): UnlinkAppleAccountUseCase
//    fun createUnlinkFacebookAccountUseCase(): UnlinkFacebookAccountUseCase
//    fun createUnlinkGoogleAccountUseCase(): UnlinkGoogleAccountUseCase
//    fun createUnlinkMoneyforwardAccountUseCase(): UnlinkMoneyforwardAccountUseCase
//    fun createUnlinkMoneytreeAccountUseCase(): UnlinkMoneytreeAccountUseCase
//    fun createUnlinkZaimAccountUseCase(): UnlinkZaimAccountUseCase
//    fun createUpdateFullNameUseCase(): UpdateFullNameUseCase
//    fun createUpdatePrivacySettingUseCase(): UpdatePrivacySettingUseCase
//    fun createUpdateUserSettingsUseCase(): UpdateUserSettingsUseCase
//    fun createUploadUserIconUseCase(): UploadUserIconUseCase
//    fun createGetKyashInfoUseCase(): GetKyashInfoUseCase
//    fun createGetNewsUseCase(): GetNewsUseCase
//    fun createReadNewsUseCase(): ReadNewsUseCase
//    fun createCheckEmailAddressUseCase(): CheckEmailAddressUseCase
//    fun createSignUpUseCase(accessTokenRepository: AccessTokenRepository): SignUpUseCase
//    fun createGetEvacuationAccountUseCase(): GetEvacuationAccountUseCase
//    fun createGetPayrollAccountUseCase(): GetPayrollAccountUseCase
//    fun createRegisterEvacuationAccountUseCase(): RegisterEvacuationAccountUseCase
//    fun createRequestPayrollAccountRegistrationUseCase(): RequestPayrollAccountRegistrationUseCase
//    fun createGetPointBalanceUseCase(): GetPointBalanceUseCase
//    fun createFindAddressUseCase(): FindAddressUseCase
//    fun createGetFriendInvitationUrlUseCase(): GetFriendInvitationUrlUseCase
//    fun createRegisterOtherOwnedBankAccountUseCase(): RegisterOtherOwnedBankAccountUseCase
//    fun createRequestOtherOwnedBankTransferUseCase(): RequestOtherOwnedBankTransferUseCase
//    fun createGetMonthlySpendingSummaryByCategoryUseCase():
//            GetMonthlySpendingSummaryByCategoryUseCase
//
//    fun createGetTimelineDetailUseCase(): GetTimelineDetailUseCase
//    fun createGetDefaultCategoriesUseCase(): GetDefaultCategoriesUseCase
//
//    fun createUpdateTimelineCategoryUseCase(): UpdateTimelineCategoryUseCase
//    fun createGetCreditCardRegistrationAcceptanceUseCase(remoteConfig: WalletConfig):
//            GetCreditCardRegistrationAcceptanceUseCase
//
//    fun createGetFundSourcesUseCase(): GetFundSourcesUseCase
//    fun createGetMembershipIncentivesUseCase(): GetMembershipIncentivesUseCase
//    fun createGetPaymentWalletIdUseCase(): GetPaymentWalletIdUseCase
//    fun createGetPendingsUseCase(): GetPendingsUseCase
//    fun createGetPrimaryWalletSummaryUseCase(): GetPrimaryWalletSummaryUseCase
//    fun createGetPromotionBannerUseCase(): GetPromotionBannerUseCase
//    fun createGetShareWalletSummaryUseCase(): GetShareWalletSummaryUseCase
//    fun createGetWalletTimelineUseCase(): GetWalletTimelineUseCase
//    fun createSetPaymentWalletUseCase(): SetPaymentWalletUseCase
//    fun createUpdateTimelinesCategoryUseCase(): UpdateTimelinesCategoryUseCase
//
//    // region KyashCoin
//    fun createGetKyashCoinAmountUseCase(): GetKyashCoinAmountUseCase
//
//    fun createGetDailyRouletteStatusUseCase(): GetDailyRouletteStatusUseCase
//
//    fun createGetCoinPrizeUseCase(): GetCoinPrizeUseCase
//
//    fun createApplyCoinPrizeUseCase(): ApplyCoinPrizeUseCase
//
//    fun createGetDailyRouletteResultUseCase(): GetDailyRouletteResultUseCase
//
//    fun createGetDailyRouletteOptionsUseCase(): GetDailyRouletteOptionsUseCase
//
//    fun createReceivePrizeUseCase(): ReceivePrizeUseCase
//
//    fun createNotifySharedToSnsUseCase(): NotifySharedToSnsUseCase
//
//    fun createNeedShowUncheckedPendingApplicationResultUseCase():
//            NeedShowUncheckedPendingApplicationResultUseCase
//
//    fun createCheckLotteryResultUseCase(): CheckLotteryResultUseCase
//
//    fun createRefreshActiveWeeklyPrizesUseCase(): RefreshActiveWeeklyPrizesUseCase
//    fun createWatchActiveWeeklyPrizesUseCase(): WatchActiveWeeklyPrizesUseCase
//    fun createRefreshActiveDailyPrizesUseCase(): RefreshActiveDailyPrizesUseCase
//    fun createWatchActiveDailyPrizesUseCase(): WatchActiveDailyPrizesUseCase
//    fun createRefreshUpcomingWeeklyPrizesUseCase(): RefreshUpcomingWeeklyPrizesUseCase
//    fun createWatchUpcomingWeeklyPrizesUseCase(): WatchUpcomingWeeklyPrizesUseCase
//    fun createRefreshAppliedWeeklyPrizesUseCase(): RefreshAppliedWeeklyPrizesUseCase
//    fun createWatchAppliedWeeklyPrizesUseCase(): WatchAppliedWeeklyPrizesUseCase
//    fun createQueryAppliedWeeklyPrizesUseCase(): QueryAppliedWeeklyPrizesUseCase
//    fun createRefreshAppliedDailyPrizesUseCase(): RefreshAppliedDailyPrizesUseCase
//    fun createWatchAppliedDailyPrizesUseCase(): WatchAppliedDailyPrizesUseCase
//    fun createQueryAppliedDailyPrizesUseCase(): QueryAppliedDailyPrizesUseCase
//    fun createNeedShowRewardTopOnboardingUseCase(): NeedShowRewardTopOnboardingUseCase
//    fun createSetShowRewardTopOnboardingUseCase(): SetShowRewardTopOnboardingUseCase
//    fun createNeedShowRewardTabNewBalloonUseCase(): NeedShowRewardTabNewBalloonUseCase
//    fun createSetShowRewardTabNewBalloonUseCase(): SetShowRewardTabNewBalloonUseCase
//    fun createGetUncheckRewardCountUseCase(): GetUncheckRewardCountUseCase
//    fun createGetKyashCoinHistoryUseCase(): GetKyashCoinHistoryUseCase
//    fun createWatchNeedShowRewardTabIndicatorUseCase(): WatchNeedShowRewardTabIndicatorUseCase
//    fun createRequestUpdateRewardTabIndicatorUseCase(): RequestUpdateRewardTabIndicatorUseCase
//    // endregion
//
//    fun createClearBudgetSettingFormUseCase(): ClearBudgetSettingFormUseCase
//    fun createDeleteBudgetSettingUseCase(): DeleteBudgetSettingUseCase
//    fun createGetBudgetSettingFormAsFlowUseCase(): GetBudgetSettingFormAsFlowUseCase
//    fun createGetMonthlyBudgetUseCase(): GetMonthlyBudgetUseCase
//    fun createSaveBudgetSettingFormUseCase(): SaveBudgetSettingFormUseCase
//    fun createSetBudgetSettingUseCase(): SetBudgetSettingUseCase
//    fun createGetSpendingAggregationStartDateUseCase(): GetSpendingAggregationStartDateUseCase
//    fun createSetSpendingAggregationStartDateUseCase(): SetSpendingAggregationStartDateUseCase
//    fun createDeleteWithdrawalBankAccountUseCase(): DeleteWithdrawalBankAccountUseCase
//    fun createGetWithdrawalBankAccountUseCase(): GetWithdrawalBankAccountUseCase
//    fun createGetMonthlyTimelineUseCase(): GetMonthlyTimelineUseCase
//    fun createGetCustomCategoryIconsUseCase(): GetCustomCategoryIconsUseCase
//    fun createCreateCustomCategoryUseCase(): CreateCustomCategoryUseCase
//    fun createUpdateCustomCategoryUseCase(): UpdateCustomCategoryUseCase
//    fun createDeleteCustomCategoryUseCase(): DeleteCustomCategoryUseCase
//    fun createGetOwnSpendingCategoriesUseCase(): GetOwnSpendingCategoriesUseCase
//    fun createGetCustomCategoriesUseCase(): GetCustomCategoriesUseCase
//    fun createSetCustomCategoryIconDraftUseCase(): SetCustomCategoryIconDraftUseCase
//    fun createWatchCustomCategoryIconDraftUseCase(): WatchCustomCategoryIconDraftUseCase
//    fun createWatchCustomCategoryIconsUseCase(): WatchCustomCategoryIconsUseCase
//    fun createCheckUserNameUseCase(): CheckUserNameUseCase
//    fun createGetBnplAutoRepaymentSettingUseCase(): GetBnplAutoRepaymentSettingUseCase
//    fun createSetBnplAutoRepaymentUseCase(): SetBnplAutoRepaymentUseCase
//    fun createCheckHasOverDueDebtUseCase(): CheckHasOverDueDebtUseCase
//    fun createGetBnplPendingRepaymentListUseCase(): GetBnplPendingRepaymentListUseCase
//    fun createGetBnplPendingRepaymentUseCase(): GetBnplPendingRepaymentUseCase
//    fun createGetBnplRepaymentHistoryUseCase(): GetBnplRepaymentHistoryUseCase
//    fun createCancelCvsRepaymentUseCase(): CancelCvsRepaymentUseCase
//    fun createGetOtherDepositMethodsUseCase(): GetOtherDepositMethodsUseCase
//    fun createGetVirtualBankAccountUseCase(): GetVirtualBankAccountUseCase
//    fun createUpdateUserNameUseCase(): UpdateUserNameUseCase
//    fun createPostRedeemUseCase(): PostRedeemUseCase
//    fun createUpdateInformationRequiredForTransactions():
//            UpdateInformationRequiredForTransactionsUseCase
//
//    fun createGetCustomerUseCase(): GetCustomerUseCase
//    fun createGetResidenceCardUseCase(): GetResidenceCardUseCase
//    fun createGetCustomerUpdateExpirationUseCase(): GetCustomerUpdateExpirationUseCase
//    fun createIssueKyashCardVirtualUseCase(): IssueKyashCardVirtualUseCase
//    fun createGetAttentionLevelUseCase(): GetAttentionLevelUseCase
//    fun createGetEKycNfcApplicationProgressUseCase(): GetEKycNfcApplicationProgressUseCase
//    fun createStartEKycNfcApplicationUseCase(): StartEKycNfcApplicationUseCase
//    fun createGetShouldShowDepositBalloonAsFlowUseCase(): GetShouldShowDepositBalloonAsFlowUseCase
//    fun createSetShouldShowDepositBalloonUseCase(): SetShouldShowDepositBalloonUseCase
//    fun createGetEKycOcrPrefillInfoUseCase(): GetEKycOcrPrefillInfoUseCase
//    fun createApplyForEKycOcrUseCase(): ApplyForEKycOcrUseCase
//
//    fun createGetCurrentStampCardUseCase(): GetCurrentStampCardUseCase

    companion object {
        fun provide(repositoryFactory: RepositoryFactory): UseCaseFactory {
            return UseCaseFactoryImpl(repositoryFactory = repositoryFactory)
        }
    }
}

@Suppress("TooManyFunctions")
internal class UseCaseFactoryImpl(
    private val repositoryFactory: RepositoryFactory,
) : UseCaseFactory {

    override fun createLoginUseCase(): LoginUseCase {
        return LoginUseCaseImpl(
            repository = repositoryFactory.createAccountRepository()
        )
    }

//    override fun createActivateKyashCardUseCase(): ActivateKyashCardUseCase {
//        return ActivateKyashCard(
//            kyashCardRepository = repositoryFactory.createKyashCardRepository(),
//        )
//    }
//
//    override fun createCloseNotificationBannerUseCase(): CloseNotificationBannerUseCase {
//        return CloseNotificationBanner(
//            repository = repositoryFactory.createNotificationBannerRepository(),
//        )
//    }
//
//    override fun createExpirationDateRenewalActivateKyashCardUseCase():
//            ExpirationDateRenewalActivateKyashCardUseCase {
//        return ExpirationDateRenewalActivateKyashCard(
//            kyashCardRepository = repositoryFactory.createKyashCardRepository(),
//        )
//    }
//
//    override fun createGetAppCheckUseCase(): GetAppCheckUseCase {
//        return GetAppCheck(
//            securityRepository = repositoryFactory.createSecurityRepository(),
//        )
//    }
//
//    override fun createGetCouponDetailUseCase(): GetCouponDetailUseCase {
//        return GetCouponDetail(
//            repository = repositoryFactory.createCouponRepository(),
//        )
//    }
//
//    override fun createGetCouponsUseCase(): GetCouponsUseCase {
//        return GetCoupons(
//            repository = repositoryFactory.createCouponRepository(),
//        )
//    }
//
//    override fun createGetKyashCardUseCase(): GetKyashCardUseCase {
//        return GetKyashCard(
//            kyashCardRepository = repositoryFactory.createKyashCardRepository(),
//        )
//    }
//
//    override fun createGetNotificationBannersUseCase(releaseFlag: ReleaseFlag):
//            GetNotificationBannersUseCase {
//        return GetNotificationBanners(
//            repository = repositoryFactory.createNotificationBannerRepository(),
//            releaseFlag = releaseFlag,
//        )
//    }
//
//    override fun createGetTopCouponsUseCase(): GetTopCouponsUseCase {
//        return GetTopCoupons(
//            repository = repositoryFactory.createCouponRepository(),
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createGetWalletBalanceUseCase(): GetWalletBalanceUseCase {
//        return GetWalletBalance(
//            balanceRepository = repositoryFactory.createBalanceRepository(),
//        )
//    }
//
//    override fun createGetBnplDebtDetailUseCase(): GetBnplDebtDetailUseCase {
//        return GetBnplDebtDetail(
//            bnplRepository = repositoryFactory.createBnplRepository(),
//        )
//    }
//
//    override fun createRequestKyashMoneyRepayUseCase(): RequestKyashMoneyRepayUseCase {
//        return RequestKyashMoneyRepay(
//            bnplRepository = repositoryFactory.createBnplRepository(),
//        )
//    }
//
//    override fun createApplyMauricioCardUseCase(): ApplyMauricioCardUseCase {
//        return ApplyMauricioCard(
//            mauricioRepository = repositoryFactory.createMauricioRepository(),
//        )
//    }
//
//    override fun createGetMauricioApplicationInfoUseCase(): GetMauricioApplicationInfoUseCase {
//        return GetMauricioApplicationInfo(
//            repository = repositoryFactory.createMauricioRepository(),
//        )
//    }
//
//    override fun createGetMauricioApplicationStatusUseCase(): GetMauricioApplicationStatusUseCase {
//        return GetMauricioApplicationStatus(
//            repository = repositoryFactory.createMauricioRepository(),
//        )
//    }
//
//    override fun createSetMauricioApplicationInfoUseCase(): SetMauricioApplicationInfoUseCase {
//        return SetMauricioApplicationInfo(
//            repository = repositoryFactory.createMauricioRepository(),
//        )
//    }
//
//    override fun createAgreeCouponPersonalInformationUseCase():
//            AgreeCouponPersonalInformationUseCase {
//        return AgreeCouponPersonalInformation(
//            personalInformationRepository = repositoryFactory
//                .createPersonalInformationRepository(),
//        )
//    }
//
//    override fun createGetCouponPersonalInformationAgreementUseCase():
//            GetCouponPersonalInformationAgreementUseCase {
//        return GetCouponPersonalInformationAgreement(
//            personalInformationRepository = repositoryFactory
//                .createPersonalInformationRepository(),
//        )
//    }
//
//    override fun createApplyForBankRegistrationUseCase(): ApplyForBankRegistrationUseCase {
//        return ApplyForBankRegistration(
//            bankRegistrationRepository = repositoryFactory
//                .createBankRegistrationRepository(),
//        )
//    }
//
//    override fun createApplyForJnbUseCase(): ApplyForJnbUseCase {
//        return ApplyForJnb(
//            bankRegistrationRepository = repositoryFactory
//                .createBankRegistrationRepository(),
//        )
//    }
//
//    override fun createApplyForMoneyTapUseCase(): ApplyForMoneyTapUseCase {
//        return ApplyForMoneyTap(
//            bankRegistrationRepository = repositoryFactory
//                .createBankRegistrationRepository(),
//        )
//    }
//
//    override fun createApplyForRakutenUseCase(): ApplyForRakutenUseCase {
//        return ApplyForRakuten(
//            bankRegistrationRepository = repositoryFactory
//                .createBankRegistrationRepository(),
//        )
//    }
//
//    override fun createFindBankUseCase(): FindBankUseCase {
//        return FindBank(
//            bankRegistrationRepository = repositoryFactory
//                .createBankRegistrationRepository(),
//        )
//    }
//
//    override fun createGetBankBranchesUseCase(): GetBankBranchesUseCase {
//        return GetBankBranches(
//            repository = repositoryFactory.createWithdrawalBankRepository(),
//        )
//    }
//
//    override fun createGetBankWithdrawalFeeUseCase(): GetBankWithdrawalFeeUseCase {
//        return GetBankWithdrawalFee(
//            repository = repositoryFactory.createWithdrawalBankRepository(),
//        )
//    }
//
//    override fun createGetFeaturedBanksUseCase(): GetFeaturedBanksUseCase {
//        return GetFeaturedBanks(
//            repository = repositoryFactory.createWithdrawalBankRepository(),
//        )
//    }
//
//    override fun createGetOtherOwnedBankTransferRequestDraftAsFlowUseCase():
//            GetOtherOwnedBankTransferRequestDraftAsFlowUseCase {
//        return GetOtherOwnedBankTransferRequestDraftAsFlow(
//            otherOwnedBankTransferRepository = repositoryFactory
//                .createOtherOwnedBankTransferRepository(),
//        )
//    }
//
//    override fun createRequestBankWithdrawalUseCase(): RequestBankWithdrawalUseCase {
//        return RequestBankWithdrawal(
//            repository = repositoryFactory
//                .createWithdrawalBankRepository(),
//        )
//    }
//
//    override fun createSaveOtherOwnedBankTransferRequestDraftUseCase():
//            SaveOtherOwnedBankTransferRequestDraftUseCase {
//        return SaveOtherOwnedBankTransferRequestDraft(
//            otherOwnedBankTransferRepository = repositoryFactory
//                .createOtherOwnedBankTransferRepository(),
//        )
//    }
//
//    override fun createGetLawsonBankAvailableAmountUseCase(): GetLawsonBankAvailableAmountUseCase {
//        return GetLawsonBankAvailableAmount(
//            depositRepository = repositoryFactory.createDepositRepository(),
//        )
//    }
//
//    override fun createRegisterLawsonBankAtmDepositUseCase(): RegisterLawsonBankAtmDepositUseCase {
//        return RegisterLawsonBankAtmDeposit(
//            lawsonBankRepository = repositoryFactory.createLawsonBankRepository(),
//        )
//    }
//
//    override fun createRegisterLawsonBankAtmWithdrawalUseCase():
//            RegisterLawsonBankAtmWithdrawalUseCase {
//        return RegisterLawsonBankAtmWithdrawal(
//            lawsonBankRepository = repositoryFactory.createLawsonBankRepository(),
//        )
//    }
//
//    override fun createGetBirthDateRangeUseCase(): GetBirthDateRangeUseCase {
//        return GetBirthDateRange()
//    }
//
//    override fun createGetEKycNfcSignTargetDataUseCase(): GetEKycNfcSignTargetDataUseCase {
//        return GetEKycNfcSignTargetData(
//            repository = repositoryFactory.createEKycRepository(),
//        )
//    }
//
//    override fun createGetKycInfoUseCase(): GetKycInfoUseCase {
//        return GetKycInfo(
//            eKycRepository = repositoryFactory.createEKycRepository(),
//        )
//    }
//
//    override fun createPreApplyForEKycOcrUseCase(): PreApplyForEKycOcrUseCase {
//        return PreApplyForEKycOcr(
//            eKycRepository = repositoryFactory.createEKycRepository(),
//        )
//    }
//
//    override fun createStructuralizeEKycNfcFamilyNameUseCase():
//            StructuralizeEKycNfcFamilyNameUseCase {
//        return StructuralizeEKycNfcFamilyName(
//            repository = repositoryFactory.createEKycRepository(),
//        )
//    }
//
//    override fun createApplyLiteUseCase(): ApplyLiteUseCase {
//        return ApplyLite(
//            repository = repositoryFactory.createLiteRepository(),
//        )
//    }
//
//    override fun createGetAndCreateLiteApplicationExpirationDateRenewalInfoUseCase():
//            GetAndCreateLiteApplicationExpirationDateRenewalInfoUseCase {
//        return GetAndCreateLiteApplicationExpirationDateRenewalInfo(
//            repository = repositoryFactory.createLiteRepository(),
//        )
//    }
//
//    override fun createGetLiteApplicationExpirationDateRenewalInfoUseCase():
//            GetLiteApplicationExpirationDateRenewalInfoUseCase {
//        return GetLiteApplicationExpirationDateRenewalInfo(
//            repository = repositoryFactory.createLiteRepository(),
//        )
//    }
//
//    override fun createGetLiteIssuanceFeeInfoUseCase(): GetLiteIssuanceFeeInfoUseCase {
//        return GetLiteIssuanceFeeInfo(
//            repository = repositoryFactory.createLiteRepository(),
//        )
//    }
//
//    override fun createUpdateLiteApplicationExpirationDateRenewalInfoUseCase():
//            UpdateLiteApplicationExpirationDateRenewalInfoUseCase {
//        return UpdateLiteApplicationExpirationDateRenewalInfo(
//            repository = repositoryFactory.createLiteRepository(),
//        )
//    }
//
//    override fun createGetAccountLinkNonceUseCase(): GetAccountLinkNonceUseCase {
//        return GetAccountLinkNonce(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createGetActiveCardUseCase(): GetActiveCardUseCase {
//        return GetActiveCard(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createGetContactsUseCase(): GetContactsUseCase {
//        return GetContacts(
//            repository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createGetIsPublicProfileUseCase(): GetIsPublicProfileUseCase {
//        return GetIsPublicProfile(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createGetMeAccountsUseCase(): GetMeAccountsUseCase {
//        return GetMeAccounts(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createGetMeUseCase(): GetMeUseCase {
//        return GetMe(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUpdateMeUseCase(): UpdateMeUseCase {
//        return UpdateMe(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createGetUserSettingsUseCase(): GetUserSettingsUseCase {
//        return GetUserSettings(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createLinkAppleAccountUseCase(): LinkAppleAccountUseCase {
//        return LinkAppleAccount(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createLinkFacebookAccountUseCase(): LinkFacebookAccountUseCase {
//        return LinkFacebookAccount(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createLinkGoogleAccountUseCase(): LinkGoogleAccountUseCase {
//        return LinkGoogleAccount(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUnlinkAppleAccountUseCase(): UnlinkAppleAccountUseCase {
//        return UnlinkAppleAccount(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUnlinkFacebookAccountUseCase(): UnlinkFacebookAccountUseCase {
//        return UnlinkFacebookAccount(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUnlinkGoogleAccountUseCase(): UnlinkGoogleAccountUseCase {
//        return UnlinkGoogleAccount(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUnlinkMoneyforwardAccountUseCase(): UnlinkMoneyforwardAccountUseCase {
//        return UnlinkMoneyforwardAccount(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUnlinkMoneytreeAccountUseCase(): UnlinkMoneytreeAccountUseCase {
//        return UnlinkMoneytreeAccount(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUnlinkZaimAccountUseCase(): UnlinkZaimAccountUseCase {
//        return UnlinkZaimAccount(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUpdateFullNameUseCase(): UpdateFullNameUseCase {
//        return UpdateFullName(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUpdatePrivacySettingUseCase(): UpdatePrivacySettingUseCase {
//        return UpdatePrivacySetting(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUpdateUserSettingsUseCase(): UpdateUserSettingsUseCase {
//        return UpdateUserSettings(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUploadUserIconUseCase(): UploadUserIconUseCase {
//        return UploadUserIcon(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createGetKyashInfoUseCase(): GetKyashInfoUseCase {
//        return GetKyashInfo(
//            repository = repositoryFactory.createNewsRepository(),
//        )
//    }
//
//    override fun createGetNewsUseCase(): GetNewsUseCase {
//        return GetNews(
//            repository = repositoryFactory.createNewsRepository(),
//        )
//    }
//
//    override fun createReadNewsUseCase(): ReadNewsUseCase {
//        return ReadNews(
//            repository = repositoryFactory.createNewsRepository(),
//        )
//    }
//
//    override fun createGetEvacuationAccountUseCase(): GetEvacuationAccountUseCase {
//        return GetEvacuationAccount(
//            repository = repositoryFactory.createPayrollRepository(),
//        )
//    }
//
//    override fun createGetPayrollAccountUseCase(): GetPayrollAccountUseCase {
//        return GetPayrollAccount(
//            repository = repositoryFactory.createPayrollRepository(),
//        )
//    }
//
//    override fun createRegisterEvacuationAccountUseCase(): RegisterEvacuationAccountUseCase {
//        return RegisterEvacuationAccount(
//            repository = repositoryFactory.createPayrollRepository(),
//        )
//    }
//
//    override fun createRequestPayrollAccountRegistrationUseCase():
//            RequestPayrollAccountRegistrationUseCase {
//        return RequestPayrollAccountRegistration(
//            repository = repositoryFactory.createPayrollRepository(),
//        )
//    }
//
//    override fun createGetPointBalanceUseCase(): GetPointBalanceUseCase {
//        return GetPointBalance(
//            repository = repositoryFactory.createPointRepository(),
//        )
//    }
//
//    override fun createFindAddressUseCase(): FindAddressUseCase {
//        return FindAddress(
//            repository = repositoryFactory.createPostalRepository(),
//        )
//    }
//
//    override fun createGetFriendInvitationUrlUseCase(): GetFriendInvitationUrlUseCase {
//        return GetFriendInvitationUrl(
//            repository = repositoryFactory.createInvitationRepository(),
//        )
//    }
//
//    override fun createGetMonthlySpendingSummaryByCategoryUseCase():
//            GetMonthlySpendingSummaryByCategoryUseCase {
//        return GetMonthlySpendingSummaryByCategory(
//            timelineRepository = repositoryFactory.createTimelineRepository(),
//        )
//    }
//
//    override fun createGetDefaultCategoriesUseCase(): GetDefaultCategoriesUseCase {
//        return GetDefaultCategories(
//            timelineRepository = repositoryFactory.createTimelineRepository(),
//        )
//    }
//
//    override fun createGetTimelineDetailUseCase(): GetTimelineDetailUseCase {
//        return GetTimelineDetail(
//            timelineRepository = repositoryFactory.createTimelineRepository(),
//        )
//    }
//
//    override fun createUpdateTimelineCategoryUseCase(): UpdateTimelineCategoryUseCase {
//        return UpdateTimelineCategory(
//            timelineRepository = repositoryFactory.createTimelineRepository(),
//        )
//    }
//
//    override fun createGetCreditCardRegistrationAcceptanceUseCase(
//        remoteConfig: WalletConfig,
//    ): GetCreditCardRegistrationAcceptanceUseCase {
//        return GetCreditCardRegistrationAcceptance(
//            remoteConfig = remoteConfig,
//        )
//    }
//
//    override fun createGetFundSourcesUseCase(): GetFundSourcesUseCase {
//        return GetFundSources(
//            walletRepository = repositoryFactory.createWalletRepository(),
//        )
//    }
//
//    override fun createGetMembershipIncentivesUseCase(): GetMembershipIncentivesUseCase {
//        return GetMembershipIncentives(
//            walletRepository = repositoryFactory.createWalletRepository(),
//        )
//    }
//
//    override fun createGetPaymentWalletIdUseCase(): GetPaymentWalletIdUseCase {
//        return GetPaymentWalletId(
//            walletRepository = repositoryFactory.createWalletRepository(),
//        )
//    }
//
//    override fun createGetPendingsUseCase(): GetPendingsUseCase {
//        return GetPendings(
//            walletRepository = repositoryFactory.createWalletRepository(),
//        )
//    }
//
//    override fun createGetPrimaryWalletSummaryUseCase(): GetPrimaryWalletSummaryUseCase {
//        return GetPrimaryWalletSummary(
//            walletRepository = repositoryFactory.createWalletRepository(),
//        )
//    }
//
//    override fun createGetPromotionBannerUseCase(): GetPromotionBannerUseCase {
//        return GetPromotionBanner(
//            walletRepository = repositoryFactory.createWalletRepository(),
//        )
//    }
//
//    override fun createGetShareWalletSummaryUseCase(): GetShareWalletSummaryUseCase {
//        return GetShareWalletSummary(
//            walletRepository = repositoryFactory.createWalletRepository(),
//        )
//    }
//
//    override fun createGetWalletTimelineUseCase(): GetWalletTimelineUseCase {
//        return GetWalletTimeline(
//            walletRepository = repositoryFactory.createWalletRepository(),
//        )
//    }
//
//    override fun createSetPaymentWalletUseCase(): SetPaymentWalletUseCase {
//        return SetPaymentWallet(
//            walletRepository = repositoryFactory.createWalletRepository(),
//        )
//    }
//
//    override fun createUpdateTimelinesCategoryUseCase(): UpdateTimelinesCategoryUseCase {
//        return UpdateTimelinesCategory(
//            timelineRepository = repositoryFactory.createTimelineRepository(),
//        )
//    }
//
//    override fun createGetKyashCoinAmountUseCase(): GetKyashCoinAmountUseCase {
//        return GetKyashCoinAmount(
//            repository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
//
//    override fun createGetDailyRouletteStatusUseCase(): GetDailyRouletteStatusUseCase {
//        return GetDailyRouletteStatus(
//            repository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
//
//    override fun createGetCoinPrizeUseCase(): GetCoinPrizeUseCase {
//        return GetCoinPrize(
//            repository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createApplyCoinPrizeUseCase(): ApplyCoinPrizeUseCase {
//        return ApplyCoinPrize(
//            kyashCoinRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createGetDailyRouletteResultUseCase(): GetDailyRouletteResultUseCase {
//        return GetDailyRouletteResult(
//            repository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
//
//    override fun createGetDailyRouletteOptionsUseCase(): GetDailyRouletteOptionsUseCase {
//        return GetDailyRouletteOptions(
//            repository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
//
//    override fun createReceivePrizeUseCase(): ReceivePrizeUseCase {
//        return ReceivePrize(
//            kyashCoinRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createRegisterOtherOwnedBankAccountUseCase():
//            RegisterOtherOwnedBankAccountUseCase {
//        return RegisterOtherOwnedBankAccount(
//            repository = repositoryFactory
//                .createSendRequestRepository(),
//        )
//    }
//
//    override fun createRequestOtherOwnedBankTransferUseCase():
//            RequestOtherOwnedBankTransferUseCase {
//        return RequestOtherOwnedBankTransfer(
//            repository = repositoryFactory
//                .createSendRequestRepository(),
//        )
//    }
//
//    override fun createNotifySharedToSnsUseCase(): NotifySharedToSnsUseCase {
//        return NotifySharedToSns(
//            kyashCoinRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createNeedShowUncheckedPendingApplicationResultUseCase(): NeedShowUncheckedPendingApplicationResultUseCase {
//        return NeedShowUncheckedPendingApplicationResult(
//            kyashCoinRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createCheckLotteryResultUseCase(): CheckLotteryResultUseCase {
//        return CheckLotteryResult(
//            kyashCoinRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createRefreshActiveWeeklyPrizesUseCase(): RefreshActiveWeeklyPrizesUseCase {
//        return RefreshActiveWeeklyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createWatchActiveWeeklyPrizesUseCase(): WatchActiveWeeklyPrizesUseCase {
//        return WatchActiveWeeklyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createRefreshActiveDailyPrizesUseCase(): RefreshActiveDailyPrizesUseCase {
//        return RefreshActiveDailyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createWatchActiveDailyPrizesUseCase(): WatchActiveDailyPrizesUseCase {
//        return WatchActiveDailyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createRefreshUpcomingWeeklyPrizesUseCase(): RefreshUpcomingWeeklyPrizesUseCase {
//        return RefreshUpcomingWeeklyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createWatchUpcomingWeeklyPrizesUseCase(): WatchUpcomingWeeklyPrizesUseCase {
//        return WatchUpcomingWeeklyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createQueryAppliedDailyPrizesUseCase(): QueryAppliedDailyPrizesUseCase {
//        return QueryAppliedDailyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createNeedShowRewardTopOnboardingUseCase(): NeedShowRewardTopOnboardingUseCase {
//        return NeedShowRewardTopOnboarding(
//            kyashCoinRepository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
//
//    override fun createSetShowRewardTopOnboardingUseCase(): SetShowRewardTopOnboardingUseCase {
//        return SetShowRewardTopOnboarding(
//            kyashCoinRepository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
//
//    override fun createNeedShowRewardTabNewBalloonUseCase(): NeedShowRewardTabNewBalloonUseCase {
//        return NeedShowRewardTabNewBalloon(
//            kyashCoinRepository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
//
//    override fun createSetShowRewardTabNewBalloonUseCase(): SetShowRewardTabNewBalloonUseCase {
//        return SetShowRewardTabNewBalloon(
//            kyashCoinRepository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
//
//    override fun createGetUncheckRewardCountUseCase(): GetUncheckRewardCountUseCase {
//        return GetUncheckRewardCount(
//            repository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createGetKyashCoinHistoryUseCase(): GetKyashCoinHistoryUseCase {
//        return GetKyashCoinHistory(
//            repository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
//
//    override fun createQueryAppliedWeeklyPrizesUseCase(): QueryAppliedWeeklyPrizesUseCase {
//        return QueryAppliedWeeklyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createRefreshAppliedDailyPrizesUseCase(): RefreshAppliedDailyPrizesUseCase {
//        return RefreshAppliedDailyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createRefreshAppliedWeeklyPrizesUseCase(): RefreshAppliedWeeklyPrizesUseCase {
//        return RefreshAppliedWeeklyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createWatchAppliedDailyPrizesUseCase(): WatchAppliedDailyPrizesUseCase {
//        return WatchAppliedDailyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createWatchAppliedWeeklyPrizesUseCase(): WatchAppliedWeeklyPrizesUseCase {
//        return WatchAppliedWeeklyPrizes(
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createWatchNeedShowRewardTabIndicatorUseCase(): WatchNeedShowRewardTabIndicatorUseCase {
//        return WatchNeedShowRewardTabIndicator(
//            kyashCoinRepository = repositoryFactory.createKyashCoinRepository(),
//            prizeRepository = repositoryFactory.createKyashPrizeRepository(),
//        )
//    }
//
//    override fun createRequestUpdateRewardTabIndicatorUseCase(): RequestUpdateRewardTabIndicatorUseCase {
//        return RequestUpdateRewardTabIndicator(
//            kyashCoinRepository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
//
//    override fun createGetBudgetSettingFormAsFlowUseCase(): GetBudgetSettingFormAsFlowUseCase {
//        return GetBudgetSettingFormAsFlow(
//            budgetRepository = repositoryFactory.createBudgetRepository(),
//        )
//    }
//
//    override fun createSaveBudgetSettingFormUseCase(): SaveBudgetSettingFormUseCase {
//        return SaveBudgetSettingForm(
//            budgetRepository = repositoryFactory.createBudgetRepository(),
//        )
//    }
//
//    override fun createClearBudgetSettingFormUseCase(): ClearBudgetSettingFormUseCase {
//        return ClearBudgetSettingForm(
//            budgetRepository = repositoryFactory.createBudgetRepository(),
//        )
//    }
//
//    override fun createSetBudgetSettingUseCase(): SetBudgetSettingUseCase {
//        return SetBudgetSetting(
//            walletRepository = repositoryFactory.createWalletRepository(),
//            budgetRepository = repositoryFactory.createBudgetRepository(),
//        )
//    }
//
//    override fun createDeleteBudgetSettingUseCase(): DeleteBudgetSettingUseCase {
//        return DeleteBudgetSetting(
//            repository = repositoryFactory.createWalletRepository(),
//        )
//    }
//
//    override fun createGetMonthlyBudgetUseCase(): GetMonthlyBudgetUseCase {
//        return GetMonthlyBudget(
//            repository = repositoryFactory.createTimelineRepository(),
//        )
//    }
//
//    override fun createGetSpendingAggregationStartDateUseCase():
//            GetSpendingAggregationStartDateUseCase {
//        return GetSpendingAggregationStartDate(
//            repository = repositoryFactory.createTimelineRepository(),
//        )
//    }
//
//    override fun createSetSpendingAggregationStartDateUseCase():
//            SetSpendingAggregationStartDateUseCase {
//        return SetSpendingAggregationStartDate(
//            repository = repositoryFactory.createTimelineRepository(),
//        )
//    }
//
//    override fun createDeleteWithdrawalBankAccountUseCase(): DeleteWithdrawalBankAccountUseCase {
//        return DeleteWithdrawalBankAccount(
//            repository = repositoryFactory.createWithdrawalBankRepository(),
//        )
//    }
//
//    override fun createGetWithdrawalBankAccountUseCase(): GetWithdrawalBankAccountUseCase {
//        return GetWithdrawalBankAccount(
//            repository = repositoryFactory.createWithdrawalBankRepository(),
//        )
//    }
//
//    override fun createGetMonthlyTimelineUseCase(): GetMonthlyTimelineUseCase {
//        return GetMonthlyTimeline(
//            timelineRepository = repositoryFactory.createTimelineRepository(),
//        )
//    }
//
//    override fun createGetCustomCategoryIconsUseCase(): GetCustomCategoryIconsUseCase {
//        return GetCustomCategoryIcons(
//            categoryRepository = repositoryFactory.createCategoryRepository(),
//            customCategoryDraftRepository = repositoryFactory.createCustomCategoryDraftRepository(),
//        )
//    }
//
//    override fun createCreateCustomCategoryUseCase(): CreateCustomCategoryUseCase {
//        return CreateCustomCategory(
//            repository = repositoryFactory.createCategoryRepository(),
//        )
//    }
//
//    override fun createUpdateCustomCategoryUseCase(): UpdateCustomCategoryUseCase {
//        return UpdateCustomCategory(
//            repository = repositoryFactory.createCategoryRepository(),
//        )
//    }
//
//    override fun createDeleteCustomCategoryUseCase(): DeleteCustomCategoryUseCase {
//        return DeleteCustomCategory(
//            categoryRepository = repositoryFactory.createCategoryRepository(),
//        )
//    }
//
//    override fun createGetOwnSpendingCategoriesUseCase(): GetOwnSpendingCategoriesUseCase {
//        return GetOwnSpendingCategories(
//            categoryRepository = repositoryFactory.createCategoryRepository(),
//        )
//    }
//
//    override fun createGetCustomCategoriesUseCase(): GetCustomCategoriesUseCase {
//        return GetCustomCategories()
//    }
//
//    override fun createSetCustomCategoryIconDraftUseCase(): SetCustomCategoryIconDraftUseCase {
//        return SetCustomCategoryIconDraft(
//            repository = repositoryFactory.createCustomCategoryDraftRepository(),
//        )
//    }
//
//    override fun createWatchCustomCategoryIconDraftUseCase(): WatchCustomCategoryIconDraftUseCase {
//        return WatchCustomCategoryIconDraft(
//            repository = repositoryFactory.createCustomCategoryDraftRepository(),
//        )
//    }
//
//    override fun createWatchCustomCategoryIconsUseCase(): WatchCustomCategoryIconsUseCase {
//        return WatchCustomCategoryIcons(
//            repository = repositoryFactory.createCustomCategoryDraftRepository(),
//        )
//    }
//
//    override fun createCheckEmailAddressUseCase(): CheckEmailAddressUseCase {
//        return CheckEmailAddress(
//            accountRepository = repositoryFactory.createAccountRepository(),
//        )
//    }
//
//    override fun createCheckUserNameUseCase(): CheckUserNameUseCase {
//        return CheckUserName(
//            accountRepository = repositoryFactory.createAccountRepository(),
//        )
//    }
//
//    override fun createSendVerificationCodeViaSmsUseCase(): SendVerificationCodeViaSmsUseCase {
//        return SendVerificationCodeViaSms(
//            accountRepository = repositoryFactory.createAccountRepository(),
//        )
//    }
//
//    override fun createSendVerificationCodeViaVoiceCallUseCase(): SendVerificationCodeViaVoiceCallUseCase {
//        return SendVerificationCodeViaVoiceCall(
//            accountRepository = repositoryFactory.createAccountRepository(),
//        )
//    }
//
//    override fun createVerifyPhoneNumberUseCase(): VerifyPhoneNumberUseCase {
//        return VerifyPhoneNumber(
//            repository = repositoryFactory.createAccountRepository(),
//        )
//    }
//
//    override fun createSignUpUseCase(
//        accessTokenRepository: AccessTokenRepository,
//    ): SignUpUseCase {
//        return SignUp(
//            accountRepository = repositoryFactory.createAccountRepository(),
//            accessTokenRepository = accessTokenRepository,
//        )
//    }
//
//    override fun createGetKycStatusUseCase(): GetKycStatusUseCase {
//        return GetKycStatus(
//            kycRepository = repositoryFactory.createKycRepository(),
//        )
//    }
//
//    override fun createGetBnplAutoRepaymentSettingUseCase(): GetBnplAutoRepaymentSettingUseCase {
//        return GetBnplAutoRepaymentSetting(
//            repository = repositoryFactory.createBnplRepository(),
//        )
//    }
//
//    override fun createSetBnplAutoRepaymentUseCase(): SetBnplAutoRepaymentUseCase {
//        return SetBnplAutoRepayment(
//            repository = repositoryFactory.createBnplRepository(),
//        )
//    }
//
//    override fun createCheckHasOverDueDebtUseCase(): CheckHasOverDueDebtUseCase {
//        return CheckHasOverDueDebt(
//            repository = repositoryFactory.createBnplRepository(),
//        )
//    }
//
//    override fun createGetBnplRepaymentHistoryUseCase(): GetBnplRepaymentHistoryUseCase {
//        return GetBnplRepaymentHistory()
//    }
//
//    override fun createGetBnplPendingRepaymentListUseCase(): GetBnplPendingRepaymentListUseCase {
//        return GetBnplPendingRepaymentList()
//    }
//
//    override fun createGetBnplPendingRepaymentUseCase(): GetBnplPendingRepaymentUseCase {
//        return GetBnplPendingRepayment()
//    }
//
//    override fun createCancelCvsRepaymentUseCase(): CancelCvsRepaymentUseCase {
//        return CancelCvsRepayment(
//            repository = repositoryFactory.createBnplRepository(),
//        )
//    }
//
//    override fun createGetOtherDepositMethodsUseCase(): GetOtherDepositMethodsUseCase {
//        return GetOtherDepositMethods()
//    }
//
//    override fun createGetVirtualBankAccountUseCase(): GetVirtualBankAccountUseCase {
//        return GetVirtualBankAccount(
//            depositBankAccountRepository = repositoryFactory.createDepositBankAccountRepository(),
//        )
//    }
//
//    override fun createUpdateUserNameUseCase(): UpdateUserNameUseCase {
//        return UpdateUserName(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createPostRedeemUseCase(): PostRedeemUseCase {
//        return PostRedeem(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createUpdateInformationRequiredForTransactions():
//            UpdateInformationRequiredForTransactionsUseCase {
//        return UpdateInformationRequiredForTransactions(
//            meRepository = repositoryFactory.createMeRepository(),
//        )
//    }
//
//    override fun createGetCustomerUseCase(): GetCustomerUseCase {
//        return GetCustomer()
//    }
//
//    override fun createGetResidenceCardUseCase(): GetResidenceCardUseCase {
//        return GetResidenceCard()
//    }
//
//    override fun createGetCustomerUpdateExpirationUseCase(): GetCustomerUpdateExpirationUseCase {
//        return GetCustomerUpdateExpiration()
//    }
//
//    override fun createIssueKyashCardVirtualUseCase(): IssueKyashCardVirtualUseCase {
//        return IssueKyashCardVirtual(
//            kyashCardVirtualRepository = repositoryFactory.createKyashCardVirtualRepository(),
//        )
//    }
//
//    override fun createGetAttentionLevelUseCase(): GetAttentionLevelUseCase {
//        return GetAttentionLevel()
//    }
//
//    override fun createGetEKycNfcApplicationProgressUseCase(): GetEKycNfcApplicationProgressUseCase {
//        return GetEKycNfcApplicationProgress(
//            repository = repositoryFactory.createEKycRepository(),
//        )
//    }
//
//    override fun createStartEKycNfcApplicationUseCase(): StartEKycNfcApplicationUseCase {
//        return StartEKycNfcApplication(
//            repository = repositoryFactory.createEKycRepository(),
//        )
//    }
//
//    override fun createGetShouldShowDepositBalloonAsFlowUseCase(): GetShouldShowDepositBalloonAsFlowUseCase {
//        return GetShouldShowDepositBalloonAsFlow(
//            repository = repositoryFactory.createDepositRepository(),
//        )
//    }
//
//    override fun createSetShouldShowDepositBalloonUseCase(): SetShouldShowDepositBalloonUseCase {
//        return SetShouldShowDepositBalloon(
//            repository = repositoryFactory.createDepositRepository(),
//        )
//    }
//
//    override fun createGetEKycOcrPrefillInfoUseCase(): GetEKycOcrPrefillInfoUseCase {
//        return GetEKycOcrPrefillInfo(
//            repository = repositoryFactory.createEKycRepository(),
//        )
//    }
//
//    override fun createApplyForEKycOcrUseCase(): ApplyForEKycOcrUseCase {
//        return ApplyForEKycOcr(
//            repository = repositoryFactory.createEKycRepository(),
//        )
//    }
//
//    override fun createGetCurrentStampCardUseCase(): GetCurrentStampCardUseCase {
//        return GetCurrentStampCard(
//            repository = repositoryFactory.createKyashCoinRepository(),
//        )
//    }
}