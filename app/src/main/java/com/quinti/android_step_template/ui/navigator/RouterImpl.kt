package com.quinti.android_step_template.ui.navigator

import android.content.Intent
import android.os.Build
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsSession
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.navigation.ActivityNavigator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalError
import com.quinti.android_step_template.kmp.data.api.exception.NonFatalError
import com.quinti.android_step_template.ui.MainActivityIntentFactory
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.launch
import okhttp3.Route
import javax.inject.Inject

@ActivityScoped
class ActivityRouterImpl @Inject constructor(
    private val activity: AppCompatActivity,
    activityNavigator: ActivityNavigator,
    mainActivityIntentFactory: MainActivityIntentFactory,
    onboardingNavigator: OnboardingNavigator,
    onboardingConfig: OnboardingConfig,
    releaseFlag: ReleaseFlag,
) : RouterImpl(
    activity = activity,
    activityNavigator = activityNavigator,
    mainActivityIntentFactory = mainActivityIntentFactory,
    onboardingNavigator = onboardingNavigator,
    onboardingConfig = onboardingConfig,
    releaseFlag = releaseFlag,
) {
    override val fragmentManager: FragmentManager = activity.supportFragmentManager

    override fun startActivity(intent: Intent) {
        activity.startActivity(intent)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        activity.startActivityForResult(intent, requestCode)
    }
}

@FragmentScoped
class FragmentRouterImpl @Inject constructor(
    private val fragment: Fragment,
    activity: AppCompatActivity,
    activityNavigator: ActivityNavigator,
    mainActivityIntentFactory: MainActivityIntentFactory,
    onboardingNavigator: OnboardingNavigator,
    onboardingConfig: OnboardingConfig,
    releaseFlag: ReleaseFlag,
) : RouterImpl(
    activity = activity,
    activityNavigator = activityNavigator,
    mainActivityIntentFactory = mainActivityIntentFactory,
    onboardingNavigator = onboardingNavigator,
    onboardingConfig = onboardingConfig,
    releaseFlag = releaseFlag,
) {
    override val fragmentManager: FragmentManager = fragment.childFragmentManager

    override fun startActivity(intent: Intent) {
        fragment.startActivity(intent)
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        fragment.startActivityForResult(intent, requestCode)
    }
}

abstract class RouterImpl(
    private val activity: AppCompatActivity,
    private val activityNavigator: ActivityNavigator,
    private val mainActivityIntentFactory: MainActivityIntentFactory,
    private val onboardingNavigator: OnboardingNavigator,
    private val onboardingConfig: OnboardingConfig,
    private val releaseFlag: ReleaseFlag,
) : Router {
    abstract val fragmentManager: FragmentManager

    abstract fun startActivity(intent: Intent)

    abstract fun startActivityForResult(intent: Intent, requestCode: Int)

    // region onboarding
    override fun navigateToWelcome(fromSplash: Boolean) {
        onboardingNavigator.navigate(fromSplash)
    }

    override fun navigateToSignIn(clearStack: Boolean) {
        if (clearStack) {
            val builder = androidx.core.app.TaskStackBuilder.create(activity)
            builder.addNextIntent(Intent(activity, OnboardingActivity::class.java))
            builder.addNextIntent(Intent(activity, SignInActivity::class.java))
            builder.startActivities()
            activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
        } else {
            activityNavigator.navigateToSignIn()
        }
    }

    override fun createPersonalInformationInputResultContractForDeposit():
            ActivityResultContract<DepositMethod, DepositMethod?> {
        return PersonalInformationInputResultContractForDeposit()
    }

    override fun createPersonalInformationInputResultContractForSendRequest():
            ActivityResultContract<Unit, Boolean> {
        return PersonalInformationInputResultContractForSendRequest()
    }

    override fun createPersonalInformationInputResultContractForRedeem():
            ActivityResultContract<Unit, Boolean> {
        return PersonalInformationInputResultContractForRedeem()
    }

    // endregion

    // region Wallet
    override fun createPrimaryWalletDetailContract(): ActivityResultContract<
            WalletId, PrimaryWalletDetailResult?,
            > = PrimaryWalletDetailResultContract()

    override fun createShareWalletDetailContract():
            ActivityResultContract<ShareWalletDetailInput, ShareWalletDetailResult?> =
        ShareWalletDetailResultContract()

    override fun createKyashPointChargeContract(
        fragment: Fragment,
    ): ActivityResultContract<Unit, KyashPointChargeResult?> =
        KyashPointChargeResultContract(fragment)

    override fun createWalletCreationContract(
        fragment: Fragment,
    ): ActivityResultContract<Unit, WalletCreationResult?> =
        ShareWalletCreationResultContract(fragment)

    override fun createWalletInvitationContract():
            ActivityResultContract<String, WalletInvitationResult?> =
        ShareWalletParticipantInvitationResultContract()
    // endregion

    // region Share Wallet
    override fun navigateToShareWalletTransferCompletionSuccess(
        requestCode: Int,
        type: ShareWalletTransferType,
    ) {
        startActivityForResult(
            ShareWalletTransferCompletionActivity.createIntentSuccess(
                activity,
                type,
            ),
            requestCode,
        )
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToShareWalletTransferFailure(requestCode: Int, message: String) {
        startActivityForResult(
            ShareWalletTransferCompletionActivity.createIntentFailure(activity, message),
            requestCode,
        )
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun showShareWalletTransferLimitInformationDialog(
        oneTimeLimitAmount: Long,
        monthlyLimitAmount: Long,
        isKycCompleted: Boolean,
        type: ShareWalletTransferType,
    ) {
        showDialogFragment(
            ShareWalletTransferLimitInformationDialogFragment.newInstance(
                oneTimeLimitAmount = oneTimeLimitAmount,
                monthlyLimitAmount = monthlyLimitAmount,
                isKycCompleted = isKycCompleted,
                type = type,
            ),
            ShareWalletTransferLimitInformationDialogFragment.TAG,
        )
    }

    override fun createShareWalletTransferAmountSettingContract(): ActivityResultContract<
            ShareWalletTransferAmountSetting, TransferAmountSettingResult?,
            > {
        return ShareWalletTransferAmountSettingResultContract()
    }

    override fun navigateToShareWalletManageParticipants(walletId: WalletId, invitableCount: Int) {
        startActivity(
            ShareWalletActivity.createIntent(
                activity,
                walletId,
                ShareWalletTransitionType.TO_MANAGED_PARTICIPANTS,
                invitableCount,
            ),
        )
    }

    override fun navigateToShareWalletInvitedParticipants(walletId: WalletId) {
        startActivity(
            ShareWalletActivity.createIntent(
                activity,
                walletId,
                ShareWalletTransitionType.TO_INVITED_PARTICIPANTS,
                0,
            ),
        )
    }
    // endregion

    // region Withdraw
    override fun navigateToWithdrawalBankRegistrationDescription(
        skipDescription: Boolean,
        requestCode: Int?,
    ) {
        val intent = WithdrawalBankRegistrationDescriptionActivity.createIntent(
            context = activity,
            skipDescription = skipDescription,
            needsShowCompletion = true,
        )
        if (requestCode == null) {
            startActivity(intent)
        } else {
            startActivityForResult(intent, requestCode)
        }
        if (!skipDescription) {
            activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
        }
    }

    override fun navigateToWithdrawalMethod() {
        startActivity(WithdrawalMethodActivity.createIntent(activity))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToWithdrawal(destination: WithdrawalDestination) {
        startActivity(BankWithdrawalActivity.createIntent(activity, destination))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToWithdrawalCompletionActivity(
        result: WithdrawalCompletionViewModel.Result,
    ) {
        startActivity(WithdrawalCompletionActivity.createIntent(activity, result))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun createBankRegistrationContract():
            ActivityResultContract<BankRegistrationResultContractInput, Boolean> {
        return BankRegistrationResultContract()
    }

    override fun createWithdrawalBankRegistrationDescriptionContract():
            ActivityResultContract<
                    WithdrawalBankRegistrationDescriptionInput,
                    WithdrawalBankRegistrationResult?,
                    > {
        return WithdrawalBankRegistrationDescriptionResultContract()
    }

    override fun navigateToAboutBankWithdrawalFee() {
        showBottomSheet(
            AboutBankWithdrawalFeeFragment.newInstance(),
            AboutBankWithdrawalFeeFragment.TAG,
        )
    }

    // endregion

    // region Funds
    override fun navigateToVirtualBankAccount(data: VirtualBankAccount) {
        startActivity(
            VirtualBankAccountActivity.createIntent(
                activity,
                detail = VirtualBankAccountDetail(
                    bankName = data.bankName,
                    branchName = data.branchName,
                    branchCode = data.branchCode,
                    depositType = VirtualBankAccountDetail.DepositType.ORDINARY,
                    accountNumber = data.accountNumber,
                    accountName = data.accountName,
                    isAvailable = data.isAvailable,
                ),
            ),
        )
    }

    override fun navigateToVirtualBankRegistration() {
        startActivity(VirtualBankAccountActivity.createIntent(activity))
    }

    override fun navigateToChargeApplicationForPayEasy() {
        startActivity(ChargeApplicationActivity.createIntentForBank(activity))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToChargeApplicationForCvs() {
        startActivity(ChargeApplicationActivity.createIntentForCvs(activity))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToSevenBankCharge() {
        startActivity(SevenBankChargeActivity.createIntent(activity))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToLawsonBankDeposit() {
        startActivity(LawsonBankDepositActivity.createIntent(activity))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun createOtherDepositMethodsContract():
            ActivityResultContract<OtherDepositMethodFilter, OtherDepositMethodsResult> {
        return OtherDepositMethodsResultContract()
    }

    override fun createCreditCardRegistrationDescriptionContract():
            ActivityResultContract<Boolean, Boolean> {
        return CreditCardRegistrationDescriptionResultContract()
    }

    // endregion

    // region Timeline

    override fun navigateToTimelineDetail(
        timelineType: TimelineType,
    ) {
        val intent = TimelineDetailActivity.createIntent(activity, timelineType)
        activity.startActivity(intent)
    }

    override fun createTimelineDetailContract():
            ActivityResultContract<TimelineType, TimelineDetailResult?> {
        return TimelineDetailResultContract()
    }

    override fun createTimelineCategorizationContract(): ActivityResultContract<
            TimelineCategorizationActivityCallParam, Boolean,
            > = TimelineCategorizationResultContract()

    override fun createCategorySelectionContract():
            ActivityResultContract<CategorySelectionActivityCallParam, CategorySelectionResult?> {
        return CategorySelectionResultContract()
    }

    override fun navigateToTimelineDetailMemo(
        requestCode: Int,
        walletId: WalletId,
        timelineType: TimelineType,
        memo: String?,
    ) {
        startActivityForResult(
            ShareWalletTimelineDetailMemoActivity
                .createIntent(activity, walletId, timelineType, memo),
            requestCode,
        )
    }
    // endregion

    override fun navigateToPointHistory() {
        startActivityForResult(
            PointHistoryActivity.createIntent(activity),
            0,
        )
    }

    override fun navigateToOfferWall() {
        startActivity(
            OfferWallActivity.createIntent(activity),
        )
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    // region SignIn/Up
    override fun navigateToSignupForGoogle(data: SignupData.Google) {
        // 空文字列または0のフィールドは後続の画面で入力される
        val signUpInfo = SignUpInfo.Google(
            sessionId = data.sessionId,
        )
        activity.startActivity(SignUpActivity.createIntent(activity, signUpInfo))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun createSignInWithGoogleContract(
        googleIdentityService: GoogleIdentityService,
    ): ActivityResultContract<IntentSenderRequest, GoogleIdentityService.SignInResult> {
        return GoogleSignInContract(googleIdentityService)
    }

    // endregion

    // region Account
    override fun navigateToFraudHelp(payment: TimelineType.Payment) {
        startActivity(FraudHelpActivity.createIntent(activity, payment))
    }

    override fun navigateToLicense() {
        startActivity(LicenseActivity.createIntent(activity))
    }

    override fun navigateToPreview() {
        startActivity(ExperimentalFeatureActivity.createIntent(activity))
    }

    override fun navigateToInvitation() {
        startActivity(InvitationActivity.createIntent(activity))
    }

    override fun navigateToConnectionSetting() {
        startActivity(ConnectionSettingActivity.createIntent(activity))
    }

    override fun createPassCodeSettingAuthContract(): ActivityResultContract<
            PassCodeSettingAuthPurpose, PassCodeSettingAuthResult?,
            > = PassCodeSettingAuthResultContract()

    override fun createPasscodeUnlockContract(): ActivityResultContract<
            PasscodeUnlockViewModel.PasscodeUnlockType, Boolean,
            > {
        return PasscodeUnlockResultContract()
    }

    override fun createPasscodeLockContract(): ActivityResultContract<
            PasscodeLockViewModel.PasscodeLockType, Boolean,
            > {
        return PasscodeLockResultContract()
    }

    override fun createIdentityConfirmationContract(): ActivityResultContract<
            AvailableIdentificationMethod.PasswordWithAccount, IdentityConfirmationResult,
            > = IdentityConfirmationContract()

    override fun navigateToQrScan(isScan: Boolean, isTargetSelect: Boolean, requestCode: Int) {
        val intent = QrScanActivity.createIntent(activity, isScan, isTargetSelect)
        startActivityForResult(intent, requestCode)
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToAccountSetting() {
        startActivity(AccountSettingActivity.createIntent(activity))
    }

    override fun navigateToEmailEdit() {
        navigateTo(EmailEditFragment.newInstance())
    }

    override fun navigateToKyashIdEdit() {
        navigateTo(KyashIdEditFragment.newInstance())
    }

    override fun navigateToFullNameEdit() {
        navigateTo(FullNameEditFragment.newInstance())
    }

    override fun navigateToUserNameEdit() {
        navigateTo(UserNameEditFragment.newInstance())
    }

    override fun navigateToNotificationSetting() {
        startActivity(NotificationSettingActivity.createIntent(activity))
    }

    override fun navigateToAboutApp() {
        startActivity(AboutAppActivity.createIntent(activity))
    }

    override fun navigateToInquiry() {
        startActivity(InquiryActivity.createIntent(activity))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToInquiryWeb(user: User.LoginUser) {
        startActivity(InquiryWebActivity.createIntent(activity, user))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToTransactionInquiry(category: String, transaction: String) {
        startActivity(
            TransactionInquiryActivity.createIntent(
                activity,
                category = category,
                transaction = transaction,
            ),
        )
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToCancellation() {
        startActivity(CancellationNoticeActivity.createIntent(activity))
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToCancellationCompletion() {
        TaskStackBuilder.create(activity)
            .addNextIntent(OnboardingActivity.createIntent(activity))
            .addNextIntent(CancellationCompletionActivity.createIntent(activity))
            .startActivities()
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToRedeem() {
        startActivity(RedeemActivity.createIntent(activity))
    }

    override fun navigateToRedeemSuccess(amount: Long) {
        val intent = RedeemSuccessActivity.createIntent(activity, amount)
        startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun createPasscodeSettingContract(
        purpose: PasscodeSettingPurpose,
    ): ActivityResultContract<Unit, Boolean> {
        return PasscodeSettingActivity.createContract(purpose = purpose)
    }

    override fun createQrScanContract(): ActivityResultContract<
            QrScanInputData, List<User.PublicUser>?,
            > {
        return QrScanResultContract()
    }

    override fun createSignInWithAppleContract(): ActivityResultContract<NonceWithSessionId, Int> {
        return SignInWithAppleContract()
    }

    override fun createCreditCard3DSecureContract():
            ActivityResultContract<CreditCard3DSecureContractInput, CreditCard3DSecureResult> {
        return CreditCard3DSecureContract()
    }

    // endregion

    // region Card Detail
    override fun navigateToCardDetail(uuid: String, tab: CardDetailActivity.Tab) {
        val intent = CardDetailActivity.createIntent(activity, uuid, tab)
        startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }
    // endregion

    // region Bnpl
    override fun navigateToBnplApplication(bnplUserFlowType: BnplUserFlowType) {
        startActivity(
            BnplApplicationActivity.createIntent(
                activity,
                bnplUserFlowType,
            ),
        )
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToMain(route: Route?) {
        if (route != null) {
            activityNavigator.navigateToMain(route)
        } else {
            activityNavigator.navigateToMain()
        }
    }

    // endregion

    // region KYC
    override fun navigateToEKycSelect(
        kycSummary: UserKycSummary,
        reason: UserEKycReason,
        from: WaitKyashCardArrivalForKycViewModel.From,
    ) {
        when (val status = kycSummary.kycStatus) {
            UserKycSummary.KycStatus.EKycInProgress.ApplicationCompleted -> {
                val body = activity.getString(reason.inProgressDialogBodyTextResId)
                showDialogFragment(
                    EKycInProgressDialogFragment.newInstance(body),
                    EKycInProgressDialogFragment.TAG,
                )
            }

            UserKycSummary.KycStatus.EKycInProgress.ImageUploaded -> {
                val body = activity.getString(reason.imageUploadedDialogBodyTextResId)
                showDialogFragment(
                    EKycImageUploadedDialogFragment.newInstance(body),
                    EKycImageUploadedDialogFragment.TAG,
                )
            }
            // 再申請の際は、初回申請のreason(サーバーから返ってくる)ものを使うため
            is UserKycSummary.KycStatus.EKycRejected -> navigateToEKycDenial(status.progress.reason)
            UserKycSummary.KycStatus.KyashCardApplicationInProgress -> {
                val body = activity.getString(from.textRes)
                showDialogFragment(
                    WaitKyashCardArrivalForKycDialogFragment.newInstance(
                        from = from,
                        body = body,
                    ),
                    WaitKyashCardArrivalForKycDialogFragment.TAG,
                )
            }

            UserKycSummary.KycStatus.KycApplicationRequired -> {
                activity.startActivity(
                    EKycSelectActivity.createIntent(
                        context = activity,
                        reason = reason,
                        skippable = false,
                    ),
                )
                activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
            }

            UserKycSummary.KycStatus.KycCompleted -> {
            }
        }
    }

    override fun navigateToEKycSelect(
        reason: KyashMobileUserEKycReason,
        skippable: Boolean,
    ) {
        activity.startActivity(
            EKycSelectActivity.createIntent(
                context = activity,
                reason = reason.fromKyashMobileUserEKycReason(),
                skippable = skippable,
            ),
        )
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToEKycDenial(reason: UserEKycReason) {
        activityNavigator.navigateToEKycDenial(reason)
    }

    override fun navigateToEKycRegistration(
        isOcrCompleted: Boolean,
        params: EKycRegistrationParams,
    ) {
        startActivity(EKycRegistrationActivity.createIntent(activity, params))
    }

    override fun showEKycInProgressDialogFragment(body: String) {
        showDialogFragment(
            EKycInProgressDialogFragment.newInstance(body = body),
            EKycInProgressDialogFragment.TAG,
        )
    }

    override fun showWaitKyashCardArrivalForKycDialog(
        from: WaitKyashCardArrivalForKycViewModel.From,
        body: String,
    ) {
        showDialogFragment(
            WaitKyashCardArrivalForKycDialogFragment.newInstance(
                from = from,
                body = body,
            ),
            WaitKyashCardArrivalForKycDialogFragment.TAG,
        )
    }

    override fun showEKycImageUploadedDialogFragment(body: String) {
        showDialogFragment(
            EKycImageUploadedDialogFragment.newInstance(body = body),
            EKycImageUploadedDialogFragment.TAG,
        )
    }

    override fun navigateToEKycNfc(reason: KyashMobileUserEKycReason) =
        startActivity(EKycNfcActivity.createIntent(activity, reason))
    // endregion

    // region Seven Bank
    override fun navigateToSevenBankWithdrawal() {
        startActivity(SevenBankWithdrawalActivity.createIntent(activity))
    }
    // endregion

    // region Lawson Bank
    override fun navigateToLawsonBankWithdrawal() {
        startActivity(LawsonBankWithdrawalActivity.createIntent(activity))
    }
    // endregion

    override fun navigateToWelcomeChallenge() {
        startActivity(WelcomeChallengeActivity.createIntent(activity))
    }

    // end region

    // region Notification
    override fun navigateToNotificationForApproval(clearStack: Boolean, bottomToTop: Boolean) {
        activityNavigator.navigateToNotificationToYou(clearStack, bottomToTop)
    }
    // endregion

    // region News
    override fun navigateToNews(requestCode: Int) {
        startActivityForResult(NewsActivity.createIntent(activity), requestCode)
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }
    // endregion

    // region Card Application
    override fun navigateToMauricioCardApplication(type: MauricioApplicationTransitionType) {
        startActivity(MauricioActivity.newIntent(activity, type))
    }

    override fun navigateToCardSelection() {
        activity.startActivity(CardSelectionActivity.createIntent(activity))
        activity.overridePendingTransition(R.anim.fade_in, R.anim.keep)
    }

    override fun navigateToOnboardingCardSelection() {
        activity.startActivity(
            OnboardingCardSelectionActivity.createIntent(activity),
        )
        activity.overridePendingTransition(R.anim.fade_in, R.anim.keep)
    }

    override fun navigateToKyashCardApplication(skipSelectKycMethod: Boolean) {
        KyashCardApplicationActivity.createIntent(activity, skipSelectKycMethod)
            .let(activity::startActivity)
    }

    override fun navigateToKyashLiteCardIssuanceStatus() {
        activity.startActivity(KyashLiteCardIssuanceStatusActivity.createIntent(activity))
    }

    override fun navigateToKyashLiteCardExpirationDateRenewalActivation() {
        activity.startActivity(
            LiteExpirationDateRenewalActivationActivity.createIntent(activity),
        )
    }

    override fun navigateToKyashLiteCardActivationConfirmation(
        pan: String,
        expiryMonth: String?,
        expiryYear: String?,
        cvv: String?,
    ) {
        activity.startActivity(
            PhysicalCardActivationConfirmationActivity.createIntent(
                activity,
                pan,
                expiryMonth,
                expiryYear,
                cvv,
            ),
        )
    }

    override fun navigateToKyashLiteCardActivated(uuid: String) {
        val builder = TaskStackBuilder.create(activity)
            .addNextIntent(mainActivityIntentFactory.create(activity))
            .addNextIntent(KyashLiteCardActivatedActivity.createIntent(activity, uuid))
        builder.startActivities()
    }

    override fun navigateToKyashLiteCardSelection(fee: Long) {
        activity.startActivity(KyashLiteCardSelectionActivity.createIntent(activity, fee))
    }

    override fun navigateToLiteExpirationDateRenewalApplication() {
        activity.startActivity(LiteExpirationDateRenewalApplicationActivity.createIntent(activity))
    }

    override fun navigateToKyashLiteCardActivation(requestCode: Int, reissue: Boolean) {
        val intent =
            PhysicalCardActivationActivity.createIntent(
                activity,
                isSingleMode = false,
                isReissue = reissue,
            )
        startActivityForResult(intent, requestCode)
    }

    override fun navigateToKyashVirtualCardIssue(isSignup: Boolean) {
        startActivity(
            KyashCardVirtualActivity.newIntent(
                context = activity,
                isSignup = isSignup,
            ),
        )
    }

    override fun createDepositMethodsDescriptionResultContact():
            ActivityResultContract<
                    DepositMethodsDescriptionStartDestination, DepositMethodsDescriptionResult?,
                    > {
        return DepositMethodsDescriptionResultContact()
    }

    override fun showAboutCardPinBottomSheet() {
        showBottomSheet(AboutCardPinBottomSheetDialogFragment.newInstance())
    }

    override fun showCardNameConfirmationDialog(fullName: String) {
        showDialogFragment(
            CardNameConfirmationDialogFragment.newInstance(fullName),
            CardNameConfirmationDialogFragment.TAG,
        )
    }
    // endregion

    // region Send Request
    override fun navigateToSendRequestSearch() {
        val intent = SendRequestSearchActivity.createIntent(activity)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.fade_in, R.anim.keep)
    }

    override fun navigateToRequestUsersSelect(
        selectedUsers: List<User.PublicUser>,
        requestCode: Int,
        isEditMode: Boolean,
        withMenu: Boolean,
        defaultAmount: Long?,
    ) {
        activity.startActivity(
            RequestUsersSelectActivity.createIntent(
                activity,
                selectedUsers,
                isEditMode,
                withMenu,
                defaultAmount,
            ),
        )
    }

    override fun navigateToOpenLinkSend() {
        val intent = SendActivity.createIntent(activity, SendScreenParam.SendingLinkCreation)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    override fun navigateToRequestForOpenLink(defaultAmount: Long?) {
        val intent = RequestActivity.createIntentForOpenLink(activity, defaultAmount)
        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_bottom_short, R.anim.keep)
    }

    // endregion

    // region BottomSheet
    override fun showDepositBottomSheet() {
        showBottomSheet(
            DepositBottomSheetDialogFragment.newInstance(),
            DepositBottomSheetDialogFragment.TAG,
        )
    }

    override fun navigateToShareWalletTransferBottomSheetDialog(
        walletId: WalletId,
        type: ShareWalletTransferType,
    ) {
        showBottomSheet(
            ShareWalletTransferBottomSheetDialogFragment.newInstance(
                walletId,
                type,
            ),
        )
    }

    override fun navigateToShareWalletTransferConfirmationBottomSheetDialog(
        type: ShareWalletTransferType,
        fromUuid: String,
        toUuid: String,
        amount: Long,
    ) {
        showBottomSheet(
            ShareWalletTransferConfirmationBottomSheetDialogFragment
                .newInstance(
                    type,
                    fromUuid,
                    toUuid,
                    amount,
                ),
        )
    }

    override fun navigateToWalletFundSourceRegistrationBottomSheetDialog() {
        showBottomSheet(
            WalletRegistrationBottomSheetDialogFragment.getInstance(),
        )
    }

    override fun navigateLiteCardApplicationInProgressBottomSheet() {
        showBottomSheet(
            LiteCardApplicationInProgressBottomSheetDialogFragment.newInstance(),
        )
    }

    // endregion

    // region Dialog

    override fun showSimpleDialog(title: String, message: String) {
        showDialogFragment(
            SimpleDialogFragment.newInstance(title, message),
            "",
        )
    }

    override fun showUserDialog(user: User.PublicUser) {
        showDialogFragment(UserDialogFragment.newInstance(user), UserDialogFragment.TAG)
    }

    override fun showBalanceInformationDialog(
        amountBreakdown: AmountBreakdown?,
    ) {
        showDialogFragment(
            BalanceInformationDialogFragment.newInstance(
                amountBreakdown = amountBreakdown,
            ),
            BalanceInformationDialogFragment.TAG,
        )
    }

    override fun showTimelineBreakdownDialog(
        type: TimelineBreakdownType,
        amountBreakdown: TimelineAmountBreakdown,
    ) {
        showDialogFragment(
            TimelineBreakdownDialogFragment.newInstance(
                timelineBreakdownType = type,
                amountBreakdown = amountBreakdown,
            ),
            TimelineBreakdownDialogFragment.TAG,
        )
    }

    override fun showEKycNfcApplicationProgressDialogFragment() {
        showDialogFragment(
            EKycNfcApplicationProgressDialogFragment.newInstance(),
            EKycNfcApplicationProgressDialogFragment.TAG,
        )
    }

    override fun hideEKycNfcApplicationProgressDialogFragment() {
        dismissDialogFragment(
            EKycNfcApplicationProgressDialogFragment.newInstance(),
            EKycNfcApplicationProgressDialogFragment.TAG,
        )
    }

    override fun showRuntimePermissionDialog(type: PermissionDialogType) {
        showDialogFragment(
            RuntimePermissionDialogFragment.newInstance(type),
            RuntimePermissionDialogFragment.TAG,
        )
    }

    override fun showEmailVerificationErrorDialog(email: String) {
        showDialogFragment(
            EmailVerificationErrorDialogFragment.newInstance(email),
            EmailVerificationErrorDialogFragment.TAG,
        )
    }

    override fun showCancellationConfirmationDialog() {
        showDialogFragment(
            CancellationConfirmationDialogFragment.newInstance(),
            CancellationConfirmationDialogFragment.TAG,
        )
    }

    override fun showInquiryEmailNotVerifiedDialog() {
        showDialogFragment(
            InquiryEmailNotVerifiedDialogFragment.newInstance(),
            InquiryEmailNotVerifiedDialogFragment.TAG,
        )
    }

    override fun showInquiryEmailVerifiedDialog(email: String) {
        showDialogFragment(
            InquiryEmailVerifiedDialogFragment.newInstance(email),
            InquiryEmailVerifiedDialogFragment.TAG,
        )
    }

    override fun showProgressDialog() {
        activityNavigator.showProgressDialog()
    }

    override fun dismissProgressDialog() {
        activityNavigator.dismissProgressDialog()
    }

    override fun showApiErrorDialog(
        throwable: Throwable,
        dismissCallback: (() -> Unit)?,
    ) {
        activityNavigator.showApiErrorDialog(
            throwable,
            dismissCallback,
        )
    }

    override fun showAlertDialog(
        title: Int?,
        message: Int?,
        layoutId: Int?,
        ok: Int?,
        okCallback: (() -> Unit)?,
        cancel: Int?,
        iconResId: Int?,
        cancelCallback: (() -> Unit)?,
        dismissCallback: (() -> Unit)?,
        cancelable: Boolean,
    ) {
        activityNavigator.showAlertDialog(
            title,
            message,
            layoutId,
            ok,
            okCallback,
            cancel,
            iconResId,
            cancelCallback,
            dismissCallback,
            cancelable,
        )
    }

    override fun showAlertDialog(
        title: String?,
        message: String?,
        layoutId: Int?,
        ok: Int?,
        okCallback: (() -> Unit)?,
        cancel: Int?,
        iconResId: Int?,
        cancelCallback: (() -> Unit)?,
        dismissCallback: (() -> Unit)?,
        cancelable: Boolean,
    ) {
        activityNavigator.showAlertDialog(
            title,
            message,
            layoutId,
            ok,
            okCallback,
            cancel,
            iconResId,
            cancelCallback,
            dismissCallback,
            cancelable,
        )
    }
    // endregion

    // region system setting
    override fun navigateSystemNotificationSetting() {
        val context = activity
        val intent = Intent().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            } else {
                // SDK 26未満は `Settings.ACTION_APP_NOTIFICATION_SETTINGS` が存在しないので以下を指定する
                // https://stackoverflow.com/questions/32366649/any-way-to-link-to-the-android-notification-settings-for-my-app
                action = "android.settings.APP_NOTIFICATION_SETTINGS"
                putExtra("app_package", context.packageName)
                putExtra("app_uid", context.applicationInfo.uid)
            }
        }
        context.startActivity(intent)
    }
    // endregion

    override fun showToast(message: String?) {
        activityNavigator.showToast(message)
    }

    override fun showApiErrorToast(throwable: Throwable) {
        activityNavigator.showApiErrorToast(throwable)
    }

    override fun showApiFatalErrorToast(fatalError: ApiFatalError) {
        activityNavigator.showApiFatalErrorToast(fatalError)
    }

    override fun showNonFatalErrorToast(nonFatalError: NonFatalError) {
        activityNavigator.showNonFatalErrorToast(nonFatalError)
    }

    override fun showNonFatalErrorDialog(nonFatalError: NonFatalError) {
        activityNavigator.showNonFatalErrorDialog(nonFatalError)
    }

    override fun navigateToChromeCustomTab(url: String, session: CustomTabsSession?) {
        IntentUtility.openCustomTabs(activity, session, url)
    }

    private fun showDialogFragment(fragment: DialogFragment, tag: String) {
        activity.lifecycleScope.launch {
            activity.lifecycle.whenResumed {
                val ft = fragmentManager.beginTransaction()
                val prev = fragmentManager.findFragmentByTag(tag)
                if (prev != null) {
                    ft.remove(prev)
                }
                ft.add(fragment, tag)
                ft.commitAllowingStateLoss()
            }
        }
    }

    private fun dismissDialogFragment(fragment: DialogFragment, tag: String) {
        activity.lifecycleScope.launch {
            activity.lifecycle.whenResumed {
                val ft = fragmentManager.beginTransaction()
                val prev = fragmentManager.findFragmentByTag(tag)
                if (prev != null) {
                    ft.remove(prev)
                }
                ft.remove(fragment)
                ft.commitAllowingStateLoss()
            }
        }
    }

    private fun showBottomSheet(fragment: BottomSheetDialogFragment, tag: String = "bottom") {
        activity.lifecycleScope.launch {
            activity.lifecycle.whenResumed {
                // BottomSheetの呼び出し元Fragmentに結果を伝えることを想定しないためactivityを使う
                val ft = activity.supportFragmentManager.beginTransaction()
                val prev = activity.supportFragmentManager.findFragmentByTag(tag)
                if (prev != null) {
                    ft.remove(prev)
                }
                ft.addToBackStack(null)
                fragment.show(ft, tag)
            }
        }
    }

    /**
     * Navigate to fragment
     * @param fragment
     */
    private fun navigateTo(fragment: Fragment) {
        val ft = fragmentManager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.pull_in_from_right,
            R.anim.pull_out_to_left,
            R.anim.pull_in_from_left,
            R.anim.pull_out_to_right,
        )
        ft.replace(R.id.container, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }
}