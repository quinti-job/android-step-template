package com.quinti.android_step_template.kmp.di

import co.kyash.mobile.account.accountlink.AccountLinkReactor
import co.kyash.mobile.account.accountsetting.AccountSettingReactor
import co.kyash.mobile.account.cancellation.AccountCancellationReactor
import co.kyash.mobile.account.customer.CustomerCompanyNameEditReactor
import co.kyash.mobile.account.customer.CustomerIncomeEditReactor
import co.kyash.mobile.account.customer.CustomerOccupationEditReactor
import co.kyash.mobile.account.customer.CustomerPositionEditReactor
import co.kyash.mobile.account.customer.CustomerPurposeEditReactor
import co.kyash.mobile.account.customer.CustomerRegistrationInformationReactor
import co.kyash.mobile.account.customer.CustomerSourceOfIncomeEditReactor
import co.kyash.mobile.account.email.EmailEditReactor
import co.kyash.mobile.account.fullname.FullNameEditReactor
import co.kyash.mobile.account.kyashid.KyashIdEditReactor
import co.kyash.mobile.account.notificationsetting.NotificationSettingReactor
import co.kyash.mobile.account.passcode.PasscodeAuthenticationReactor
import co.kyash.mobile.account.passcode.PasscodeGuideReactor
import co.kyash.mobile.account.passcode.PasscodeRegistrationReactor
import co.kyash.mobile.account.passcode.PasscodeRenewalOnboardingReactor
import co.kyash.mobile.account.passcode.PasscodeVerificationReactor
import co.kyash.mobile.account.username.UserNameEditReactor
import co.kyash.mobile.bnpl.BnplApplicationConfirmCallParam
import co.kyash.mobile.bnpl.BnplApplicationConfirmReactor
import co.kyash.mobile.bnpl.BnplAutoRepaymentSettingReactor
import co.kyash.mobile.bnpl.BnplAutoRepaymentSettingResultReactor
import co.kyash.mobile.bnpl.BnplAutoRepaymentSuggestionModalReactor
import co.kyash.mobile.bnpl.BnplIntroductionReactor
import co.kyash.mobile.bnpl.BnplKyashMoneyRepaymentConfirmationReactor
import co.kyash.mobile.bnpl.BnplPendingRepaymentListReactor
import co.kyash.mobile.bnpl.BnplRepaymentConfirmationReactor
import co.kyash.mobile.bnpl.BnplRepaymentHistoryReactor
import co.kyash.mobile.bnpl.BnplSelectRepaymentMethodReactor
import co.kyash.mobile.bnpl.BnplTermsReactor
import co.kyash.mobile.cardapplication.common.CardActivationConfirmationReactor
import co.kyash.mobile.cardapplication.lite.LiteCardExpirationDateRenewalActivationReactor
import co.kyash.mobile.cardapplication.lite.application.LiteExpirationDateRenewalAddressConfirmationReactor
import co.kyash.mobile.cardapplication.lite.application.LiteExpirationDateRenewalAddressRegistrationReactor
import co.kyash.mobile.cardapplication.mauricio.MauricioApplicationAddressConfirmationReactor
import co.kyash.mobile.cardapplication.mauricio.MauricioApplicationReactor
import co.kyash.mobile.cardapplication.mauricio.MauricioCardActivatedReactor
import co.kyash.mobile.cardapplication.mauricio.MauricioCardActivationConfirmationReactor
import co.kyash.mobile.cardapplication.mauricio.MauricioCardPanActivationReactor
import co.kyash.mobile.cardapplication.mauricio.MauricioIssueStatusReactor
import co.kyash.mobile.cardapplication.mauricio.MauricioShippingAddressConfirmationReactor
import co.kyash.mobile.cardapplication.mauricio.MauricioShippingAddressRegistrationReactor
import co.kyash.mobile.cardapplication.virtual.KyashCardVirtualIssueReactor
import co.kyash.mobile.core.featureflag.ReleaseFlag
import co.kyash.mobile.core.result.ResultCallParam
import co.kyash.mobile.core.result.ResultReactor
import co.kyash.mobile.data.api.di.ApiFactory
import co.kyash.mobile.data.api.di.KyashApiFactory
import co.kyash.mobile.data.datasource.di.LocalDataSourceFactory
import co.kyash.mobile.data.repository.di.provideRepositoryFactory
import co.kyash.mobile.funds.bankregistration.BankRegistrationReactor
import co.kyash.mobile.funds.deposit.FirstDepositMethodReactor
import co.kyash.mobile.funds.deposit.OtherDepositMethodReactor
import co.kyash.mobile.funds.deposit.card.CreditCardRegistrationDescriptionReactor
import co.kyash.mobile.funds.deposit.lawsonbank.LawsonBankAtmDepositCompletedReactor
import co.kyash.mobile.funds.deposit.lawsonbank.LawsonBankAtmDepositInstructionReactor
import co.kyash.mobile.funds.deposit.lawsonbank.LawsonBankAtmDepositQrScanReactor
import co.kyash.mobile.funds.withdrawal.bank.AboutBankWithdrawalFeeReactor
import co.kyash.mobile.funds.withdrawal.bank.BankWithdrawalConfirmationReactor
import co.kyash.mobile.funds.withdrawal.bank.BankWithdrawalReactor
import co.kyash.mobile.funds.withdrawal.lawsonbank.LawsonBankAtmWithdrawalCompletedReactor
import co.kyash.mobile.funds.withdrawal.lawsonbank.LawsonBankAtmWithdrawalInstructionReactor
import co.kyash.mobile.funds.withdrawal.lawsonbank.LawsonBankAtmWithdrawalQrScanReactor
import co.kyash.mobile.funds.withdrawal.sevenbank.SevenBankAtmWithdrawalInstructionReactor
import co.kyash.mobile.kyc.common.EKycSelectReactor
import co.kyash.mobile.kyc.nfc.EKycNfcCardReadingReactor
import co.kyash.mobile.kyc.nfc.EKycNfcCompletionReactor
import co.kyash.mobile.kyc.nfc.EKycNfcIdentificationReactor
import co.kyash.mobile.kyc.nfc.EKycNfcPasswordIdentificationReactor
import co.kyash.mobile.kyc.ocr.EKycOcrIdentificationReactor
import co.kyash.mobile.model.UserEKycReason
import co.kyash.mobile.model.funds.deposit.OtherDepositMethodFilter
import co.kyash.mobile.model.kyc.common.EKycNfcSignTargetDataResult
import co.kyash.mobile.model.kyc.nfc.EKycNfcCardInfo
import co.kyash.mobile.model.kyc.nfc.EKycNfcResult
import co.kyash.mobile.model.onboarding.SignUpInfo
import co.kyash.mobile.model.payroll.EvacuationAccountTargetType
import co.kyash.mobile.model.reward.UncheckedPrizeSummary
import co.kyash.mobile.model.timeline.categorization.CategoryUpdateTarget
import co.kyash.mobile.model.wallet.WalletConfig
import co.kyash.mobile.model.wallet.budget.SpendingCategory
import co.kyash.mobile.news.news.NewsReactor
import co.kyash.mobile.onboarding.config.OnboardingConfig
import co.kyash.mobile.onboarding.phoneverification.PhoneNumberInputReactor
import co.kyash.mobile.onboarding.phoneverification.PhoneNumberVerificationReactor
import co.kyash.mobile.onboarding.signup.EmailSignUpReactor
import co.kyash.mobile.onboarding.signup.personal.PersonalInformationCallParam
import co.kyash.mobile.onboarding.signup.personal.PersonalInformationInputReactor
import co.kyash.mobile.onboarding.signup.username.UserNameInputCallParam
import co.kyash.mobile.onboarding.signup.username.UserNameInputReactor
import co.kyash.mobile.onboarding.ui.card_selection.OnboardingCardSelectionReactor
import co.kyash.mobile.payroll.accountregistration.PayrollAccountRegistrationReactor
import co.kyash.mobile.payroll.evacuationaccount.EvacuationAccountSelectionReactor
import co.kyash.mobile.payroll.evacuationaccountregistration.EvacuationAccountRegistrationReactor
import co.kyash.mobile.payroll.guide.PayrollAccountGuideReactor
import co.kyash.mobile.payroll.payrollaccount.PayrollAccountReactor
import co.kyash.mobile.promotion.campaign.CampaignCodeInputReactor
import co.kyash.mobile.promotion.coupon.CouponDetailReactor
import co.kyash.mobile.promotion.coupon.CouponListReactor
import co.kyash.mobile.promotion.coupon.CouponPersonalInformationAgreementReactor
import co.kyash.mobile.promotion.coupon.CouponTopReactor
import co.kyash.mobile.promotion.friendinvitation.FriendInvitationReactor
import co.kyash.mobile.promotion.redeem.RedeemReactor
import co.kyash.mobile.repository.AccessTokenRepository
import co.kyash.mobile.repository.di.RepositoryFactory
import co.kyash.mobile.reward.PrizeApplicationResult
import co.kyash.mobile.reward.coin.KyashCoinDetailReactor
import co.kyash.mobile.reward.offerwall.SkyFlagOfferWallReactor
import co.kyash.mobile.reward.prize.AppliedPrizeListReactor
import co.kyash.mobile.reward.prize.RewardPrizeApplicationResultReactor
import co.kyash.mobile.reward.prize.RewardPrizeDetailReactor
import co.kyash.mobile.reward.prize.UncheckedRewardApplicationResultReactor
import co.kyash.mobile.reward.roulette.DailyRouletteReactor
import co.kyash.mobile.reward.top.RewardTopReactor
import co.kyash.mobile.reward.welcome.WelcomeChallengeReactor
import co.kyash.mobile.sendrequest.SendRequestReactor
import co.kyash.mobile.sendrequest.otherownedbank.OtherOwnedBankTransferAccountOwnerNameInputReactor
import co.kyash.mobile.sendrequest.otherownedbank.OtherOwnedBankTransferAccountTypeAndNumberInputReactor
import co.kyash.mobile.sendrequest.otherownedbank.OtherOwnedBankTransferAmountInputReactor
import co.kyash.mobile.sendrequest.otherownedbank.OtherOwnedBankTransferBankSelectionReactor
import co.kyash.mobile.sendrequest.otherownedbank.OtherOwnedBankTransferBranchSelectionReactor
import co.kyash.mobile.sendrequest.otherownedbank.OtherOwnedBankTransferConfirmationReactor
import co.kyash.mobile.sendrequest.otherownedbank.OtherOwnedBankTransferRequesterDisplayNameInputReactor
import co.kyash.mobile.timeline.categorization.CategorySelectionReactor
import co.kyash.mobile.timeline.categorization.CustomCategoryIconSelectionReactor
import co.kyash.mobile.timeline.categorization.CustomCategoryReactor
import co.kyash.mobile.timeline.categorization.MonthlyCategorizationSpendingSummaryReactor
import co.kyash.mobile.timeline.categorization.TimelineCategorizationReactor
import co.kyash.mobile.usecase.di.UseCaseFactory
import co.kyash.mobile.wallet.budget.BudgetSettingReactor
import co.kyash.mobile.wallet.budget.CategoryBudgetSettingReactor
import co.kyash.mobile.wallet.budget.MonthlyBudgetSummaryReactor
import co.kyash.mobile.wallet.spendingaggregation.SpendingAggregationStartDateReactor
import co.kyash.mobile.wallet.wallet.WalletPageReactor
import com.quinti.android_step_template.kmp.data.api.di.ApiFactory
import com.quinti.android_step_template.kmp.data.api.di.KyashApiFactory
import com.quinti.android_step_template.kmp.data.datasource.di.LocalDataSourceFactory
import com.quinti.android_step_template.kmp.data.repository.di.RepositoryFactory
import com.quinti.android_step_template.kmp.data.repository.di.provideRepositoryFactory
import com.quinti.android_step_template.kmp.domain.reactor.AppliedPrizeListReactor
import com.quinti.android_step_template.kmp.domain.reactor.DailyRouletteReactor
import com.quinti.android_step_template.kmp.domain.reactor.SkyFlagOfferWallReactor
import com.quinti.android_step_template.kmp.domain.usecase.di.UseCaseFactory
import korlibs.time.DateTimeTz
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * クライアントで利用するReactorの依存解決と生成の責務を担うDIコンテナ。
 *
 * 各Reactor固有で必要となるパラメータはそれぞれの生成関数のパラメータで受け取るように実装する。
 *
 * 例
 * ```kt
 * fun createSampleReactor(param1: String, param2: Int): SampleReactor {
 *     return SampleReactor(param1 = param1, param2 = param2)
 * }
 * ```
 *
 * ref: https://github.com/Kyash/Kyash-Mobile-Module/discussions/929
 */
@Suppress("TooManyFunctions", "MaxLineLength", "LargeClass", "LongParameterList")
class ReactorDiContainer(
    kyashApiFactory: KyashApiFactory,
) {
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
    private val apiFactory: ApiFactory = ApiFactory.provide(kyashApiFactory)
    private val localDataSourceFactory: LocalDataSourceFactory = LocalDataSourceFactory.provide()
    private val repositoryFactory: RepositoryFactory = provideRepositoryFactory(
        apiFactory = apiFactory,
        localDataSourceFactory = localDataSourceFactory,
    )
    private val useCaseFactory: UseCaseFactory = UseCaseFactory.provide(
        repositoryFactory,
    )

    fun createOtherOwnedBankTransferBankSelectionReactor(): OtherOwnedBankTransferBankSelectionReactor {
        return OtherOwnedBankTransferBankSelectionReactor(
            mainDispatcher = mainDispatcher,
            getFeaturedBanks = useCaseFactory.createGetFeaturedBanksUseCase(),
            saveRequestDraft = useCaseFactory.createSaveOtherOwnedBankTransferRequestDraftUseCase(),
            getPasscodeSetting = useCaseFactory.createGetPasscodeSettingUseCase(),
        )
    }

    /**
     * [page] : 現在日時の集計月から何ヶ月前かの値。が`0`の場合は現在日時の集計月の集計期間とする。
     */
    fun createMonthlySpendingReactor(
        page: Int,
    ): MonthlyCategorizationSpendingSummaryReactor {
        return MonthlyCategorizationSpendingSummaryReactor(
            mainDispatcher = mainDispatcher,
            page = page,
            getMonthlySpendingSummaryByCategory = useCaseFactory.createGetMonthlySpendingSummaryByCategoryUseCase(),
        )
    }

    /**
     * [page] : 現在日時の集計月から何ヶ月前かの値。が`0`の場合は現在日時の集計月の集計期間とする。
     */
    fun createMonthlyBudgetSummaryReactor(
        page: Int,
    ): MonthlyBudgetSummaryReactor {
        return MonthlyBudgetSummaryReactor(
            mainDispatcher = mainDispatcher,
            page = page,
            getMonthlySpendingSummaryByCategory = useCaseFactory.createGetMonthlySpendingSummaryByCategoryUseCase(),
        )
    }

    fun createCategorySelectionReactorForUpdateSingle(
        timelineType: String,
        txUuid: String,
        initialCategoryCode: String,
    ): CategorySelectionReactor {
        return CategorySelectionReactor(
            mainDispatcher = mainDispatcher,
            getOwnSpendingCategories = useCaseFactory.createGetOwnSpendingCategoriesUseCase(),
            updateTimelineCategory = useCaseFactory.createUpdateTimelineCategoryUseCase(),
            updateTimelinesCategory = useCaseFactory.createUpdateTimelinesCategoryUseCase(),
            deleteCustomCategory = useCaseFactory.createDeleteCustomCategoryUseCase(),
            initialArgs = CategorySelectionReactor.InitialArgs.ForUpdateSingle(
                timelineType = timelineType,
                txUuid = txUuid,
                initialCategoryId = initialCategoryCode,
            ),
        )
    }

    fun createCategorySelectionReactorForUpdateMultiple(
        categoryUpdateTargets: Set<CategoryUpdateTarget>,
        initialCategoryCode: String,
    ): CategorySelectionReactor {
        return CategorySelectionReactor(
            mainDispatcher = mainDispatcher,
            getOwnSpendingCategories = useCaseFactory.createGetOwnSpendingCategoriesUseCase(),
            updateTimelineCategory = useCaseFactory.createUpdateTimelineCategoryUseCase(),
            updateTimelinesCategory = useCaseFactory.createUpdateTimelinesCategoryUseCase(),
            deleteCustomCategory = useCaseFactory.createDeleteCustomCategoryUseCase(),
            initialArgs = CategorySelectionReactor.InitialArgs.ForUpdateMultiple(
                categoryUpdateTargets = categoryUpdateTargets,
                initialCategoryId = initialCategoryCode,
            ),
        )
    }

    fun createCategorySelectionReactorForCustomCategory(): CategorySelectionReactor {
        return CategorySelectionReactor(
            mainDispatcher = mainDispatcher,
            getOwnSpendingCategories = useCaseFactory.createGetOwnSpendingCategoriesUseCase(),
            updateTimelineCategory = useCaseFactory.createUpdateTimelineCategoryUseCase(),
            updateTimelinesCategory = useCaseFactory.createUpdateTimelinesCategoryUseCase(),
            deleteCustomCategory = useCaseFactory.createDeleteCustomCategoryUseCase(),
            initialArgs = CategorySelectionReactor.InitialArgs.ForCustomCategory,
        )
    }

    fun createRewardTopReactor(
        isAutoNavigateToRouletteEnabled: Boolean,
    ): RewardTopReactor {
        return RewardTopReactor(
            mainDispatcher = mainDispatcher,
            getDailyRouletteStatus = useCaseFactory.createGetDailyRouletteStatusUseCase(),
            getKyashCoinAmount = useCaseFactory.createGetKyashCoinAmountUseCase(),
            getUncheckRewardCount = useCaseFactory.createGetUncheckRewardCountUseCase(),
            watchActiveWeeklyPrizes = useCaseFactory.createWatchActiveWeeklyPrizesUseCase(),
            watchActiveDailyPrizes = useCaseFactory.createWatchActiveDailyPrizesUseCase(),
            watchUpcomingWeeklyPrizes = useCaseFactory.createWatchUpcomingWeeklyPrizesUseCase(),
            refreshActiveWeeklyPrizes = useCaseFactory.createRefreshActiveWeeklyPrizesUseCase(),
            refreshActiveDailyPrizes = useCaseFactory.createRefreshActiveDailyPrizesUseCase(),
            refreshUpcomingWeeklyPrizes = useCaseFactory
                .createRefreshUpcomingWeeklyPrizesUseCase(),
            setShowRewardTopOnboarding = useCaseFactory.createSetShowRewardTopOnboardingUseCase(),
            needShowRewardTopOnboarding = useCaseFactory.createNeedShowRewardTopOnboardingUseCase(),
            requestUpdateRewardTabIndicator = useCaseFactory.createRequestUpdateRewardTabIndicatorUseCase(),
            isAutoNavigateToRouletteEnabled = isAutoNavigateToRouletteEnabled,
            countDownEnabled = true,
            getCurrentStampCard = useCaseFactory.createGetCurrentStampCardUseCase(),
            exchangeStampCardReward = useCaseFactory.createExchangeStampCardRewardUseCase(),
            getPointBalance = useCaseFactory.createGetPointBalanceUseCase(),
            isWelcomeChallengeEnabled = releaseFlag.isRewardOnboarding202404Enabled,
            needShowRewardSpotlight = useCaseFactory.createNeedShowRewardSpotlightUseCase(),
            refreshWelcomePrizes = useCaseFactory.createRefreshWelcomePrizesUseCase(),
            watchWelcomePrizes = useCaseFactory.createWatchWelcomePrizesUseCase(),
            needShowRewardStampOnboarding = useCaseFactory.createNeedShowRewardStampOnboardingUseCase(),
            setNeedShowRewardStampOnboarding = useCaseFactory.createSetShowRewardStampOnboardingUseCase(),
            sendShowRewardTopOnboardingFirstOpenEvent = useCaseFactory.createSendShowRewardTopOnboardingEventUseCase(),
        )
    }

    fun createKyashCoinDetailReactor(): KyashCoinDetailReactor {
        return KyashCoinDetailReactor(
            mainDispatcher = mainDispatcher,
            getKyashCoinAmount = useCaseFactory.createGetKyashCoinAmountUseCase(),
            getKyashCoinHistoryUseCase = useCaseFactory.createGetKyashCoinHistoryUseCase(),
        )
    }

    fun createDailyRouletteReactor(): DailyRouletteReactor {
        return DailyRouletteReactor(
            mainDispatcher = mainDispatcher,
            getDailyRouletteResult = useCaseFactory.createGetDailyRouletteResultUseCase(),
            getDailyRouletteStatus = useCaseFactory.createGetDailyRouletteStatusUseCase(),
            getDailyRouletteOptions = useCaseFactory.createGetDailyRouletteOptionsUseCase(),
        )
    }

    fun createRewardPrizeDetailReactor(
        args: RewardPrizeDetailReactor.InitialArgs,
    ): RewardPrizeDetailReactor {
        return RewardPrizeDetailReactor(
            mainDispatcher = mainDispatcher,
            getKyashCoinAmount = useCaseFactory.createGetKyashCoinAmountUseCase(),
            getPrize = useCaseFactory.createGetCoinPrizeUseCase(),
            applyCoinPrize = useCaseFactory.createApplyCoinPrizeUseCase(),
            notifySharedToSns = useCaseFactory.createNotifySharedToSnsUseCase(),
            receivePrize = useCaseFactory.createReceivePrizeUseCase(),
            args = args,
        )
    }

    fun createRewardPrizeApplicationResultReactor(
        result: PrizeApplicationResult,
    ): RewardPrizeApplicationResultReactor {
        return RewardPrizeApplicationResultReactor(
            mainDispatcher = mainDispatcher,
            result = result,
            notifySharedToSns = useCaseFactory.createNotifySharedToSnsUseCase(),
            receiveCoinPrize = useCaseFactory.createReceivePrizeUseCase(),
            checkLotteryResult = useCaseFactory.createCheckLotteryResultUseCase(),
            refreshWelcomePrizes = useCaseFactory.createRefreshWelcomePrizesUseCase(),
        )
    }

    fun createAppliedPrizeListReactor(): AppliedPrizeListReactor {
        return AppliedPrizeListReactor(
            mainDispatcher = mainDispatcher,
            watchAppliedWeeklyPrizes = useCaseFactory.createWatchAppliedWeeklyPrizesUseCase(),
            watchAppliedDailyPrizes = useCaseFactory.createWatchAppliedDailyPrizesUseCase(),
            queryAppliedWeeklyPrizes = useCaseFactory.createQueryAppliedWeeklyPrizesUseCase(),
            queryAppliedDailyPrizes = useCaseFactory.createQueryAppliedDailyPrizesUseCase(),
            refreshAppliedWeeklyPrizes = useCaseFactory.createRefreshAppliedWeeklyPrizesUseCase(),
            refreshAppliedDailyPrizes = useCaseFactory.createRefreshAppliedDailyPrizesUseCase(),
            getUncheckRewardCountUseCase = useCaseFactory.createGetUncheckRewardCountUseCase(),
        )
    }

    fun createSkyFlagOfferWallReactor(): SkyFlagOfferWallReactor {
        return SkyFlagOfferWallReactor(
            mainDispatcher = mainDispatcher,
            getSkyFlagOfferWall = useCaseFactory.createGetSkyFlagOfferWallUseCase(),
            agreePersonalInformation = useCaseFactory.createAgreeToProvidePersonalInformationForSkyFlagUseCase(),
            extractSkyFlagOfferWallPromotionUrl = useCaseFactory.createExtractSkyFlagOfferWallPromotionUrl(),
            isOfferWallEnabled = releaseFlag.isOfferwallEnabled,
        )
    }

    fun createWelcomeChallengeReactor(): WelcomeChallengeReactor {
        return WelcomeChallengeReactor(
            mainDispatcher = mainDispatcher,
            getWelcomeChallenge = useCaseFactory.createGetWelcomeChallengeUseCase(),
            applyCoinPrize = useCaseFactory.createApplyCoinPrizeUseCase(),
            getKyashPoint = useCaseFactory.createGetPointBalanceUseCase(),
            sendShowRewardTopOnboardingEvent = useCaseFactory
                .createSendShowRewardTopOnboardingEventUseCase(),
        )
    }

    fun createUncheckedRewardApplicationResultReactor(
        uncheckedAppliedPrizeSummary: UncheckedPrizeSummary,
    ): UncheckedRewardApplicationResultReactor {
        return UncheckedRewardApplicationResultReactor(
            mainDispatcher = mainDispatcher,
            getPrize = useCaseFactory.createGetCoinPrizeUseCase(),
            uncheckedAppliedPrizeSummary = uncheckedAppliedPrizeSummary,
        )
    }

    fun createBudgetSettingReactor(): BudgetSettingReactor {
        return BudgetSettingReactor(
            mainDispatcher = mainDispatcher,
            getMonthlyBudget = useCaseFactory.createGetMonthlyBudgetUseCase(),
            getBudgetSettingFormAsFlow = useCaseFactory.createGetBudgetSettingFormAsFlowUseCase(),
            saveBudgetSettingForm = useCaseFactory.createSaveBudgetSettingFormUseCase(),
            deleteBudgetSetting = useCaseFactory.createDeleteBudgetSettingUseCase(),
            clearBudgetSettingForm = useCaseFactory.createClearBudgetSettingFormUseCase(),
            getMonthlySpendingSummaryByCategory = useCaseFactory.createGetMonthlySpendingSummaryByCategoryUseCase(),
        )
    }

    fun createCategoryBudgetSettingReactor(): CategoryBudgetSettingReactor {
        return CategoryBudgetSettingReactor(
            mainDispatcher = mainDispatcher,
            getBudgetSettingFormAsFlow = useCaseFactory.createGetBudgetSettingFormAsFlowUseCase(),
            saveBudgetSettingForm = useCaseFactory.createSaveBudgetSettingFormUseCase(),
            setBudgetSetting = useCaseFactory.createSetBudgetSettingUseCase(),
        )
    }

    fun createSpendingAggregationStartDateReactor(): SpendingAggregationStartDateReactor {
        return SpendingAggregationStartDateReactor(
            mainDispatcher = mainDispatcher,
            getSpendingAggregationStartDate = useCaseFactory.createGetSpendingAggregationStartDateUseCase(),
            setSpendingAggregationStartDate = useCaseFactory.createSetSpendingAggregationStartDateUseCase(),
        )
    }

    /**
     * [page] : 現在日時の集計月から何ヶ月前かの値。が`0`の場合は現在日時の集計月の集計期間とする。
     */
    fun createTimelineCategorizationReactor(
        category: SpendingCategory,
        page: Int,
    ): TimelineCategorizationReactor {
        return TimelineCategorizationReactor(
            mainDispatcher = mainDispatcher,
            getMonthlyTimeline = useCaseFactory.createGetMonthlyTimelineUseCase(),
            category = category,
            page = page,
        )
    }

    fun createAccountLinkReactor(): AccountLinkReactor {
        return AccountLinkReactor(
            mainDispatcher = mainDispatcher,
            getMeAccounts = useCaseFactory.createGetMeAccountsUseCase(),
            getAccountLinkNonce = useCaseFactory.createGetAccountLinkNonceUseCase(),
            linkFacebookAccount = useCaseFactory.createLinkFacebookAccountUseCase(),
            linkAppleAccount = useCaseFactory.createLinkAppleAccountUseCase(),
            linkGoogleAccount = useCaseFactory.createLinkGoogleAccountUseCase(),
            unlinkFacebookAccount = useCaseFactory.createUnlinkFacebookAccountUseCase(),
            unlinkAppleAccount = useCaseFactory.createUnlinkAppleAccountUseCase(),
            unlinkGoogleAccount = useCaseFactory.createUnlinkGoogleAccountUseCase(),
            unlinkMoneyforwardAccount = useCaseFactory.createUnlinkMoneyforwardAccountUseCase(),
            unlinkZaimAccount = useCaseFactory.createUnlinkZaimAccountUseCase(),
            unlinkMoneytreeAccount = useCaseFactory.createUnlinkMoneytreeAccountUseCase(),
        )
    }

    fun createAccountSettingReactor(): AccountSettingReactor {
        return AccountSettingReactor(
            mainDispatcher = mainDispatcher,
            getMe = useCaseFactory.createGetMeUseCase(),
            getIsPublicProfile = useCaseFactory.createGetIsPublicProfileUseCase(),
            updatePrivacySetting = useCaseFactory.createUpdatePrivacySettingUseCase(),
            uploadUserIcon = useCaseFactory.createUploadUserIconUseCase(),
        )
    }

    fun createAccountCancellationReactor(): AccountCancellationReactor {
        return AccountCancellationReactor(
            mainDispatcher = mainDispatcher,
            getMe = useCaseFactory.createGetMeUseCase(),
            getWalletBalance = useCaseFactory.createGetWalletBalanceUseCase(),
            getPointBalance = useCaseFactory.createGetPointBalanceUseCase(),
            getBankWithdrawalFee = useCaseFactory.createGetBankWithdrawalFeeUseCase(),
        )
    }

    fun createEmailEditReactor(): EmailEditReactor {
        return EmailEditReactor(
            mainDispatcher = mainDispatcher,
            getMe = useCaseFactory.createGetMeUseCase(),
            updateMe = useCaseFactory.createUpdateMeUseCase(),
        )
    }

    fun createFullNameEditReactor(): FullNameEditReactor {
        return FullNameEditReactor(
            mainDispatcher = mainDispatcher,
            getMe = useCaseFactory.createGetMeUseCase(),
            updateFullName = useCaseFactory.createUpdateFullNameUseCase(),
        )
    }

    fun createKyashIdEditReactor(): KyashIdEditReactor {
        return KyashIdEditReactor(
            mainDispatcher = mainDispatcher,
            getMe = useCaseFactory.createGetMeUseCase(),
            updateMe = useCaseFactory.createUpdateMeUseCase(),
        )
    }

    fun createNotificationSettingReactor(): NotificationSettingReactor {
        return NotificationSettingReactor(
            mainDispatcher = mainDispatcher,
            getUserSettingsUseCase = useCaseFactory.createGetUserSettingsUseCase(),
            updateUserSettingsUseCase = useCaseFactory.createUpdateUserSettingsUseCase(),
        )
    }

    fun createUserNameEditReactor(): UserNameEditReactor {
        return UserNameEditReactor(
            mainDispatcher = mainDispatcher,
            getMe = useCaseFactory.createGetMeUseCase(),
            updateMe = useCaseFactory.createUpdateMeUseCase(),
        )
    }

    fun createBnplKyashMoneyRepaymentConfirmationReactor():
            BnplKyashMoneyRepaymentConfirmationReactor {
        return BnplKyashMoneyRepaymentConfirmationReactor(
            mainDispatcher = mainDispatcher,
            getBnplRepaymentTotalAmount = useCaseFactory.createGetBnplRepaymentTotalAmountUseCase(),
            getWalletBalance = useCaseFactory.createGetWalletBalanceUseCase(),
            requestKyashMoneyRepay = useCaseFactory.createRequestKyashMoneyRepayUseCase(),
        )
    }

    fun createCardActivationConfirmationReactor(
        currentCardPan: String,
        currentCardExpiryMonth: String,
        currentCardExpiryYear: String,
        currentCardCvv2: String,
        activationCardPan: String,
        activationCardExpiryMonth: String?,
        activationCardExpiryYear: String?,
        activationCardCvv2: String?,
        isExpirationDateRenewal: Boolean,
    ): CardActivationConfirmationReactor {
        return CardActivationConfirmationReactor(
            mainDispatcher = mainDispatcher,
            currentCardPan = currentCardPan,
            currentCardExpiryMonth = currentCardExpiryMonth,
            currentCardExpiryYear = currentCardExpiryYear,
            currentCardCvv2 = currentCardCvv2,
            activationCardPan = activationCardPan,
            activationCardExpiryMonth = activationCardExpiryMonth,
            activationCardExpiryYear = activationCardExpiryYear,
            activationCardCvv2 = activationCardCvv2,
            isExpirationDateRenewal = isExpirationDateRenewal,
            activateKyashCard = useCaseFactory.createActivateKyashCardUseCase(),
            expirationDateRenewalActivateKyashCard =
            useCaseFactory.createExpirationDateRenewalActivateKyashCardUseCase(),
        )
    }

    fun createLiteCardExpirationDateRenewalActivationReactor():
            LiteCardExpirationDateRenewalActivationReactor {
        return LiteCardExpirationDateRenewalActivationReactor(
            mainDispatcher = mainDispatcher,
            getKyashCard = useCaseFactory.createGetKyashCardUseCase(),
            activateKyashCard = useCaseFactory.createActivateKyashCardUseCase(),
        )
    }

    fun createLiteExpirationDateRenewalAddressConfirmationReactor():
            LiteExpirationDateRenewalAddressConfirmationReactor {
        return LiteExpirationDateRenewalAddressConfirmationReactor(
            mainDispatcher = mainDispatcher,
            getLiteIssuanceFeeInfo =
            useCaseFactory.createGetLiteIssuanceFeeInfoUseCase(),
            getAndCreateApplication =
            useCaseFactory.createGetAndCreateLiteApplicationExpirationDateRenewalInfoUseCase(),
            applyLite = useCaseFactory.createApplyLiteUseCase(),
        )
    }

    fun createLiteExpirationDateRenewalAddressRegistrationReactor():
            LiteExpirationDateRenewalAddressRegistrationReactor {
        return LiteExpirationDateRenewalAddressRegistrationReactor(
            mainDispatcher = mainDispatcher,
            getLiteApplicationExpirationDateRenewalInfo =
            useCaseFactory.createGetLiteApplicationExpirationDateRenewalInfoUseCase(),
            updateLiteApplicationExpirationDateRenewalInfo =
            useCaseFactory.createUpdateLiteApplicationExpirationDateRenewalInfoUseCase(),
            findAddress = useCaseFactory.createFindAddressUseCase(),
        )
    }

    fun createMauricioApplicationAddressConfirmationReactor():
            MauricioApplicationAddressConfirmationReactor {
        return MauricioApplicationAddressConfirmationReactor(
            mainDispatcher = mainDispatcher,
            getMauricioApplicationInfo = useCaseFactory.createGetMauricioApplicationInfoUseCase(),
            applyMauricioCard = useCaseFactory.createApplyMauricioCardUseCase(),
        )
    }

    fun createMauricioApplicationReactor():
            MauricioApplicationReactor {
        return MauricioApplicationReactor(
            mainDispatcher = mainDispatcher,
            getFundSources = useCaseFactory.createGetFundSourcesUseCase(),
            getMauricioApplicationInfo = useCaseFactory.createGetMauricioApplicationInfoUseCase(),
        )
    }

    fun createMauricioCardActivatedReactor(
        mauricioCardUuid: String,
    ): MauricioCardActivatedReactor {
        return MauricioCardActivatedReactor(
            mauricioCardUuid = mauricioCardUuid,
            mainDispatcher = mainDispatcher,
        )
    }

    fun createMauricioCardActivationConfirmationReactor(
        mauricioCardPan: String,
        currentCardPan: String,
        currentCardExpiryMonth: String,
        currentCardExpiryYear: String,
        currentCardCvv2: String,
    ):
            MauricioCardActivationConfirmationReactor {
        return MauricioCardActivationConfirmationReactor(
            mainDispatcher = mainDispatcher,
            mauricioCardPan = mauricioCardPan,
            currentCardPan = currentCardPan,
            currentCardExpiryMonth = currentCardExpiryMonth,
            currentCardExpiryYear = currentCardExpiryYear,
            currentCardCvv2 = currentCardCvv2,
            activateKyashCard = useCaseFactory.createActivateKyashCardUseCase(),
        )
    }

    fun createMauricioCardPanActivationReactor(): MauricioCardPanActivationReactor {
        return MauricioCardPanActivationReactor(
            mainDispatcher = mainDispatcher,
            getKyashCard = useCaseFactory.createGetKyashCardUseCase(),
            activateKyashCard = useCaseFactory.createActivateKyashCardUseCase(),
        )
    }

    fun createMauricioIssueStatusReactor(): MauricioIssueStatusReactor {
        return MauricioIssueStatusReactor(
            mainDispatcher = mainDispatcher,
            getMauricioApplicationStatus =
            useCaseFactory.createGetMauricioApplicationStatusUseCase(),
        )
    }

    fun createMauricioShippingAddressConfirmationReactor():
            MauricioShippingAddressConfirmationReactor {
        return MauricioShippingAddressConfirmationReactor(
            mainDispatcher = mainDispatcher,
            getMauricioApplicationInfo = useCaseFactory.createGetMauricioApplicationInfoUseCase(),
        )
    }

    fun createMauricioShippingAddressRegistrationReactor():
            MauricioShippingAddressRegistrationReactor {
        return MauricioShippingAddressRegistrationReactor(
            mainDispatcher = mainDispatcher,
            getMauricioApplicationInfo = useCaseFactory.createGetMauricioApplicationInfoUseCase(),
            setMauricioApplicationInfo = useCaseFactory.createSetMauricioApplicationInfoUseCase(),
            findAddress = useCaseFactory.createFindAddressUseCase(),
        )
    }

    fun createKyashCardVirtualIssueReactor(
        isSignup: Boolean,
    ): KyashCardVirtualIssueReactor {
        return KyashCardVirtualIssueReactor(
            mainDispatcher = mainDispatcher,
            isSignup = isSignup,
            issueKyashCardVirtual = useCaseFactory.createIssueKyashCardVirtualUseCase(),
            setShouldShowDepositBalloon = useCaseFactory.createSetShouldShowDepositBalloonUseCase(),
        )
    }

    fun createBankRegistrationReactor(bankCode: String): BankRegistrationReactor {
        return BankRegistrationReactor(
            mainDispatcher = mainDispatcher,
            getMe = useCaseFactory.createGetMeUseCase(),
            getKycInfo = useCaseFactory.createGetKycInfoUseCase(),
            findBank = useCaseFactory.createFindBankUseCase(),
            getBirthDateRange = useCaseFactory.createGetBirthDateRangeUseCase(),
            applyForBankRegistration = useCaseFactory.createApplyForBankRegistrationUseCase(),
            applyForMoneyTap = useCaseFactory.createApplyForMoneyTapUseCase(),
            applyForJnb = useCaseFactory.createApplyForJnbUseCase(),
            applyForRakuten = useCaseFactory.createApplyForRakutenUseCase(),
            bankCode = bankCode,
        )
    }

    fun createLawsonBankAtmDepositCompletedReactor(
        availableAmount: Long,
        companyCode: String,
    ): LawsonBankAtmDepositCompletedReactor {
        return LawsonBankAtmDepositCompletedReactor(
            mainDispatcher = mainDispatcher,
            availableAmount = availableAmount,
            companyCode = companyCode,
        )
    }

    fun createLawsonBankAtmDepositInstructionReactor(): LawsonBankAtmDepositInstructionReactor {
        return LawsonBankAtmDepositInstructionReactor(
            mainDispatcher = mainDispatcher,
            getLawsonBankAvailableAmountUseCase =
            useCaseFactory.createGetLawsonBankAvailableAmountUseCase(),
            getKycStatusUseCase = useCaseFactory.createGetKycStatusUseCase(),
        )
    }

    fun createLawsonBankAtmDepositQrScanReactor(
        availableAmount: Long,
    ): LawsonBankAtmDepositQrScanReactor {
        return LawsonBankAtmDepositQrScanReactor(
            mainDispatcher = mainDispatcher,
            registerLawsonBankAtmDeposit =
            useCaseFactory.createRegisterLawsonBankAtmDepositUseCase(),
            availableAmount = availableAmount,
        )
    }

    fun createAboutBankWithdrawalFeeReactor(): AboutBankWithdrawalFeeReactor {
        return AboutBankWithdrawalFeeReactor(
            mainDispatcher = mainDispatcher,
            getBankWithdrawalFee = useCaseFactory.createGetBankWithdrawalFeeUseCase(),
        )
    }

    fun createBankWithdrawalConfirmationReactor(
        bankUuid: String,
        bankImageUrl: String,
        bankName: String,
        branchName: String,
        firstName: String,
        lastName: String,
        last3: String,
        withdrawalAmount: Long,
    ): BankWithdrawalConfirmationReactor {
        return BankWithdrawalConfirmationReactor(
            mainDispatcher = mainDispatcher,
            bankUuid = bankUuid,
            bankImageUrl = bankImageUrl,
            bankName = bankName,
            branchName = branchName,
            firstName = firstName,
            lastName = lastName,
            last3 = last3,
            withdrawalAmount = withdrawalAmount,
            getPayrollAccount = useCaseFactory.createGetPayrollAccountUseCase(),
            getWalletBalance = useCaseFactory.createGetWalletBalanceUseCase(),
            getBankWithdrawalFee = useCaseFactory.createGetBankWithdrawalFeeUseCase(),
            requestBankWithdrawal = useCaseFactory.createRequestBankWithdrawalUseCase(),
        )
    }

    fun createBankWithdrawalReactor(
        bankUuid: String,
        bankImageUrl: String,
        bankName: String,
        branchName: String,
        firstName: String,
        lastName: String,
        last3: String,
    ): BankWithdrawalReactor {
        return BankWithdrawalReactor(
            mainDispatcher = mainDispatcher,
            bankUuid = bankUuid,
            bankImageUrl = bankImageUrl,
            bankName = bankName,
            branchName = branchName,
            firstName = firstName,
            lastName = lastName,
            last3 = last3,
            getWalletBalance = useCaseFactory.createGetWalletBalanceUseCase(),
            getBankWithdrawalFee = useCaseFactory.createGetBankWithdrawalFeeUseCase(),
            getPayrollAccount = useCaseFactory.createGetPayrollAccountUseCase(),
        )
    }

    fun createLawsonBankAtmWithdrawalCompletedReactor(
        oneTimePassword: String,
        companyCode: String,
    ): LawsonBankAtmWithdrawalCompletedReactor {
        return LawsonBankAtmWithdrawalCompletedReactor(
            mainDispatcher = mainDispatcher,
            oneTimePassword = oneTimePassword,
            companyCode = companyCode,
        )
    }

    fun createLawsonBankAtmWithdrawalInstructionReactor():
            LawsonBankAtmWithdrawalInstructionReactor {
        return LawsonBankAtmWithdrawalInstructionReactor(
            mainDispatcher = mainDispatcher,
        )
    }

    fun createLawsonBankAtmWithdrawalQrScanReactor(): LawsonBankAtmWithdrawalQrScanReactor {
        return LawsonBankAtmWithdrawalQrScanReactor(
            mainDispatcher = mainDispatcher,
            registerLawsonBankAtmWithdrawal =
            useCaseFactory.createRegisterLawsonBankAtmWithdrawalUseCase(),
        )
    }

    fun createSevenBankAtmWithdrawalInstructionReactor():
            SevenBankAtmWithdrawalInstructionReactor {
        return SevenBankAtmWithdrawalInstructionReactor(
            mainDispatcher = mainDispatcher,
            getPayrollAccount = useCaseFactory.createGetPayrollAccountUseCase(),
            getBankWithdrawalFee = useCaseFactory.createGetBankWithdrawalFeeUseCase(),
        )
    }

    fun createEKycSelectReactor(
        userEKycReason: UserEKycReason,
    ): EKycSelectReactor {
        return EKycSelectReactor(
            mainCoroutineDispatcher = mainDispatcher,
            userEKycReason = userEKycReason,
            preApplyForEKycOcr = useCaseFactory.createPreApplyForEKycOcrUseCase(),
        )
    }

    fun createEKycNfcCardReadingReactor(): EKycNfcCardReadingReactor {
        return EKycNfcCardReadingReactor(
            mainDispatcher = mainDispatcher,
            getEKycNfcSignTargetData = useCaseFactory.createGetEKycNfcSignTargetDataUseCase(),
            structuralizeEKycNfcFamilyName =
            useCaseFactory.createStructuralizeEKycNfcFamilyNameUseCase(),
        )
    }

    fun createEKycNfcCompletionReactor(
        result: EKycNfcResult,
    ): EKycNfcCompletionReactor {
        return EKycNfcCompletionReactor(
            mainCoroutineDispatcher = mainDispatcher,
            result = result,
        )
    }

    fun createEKycNfcPasswordIdentificationReactor(
        userEKycReason: UserEKycReason,
    ): EKycNfcPasswordIdentificationReactor {
        return EKycNfcPasswordIdentificationReactor(
            mainDispatcher = mainDispatcher,
            userEKycReason = userEKycReason,
        )
    }

    fun createNewsReactor(): NewsReactor {
        return NewsReactor(
            mainDispatcher = mainDispatcher,
            getNews = useCaseFactory.createGetNewsUseCase(),
            getKyashInfo = useCaseFactory.createGetKyashInfoUseCase(),
            readNews = useCaseFactory.createReadNewsUseCase(),
            getTimelineTransfer = useCaseFactory.createGetTimelineDetailUseCase(),
        )
    }

    fun createPhoneNumberInputReactor(
        signUpInfo: SignUpInfo,
    ): PhoneNumberInputReactor {
        return PhoneNumberInputReactor(
            mainDispatcher = mainDispatcher,
            sendVerificationCodeViaSmsUseCase =
            useCaseFactory.createSendVerificationCodeViaSmsUseCase(),
            signUpInfo = signUpInfo,
        )
    }

    fun createOnboardingCardSelectionReactor(
        onboardingConfig: OnboardingConfig,
    ): OnboardingCardSelectionReactor {
        return OnboardingCardSelectionReactor(
            mainDispatcher = mainDispatcher,
            getLiteIssuanceFeeInfo = useCaseFactory.createGetLiteIssuanceFeeInfoUseCase(),
            needsShowLiteCardAtOnboardingCardSelection =
            onboardingConfig.needsShowLiteCardAtOnboardingCardSelection,
        )
    }

    fun createPayrollAccountRegistrationReactor(): PayrollAccountRegistrationReactor {
        return PayrollAccountRegistrationReactor(
            mainDispatcher = mainDispatcher,
            getKycStatus = useCaseFactory.createGetKycStatusUseCase(),
            getEvacuationAccount = useCaseFactory.createGetEvacuationAccountUseCase(),
            getWithdrawalBankAccount = useCaseFactory.createGetWithdrawalBankAccountUseCase(),
            requestPayrollAccountRegistration =
            useCaseFactory.createRequestPayrollAccountRegistrationUseCase(),
            getPayrollAccount = useCaseFactory.createGetPayrollAccountUseCase(),
        )
    }

    fun createEvacuationAccountSelectionReactor(): EvacuationAccountSelectionReactor {
        return EvacuationAccountSelectionReactor(
            mainDispatcher = mainDispatcher,
            getWithdrawalBankAccount = useCaseFactory.createGetWithdrawalBankAccountUseCase(),
            getEvacuationAccount = useCaseFactory.createGetEvacuationAccountUseCase(),
            getKycStatus = useCaseFactory.createGetKycStatusUseCase(),
            registerEvacuationAccount = useCaseFactory.createRegisterEvacuationAccountUseCase(),
            deleteWithdrawalBankAccount = useCaseFactory.createDeleteWithdrawalBankAccountUseCase(),
        )
    }

    fun createEvacuationAccountRegistrationReactor(
        accountTargetUUID: String,
        accountTargetType: EvacuationAccountTargetType,
    ): EvacuationAccountRegistrationReactor {
        return EvacuationAccountRegistrationReactor(
            mainDispatcher = mainDispatcher,
            accountTargetUUID = accountTargetUUID,
            accountTargetType = accountTargetType,
            registerEvacuationAccount = useCaseFactory.createRegisterEvacuationAccountUseCase(),
        )
    }

    fun createPayrollAccountGuideReactor(): PayrollAccountGuideReactor {
        return PayrollAccountGuideReactor(
            mainDispatcher = mainDispatcher,
        )
    }

    fun createPayrollAccountReactor(): PayrollAccountReactor {
        return PayrollAccountReactor(
            mainDispatcher = mainDispatcher,
            getEvacuationAccount = useCaseFactory.createGetEvacuationAccountUseCase(),
            getPayrollAccount = useCaseFactory.createGetPayrollAccountUseCase(),
        )
    }

    fun createCouponDetailReactor(
        id: String,
    ): CouponDetailReactor {
        return CouponDetailReactor(
            mainDispatcher = mainDispatcher,
            getCouponDetail = useCaseFactory.createGetCouponDetailUseCase(),
            getCouponPersonalInformationAgreement =
            useCaseFactory.createGetCouponPersonalInformationAgreementUseCase(),
            id = id,
        )
    }

    fun createCouponListReactor(): CouponListReactor {
        return CouponListReactor(
            mainDispatcher = mainDispatcher,
            getCoupons = useCaseFactory.createGetCouponsUseCase(),
        )
    }

    fun createCouponPersonalInformationAgreementReactor():
            CouponPersonalInformationAgreementReactor {
        return CouponPersonalInformationAgreementReactor(
            mainDispatcher = mainDispatcher,
            agreeCouponPersonalInformation =
            useCaseFactory.createAgreeCouponPersonalInformationUseCase(),
        )
    }

    fun createCouponTopReactor(): CouponTopReactor {
        return CouponTopReactor(
            mainDispatcher = mainDispatcher,
            getTopCoupons = useCaseFactory.createGetTopCouponsUseCase(),
        )
    }

    fun createFriendInvitationReactor(): FriendInvitationReactor {
        return FriendInvitationReactor(
            mainDispatcher = mainDispatcher,
            getFriendInvitationUrl = useCaseFactory.createGetFriendInvitationUrlUseCase(),
        )
    }

    fun createOtherOwnedBankTransferAccountOwnerNameInputReactor():
            OtherOwnedBankTransferAccountOwnerNameInputReactor {
        return OtherOwnedBankTransferAccountOwnerNameInputReactor(
            mainDispatcher = mainDispatcher,
            loadRequestDraft =
            useCaseFactory.createGetOtherOwnedBankTransferRequestDraftAsFlowUseCase(),
            saveRequestDraft =
            useCaseFactory.createSaveOtherOwnedBankTransferRequestDraftUseCase(),
        )
    }

    fun createOtherOwnedBankTransferAccountTypeAndNumberInputReactor():
            OtherOwnedBankTransferAccountTypeAndNumberInputReactor {
        return OtherOwnedBankTransferAccountTypeAndNumberInputReactor(
            mainDispatcher = mainDispatcher,
            loadRequestDraft =
            useCaseFactory.createGetOtherOwnedBankTransferRequestDraftAsFlowUseCase(),
            saveRequestDraft =
            useCaseFactory.createSaveOtherOwnedBankTransferRequestDraftUseCase(),
        )
    }

    fun createOtherOwnedBankTransferAmountInputReactor(): OtherOwnedBankTransferAmountInputReactor {
        return OtherOwnedBankTransferAmountInputReactor(
            mainDispatcher = mainDispatcher,
            getWalletBalance = useCaseFactory.createGetWalletBalanceUseCase(),
            getBankWithdrawalFee = useCaseFactory.createGetBankWithdrawalFeeUseCase(),
            loadRequestDraft =
            useCaseFactory.createGetOtherOwnedBankTransferRequestDraftAsFlowUseCase(),
            saveRequestDraft =
            useCaseFactory.createSaveOtherOwnedBankTransferRequestDraftUseCase(),
        )
    }

    fun createOtherOwnedBankTransferBranchSelectionReactor():
            OtherOwnedBankTransferBranchSelectionReactor {
        return OtherOwnedBankTransferBranchSelectionReactor(
            mainDispatcher = mainDispatcher,
            getBankBranches = useCaseFactory.createGetBankBranchesUseCase(),
            loadRequestDraft =
            useCaseFactory.createGetOtherOwnedBankTransferRequestDraftAsFlowUseCase(),
            saveRequestDraft =
            useCaseFactory.createSaveOtherOwnedBankTransferRequestDraftUseCase(),
        )
    }

    fun createOtherOwnedBankTransferConfirmationReactor():
            OtherOwnedBankTransferConfirmationReactor {
        return OtherOwnedBankTransferConfirmationReactor(
            mainDispatcher = mainDispatcher,
            getWalletBalance = useCaseFactory.createGetWalletBalanceUseCase(),
            getBankWithdrawalFee = useCaseFactory.createGetBankWithdrawalFeeUseCase(),
            loadRequestDraft =
            useCaseFactory.createGetOtherOwnedBankTransferRequestDraftAsFlowUseCase(),
            registerOtherOwnedBank = useCaseFactory.createRegisterOtherOwnedBankAccountUseCase(),
            requestOtherOwnedBankTransfer =
            useCaseFactory.createRequestOtherOwnedBankTransferUseCase(),
        )
    }

    fun createOtherOwnedBankTransferRequesterDisplayNameInputReactor():
            OtherOwnedBankTransferRequesterDisplayNameInputReactor {
        return OtherOwnedBankTransferRequesterDisplayNameInputReactor(
            mainDispatcher = mainDispatcher,
            loadRequestDraft =
            useCaseFactory.createGetOtherOwnedBankTransferRequestDraftAsFlowUseCase(),
            saveRequestDraft = useCaseFactory.createSaveOtherOwnedBankTransferRequestDraftUseCase(),
            getKycInfo = useCaseFactory.createGetKycInfoUseCase(),
        )
    }

    fun createWalletPageReactor(
        walletConfig: WalletConfig,
        params: WalletPageReactor.Parameter,
    ): WalletPageReactor {
        return WalletPageReactor(
            mainDispatcher = mainDispatcher,
            getWalletTimeline = useCaseFactory.createGetWalletTimelineUseCase(),
            getPrimaryWalletSummary = useCaseFactory.createGetPrimaryWalletSummaryUseCase(),
            getShareWalletSummary = useCaseFactory.createGetShareWalletSummaryUseCase(),
            getMembershipIncentives = useCaseFactory.createGetMembershipIncentivesUseCase(),
            getShouldShowDepositBalloonAsFlow =
            useCaseFactory.createGetShouldShowDepositBalloonAsFlowUseCase(),
            setShouldShowDepositBalloon = useCaseFactory.createSetShouldShowDepositBalloonUseCase(),
            getCreditCardRegistrationAcceptance =
            useCaseFactory.createGetCreditCardRegistrationAcceptanceUseCase(
                remoteConfig = walletConfig,
            ),
            getKycStatus = useCaseFactory.createGetKycStatusUseCase(),
            getNotificationBanners = useCaseFactory.createGetNotificationBannersUseCase(),
            closeNotificationBanner = useCaseFactory.createCloseNotificationBannerUseCase(),
            params = params,
        )
    }

    fun createCustomCategoryReactorForCreate(): CustomCategoryReactor {
        return CustomCategoryReactor(
            mainDispatcher = mainDispatcher,
            initialArgs = CustomCategoryReactor.InitialArgs.ForCreate,
            getCustomCategoryIcons = useCaseFactory.createGetCustomCategoryIconsUseCase(),
            setCustomCategoryIconDraft = useCaseFactory.createSetCustomCategoryIconDraftUseCase(),
            watchCustomCategoryIconDraft = useCaseFactory.createWatchCustomCategoryIconDraftUseCase(),
            createCustomCategory = useCaseFactory.createCreateCustomCategoryUseCase(),
            updateCustomCategory = useCaseFactory.createUpdateCustomCategoryUseCase(),
        )
    }

    fun createCustomCategoryReactorForUpdate(
        categoryId: String,
        categoryName: String,
        categoryImageId: String,
        categoryImageUrl: String,
    ): CustomCategoryReactor {
        return CustomCategoryReactor(
            mainDispatcher = mainDispatcher,
            initialArgs = CustomCategoryReactor.InitialArgs.ForUpdate(
                categoryId = categoryId,
                categoryName = categoryName,
                categoryImageId = categoryImageId,
                categoryImageUrl = categoryImageUrl,
            ),
            getCustomCategoryIcons = useCaseFactory.createGetCustomCategoryIconsUseCase(),
            setCustomCategoryIconDraft = useCaseFactory.createSetCustomCategoryIconDraftUseCase(),
            watchCustomCategoryIconDraft = useCaseFactory.createWatchCustomCategoryIconDraftUseCase(),
            createCustomCategory = useCaseFactory.createCreateCustomCategoryUseCase(),
            updateCustomCategory = useCaseFactory.createUpdateCustomCategoryUseCase(),
        )
    }

    fun createCustomCategoryIconSelectionReactor(): CustomCategoryIconSelectionReactor {
        return CustomCategoryIconSelectionReactor(
            mainDispatcher = mainDispatcher,
            watchCustomCategoryIcons = useCaseFactory.createWatchCustomCategoryIconsUseCase(),
            watchCustomCategoryIconDraft = useCaseFactory.createWatchCustomCategoryIconDraftUseCase(),
            setCustomCategoryIconDraft = useCaseFactory.createSetCustomCategoryIconDraftUseCase(),
        )
    }

    fun createEmailSignUpReactor(): EmailSignUpReactor {
        return EmailSignUpReactor(
            mainDispatcher = mainDispatcher,
            checkEmailAddress = useCaseFactory.createCheckEmailAddressUseCase(),
        )
    }

    fun createPersonalInformationInputReactor(
        callParam: PersonalInformationCallParam,
    ): PersonalInformationInputReactor {
        return PersonalInformationInputReactor(
            mainDispatcher = mainDispatcher,
            callParam = callParam,
            getMe = useCaseFactory.createGetMeUseCase(),
            updateInformationRequiredForTransactions =
            useCaseFactory.createUpdateInformationRequiredForTransactions(),
            checkUserName = useCaseFactory.createCheckUserNameUseCase(),
            updateUserName = useCaseFactory.createUpdateUserNameUseCase(),
        )
    }

    fun createUserNameInputReactor(
        callParam: UserNameInputCallParam,
    ): UserNameInputReactor {
        return UserNameInputReactor(
            mainDispatcher = mainDispatcher,
            callParam = callParam,
            checkUserName = useCaseFactory.createCheckUserNameUseCase(),
            updateUserName = useCaseFactory.createUpdateUserNameUseCase(),
        )
    }

    fun createPhoneNumberVerificationReactor(
        signUpSmsErrorHandlingEnabled: Boolean,
        accessTokenRepository: AccessTokenRepository,
        signUpInfo: SignUpInfo,
        validPhoneNumber: String,
    ): PhoneNumberVerificationReactor {
        return PhoneNumberVerificationReactor(
            mainDispatcher = mainDispatcher,
            signUpSmsErrorHandlingEnabled = signUpSmsErrorHandlingEnabled,
            sendVerificationCodeViaSms = useCaseFactory.createSendVerificationCodeViaSmsUseCase(),
            sendVerificationCodeViaVoiceCall = useCaseFactory.createSendVerificationCodeViaVoiceCallUseCase(),
            verifyPhoneNumber = useCaseFactory.createVerifyPhoneNumberUseCase(),
            signUp = useCaseFactory.createSignUpUseCase(
                accessTokenRepository = accessTokenRepository,
            ),
            signUpInfo = signUpInfo,
            getMe = useCaseFactory.createGetMeUseCase(),
            sendHwid = useCaseFactory.createSendHwidUseCase(),
            validPhoneNumber = validPhoneNumber,
        )
    }

    fun createBnplPendingRepaymentListReactor(): BnplPendingRepaymentListReactor {
        return BnplPendingRepaymentListReactor(
            mainDispatcher = mainDispatcher,
            getBnplPendingRepaymentList = useCaseFactory.createGetBnplPendingRepaymentListUseCase(),
            getBnplRepaymentHistory = useCaseFactory.createGetBnplRepaymentHistoryUseCase(),
        )
    }

    fun createBnplRepaymentHistoryReactor(): BnplRepaymentHistoryReactor {
        return BnplRepaymentHistoryReactor(
            mainDispatcher = mainDispatcher,
            getBnplRepaymentHistory = useCaseFactory.createGetBnplRepaymentHistoryUseCase(),
        )
    }

    fun createBnplRepaymentConfirmationReactor(
        paymentDueDate: DateTimeTz,
    ): BnplRepaymentConfirmationReactor {
        return BnplRepaymentConfirmationReactor(
            mainDispatcher = mainDispatcher,
            paymentDueDate = paymentDueDate,
            getBnplDebtDetail = useCaseFactory.createGetBnplDebtDetailUseCase(),
        )
    }

    fun createBnplSelectRepaymentMethodReactor(): BnplSelectRepaymentMethodReactor {
        return BnplSelectRepaymentMethodReactor(
            mainDispatcher = mainDispatcher,
            getKycStatus = useCaseFactory.createGetKycStatusUseCase(),
            getBnplRepaymentUsage = useCaseFactory.createGetBnplRepaymentUsageUseCase(),
        )
    }

    fun createBnplAutoRepaymentSettingReactor(): BnplAutoRepaymentSettingReactor {
        return BnplAutoRepaymentSettingReactor(
            mainDispatcher = mainDispatcher,
            getBnplAutoRepaymentSetting = useCaseFactory.createGetBnplAutoRepaymentSettingUseCase(),
            setBnplAutoRepayment = useCaseFactory.createSetBnplAutoRepaymentUseCase(),
            checkHasOverDueDebt = useCaseFactory.createCheckHasOverDueDebtUseCase(),
        )
    }

    fun createBnplAutoRepaymentSettingResultReactorForSuccess(
        nextRepaymentDate: DateTimeTz?,
        nextRepaymentOverdue: Boolean,
    ): BnplAutoRepaymentSettingResultReactor {
        return BnplAutoRepaymentSettingResultReactor(
            mainDispatcher = mainDispatcher,
            initialArgs = BnplAutoRepaymentSettingResultReactor.InitialArgs.Success(
                nextRepaymentDate = nextRepaymentDate,
                nextRepaymentOverdue = nextRepaymentOverdue,
            ),
            checkHasOverDueDebt = useCaseFactory.createCheckHasOverDueDebtUseCase(),
        )
    }

    fun createBnplAutoRepaymentSettingResultReactorForFailure():
            BnplAutoRepaymentSettingResultReactor {
        return BnplAutoRepaymentSettingResultReactor(
            mainDispatcher = mainDispatcher,
            initialArgs = BnplAutoRepaymentSettingResultReactor.InitialArgs.Failure,
            checkHasOverDueDebt = useCaseFactory.createCheckHasOverDueDebtUseCase(),
        )
    }

    fun createSendRequestReactor(): SendRequestReactor {
        return SendRequestReactor(
            mainDispatcher = mainDispatcher,
            getMe = useCaseFactory.createGetMeUseCase(),
            getContacts = useCaseFactory.createGetContactsUseCase(),
            getKycStatus = useCaseFactory.createGetKycStatusUseCase(),
            deleteContact = useCaseFactory.createDeleteContactUseCase(),
        )
    }

    fun createOtherDepositMethodReactor(
        filter: OtherDepositMethodFilter,
    ): OtherDepositMethodReactor {
        return OtherDepositMethodReactor(
            mainDispatcher = mainDispatcher,
            filter = filter,
            getMe = useCaseFactory.createGetMeUseCase(),
            getOtherDepositMethodsUseCase = useCaseFactory.createGetOtherDepositMethodsUseCase(),
            getVirtualBankAccountUseCase = useCaseFactory.createGetVirtualBankAccountUseCase(),
            getKycStatusUseCase = useCaseFactory.createGetKycStatusUseCase(),
        )
    }

    fun createCreditCardRegistrationDescriptionReactor(): CreditCardRegistrationDescriptionReactor {
        return CreditCardRegistrationDescriptionReactor(
            mainDispatcher = mainDispatcher,
            getMe = useCaseFactory.createGetMeUseCase(),
        )
    }

    fun createRedeemReactor(): RedeemReactor {
        return RedeemReactor(
            mainDispatcher = mainDispatcher,
            getMe = useCaseFactory.createGetMeUseCase(),
            postRedeem = useCaseFactory.createPostRedeemUseCase(),
        )
    }

    fun createCustomerRegistrationInformationReactor(): CustomerRegistrationInformationReactor {
        return CustomerRegistrationInformationReactor(
            mainDispatcher = mainDispatcher,
            getCustomer = useCaseFactory.createGetCustomerUseCase(),
            getCustomerUpdateExpiration = useCaseFactory.createGetCustomerUpdateExpirationUseCase(),
            getKycSummary = useCaseFactory.createGetKycSummaryUseCase(),
            postCustomerConfirmLatest = useCaseFactory.createPostCustomerConfirmLatestUseCase(),
            getCddScreenRestrictionStatus = useCaseFactory.createGetCddScreenRestrictionStatusUseCase(),
        )
    }

    fun createCustomerOccupationEditReactor(): CustomerOccupationEditReactor {
        return CustomerOccupationEditReactor(
            mainDispatcher = mainDispatcher,
            updateCustomerOccupation = useCaseFactory.createUpdateCustomerOccupationUseCase(),
        )
    }

    fun createCustomerCompanyNameEditReactor(): CustomerCompanyNameEditReactor {
        return CustomerCompanyNameEditReactor(
            mainDispatcher = mainDispatcher,
            updateCustomerCompanyName = useCaseFactory.createUpdateCustomerCompanyNameUseCase(),
        )
    }

    fun createCustomerPositionEditReactor(): CustomerPositionEditReactor {
        return CustomerPositionEditReactor(
            mainDispatcher = mainDispatcher,
            updateCustomerPosition = useCaseFactory.createUpdateCustomerPositionUseCase(),
        )
    }

    fun createCustomerIncomeEditReactor(): CustomerIncomeEditReactor {
        return CustomerIncomeEditReactor(
            mainDispatcher = mainDispatcher,
            updateCustomerIncome = useCaseFactory.createUpdateCustomerIncomeUseCase(),
        )
    }

    fun createCustomerSourceOfIncomeEditReactor(): CustomerSourceOfIncomeEditReactor {
        return CustomerSourceOfIncomeEditReactor(
            mainDispatcher = mainDispatcher,
            updateCustomerSourceOfIncome = useCaseFactory.createUpdateCustomerSourceOfIncomeUseCase(),
        )
    }

    fun createCustomerPurposeEditReactor(): CustomerPurposeEditReactor {
        return CustomerPurposeEditReactor(
            mainDispatcher = mainDispatcher,
            updateCustomerPurpose = useCaseFactory.createUpdateCustomerPurposeUseCase(),
        )
    }

    fun createEKycNfcIdentificationReactor(
        cardInfo: EKycNfcCardInfo,
        userEKycReason: UserEKycReason,
        signTargetDataResult: EKycNfcSignTargetDataResult,
    ): EKycNfcIdentificationReactor {
        return EKycNfcIdentificationReactor(
            mainDispatcher = mainDispatcher,
            getAttentionLevel = useCaseFactory.createGetAttentionLevelUseCase(),
            getEKycNfcApplicationProgress =
            useCaseFactory.createGetEKycNfcApplicationProgressUseCase(),
            startEKycNfcApplication = useCaseFactory.createStartEKycNfcApplicationUseCase(),
            cardInfo = cardInfo,
            userEKycReason = userEKycReason,
            signTargetDataResult = signTargetDataResult,
        )
    }

    fun createEKycOcrIdentificationReactor(
        userEKycReason: UserEKycReason,
    ): EKycOcrIdentificationReactor {
        return EKycOcrIdentificationReactor(
            mainDispatcher = mainDispatcher,
            getAttentionLevel = useCaseFactory.createGetAttentionLevelUseCase(),
            getEKycOcrPrefillInfo = useCaseFactory.createGetEKycOcrPrefillInfoUseCase(),
            applyForEKycOcr = useCaseFactory.createApplyForEKycOcrUseCase(),
            findAddress = useCaseFactory.createFindAddressUseCase(),
            userEKycReason = userEKycReason,
        )
    }

    fun createFirstDepositMethodReactor(): FirstDepositMethodReactor {
        return FirstDepositMethodReactor(
            mainDispatcher = mainDispatcher,
        )
    }

    fun createCampaignCodeInputReactor(): CampaignCodeInputReactor {
        return CampaignCodeInputReactor(
            mainDispatcher = mainDispatcher,
            checkAdvertisementCampaignCode =
            useCaseFactory.createCheckAdvertisementCampaignCodeUseCase(),
            useAdvertisementCampaignCode =
            useCaseFactory.createUseAdvertisementCampaignCodeUseCase(),
        )
    }

    fun createPasscodeGuideReactor(): PasscodeGuideReactor {
        return PasscodeGuideReactor(
            mainDispatcher = mainDispatcher,
            getPasscodeSetting = useCaseFactory.createGetPasscodeSettingUseCase(),
        )
    }

    fun createPasscodeAuthenticationReactor(
        isUpdate: Boolean,
    ): PasscodeAuthenticationReactor {
        return PasscodeAuthenticationReactor(
            mainDispatcher = mainDispatcher,
            isUpdate = isUpdate,
            getIdentityVerificationAuthentication = useCaseFactory
                .createGetIdentityVerificationAuthenticationUseCase(),
            getAccountLinkNonce = useCaseFactory.createGetAccountLinkNonceUseCase(),
            verifyIdentityByApple = useCaseFactory.createVerifyIdentityByAppleUseCase(),
            verifyIdentityByGoogle = useCaseFactory.createVerifyIdentityByGoogleUseCase(),
            verifyIdentityByEmail = useCaseFactory.createVerifyIdentityByEmailUseCase(),
            getMe = useCaseFactory.createGetMeUseCase(),
            requestPasswordReset = useCaseFactory.createRequestPasswordResetUseCase(),
        )
    }

    fun createPasscodeRegistrationReactor(
        isUpdate: Boolean,
    ): PasscodeRegistrationReactor {
        return PasscodeRegistrationReactor(
            mainDispatcher = mainDispatcher,
            postPasscode = useCaseFactory.createPostPasscodeUseCase(),
            updatePasscode = useCaseFactory.createUpdatePasscodeUseCase(),
            isUpdate = isUpdate,
        )
    }

    fun createPasscodeVerificationReactor(): PasscodeVerificationReactor {
        return PasscodeVerificationReactor(
            mainDispatcher = mainDispatcher,
            verifyPasscode = useCaseFactory.createVerifyPasscodeUseCase(),
        )
    }

    /**
     * @param skippable パスコードの更新をスキップできるかどうか
     */
    fun createPasscodeRenewalOnboardingReactor(
        skippable: Boolean,
    ): PasscodeRenewalOnboardingReactor {
        return PasscodeRenewalOnboardingReactor(
            mainDispatcher = mainDispatcher,
            skippable = skippable,
            getPasscodeSetting = useCaseFactory.createGetPasscodeSettingUseCase(),
        )
    }

    fun createBnplTermsReactor(): BnplTermsReactor {
        return BnplTermsReactor(
            mainDispatcher = mainDispatcher,
            agreeBnplRetryPersonalInformation =
            useCaseFactory.createAgreeBnplRetryPersonalInformationUseCase(),
            getBnplRetryPersonalInformationAgreement =
            useCaseFactory.createGetBnplRetryPersonalInformationAgreementUseCase(),
        )
    }

    fun createBnplApplicationConfirmReactor(
        callParam: BnplApplicationConfirmCallParam,
    ): BnplApplicationConfirmReactor {
        return BnplApplicationConfirmReactor(
            mainDispatcher = mainDispatcher,
            callParam = callParam,
            getBnplDue = useCaseFactory.createGetBnplDueUseCase(),
            getBnplAutoRepaymentSetting = useCaseFactory.createGetBnplAutoRepaymentSettingUseCase(),
            chargeByBnpl = useCaseFactory.createChargeByBnplUseCase(),
            getBnplConditions = useCaseFactory.createGetBnplConditionsUseCase(),
            setBnplAutoRepayment = useCaseFactory.createSetBnplAutoRepaymentUseCase(),
            getBnplRepaymentUsage = useCaseFactory.createGetBnplRepaymentUsageUseCase(),
        )
    }

    fun createBnplAutoRepaymentSuggestionModalReactor(): BnplAutoRepaymentSuggestionModalReactor {
        return BnplAutoRepaymentSuggestionModalReactor(
            mainDispatcher = mainDispatcher,
            getBnplRepaymentUsage = useCaseFactory.createGetBnplRepaymentUsageUseCase(),
        )
    }

    fun createBnplIntroductionReactor(): BnplIntroductionReactor {
        return BnplIntroductionReactor(
            mainDispatcher = mainDispatcher,
            getBnplRepaymentUsage = useCaseFactory.createGetBnplRepaymentUsageUseCase(),
            getBnplAmountRange = useCaseFactory.createGetBnplAmountRangeUseCase(),
        )
    }

    fun createResultReactor(
        callParam: ResultCallParam,
    ): ResultReactor {
        return ResultReactor(
            mainDispatcher = mainDispatcher,
            callParam = callParam,
        )
    }
}