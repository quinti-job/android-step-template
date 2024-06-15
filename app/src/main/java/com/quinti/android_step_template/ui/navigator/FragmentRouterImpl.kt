package com.quinti.android_step_template.ui.navigator

import javax.inject.Inject

class FragmentRouterImpl @Inject constructor() : Router {
    override fun navigateToHome() {
        TODO("Not yet implemented")
    }

    override fun navigateToMyProfile() {
        TODO("Not yet implemented")
    }

    override fun navigateToTimeLine() {
        TODO("Not yet implemented")
    }

    override fun navigateToChatList() {
        TODO("Not yet implemented")
    }

    override fun navigateToSetting() {
        TODO("Not yet implemented")
    }

    override fun navigateToWithdrawalBankRegistrationDescription(
        skipDescription: Boolean,
        requestCode: Int?
    ) {
        TODO("Not yet implemented")
    }

    override fun navigateToWithdrawalMethod() {
        TODO("Not yet implemented")
    }

    override fun navigateToAboutBankWithdrawalFee() {
        TODO("Not yet implemented")
    }

    override fun navigateToVirtualBankRegistration() {
        TODO("Not yet implemented")
    }

    override fun navigateToChargeApplicationForPayEasy() {
        TODO("Not yet implemented")
    }

    override fun navigateToChargeApplicationForCvs() {
        TODO("Not yet implemented")
    }

    override fun navigateToSevenBankCharge() {
        TODO("Not yet implemented")
    }

    override fun showEKycImageUploadedDialogFragment(body: String) {
        TODO("Not yet implemented")
    }

    override fun showEKycNfcApplicationProgressDialogFragment() {
        TODO("Not yet implemented")
    }

    override fun hideEKycNfcApplicationProgressDialogFragment() {
        TODO("Not yet implemented")
    }

    override fun navigateToInvitation() {
        TODO("Not yet implemented")
    }

    override fun navigateToSevenBankWithdrawal() {
        TODO("Not yet implemented")
    }

    override fun navigateToLawsonBankWithdrawal() {
        TODO("Not yet implemented")
    }

    override fun navigateToCouponDetail(couponId: String) {
        TODO("Not yet implemented")
    }

    override fun navigateToCouponList() {
        TODO("Not yet implemented")
    }

    override fun navigateCouponRewardIntroductionBottomSheet(isKyashCard: Boolean) {
        TODO("Not yet implemented")
    }

    override fun navigateToWelcomeChallenge() {
        TODO("Not yet implemented")
    }

    override fun navigateToNotificationForApproval(clearStack: Boolean, bottomToTop: Boolean) {
        TODO("Not yet implemented")
    }

    override fun navigateToNews(requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToKyashCardApplication(skipSelectKycMethod: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showAboutCardPinBottomSheet() {
        TODO("Not yet implemented")
    }

    override fun showCardNameConfirmationDialog(fullName: String) {
        TODO("Not yet implemented")
    }

    override fun navigateToCardSelection() {
        TODO("Not yet implemented")
    }

    override fun navigateToOnboardingCardSelection() {
        TODO("Not yet implemented")
    }

    override fun navigateToKyashLiteCardIssuanceStatus() {
        TODO("Not yet implemented")
    }

    override fun navigateToKyashLiteCardExpirationDateRenewalActivation() {
        TODO("Not yet implemented")
    }

    override fun navigateToKyashLiteCardActivationConfirmation(
        pan: String,
        expiryMonth: String?,
        expiryYear: String?,
        cvv: String?
    ) {
        TODO("Not yet implemented")
    }

    override fun navigateToKyashLiteCardActivated(uuid: String) {
        TODO("Not yet implemented")
    }

    override fun navigateToKyashLiteCardSelection(fee: Long) {
        TODO("Not yet implemented")
    }

    override fun navigateToKyashLiteCardActivation(requestCode: Int, reissue: Boolean) {
        TODO("Not yet implemented")
    }

    override fun navigateToKyashVirtualCardIssue(isSignup: Boolean) {
        TODO("Not yet implemented")
    }

    override fun navigateToLiteExpirationDateRenewalApplication() {
        TODO("Not yet implemented")
    }

    override fun navigateToSendRequestSearch() {
        TODO("Not yet implemented")
    }

    override fun navigateToOpenLinkSend() {
        TODO("Not yet implemented")
    }

    override fun navigateToRequestForOpenLink(defaultAmount: Long?) {
        TODO("Not yet implemented")
    }

    override fun navigateToLicense() {
        TODO("Not yet implemented")
    }

    override fun navigateToPreview() {
        TODO("Not yet implemented")
    }

    override fun navigateToQrScan(isScan: Boolean, isTargetSelect: Boolean, requestCode: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToConnectionSetting() {
        TODO("Not yet implemented")
    }

    override fun navigateToAccountSetting() {
        TODO("Not yet implemented")
    }

    override fun navigateToEmailEdit() {
        TODO("Not yet implemented")
    }

    override fun navigateToKyashIdEdit() {
        TODO("Not yet implemented")
    }

    override fun navigateToFullNameEdit() {
        TODO("Not yet implemented")
    }

    override fun navigateToUserNameEdit() {
        TODO("Not yet implemented")
    }

    override fun navigateToNotificationSetting() {
        TODO("Not yet implemented")
    }

    override fun navigateToAboutApp() {
        TODO("Not yet implemented")
    }

    override fun navigateToInquiry() {
        TODO("Not yet implemented")
    }

    override fun navigateToTransactionInquiry(category: String, transaction: String) {
        TODO("Not yet implemented")
    }

    override fun navigateToCancellation() {
        TODO("Not yet implemented")
    }

    override fun navigateToCancellationCompletion() {
        TODO("Not yet implemented")
    }

    override fun navigateToRedeem() {
        TODO("Not yet implemented")
    }

    override fun navigateToRedeemSuccess(amount: Long) {
        TODO("Not yet implemented")
    }

    override fun showDepositBottomSheet() {
        TODO("Not yet implemented")
    }

    override fun navigateToWalletFundSourceRegistrationBottomSheetDialog() {
        TODO("Not yet implemented")
    }

    override fun navigateCouponIdentifyAgreementBottomSheet(url: String) {
        TODO("Not yet implemented")
    }

    override fun navigateLiteCardApplicationInProgressBottomSheet() {
        TODO("Not yet implemented")
    }

    override fun showSimpleDialog(title: String, message: String) {
        TODO("Not yet implemented")
    }

    override fun showEmailVerificationErrorDialog(email: String) {
        TODO("Not yet implemented")
    }

    override fun showCancellationConfirmationDialog() {
        TODO("Not yet implemented")
    }

    override fun showInquiryEmailNotVerifiedDialog() {
        TODO("Not yet implemented")
    }

    override fun showInquiryEmailVerifiedDialog(email: String) {
        TODO("Not yet implemented")
    }

    override fun showProgressDialog() {
        TODO("Not yet implemented")
    }

    override fun dismissProgressDialog() {
        TODO("Not yet implemented")
    }

    override fun showApiErrorDialog(throwable: Throwable, dismissCallback: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun navigateSystemNotificationSetting() {
        TODO("Not yet implemented")
    }

}