package com.quinti.android_step_template.ui.navigator

import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.browser.customtabs.CustomTabsSession
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalError
import com.quinti.android_step_template.kmp.data.api.exception.NonFatalError
import okhttp3.Route
import javax.inject.Qualifier

/**
 * 役割はNavigatorと同一ですが、ActivityNavigatorはcore moduleで広く使われていてapp moduleに移動できないため、
 * あたらしくinterfaceを切っています。Routerの実装はapp moduleにあり、Hilt化やmulti-module対応をすすめることで
 * 最終的にNavigatorを消すことを目指します。
 */
interface Router {

    fun navigateToHome()
    fun navigateToMyProfile()
    fun navigateToTimeLine()
    fun navigateToChatList()
    fun navigateToSetting()

    // Onboarding
//    fun navigateToWelcome(fromSplash: Boolean)
//
//    fun navigateToSignIn(clearStack: Boolean)

//    fun createPersonalInformationInputResultContractForDeposit():
//            ActivityResultContract<DepositMethod, DepositMethod?>
//
//    fun createPersonalInformationInputResultContractForSendRequest():
//            ActivityResultContract<Unit, Boolean>
//
//    fun createPersonalInformationInputResultContractForRedeem():
//            ActivityResultContract<Unit, Boolean>
//
//    // Wallet
//    fun createPrimaryWalletDetailContract(): ActivityResultContract<WalletId, PrimaryWalletDetailResult?>
//    fun createShareWalletDetailContract(): ActivityResultContract<ShareWalletDetailInput, ShareWalletDetailResult?>
//    fun createKyashPointChargeContract(fragment: Fragment): ActivityResultContract<Unit, KyashPointChargeResult?>
//    fun createWalletCreationContract(
//        fragment: Fragment,
//    ): ActivityResultContract<Unit, WalletCreationResult?>
//
//    fun createWalletInvitationContract(): ActivityResultContract<String, WalletInvitationResult?>
//
//    // Share Wallet
//    fun navigateToShareWalletTransferCompletionSuccess(
//        requestCode: Int,
//        type: ShareWalletTransferType,
//    )

//    fun navigateToShareWalletTransferFailure(requestCode: Int, message: String)
//    fun showShareWalletTransferLimitInformationDialog(
//        oneTimeLimitAmount: Long,
//        monthlyLimitAmount: Long,
//        isKycCompleted: Boolean,
//        type: ShareWalletTransferType,
//    )
//
//    fun createShareWalletTransferAmountSettingContract(): ActivityResultContract<ShareWalletTransferAmountSetting, TransferAmountSettingResult?>
//
//    fun navigateToShareWalletManageParticipants(walletId: WalletId, invitableCount: Int)
//
//    fun navigateToShareWalletInvitedParticipants(walletId: WalletId)

    // Withdraw
    fun navigateToWithdrawalBankRegistrationDescription(
        skipDescription: Boolean,
        requestCode: Int? = null,
    )

    fun navigateToWithdrawalMethod()

//    fun navigateToWithdrawal(destination: WithdrawalDestination)
//
//    fun navigateToWithdrawalCompletionActivity(result: WithdrawalCompletionViewModel.Result)
//
//    fun createBankRegistrationContract():
//            ActivityResultContract<BankRegistrationResultContractInput, Boolean>
//
//    fun createWithdrawalBankRegistrationDescriptionContract():
//            ActivityResultContract<
//                    WithdrawalBankRegistrationDescriptionInput,
//                    WithdrawalBankRegistrationResult?,
//                    >
//
//    fun createCreditCard3DSecureContract():
//            ActivityResultContract<CreditCard3DSecureContractInput, CreditCard3DSecureResult>

    fun navigateToAboutBankWithdrawalFee()

    // Deposit
//    fun navigateToVirtualBankAccount(data: VirtualBankAccount)

    fun navigateToVirtualBankRegistration()

    fun navigateToChargeApplicationForPayEasy()

    fun navigateToChargeApplicationForCvs()

    fun navigateToSevenBankCharge()

//    fun createOtherDepositMethodsContract():
//            ActivityResultContract<OtherDepositMethodFilter, OtherDepositMethodsResult>

//    fun createCreditCardRegistrationDescriptionContract():
//            ActivityResultContract<Boolean, Boolean>

//    fun navigateToLawsonBankDeposit()

    // Timeline
//    fun navigateToTimelineDetail(
//        timelineType: TimelineType,
//    )
//
//    fun createTimelineDetailContract(): ActivityResultContract<TimelineType, TimelineDetailResult?>

    /**
     * カテゴリ履歴画面の ActivityResultContract を取得する。
     * 画面の結果が true の時はタイムラインが更新されたので、呼び出し元は必要に応じてリロードを行う。
     */
//    fun createTimelineCategorizationContract(): ActivityResultContract<
//            TimelineCategorizationActivityCallParam, Boolean,
//            >
//
//    fun createCategorySelectionContract(): ActivityResultContract<
//            CategorySelectionActivityCallParam, CategorySelectionResult?,
//            >
//
//    fun navigateToTimelineDetailMemo(
//        requestCode: Int,
//        walletId: WalletId,
//        timelineType: TimelineType,
//        memo: String?,
//    )

//    fun navigateToPointHistory()
//
//    fun navigateToOfferWall()

//    // SignIn/Up
//    fun navigateToSignupForGoogle(data: SignupData.Google)
//    fun createSignInWithGoogleContract(googleIdentityService: GoogleIdentityService): ActivityResultContract<IntentSenderRequest, GoogleIdentityService.SignInResult>
//
//    // Account
//    fun createPassCodeSettingAuthContract():
//            ActivityResultContract<PassCodeSettingAuthPurpose, PassCodeSettingAuthResult?>
//
//    fun createPasscodeUnlockContract(): ActivityResultContract<
//            PasscodeUnlockViewModel.PasscodeUnlockType, Boolean,
//            >
//
//    fun createPasscodeLockContract(): ActivityResultContract<
//            PasscodeLockViewModel.PasscodeLockType, Boolean,
//            >
//
//    // Identify Confirmation
//    fun createIdentityConfirmationContract(): ActivityResultContract<AvailableIdentificationMethod.PasswordWithAccount, IdentityConfirmationResult>
//
//    // Bnpl
//    fun navigateToBnplApplication(bnplUserFlowType: BnplUserFlowType)
//    fun navigateToMain(route: Route? = null)
//
//    // KYC
//    fun navigateToEKycSelect(
//        kycSummary: UserKycSummary,
//        reason: UserEKycReason,
//        from: WaitKyashCardArrivalForKycViewModel.From,
//    )
//
//    fun navigateToEKycRegistration(
//        isOcrCompleted: Boolean,
//        params: EKycRegistrationParams,
//    )
//
//    fun navigateToEKycSelect(
//        reason: KyashMobileUserEKycReason,
//        skippable: Boolean = false,
//    )

//    fun navigateToEKycDenial(reason: UserEKycReason)

    /**
     * 「審査中です」のダイアログ
     */
//    fun showEKycInProgressDialogFragment(body: String)

    /**
     * 「カード到着をお待ち下さい」のダイアログ
     */
//    fun showWaitKyashCardArrivalForKycDialog(
//        from: WaitKyashCardArrivalForKycViewModel.From,
//        body: String,
//    )

    /**
     * 「本人確認情報の入力待ちです」のダイアログ
     */
    fun showEKycImageUploadedDialogFragment(body: String)
//    fun navigateToEKycNfc(reason: KyashMobileUserEKycReason)
    fun showEKycNfcApplicationProgressDialogFragment()
    fun hideEKycNfcApplicationProgressDialogFragment()

    // Invite
    fun navigateToInvitation()

    // Seven Bank
    fun navigateToSevenBankWithdrawal()

    fun navigateToLawsonBankWithdrawal()

    // Coupon
    fun navigateToCouponDetail(couponId: String)
    fun navigateToCouponList()
    fun navigateCouponRewardIntroductionBottomSheet(isKyashCard: Boolean)
    fun navigateToWelcomeChallenge()

    // Notification
    fun navigateToNotificationForApproval(clearStack: Boolean, bottomToTop: Boolean)

    // News
    fun navigateToNews(requestCode: Int)

    // Card Application
//    fun navigateToMauricioCardApplication(type: MauricioApplicationTransitionType)
    fun navigateToKyashCardApplication(skipSelectKycMethod: Boolean)
    fun showAboutCardPinBottomSheet()
    fun showCardNameConfirmationDialog(fullName: String)

    fun navigateToCardSelection()
    fun navigateToOnboardingCardSelection()
    fun navigateToKyashLiteCardIssuanceStatus()
    fun navigateToKyashLiteCardExpirationDateRenewalActivation()
    fun navigateToKyashLiteCardActivationConfirmation(
        pan: String,
        expiryMonth: String?,
        expiryYear: String?,
        cvv: String?,
    )

    fun navigateToKyashLiteCardActivated(uuid: String)

    fun navigateToKyashLiteCardSelection(fee: Long)
    fun navigateToKyashLiteCardActivation(requestCode: Int, reissue: Boolean = false)
    fun navigateToKyashVirtualCardIssue(isSignup: Boolean = false)

//    fun createDepositMethodsDescriptionResultContact():
//            ActivityResultContract<
//                    DepositMethodsDescriptionStartDestination, DepositMethodsDescriptionResult?,
//                    >

    /**
     * Liteカードの有効期限更新申込みへの遷移
     */
    fun navigateToLiteExpirationDateRenewalApplication()

    // Send Request
    fun navigateToSendRequestSearch()
//    fun navigateToRequestUsersSelect(
//        selectedUsers: List<User.PublicUser>,
//        requestCode: Int,
//        isEditMode: Boolean,
//        withMenu: Boolean,
//        defaultAmount: Long?,
//    )

    fun navigateToOpenLinkSend()
    fun navigateToRequestForOpenLink(defaultAmount: Long? = null)

    // Account
//    fun navigateToFraudHelp(payment: TimelineType.Payment)
    fun navigateToLicense()
    fun navigateToPreview()
    fun navigateToQrScan(isScan: Boolean, isTargetSelect: Boolean, requestCode: Int)
    fun navigateToConnectionSetting()
    fun navigateToAccountSetting()
    fun navigateToEmailEdit()
    fun navigateToKyashIdEdit()
    fun navigateToFullNameEdit()
    fun navigateToUserNameEdit()
    fun navigateToNotificationSetting()
    fun navigateToAboutApp()
    fun navigateToInquiry()
//    fun navigateToInquiryWeb(user: User.LoginUser)
    fun navigateToTransactionInquiry(category: String, transaction: String)
    fun navigateToCancellation()
    fun navigateToCancellationCompletion()
    fun navigateToRedeem()
    fun navigateToRedeemSuccess(amount: Long)

//    fun createPasscodeSettingContract(
//        purpose: PasscodeSettingPurpose,
//    ): ActivityResultContract<Unit, Boolean>
//
//    fun createQrScanContract(): ActivityResultContract<QrScanInputData, List<User.PublicUser>?>

    // Card Detail
//    fun navigateToCardDetail(uuid: String, tab: CardDetailActivity.Tab)
//    fun createSignInWithAppleContract(): ActivityResultContract<NonceWithSessionId, Int>

    // BottomSheet
    fun showDepositBottomSheet()

//    fun navigateToShareWalletTransferBottomSheetDialog(
//        walletId: WalletId,
//        type: ShareWalletTransferType,
//    )

//    fun navigateToShareWalletTransferConfirmationBottomSheetDialog(
//        type: ShareWalletTransferType,
//        fromUuid: String,
//        toUuid: String,
//        amount: Long,
//    )

    fun navigateToWalletFundSourceRegistrationBottomSheetDialog()

    fun navigateCouponIdentifyAgreementBottomSheet(url: String)

    fun navigateLiteCardApplicationInProgressBottomSheet()

    // Dialog
    fun showSimpleDialog(title: String, message: String)

//    fun showUserDialog(user: User.PublicUser)

    /**
     * [amountBreakdown]を指定した場合は、その値を表示し、未指定の場合はAPIから取得した値を表示する。
     * APIから取得する場合は個人口座の[AmountBreakdown]となることに注意すること。
     */
//    fun showBalanceInformationDialog(amountBreakdown: AmountBreakdown? = null)

    /**
     * [TimelineAmountBreakdown]の内訳を表示する。
     * 表示する[TimelineBreakdownType]によってタイトルが変わる。
     */
//    fun showTimelineBreakdownDialog(
//        type: TimelineBreakdownType,
//        amountBreakdown: TimelineAmountBreakdown,
//    )

//    fun showRuntimePermissionDialog(type: PermissionDialogType)

    fun showEmailVerificationErrorDialog(email: String)
    fun showCancellationConfirmationDialog()

    fun showInquiryEmailNotVerifiedDialog()
    fun showInquiryEmailVerifiedDialog(email: String)

    fun showProgressDialog()
    fun dismissProgressDialog()

    /**
     * dismissCallbackはBan等の特別な処理以外のときのみ適用される
     */
    fun showApiErrorDialog(
        throwable: Throwable,
        dismissCallback: (() -> Unit)? = null,
    )

//    fun showAlertDialog(
//        @StringRes title: Int? = null,
//        @StringRes message: Int? = null,
//        @LayoutRes layoutId: Int? = null,
//        @StringRes ok: Int? = R.string.ok,
//        okCallback: (() -> Unit)? = null,
//        @StringRes cancel: Int? = null,
//        @DrawableRes iconResId: Int? = null,
//        cancelCallback: (() -> Unit)? = null,
//        dismissCallback: (() -> Unit)? = null,
//        cancelable: Boolean = true,
//    )

//    fun showAlertDialog(
//        title: String? = null,
//        message: String? = null,
//        @LayoutRes layoutId: Int? = null,
//        @StringRes ok: Int? = R.string.ok,
//        okCallback: (() -> Unit)? = null,
//        @StringRes cancel: Int? = null,
//        @DrawableRes iconResId: Int? = null,
//        cancelCallback: (() -> Unit)? = null,
//        dismissCallback: (() -> Unit)? = null,
//        cancelable: Boolean = true,
//    )

    // Navigate system settings
    fun navigateSystemNotificationSetting()

    // Toast
//    fun showToast(message: String?)
//    fun showApiErrorToast(throwable: Throwable)
    fun showApiFatalErrorToast(fatalError: ApiFatalError)
//    fun showNonFatalErrorToast(nonFatalError: NonFatalError)
    fun showNonFatalErrorDialog(nonFatalError: NonFatalError)

    // ChromeCustomTab
    fun navigateToChromeCustomTab(url: String, session: CustomTabsSession?)
}

/**
 * このアノテーションがついたRouterはFragmentからstartActivityForResultします。
 * FragmentのonActivityResultで結果をハンドリングしたい場合に利用します。
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentRouter
