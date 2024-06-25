@file:Suppress(
    "ktlint",
    "detekt.all",
    "RedundantVisibilityModifier",
)

package com.quinti.android_step_template.kmp.domain.analytics

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

public object Tracking {

    private fun convertDateToString(date: Date): String = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(date)

    public sealed class Screen(
        public val eventName: String,
    ) : Parcelable {
        /**
         * アプリ開始画面
         */
        @Parcelize
        public data object Landing : Screen("Landing")

        /**
         * 会員登録方法の選択
         */
        @Parcelize
        public data object SignUpSelect : Screen("SignUpSelect")

        /**
         * アカウントを登録
         */
        @Parcelize
        public data object SignupRegister : Screen("SignupRegister")

        /**
         * 本人情報を入力
         */
        @Parcelize
        public data object PersonalInformationInput : Screen("PersonalInformationInput")

        /**
         * 本名の確認ダイアログ
         */
        @Parcelize
        public data object SignupNameConfirm : Screen("SignupNameConfirm")

        /**
         * ユーザー名を入力
         */
        @Parcelize
        public data object UserNameInput : Screen("UserNameInput")

        /**
         * 会員登録画面（生まれた年選択）
         */
        @Parcelize
        public data object SignupBirthYearSelect : Screen("SignupBirthYearSelect")

        /**
         * 電話番号登録画面
         */
        @Parcelize
        public data object SignupPhoneNumberRegister : Screen("SignupPhoneNumberRegister")

        /**
         * SMS認証画面
         */
        @Parcelize
        public data object SignupSmsAuth : Screen("SignupSmsAuth")

        /**
         * Kyash Card Virtual発行画面
         */
        @Parcelize
        public data object KyashCardVirtualToIssue : Screen("KyashCardVirtualToIssue")

        /**
         * Kyash Card Virtual発行完了画面
         */
        @Parcelize
        public data object KyashCardVirtualIssued : Screen("KyashCardVirtualIssued")

        /**
         * オンボーディング時の入金方法選択画面
         */
        @Parcelize
        public data object SelectFundSourceOnboarding : Screen("SelectInitialDepositMethod")

        /**
         * オンボーディングカード選択画面
         */
        @Parcelize
        public data object OnboardingCardSelection : Screen("OnboardingCardSelection")

        /**
         * Kyash Card Virtualを発行
         */
        @Parcelize
        public data object KyashCardVirtualDescription : Screen("KyashCardVirtualDescription")

        /**
         * カード名義を登録
         */
        @Parcelize
        public data object KyashCardVirtualIssue : Screen("KyashCardVirtualIssue")

        /**
         * Kyash Card Virtual発行完了
         */
        @Parcelize
        public data object KyashCardVirtualIssueCompletion : Screen("KyashCardVirtualIssueCompletion")

        /**
         * カードを登録画面
         */
        @Parcelize
        public data object CardRegistration : Screen("CreditCardRegistrationDescription")

        /**
         * カード情報入力画面
         */
        @Parcelize
        public data object AutoChargeCardRegistration : Screen("LinkedCardRegistrationFragment")

        /**
         * カード登録完了画面
         */
        @Parcelize
        public data object CreditCardRegistrationSucceeded : Screen("CreditCardRegistrationSucceeded")

        /**
         * カードリンク解除ダイアログ（入金）
         */
        @Parcelize
        public data object DisableAutoChargeDialogByDeposit : Screen("DisableAutoChargeDialogByDeposit")

        /**
         * カードリンク解除ダイアログ（リンク解除）
         */
        @Parcelize
        public data object DisableAutoChargeDialogByUnlink : Screen("DisableAutoChargeDialogByUnlink")

        /**
         * Linked Card 一覧画面
         */
        @Parcelize
        public data object LinkedCardSelect : Screen("LinkedCardSelect")

        /**
         * Linked Card 削除ダイアログ
         */
        @Parcelize
        public data object LinkedCardDeleteDialog : Screen("LinkedCardDeleteDialog")

        /**
         * 入金用銀行口座登録時の説明画面 (Kyashマネーアカウント）
         */
        @Parcelize
        public data object KyashMoneyDepositBankAccountRegisterTerms : Screen("DepositBankDescription")

        /**
         * 入金用銀行口座登録時の説明+利用規約同意画面 （Kyashバリューアカウント）
         */
        @Parcelize
        public data object KyashValueDepositBankAccountRegisterTerms :
            Screen("DepositBankDescriptionWithTerms")

        /**
         * 入金用銀行口座登録時の銀行選択画面
         */
        @Parcelize
        public data object DepositBankRegistrationSelect : Screen("BankSelectionFragment")

        /**
         * 出金用銀行口座選択画面
         */
        @Parcelize
        public data object WithdrawBankSelect : Screen("WithdrawalMethodFragment")

        /**
         * 入金用銀行口座登録フォーム画面
         */
        @Parcelize
        public data object DepositBankRegister : Screen("BankRegistrationFragment")

        /**
         * 銀行口座登録完了画面 (銀行口座登録目的・KYC依拠成功)
         */
        @Parcelize
        public data object BankRegistrationSucceeded : Screen("BankRegistrationCompletion")

        /**
         * 銀行口座登録完了画面 (銀行口座登録目的・KYC依拠失敗)
         */
        @Parcelize
        public data object BankRegistrationSucceededWithoutKyc : Screen("BankRegSuccessNotKyc")

        /**
         * 銀行口座登録失敗画面 (銀行口座登録目的)
         */
        @Parcelize
        public data object BankRegistrationFailed : Screen("BankRegistrationFailed")

        /**
         * 本人確認方法選択画面
         */
        @Parcelize
        public data object SelectKycMethod : Screen("SelectKycMethod")

        /**
         * スマホ本人確認入力フォーム画面
         */
        @Parcelize
        public data object EKycApplication : Screen("EKycApplication")

        /**
         * スマホ本人確認入力確認ダイアログ
         */
        @Parcelize
        public data object EKycApplicationConfirm : Screen("EKycApplicationConfirm")

        /**
         * スマホ本人確認申込完了画面
         */
        @Parcelize
        public data object EKycApplicationCompletion : Screen("EKycApplicationCompletion")

        /**
         * スマホ本人確認否認画面
         */
        @Parcelize
        public data object EKycDenial : Screen("EKycDenial")

        /**
         * Kyashの入金方法(説明)
         */
        @Parcelize
        public data object DepositMethodsDescription : Screen("DepositMethodsDescription")

        /**
         * 入金方法を選択(2024/01)
         */
        @Parcelize
        public data object FirstDepositMethodSelection : Screen("FirstDepositMethodSelection")

        /**
         * 入金方法一覧画面
         */
        @Parcelize
        public data object DepositSelect : Screen("ChargeMethodFragment")

        /**
         * 入金用銀行削除ダイアログ
         */
        @Parcelize
        public data object DepositBankDeleteDialog : Screen("DepositBankDeleteDialog")

        /**
         * 表示名変更画面
         */
        @Parcelize
        public data object ChangeNickname : Screen("ChangeNickname")

        /**
         * 自動入金設定画面
         */
        @Parcelize
        public data object AutoDepositSetting : Screen("AutoDepositSetting")

        /**
         * 自動入金設定 内容破棄ダイアログ
         */
        @Parcelize
        public data object AutoDepositDiscardDialog : Screen("AutoDepositDiscardDialog")

        /**
         * 自動入金設定 残高なし入金ダイアログ
         */
        @Parcelize
        public data object AutoDepositNoBalanceDialog : Screen("AutoDepositNoBalanceDialog")

        /**
         * 自動入金設定 残高不足入金ダイアログ
         */
        @Parcelize
        public data object AutoDepositLackingBalanceDialog : Screen("AutoDepositLackingBalanceDialog")

        /**
         * 入金半モーダル (ファンドソースなし)
         */
        @Parcelize
        public data object DepositHalfModalNoBank : Screen("DepositHalfModalNoBank")

        /**
         * 入金半モーダル (ファンドソースあり)
         */
        @Parcelize
        public data object DepositHalfModalRegisteredBank : Screen("DepositHalfModalRegisteredBank")

        /**
         * その他の方法で入金画面
         */
        @Parcelize
        public data object DepositByOtherWay : Screen("DepositByOtherWay")

        /**
         * 入金確認ダイアログ
         */
        @Parcelize
        public data object DepositConfirm : Screen("DepositConfirm")

        /**
         * クレジットカード入金3Dセキュア認証画面
         */
        @Parcelize
        public data object CreditCardDeposit3dSecure : Screen("CreditCardDeposit3dSecure")

        /**
         * 銀行入金完了画面
         */
        @Parcelize
        public data object BankDepositSuccess : Screen("BankDepositSuccess")

        /**
         * カード入金完了画面
         */
        @Parcelize
        public data object CardDepositSuccess : Screen("CardDepositSuccess")

        /**
         * ローソン銀行入金説明画面
         */
        @Parcelize
        public data object DepositLawsonInst : Screen("DepositLawsonInst")

        /**
         * ローソン銀行入金QR読み取り画面
         */
        @Parcelize
        public data object DepositLawsonQr : Screen("DepositLawsonQr")

        /**
         * ローソン銀行入金QR読み取り完了画面
         */
        @Parcelize
        public data object DepositLawsonQrComplete : Screen("DepositLawsonQrComplete")

        /**
         * ウォレット画面（親）
         */
        @Parcelize
        public data object Wallet : Screen("Wallet")

        /**
         * ウォレット画面（子）
         */
        @Parcelize
        public data object WalletPage : Screen("WalletPage")

        /**
         * 共有口座を作成画面
         */
        @Parcelize
        public data object CreateShareWallet : Screen("CreateShareWallet")

        /**
         * 口座情報の設定画面
         */
        @Parcelize
        public data object ShareWalletSetting : Screen("ShareWalletSetting")

        /**
         * 主口座詳細画面
         */
        @Parcelize
        public data object PrimaryWalletDetail : Screen("PrimaryWalletDetail")

        /**
         * 共有口座詳細画面
         */
        @Parcelize
        public data object ShareWalletDetail : Screen("ShareWalletDetail")

        /**
         * 送金・請求タブ
         */
        @Parcelize
        public data object SendRequest : Screen("SendRequest")

        /**
         * ユーザダイアログ
         */
        @Parcelize
        public data object UserDialog : Screen("UserDialog")

        /**
         * 送金作成画面
         */
        @Parcelize
        public data object CreateSend : Screen("CreateSend")

        /**
         * 送金確認画面
         */
        @Parcelize
        public data object ConfirmSend : Screen("ConfirmSend")

        /**
         * 請求作成画面
         */
        @Parcelize
        public data object CreateRequest : Screen("CreateRequest")

        /**
         * 請求確認画面
         */
        @Parcelize
        public data object ConfirmRequest : Screen("ConfirmRequest")

        /**
         * 送金・請求完了画面 送金成功
         */
        @Parcelize
        public data object SendSuccess : Screen("SendSuccess")

        /**
         * 送金・請求完了画面 送金失敗
         */
        @Parcelize
        public data object SendFailure : Screen("SendFailure")

        /**
         * 送金・請求完了画面 請求成功
         */
        @Parcelize
        public data object RequestSuccess : Screen("RequestSuccess")

        /**
         * 送金・請求完了画面 請求失敗
         */
        @Parcelize
        public data object RequestFailure : Screen("RequestFailure")

        /**
         * クーポンタブ
         */
        @Parcelize
        public data object Coupon : Screen("Coupon")

        /**
         * クーポン詳細
         */
        @Parcelize
        public data object CouponDetail : Screen("CouponDetail")

        /**
         * 全クーポン一覧
         */
        @Parcelize
        public data object AllCoupons : Screen("AllCoupons")

        /**
         * 友達招待画面 (通常)
         */
        @Parcelize
        public data object FriendInvitation : Screen("FriendInvitation")

        /**
         * 友達招待画面 (Kyashカード)
         */
        @Parcelize
        public data object KyashCardFriendInvitation : Screen("KyashCardFriendInvitation")

        /**
         * スマホ本人確認審査中ダイアログ
         */
        @Parcelize
        public data object EKycInProgressDialog : Screen("EKycInProgressDialog")

        /**
         * スマホ本人確認情報入力待ちダイアログ
         */
        @Parcelize
        public data object EKycImageUploadedDialog : Screen("EKycImageUploadedDialog")

        /**
         * KyashCard到着待ちダイアログ
         */
        @Parcelize
        public data object WaitingKyashCardArrivalDialog : Screen("WaitingKyashCardArrivalDialog")

        /**
         * 残高ダイアログ
         */
        @Parcelize
        public data object AboutBalanceDialog : Screen("AboutBalanceDialog")

        /**
         * 送金可能残高ダイアログ
         */
        @Parcelize
        public data object AboutSendableBalanceDialog : Screen("AboutSendableBalanceDialog")

        /**
         * 履歴詳細内訳ダイアログ
         */
        @Parcelize
        public data object TimelineAmountBreakdownDialog : Screen("TimelineAmountBreakdownDialog")

        /**
         * Kyashポイント画面
         */
        @Parcelize
        public data object KyashPoint : Screen("KyashPointChargeFragment")

        /**
         * Kyashポイント入金確認半モーダル
         */
        @Parcelize
        public data object KyashPointChargeConfirm : Screen("KyashPointChargeConfirm")

        /**
         * Kyashポイント履歴画面
         */
        @Parcelize
        public data object KyashPointHistory : Screen("PointHistoryFragment")

        /**
         * Kyashポイントプログラム画面
         */
        @Parcelize
        public data object KyashPointInformation : Screen("AboutKyashPointActivity")

        /**
         * 出金金額入力画面
         */
        @Parcelize
        public data object WithdrawAmountInput : Screen("WithdrawalFragment")

        /**
         * 出金確認画面
         */
        @Parcelize
        public data object WithdrawConfirm : Screen("WithdrawalConfirmationFragment")

        /**
         * 残高利息半率モーダル
         */
        @Parcelize
        public data object InterestRateHalfModal : Screen("InterestRateHalfModal")

        /**
         * KyashCard申込時のKYC方法選択画面
         */
        @Parcelize
        public data object CardApplicationKYCMethodSelect : Screen("CardApplicationKYCMethodSelect")

        /**
         * KyashCard再申請時のKYC方法選択画面
         */
        @Parcelize
        public data object CardReapplicationKYCMethodSelect : Screen("CardReapplicationKYCMethodSelect")

        /**
         * カード設定画面 設定タブ
         */
        @Parcelize
        public data object CardSettings : Screen("CardSettings")

        /**
         * カード設定画面 情報タブ
         */
        @Parcelize
        public data object CardInformation : Screen("CardInformation")

        /**
         * push通知許諾訴求画面
         */
        @Parcelize
        public data object NotificationAppealDialog : Screen("NotificationAppealDialog")

        /**
         * ローソン銀行出金説明画面
         */
        @Parcelize
        public data object WithdrawalLawsonInst : Screen("WithdrawalLawsonInst")

        /**
         * ローソン銀行出金QR読み取り画面
         */
        @Parcelize
        public data object WithdrawalLawsonQr : Screen("WithdrawalLawsonQr")

        /**
         * ローソン銀行出金QR読み取り完了画面
         */
        @Parcelize
        public data object WithdrawalLawsonQrComplete : Screen("WithdrawalLawsonQrComplete")

        /**
         * ローソン銀行ATM入金履歴詳細画面
         */
        @Parcelize
        public data object TimelineDetailDepositLawson : Screen("TimelineDetailDepositLawson")

        /**
         * ローソン銀行ATM入金キャンセル履歴詳細画面
         */
        @Parcelize
        public data object TimelineDetailDepositCancelLawson :
            Screen("TimelineDetailDepositCancelLawson")

        /**
         * スマホ本人確認画面(ワ方式かOCRの選択画面)
         */
        @Parcelize
        public data object EKycSelect : Screen("EKycSelect")

        /**
         * 写真撮影で本人確認画面
         */
        @Parcelize
        public data object EKycPicture : Screen("EKycPicture")

        /**
         * 署名電子証明書パスワード画面
         */
        @Parcelize
        public data object EKycInputPass : Screen("EKycInputPass")

        /**
         * タッチで本人確認画面
         */
        @Parcelize
        public data object EKycReadCard : Screen("EKycReadCard")

        /**
         * 本人確認情報画面
         */
        @Parcelize
        public data object EKycIdentification : Screen("EKycIdentification")

        /**
         * 認証中ダイアログ
         */
        @Parcelize
        public data object EKycAuthentication : Screen("EKycAuthentication")

        /**
         * 本人確認完了画面
         */
        @Parcelize
        public data object EKycNfcSuccess : Screen("EKycNfcSuccess")

        /**
         * 本人確認時間がかかってる画面
         */
        @Parcelize
        public data object EKycNfcDelayed : Screen("EKycNfcDelayed")

        /**
         * 本人確認失敗画面
         */
        @Parcelize
        public data object EKycNfcFailed : Screen("EKycNfcFailed")

        /**
         * NFC非対応ダイアログ
         */
        @Parcelize
        public data object EKycNotSupportedNfc : Screen("EKycNotSupportedNfc")

        /**
         * NFCが有効になってないダイアログ
         */
        @Parcelize
        public data object EKycDisabledNfc : Screen("EKycDisabledNfc")

        /**
         * アカウント画面
         */
        @Parcelize
        public data object Account : Screen("Account")

        /**
         * 解約画面
         */
        @Parcelize
        public data object AccountCancellation : Screen("AccountCancellation")

        /**
         * ギフトコード
         */
        @Parcelize
        public data object Redeem : Screen("Redeem")

        /**
         * ギフトコード適用結果
         */
        @Parcelize
        public data object RedeemSuccess : Screen("RedeemSuccess")

        /**
         * キャンペーンに応募する
         */
        @Parcelize
        public data object CampaignCodeInput : Screen("CampaignCodeInput")

        /**
         * キャンペーン応募完了
         */
        @Parcelize
        public data object CampaignCodeCompletion : Screen("CampaignCodeCompletion")

        /**
         * イマすぐ入金申し込みの説明（最初の表示）
         */
        @Parcelize
        public data object BnplApplyInfo : Screen("BnplApplyInfo")

        /**
         * イマすぐ入金の申し込みと支払いの画面（アカウント→イマすぐ入金）
         */
        @Parcelize
        public data object BnplPage : Screen("BnplPage")

        /**
         * イマすぐ入金の初回利用でポイントプレゼントの説明
         */
        @Parcelize
        public data object BnplFirstTimeBonusGuide : Screen("BnplFirstTimeBonusGuide")

        /**
         * イマすぐ入金の申し込み情報（氏名、生年月日、電話番号などの入力）
         */
        @Parcelize
        public data object BnplKyc : Screen("BnplKyc")

        /**
         * イマすぐ入金の申し込みの「メールアドレス認証が必要です」ダイアログ
         */
        @Parcelize
        public data object BnplEmailNotVerifiedDialog : Screen("BnplEmailNotVerifiedDialog")

        /**
         * イマすぐ入金の氏名（漢字）入力
         */
        @Parcelize
        public data object BnplKycNameEdit : Screen("BnplKycNameEdit")

        /**
         * イマすぐ入金の氏名（カナ）入力
         */
        @Parcelize
        public data object BnplKycRubyEdit : Screen("BnplKycRubyEdit")

        /**
         * イマすぐ入金の生年月日入力
         */
        @Parcelize
        public data object BnplKycBirthDayEdit : Screen("BnplKycBirthDayEdit")

        /**
         * イマすぐ入金申し込みの説明（「イマすぐ入金とは」を押したときに表示）
         */
        @Parcelize
        public data object BnplDescription : Screen("BnplDescription")

        /**
         * 利用規約(イマすぐ入金)
         */
        @Parcelize
        public data object BnplTermsKyash : Screen("BnplTermsKyash")

        /**
         * 利用規約(ミライバライ)
         */
        @Parcelize
        public data object BnplTermsMiraibarai : Screen("BnplTermsMiraibarai")

        /**
         * イマすぐ入金の金額選択
         */
        @Parcelize
        public data object BnplSelectAmount : Screen("BnplSelectAmount")

        /**
         * イマすぐ入金の「申し込み可能額が入金額を下回っています」ダイアログ
         */
        @Parcelize
        public data object BnplInvalidateAmount : Screen("BnplInvalidateAmount")

        /**
         * イマすぐ入金申し込みの確認
         */
        @Parcelize
        public data object BnplApplicationConfirm : Screen("BnplApplicationConfirm")

        /**
         * イマすぐ入金申し込みの確認の自動引落し設定の説明ボトムシート
         */
        @Parcelize
        public data object BnplAutoRepaymentGuideModal : Screen("BnplAutoRepaymentGuideModal")

        /**
         * イマすぐ入金申し込みの最終確認シートまたはダイアログ
         */
        @Parcelize
        public data object BnplApplicationConfirmDialog : Screen("BnplApplicationConfirmDialog")

        /**
         * イマすぐ入金成功画面
         */
        @Parcelize
        public data object BnplSuccess : Screen("BnplSuccess")

        /**
         * イマすぐ入金失敗画面
         */
        @Parcelize
        public data object BnplFailure : Screen("BnplFailure")

        /**
         * イマすぐ入金再申請画面
         */
        @Parcelize
        public data object BnplRetry : Screen("BnplRetry")

        /**
         * イマすぐ入金遅延画面
         */
        @Parcelize
        public data object BnplDelay : Screen("BnplDelay")

        /**
         * イマすぐ入金の申し込みを中断するダイアログ（金額選択）
         */
        @Parcelize
        public data object BnplSuspensionDialogAmount : Screen("BnplSuspensionDialogAmount")

        /**
         * イマすぐ入金の申し込みを中断するダイアログ（再申請）
         */
        @Parcelize
        public data object BnplSuspensionDialogRetry : Screen("BnplSuspensionDialogRetry")

        /**
         * イマすぐ入金のウォレットタブでの自動引落しの提案ボトムシート
         */
        @Parcelize
        public data object BnplAutoRepaymentSuggestionModal : Screen("BnplAutoRepaymentSuggestionModal")

        /**
         * イマすぐ入金の口座引落し設定
         */
        @Parcelize
        public data object BnplAutoRepaymentSetting : Screen("BnplAutoRepaymentSetting")

        /**
         * イマすぐ入金の口座引落し設定の結果（成功）
         */
        @Parcelize
        public data object BnplAutoRepaymentSettingSuccess : Screen("BnplAutoRepaymentSettingSuccess")

        /**
         * イマすぐ入金の口座引落し設定の結果（失敗）
         */
        @Parcelize
        public data object BnplAutoRepaymentSettingFailure : Screen("BnplAutoRepaymentSettingFailure")

        /**
         * イマすぐ入金の支払い(一覧)
         */
        @Parcelize
        public data object BnplPendingRepaymentList : Screen("BnplPendingRepaymentList")

        /**
         * イマすぐ入金の支払い内容の確認(「支払い申請」タップ)
         */
        @Parcelize
        public data object BnplRepaymentConfirmation : Screen("BnplRepaymentConfirmation")

        /**
         * イマすぐ入金の支払い方法を選択
         */
        @Parcelize
        public data object BnplSelectRepaymentMethod : Screen("BnplSelectRepaymentMethod")

        /**
         * イマすぐ入金の支払の確認(支払い方法を選択画面で「アプリ残高」を選択)
         */
        @Parcelize
        public data object BnplMoneyRepaymentConfirmation : Screen("BnplMoneyRepaymentConfirmation")

        /**
         * イマすぐ入金の支払の最終確認ダイアログ
         */
        @Parcelize
        public data object BnplMoneyRepaymentConfirmationDialog :
            Screen("BnplMoneyRepaymentConfirmDialog")

        /**
         * イマすぐ入金の支払い完了(result画面、成功)
         */
        @Parcelize
        public data object BnplRepaymentCompletion : Screen("BnplRepaymentCompletion")

        /**
         * 給与口座作成画面
         */
        @Parcelize
        public data object PayrollAccountRegistration : Screen("PayrollAccountRegistration")

        /**
         * 給与口座作成画面のエラーダイアログ
         */
        @Parcelize
        public data object PayrollAccountRegisterErrorDialog :
            Screen("PayrollAccountRegisterErrorDialog")

        /**
         * 代替口座選択画面
         */
        @Parcelize
        public data object EvacuationAccountSelection : Screen("EvacuationAccountSelection")

        /**
         * 代替口座選択画面のエラーダイアログ
         */
        @Parcelize
        public data object EvacucationAccountSelectionErrorDialog :
            Screen("EvacAccountSelectionErrorDialog")

        /**
         * 家計簿 - カテゴリ
         */
        @Parcelize
        public data object MonthlyCategorizationSummary : Screen("MonthlyCategorizationSummary")

        /**
         * 家計簿 - 予算
         */
        @Parcelize
        public data object MonthlyBudgetSummary : Screen("MonthlyBudgetSummary")

        /**
         * 予算設定
         */
        @Parcelize
        public data object BudgetSetting : Screen("BudgetSetting")

        /**
         * カテゴリ予算設定
         */
        @Parcelize
        public data object CategoryBudgetSetting : Screen("CategoryBudgetSetting")

        /**
         * 予算設定完了
         */
        @Parcelize
        public data object BudgetSettingCompletion : Screen("BudgetSettingCompletion")

        /**
         * カテゴリ選択画面
         */
        @Parcelize
        public data object CategorySelection : Screen("CategorySelection")

        /**
         * カスタムカテゴリ作成/編集画面
         */
        @Parcelize
        public data object CustomCategory : Screen("CustomCategory")

        /**
         * カスタムカテゴリアイコン選択画面
         */
        @Parcelize
        public data object CustomCategoryIcons : Screen("CustomCategoryIcons")

        /**
         * カテゴリ履歴
         */
        @Parcelize
        public data object TimelineCategorization : Screen("TimelineCategorization")

        /**
         * リワードトップが表示された
         */
        @Parcelize
        public data object RewardTop : Screen("RewardTop")

        /**
         * デイリールーレット
         */
        @Parcelize
        public data object RewardDailyRouletteReady : Screen("RewardDailyRouletteReady")

        /**
         * デイリールーレット結果
         */
        @Parcelize
        public data object RewardDailyRouletteCompletion : Screen("RewardDailyRouletteCompletion")

        /**
         * チャレンジ応募詳細画面(Weekly)
         */
        @Parcelize
        public data object CoinPrizeDetailWeekly : Screen("CoinPrizeDetailWeekly")

        /**
         * チャレンジ応募確認画面(Weekly)
         */
        @Parcelize
        public data object CoinPrizeApplicationConfirmWeekly :
            Screen("CoinPrizeApplicationConfirmWeekly")

        /**
         * チャレンジ応募完了画面(Weekly)
         */
        @Parcelize
        public data object CoinPrizeResultPendingWeekly : Screen("CoinPrizeResultPendingWeekly")

        /**
         * チャレンジ結果画面(Weekly)(WON)
         */
        @Parcelize
        public data object CoinPrizeResultWeeklyWon : Screen("CoinPrizeResultWeeklyWon")

        /**
         * チャレンジ結果画面(Weekly)(LOST)
         */
        @Parcelize
        public data object CoinPrizeResultWeeklyLost : Screen("CoinPrizeResultWeeklyLost")

        /**
         * チャレンジ応募詳細画面(Daily)
         */
        @Parcelize
        public data object CoinPrizeDetailDaily : Screen("CoinPrizeDetailDaily")

        /**
         * チャレンジ応募確認画面(Daily)
         */
        @Parcelize
        public data object CoinPrizeApplicationConfirmDaily : Screen("CoinPrizeApplicationConfirmDaily")

        /**
         * チャレンジ結果画面(Daily)(WON)
         */
        @Parcelize
        public data object CoinPrizeResultDailyWon : Screen("CoinPrizeResultDailyWon")

        /**
         * チャレンジ結果画面(Daily)(LOST)
         */
        @Parcelize
        public data object CoinPrizeResultDailyLost : Screen("CoinPrizeResultDailyLost")

        /**
         * チャレンジ応募詳細画面(Welcome)
         */
        @Parcelize
        public data object CoinPrizeDetailWelcome : Screen("CoinPrizeDetailWelcome")

        /**
         * チャレンジ応募確認画面(Welcome)
         */
        @Parcelize
        public data object CoinPrizeApplicationConfirmWelcome : Screen("CoinPrizeApplicationConfirmWel")

        /**
         * チャレンジ結果画面(Welcome)(WON)
         */
        @Parcelize
        public data object CoinPrizeResultWelcomeWon : Screen("CoinPrizeResultWelcomeWon")

        /**
         * チャレンジ結果画面(Welcome)(LOST)
         */
        @Parcelize
        public data object CoinPrizeResultWelcomeLost : Screen("CoinPrizeResultWelcomeLost")

        /**
         * チャレンジ応募履歴
         */
        @Parcelize
        public data object CoinPrizeHistory : Screen("CoinPrizeHistory")

        /**
         * コイン詳細画面
         */
        @Parcelize
        public data object CoinDetail : Screen("CoinDetail")

        /**
         * リワードスタンプカード
         */
        @Parcelize
        public data object RewardStampCard : Screen("RewardStampCard")

        /**
         * ウェルカムチャレンジ応募
         */
        @Parcelize
        public data object WelcomeChallengeApplication : Screen("WelcomeChallengeApplication")

        /**
         * ウェルカムチャレンジ応募完了
         */
        @Parcelize
        public data object WelcomeChallengeResult : Screen("WelcomeChallengeResult")

        /**
         * オファーウォール
         */
        @Parcelize
        public data object OfferWall : Screen("OfferWall")

        /**
         * オファーウォール個人関連情報提供同意画面
         */
        @Parcelize
        public data object OfferWallAgreement : Screen("OfferWallAgreement")

        /**
         * オファーウォールメンテナンス画面
         */
        @Parcelize
        public data object OfferWallMaintenance : Screen("OfferWallMaintenance")

        /**
         * パスコードガイド画面
         */
        @Parcelize
        public data object PasscodeGuide : Screen("PasscodeGuide")

        /**
         * パスコード用ユーザー情報の確認画面
         */
        @Parcelize
        public data object PasscodeAuthentication : Screen("PasscodeAuthentication")

        /**
         * パスコード認証画面
         */
        @Parcelize
        public data object PasscodeVerification : Screen("PasscodeVerification")

        /**
         * パスコード登録/更新画面
         */
        @Parcelize
        public data object PasscodeRegistration : Screen("PasscodeRegistration")
    }

    public sealed class Action(
        public val eventName: String,
        public val parameters: Map<String, String>,
    ) : Parcelable {

        /**
         * リワードスタンプカード
         */
        public sealed class RewardStamp(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * スタンプカード ヘルプタップ
             */
            @Parcelize
            public data object ClickedHelp : RewardStamp("reward_stamp_click_help", emptyMap())
        }

        /**
         * コイン履歴画面
         */
        public sealed class RewardCoinDetail(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 情報アイコン タップ
             */
            @Parcelize
            public data object ClickHelp : RewardCoinDetail("reward_coin_detail_click_help", emptyMap())

            /**
             * 懸賞応募履歴 タップ
             */
            @Parcelize
            public data object ClickRewardHistory : RewardCoinDetail("reward_coin_detail_click_rwd_hist",
                emptyMap())
        }

        /**
         * チャレンジ応募履歴画面
         */
        public sealed class RewardHistory(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * タブ タップ
             */
            @Parcelize
            public data class ClickedTab(
                public val prizeType: String,
            ) : RewardHistory("reward_history_click_tab", mapOf(
                "type" to prizeType,
            )
            ) {
                public object PrizeType {
                    public const val weekly: String = "weekly"

                    public const val daily: String = "daily"

                    public const val welcome: String = "welcome"
                }
            }

            /**
             * 応募した項目 タップ
             */
            @Parcelize
            public data class ClickedReward(
                public val prizeId: String,
                public val prizeType: String,
            ) : RewardHistory("reward_history_click_reward", mapOf(
                "id" to prizeId,
                "type" to prizeType,
            )
            ) {
                public object PrizeType {
                    public const val weekly: String = "weekly"

                    public const val daily: String = "daily"

                    public const val welcome: String = "welcome"
                }
            }
        }
        /**
         * デイリールーレット画面
         */
        public sealed class RewardDailyRoulette(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * ルーレット開始
             */
            @Parcelize
            public data object ClickStart : RewardDailyRoulette("reward_daily_roulette_start", emptyMap())
        }
        /**
         * 初期化処理
         */
        public sealed class Initialization(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * RemoteConfig を取得した
             */
            @Parcelize
            public data object FetchedRemoteConfig : Initialization(
                "init_fetched_remote_config",
                emptyMap()
            )
        }

        /**
         * Welcome
         */
        public sealed class Welcome(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * ウォークスルー
             */
            @Parcelize
            public data class SelectedWalkThrough(
                public val page: Long,
            ) : Welcome(
                "welcome_selected_walkthrough", mapOf(
                    "page" to page.toString(),
                )
            )

            /**
             * Facebook で続ける タップ
             */
            @Parcelize
            public data object ClickedFacebookSignIn : Welcome(
                "welcome_clicked_facebook_sign_in",
                emptyMap()
            )

            /**
             * ログイン タップ
             */
            @Parcelize
            public data object ClickedEmailSignIn :
                Welcome("welcome_clicked_email_sign_in", emptyMap())

            /**
             * ナビゲーションクローズ タップ
             */
            @Parcelize
            public data object ClickedCloseSignIn :
                Welcome("welcome_clicked_close_sign_in", emptyMap())

            /**
             * Google で続ける タップ
             */
            @Parcelize
            public data object ClickedSiwgSignIn :
                Welcome("welcome_clicked_siwg_sign_in", emptyMap())

            /**
             * Apple で続ける タップ
             */
            @Parcelize
            public data object ClickedSiwaSignIn :
                Welcome("welcome_clicked_siwa_sign_in", emptyMap())
        }

        /**
         * 会員登録画面
         */
        public sealed class UserInformation(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 完了
             */
            @Parcelize
            public data object ClickedNext : UserInformation("user_info_clicked_next", emptyMap())
        }

        /**
         * 会員登録確認画面
         */
        public sealed class UserInformationConfirm(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * OK
             */
            @Parcelize
            public data class ClickedOk(
                public val from: String,
            ) : UserInformationConfirm(
                "user_info_confirm_clicked_ok", mapOf(
                    "from" to from,
                )
            ) {
                public object From {
                    public const val facebook: String = "facebook"

                    public const val siwg: String = "siwg"
                }
            }

            /**
             * キャンセル
             */
            @Parcelize
            public data class ClickedCancel(
                public val from: String,
            ) : UserInformationConfirm(
                "user_info_confirm_clicked_cancel", mapOf(
                    "from" to from,
                )
            ) {
                public object From {
                    public const val facebook: String = "facebook"

                    public const val siwg: String = "siwg"
                }
            }
        }

        /**
         * SMSコード入力
         */
        public sealed class SmsCode(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * サインイン完了
             */
            @Parcelize
            public data object SignedUp : SmsCode("sms_code_signed_up", emptyMap())

            /**
             * 入力完了
             */
            @Parcelize
            public data object ClickedNext : SmsCode("sms_code_clicked_next", emptyMap())
        }

        /**
         * deep linkハンドリング
         */
        public sealed class DeepLink(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * リファラー付きのdeep link起動
             */
            @Parcelize
            public data class Launch(
                public val refererId: String,
            ) : DeepLink(
                "deep_link_launch", mapOf(
                    "referer_id" to refererId,
                )
            )
        }

        /**
         * オンボーディング時の入金方法選択画面
         */
        public sealed class SelectFundSourceOnboarding(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * スキップ タップ
             */
            @Parcelize
            public data object ClickedSkip : SelectFundSourceOnboarding(
                "select_fund_source_clicked_skip",
                emptyMap()
            )

            /**
             * 選抜6行 タップ
             */
            @Parcelize
            public data class ClickedBank(
                public val bankCode: String,
            ) : SelectFundSourceOnboarding(
                "select_fund_source_clicked_bank", mapOf(
                    "bank_code" to bankCode,
                )
            )

            /**
             * その他の銀行口座から入金 タップ
             */
            @Parcelize
            public data object ClickedOtherBanks :
                SelectFundSourceOnboarding("select_fund_source_clicked_banks", emptyMap())

            /**
             * 登録不要で今すぐ入金 タップ
             */
            @Parcelize
            public data object ClickedDepositWithoutRegistration :
                SelectFundSourceOnboarding("select_fund_source_clicked_deposit", emptyMap())

            /**
             * クレジットカードを登録 タップ
             */
            @Parcelize
            public data object ClickedRegisterCard :
                SelectFundSourceOnboarding("select_fund_source_clicked_card", emptyMap())

            /**
             * ポイント還元率の比較表 表示
             */
            @Parcelize
            public data object AppearedComparisonTable :
                SelectFundSourceOnboarding("select_fund_source_appeared_comp", emptyMap())

            /**
             * KyashPointの注釈リンク タップ
             */
            @Parcelize
            public data object ClickedLink1 :
                SelectFundSourceOnboarding("select_fund_source_clicked_link_1", emptyMap())

            /**
             * 残高の種類の注釈リンク タップ
             */
            @Parcelize
            public data object ClickedLink2 :
                SelectFundSourceOnboarding("select_fund_source_clicked_link_2", emptyMap())
        }

        /**
         * オンボーディングカード選択画面
         */
        public sealed class OnboardingCardSelection(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * Kyash Card Lite発行申し込みボタン
             */
            @Parcelize
            public data object ClickedLiteApplication :
                OnboardingCardSelection("onboarding_card_clicked_lite", emptyMap())

            /**
             * すでにお持ちの方
             */
            @Parcelize
            public data object ClickedLiteActivation :
                OnboardingCardSelection("onboarding_card_clicked_lite_act", emptyMap())

            /**
             * Kyash Card Virtual発行ボタン
             */
            @Parcelize
            public data object ClickedVirtualIssuance :
                OnboardingCardSelection("onboarding_card_clicked_virtual", emptyMap())
        }

        /**
         * ウォレットタブ
         */
        public sealed class Wallet(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 出金 タップ
             */
            @Parcelize
            public data object ClickedWithdraw : Wallet("wallet_clicked_withdraw", emptyMap())

            /**
             * 入金方法 タップ
             */
            @Parcelize
            public data object ClickedDepositMethod :
                Wallet("wallet_clicked_fundsource", emptyMap())

            /**
             * 入金 タップ
             */
            @Parcelize
            public data class ClickedDeposit(
                public val isBankRegistered: Boolean,
                public val enableCardAutoCharge: Boolean,
            ) : Wallet(
                "wallet_clicked_deposit", mapOf(
                    "is_bank_registered" to isBankRegistered.toString(),
                    "enable_card_auto_charge" to enableCardAutoCharge.toString(),
                )
            )

            /**
             * 銀行口座を登録 タップ
             */
            @Parcelize
            public data object ClickedRegisterBankAccount :
                Wallet("wallet_clicked_add_bank", emptyMap())

            /**
             * カードを登録 タップ
             */
            @Parcelize
            public data object ClickedRegisterCard :
                Wallet("wallet_clicked_register_card", emptyMap())

            /**
             * セブン銀行 タップ
             */
            @Parcelize
            public data object ClickedDepositSevenBank : Wallet(
                "wallet_clicked_deposit_seven",
                emptyMap()
            )

            /**
             * ローソン銀行 タップ
             */
            @Parcelize
            public data object ClickedDepositLawsonBank : Wallet(
                "wallet_clicked_deposit_lawson",
                emptyMap()
            )

            /**
             * コンビニ タップ
             */
            @Parcelize
            public data object ClickedDepositCVS : Wallet("wallet_clicked_deposit_cvs", emptyMap())

            /**
             * ペイジー タップ
             */
            @Parcelize
            public data object ClickedDepositPayEasy : Wallet(
                "wallet_clicked_deposit_payeasy",
                emptyMap()
            )

            /**
             * その他の方法で入金 タップ
             */
            @Parcelize
            public data object ClickedChargeOption : Wallet("wallet_click_opt", emptyMap())

            /**
             * 共有口座を追加 タップ
             */
            @Parcelize
            public data object ClickedAddShareWallet : Wallet(
                "wallet_clicked_add_share_wallet",
                emptyMap()
            )

            /**
             * イマすぐ入金 タップ
             */
            @Parcelize
            public data object ClickedDepositBnpl :
                Wallet("wallet_clicked_deposit_bnpl", emptyMap())

            /**
             * 口座情報エリア タップ
             */
            @Parcelize
            public data class ClickedWalletInfo(
                public val isPrimary: Boolean,
            ) : Wallet(
                "wallet_clicked_wallet_info", mapOf(
                    "is_primary" to isPrimary.toString(),
                )
            )

            /**
             * ApplePayを設定 タップ
             */
            @Parcelize
            public data object ClickedApplePay : Wallet("wallet_clicked_apple_pay", emptyMap())

            /**
             * カード タップ
             */
            @Parcelize
            public data object ClickedCard : Wallet("wallet_clicked_card", emptyMap())

            /**
             * カード申込（Kyash Card申し込み) タップ
             */
            @Parcelize
            public data object ClickedCardSelection :
                Wallet("wallet_clicked_card_selection", emptyMap())

            /**
             * カード申込（配送ステータス) タップ
             */
            @Parcelize
            public data object ClickedIssuanceStatus : Wallet(
                "wallet_clicked_issuance_status",
                emptyMap()
            )

            /**
             * 履歴セル タップ
             */
            @Parcelize
            public data object ClickedTimeline : Wallet("wallet_clicked_timeline", emptyMap())

            /**
             * 一覧を見る タップ
             */
            @Parcelize
            public data object ClickedSeeMoreTimeline : Wallet(
                "wallet_clicked_see_more_timeline",
                emptyMap()
            )

            /**
             * やることリスト タップ
             */
            @Parcelize
            public data object ClickedTodo : Wallet("wallet_clicked_todo", emptyMap())

            /**
             * 新規入会特典 タップ
             */
            @Parcelize
            public data class ClickedIncentive(
                public val title: String,
            ) : Wallet(
                "wallet_clicked_incentive", mapOf(
                    "title" to title,
                )
            )

            /**
             * カードのお届け準備中
             */
            @Parcelize
            public data object ClickedCardShipping : Wallet("wallet_clicked_shipping", emptyMap())

            /**
             * 「給与が受け取れるようになりました」バナータップ
             */
            @Parcelize
            public data object ClickedNotificationBannerPayroll :
                Wallet("wallet_click_notice_banner_payroll", emptyMap())

            /**
             * 有効化のやることリスト タップ
             */
            @Parcelize
            public data object ClickedKyashCardActivation : Wallet(
                "wallet_clicked_kyash_card_act",
                emptyMap()
            )

            /**
             * ショートカットの銀行口座振込タップ
             */
            @Parcelize
            public data object ClickShortcutOtherOwned : Wallet(
                "wallet_click_shortcut_other_owned",
                emptyMap()
            )

            /**
             * ショートカットのイマすぐ入金タップ
             */
            @Parcelize
            public data object ClickShortcutBnpl : Wallet("wallet_click_shortcut_bnpl", emptyMap())

            /**
             * ショートカットの自動入金設定タップ
             */
            @Parcelize
            public data object ClickShortcutAutoDeposit : Wallet(
                "wallet_click_shortcut_auto_deposit",
                emptyMap()
            )

            /**
             * ショートカットのKyashポイントパークタップ
             */
            @Parcelize
            public data object ClickShortcutPointPark : Wallet(
                "wallet_click_shortcut_point_park",
                emptyMap()
            )
        }

        /**
         * 履歴
         */
        public sealed class Timeline(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 請求を作る タップ
             */
            @Parcelize
            public data object ClickedRequest : Timeline("timeline_clicked_request", emptyMap())

            /**
             * ポイント note タップ
             */
            @Parcelize
            public data class ClickedDetail(
                public val url: String,
            ) : Timeline(
                "timeline_clicked_detail", mapOf(
                    "url" to url,
                )
            )
        }

        /**
         * クーポンタブ
         */
        public sealed class Coupon(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * ヘッダクーポン タップ
             */
            @Parcelize
            public data class ClickedHeaderPickup(
                public val couponId: String,
            ) : Coupon(
                "coupon_click_header", mapOf(
                    "coupon_id" to couponId,
                )
            )

            /**
             * ヘッダクーポン スワイプ
             */
            @Parcelize
            public data class SwipeHeaderPickup(
                public val couponId: String,
            ) : Coupon(
                "coupon_swipe_header", mapOf(
                    "coupon_id" to couponId,
                )
            )

            /**
             * 新着クーポン タップ
             */
            @Parcelize
            public data class ClickedNewArrival(
                public val couponId: String,
            ) : Coupon(
                "coupon_click_new_arrival", mapOf(
                    "coupon_id" to couponId,
                )
            )

            /**
             * おすすめクーポン タップ
             */
            @Parcelize
            public data class ClickedRecommended(
                public val couponId: String,
            ) : Coupon(
                "coupon_click_recommend", mapOf(
                    "coupon_id" to couponId,
                )
            )

            /**
             * リワード対象のお店クーポン タップ
             */
            @Parcelize
            public data class ClickedOther(
                public val couponId: String,
            ) : Coupon(
                "coupon_click_other", mapOf(
                    "coupon_id" to couponId,
                )
            )

            /**
             * リワード対象のお店 すべてを表示 タップ
             */
            @Parcelize
            public data object ClickedSeeAll : Coupon("coupon_click_see_all", emptyMap())
        }

        /**
         * リワードタブ
         */
        public sealed class RewardTop(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * ウィークリーチャレンジ 横スクロール
             */
            @Parcelize
            public data object SwipeWeeklyChallenge : RewardTop(
                "reward_top_swipe_weekly_challenge",
                emptyMap()
            )

            /**
             * チャレンジアイテム タップ
             */
            @Parcelize
            public data class ClickChallenge(
                public val prizeId: String,
                public val prizeType: String,
            ) : RewardTop(
                "reward_top_click_challenge", mapOf(
                    "id" to prizeId,
                    "type" to prizeType,
                )
            ) {
                public object PrizeType {
                    public const val weekly: String = "weekly"

                    public const val daily: String = "daily"

                    public const val upcoming: String = "upcoming"
                }
            }

            /**
             * デイリールーレット タップ
             */
            @Parcelize
            public data object ClickRoulette : RewardTop("reward_top_click_roulette", emptyMap())

            /**
             * リワードの楽しみ方 タップ
             */
            @Parcelize
            public data object ClickAboutReward :
                RewardTop("reward_top_click_about_reward", emptyMap())

            /**
             * コイン詳細 タップ
             */
            @Parcelize
            public data object ClickCoinDetail :
                RewardTop("reward_top_click_coin_detail", emptyMap())

            /**
             * オファーウォールバナー タップ
             */
            @Parcelize
            public data object ClickOfferWall : RewardTop("reward_top_click_offerwall", emptyMap())
        }

        /**
         * 送金・請求タブ
         */
        public sealed class SendRequest(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 本人確認推奨導線 タップ
             */
            @Parcelize
            public data object ClickedKycSuggestion : SendRequest(
                "send_request_clicked_kyc_suggest",
                emptyMap()
            )

            /**
             * 銀行登録導線 タップ
             */
            @Parcelize
            public data class ClickedBankRegistration(
                public val isKycUser: Boolean,
            ) : SendRequest(
                "send_request_clicked_bank_reg", mapOf(
                    "is_kyc" to isKycUser.toString(),
                )
            )

            /**
             * リンクを作成 タップ
             */
            @Parcelize
            public data object ClickedCreateSendLink : SendRequest(
                "send_request_clicked_link",
                emptyMap()
            )

            /**
             * みんなに請求 タップ
             */
            @Parcelize
            public data object ClickedMultiRequest : SendRequest(
                "send_request_clicked_multi_request",
                emptyMap()
            )

            /**
             * QRコード タップ
             */
            @Parcelize
            public data object ClickedQr : SendRequest("send_request_clicked_qr", emptyMap())

            /**
             * Facebookの友達を追加 タップ
             */
            @Parcelize
            public data object ClickedFacebook :
                SendRequest("send_request_clicked_facebook", emptyMap())

            /**
             * ユーザー タップ
             */
            @Parcelize
            public data class ClickedUser(
                public val publicId: Long,
            ) : SendRequest(
                "send_request_clicked_user", mapOf(
                    "public_id" to publicId.toString(),
                )
            )

            /**
             * 検索窓 タップ
             */
            @Parcelize
            public data object ClickedSearch :
                SendRequest("send_request_clicked_search", emptyMap())
        }

        /**
         * アカウントタブ
         */
        public sealed class Account(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * Kyash Card 有効化 タップ
             */
            @Parcelize
            public data object ClickedKyashCardActivation : Account(
                "account_clicked_kyash_card_act",
                emptyMap()
            )

            /**
             * 本人確認 未完了 タップ
             */
            @Parcelize
            public data object ClickedKycStatus : Account("account_clicked_kyc_status", emptyMap())

            /**
             * 友達を招待する タップ
             */
            @Parcelize
            public data object ClickedFriendInvitation :
                Account("account_clicked_invitation", emptyMap())

            /**
             * シェア用サービスURL タップ
             */
            @Parcelize
            public data object ClickedInvitationUrl : Account(
                "account_clicked_invitation_url",
                emptyMap()
            )

            /**
             * リンクをシェア タップ
             */
            @Parcelize
            public data object ClickedInvitationCopy : Account(
                "account_clicked_invitation_copy",
                emptyMap()
            )

            /**
             * イマすぐ入金 タップ
             */
            @Parcelize
            public data object ClickedBnpl : Account("account_clicked_bnpl", emptyMap())

            /**
             * Kyash採用情報 タップ
             */
            @Parcelize
            public data object ClickedRecruit : Account("account_clicked_recruit", emptyMap())

            /**
             * 給与口座を作成 タップ
             */
            @Parcelize
            public data object ClickedCreatePayrollAccount : Account(
                "account_click_make_payroll_account",
                emptyMap()
            )

            /**
             * アカウントを解約する タップ
             */
            @Parcelize
            public data object ClickedCloseAccount :
                Account("account_clicked_close_account", emptyMap())

            /**
             * お問い合わせ タップ
             */
            @Parcelize
            public data object ClickedInquiry : Account("account_clicked_inquiry", emptyMap())

            /**
             * Kyash Cardを申し込む タップ
             */
            @Parcelize
            public data object ClickedCardSelection : Account(
                "account_clicked_card_selection",
                emptyMap()
            )
        }

        /**
         * 問い合わせ画面
         */
        public sealed class Inquiry(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 表示
             */
            @Parcelize
            public data class Shown(
                public val from: String,
            ) : Inquiry(
                "inquiry_shown", mapOf(
                    "from" to from,
                )
            ) {
                public object From {
                    public const val others: String = "others"
                }
            }
        }

        /**
         * アカウント解約 注意事項
         */
        public sealed class AccountCloseCaution(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 同意する タップ
             */
            @Parcelize
            public data object Checked :
                AccountCloseCaution("account_close_caution_checked", emptyMap())

            /**
             * 解約に進む タップ
             */
            @Parcelize
            public data object ClickNext : AccountCloseCaution(
                "account_close_caution_click_next",
                emptyMap()
            )
        }

        /**
         * アカウント解約
         */
        public sealed class AccountClose(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 銀行口座に出金する タップ
             */
            @Parcelize
            public data object ClickedWithdraw : AccountClose(
                "account_close_clicked_withdraw",
                emptyMap()
            )

            /**
             * 友達に送る タップ
             */
            @Parcelize
            public data object ClickedSend : AccountClose("account_close_click_send", emptyMap())

            /**
             * 残高放棄に同意する タップ
             */
            @Parcelize
            public data object Checked : AccountClose("account_close_checked", emptyMap())

            /**
             * アカウントの解約に進む タップ
             */
            @Parcelize
            public data object ClickedNext : AccountClose("account_close_clicked_next", emptyMap())
        }

        /**
         * アカウント解約理由
         */
        public sealed class AccountCloseReason(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 解約する タップ
             */
            @Parcelize
            public data object ClickedNext : AccountCloseReason(
                "account_close_reason_clicked_next",
                emptyMap()
            )
        }

        /**
         * アカウント解約確認ダイアログ
         */
        public sealed class AccountCloseConfirm(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * はい タップ
             */
            @Parcelize
            public data object ClickedOk : AccountCloseConfirm(
                "account_close_confirm_clicked_ok",
                emptyMap()
            )
        }

        /**
         * Kyashからのお知らせ詳細画面
         */
        public sealed class NotificationDetailFromKyash(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 下部のdeep linkボタン タップ
             */
            @Parcelize
            public data object ClickedDeepLinkButton :
                NotificationDetailFromKyash("kyash_noti_detail_clicked_button", emptyMap())
        }

        /**
         * 支払い待ち請求一覧画面
         */
        public sealed class RequestedPayments(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 支払う タップ
             */
            @Parcelize
            public data object ClickedPay : RequestedPayments(
                "requested_payments_clicked_pay",
                emptyMap()
            )

            /**
             * 削除 タップ
             */
            @Parcelize
            public data object ClickedDelete : RequestedPayments(
                "requested_payments_clicked_delete",
                emptyMap()
            )
        }

        /**
         * 銀行口座登録完了画面 (銀行口座登録目的)
         */
        public sealed class BankRegistrationSucceeded(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 完了 タップ
             */
            @Parcelize
            public data object ClickedDone : BankRegistrationSucceeded(
                "bank_success_clicked_done",
                emptyMap()
            )

            /**
             * 入金 タップ
             */
            @Parcelize
            public data object ClickedDeposit : BankRegistrationSucceeded(
                "bank_success_clicked_deposit",
                emptyMap()
            )

            /**
             * 自動入金設定 タップ
             */
            @Parcelize
            public data object ClickedAutoDepositConfig :
                BankRegistrationSucceeded("bank_success_clicked_auto_deposit", emptyMap())
        }

        /**
         * 銀行口座登録失敗画面 (銀行口座登録目的)
         */
        public sealed class BankRegistrationFailed(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * OK タップ
             */
            @Parcelize
            public data object ClickedOk :
                BankRegistrationFailed("bank_failure_clicked_ok", emptyMap())
        }

        /**
         * 本人確認方法選択画面
         */
        public sealed class SelectKycMethod(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 銀行口座を登録 タップ
             */
            @Parcelize
            public data object ClickedBankRegistration : SelectKycMethod(
                "select_kyc_clicked_bank",
                emptyMap()
            )

            /**
             * スマホ本人確認 タップ
             */
            @Parcelize
            public data object ClickedEKycRegistration : SelectKycMethod(
                "select_kyc_clicked_e_kyc",
                emptyMap()
            )
        }

        /**
         * スマホ本人確認入力フォーム画面
         */
        public sealed class EKycApplication(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 申し込む タップ
             */
            @Parcelize
            public data object ClickedProceed : EKycApplication(
                "e_kyc_application_clicked_proceed",
                emptyMap()
            )

            /**
             * xボタン タップ
             */
            @Parcelize
            public data object ClickedBack :
                EKycApplication("e_kyc_application_clicked_back", emptyMap())
        }

        /**
         * スマホ本人確認入力確認ダイアログ
         */
        public sealed class EkycApplicationConfirm(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * はい タップ
             */
            @Parcelize
            public data object ClickedYes : EkycApplicationConfirm(
                "e_kyc_application_confirm_tap_yes",
                emptyMap()
            )
        }

        /**
         * スマホ本人確認申込完了画面
         */
        public sealed class EKycApplicationCompletion(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * OK タップ
             */
            @Parcelize
            public data object ClickedOk : EKycApplicationCompletion(
                "e_kyc_complete_clicked_ok",
                emptyMap()
            )
        }

        /**
         * スマホ本人確認否認画面
         */
        public sealed class EKycDenial(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 再申請 タップ
             */
            @Parcelize
            public data object ClickedReapplication : EKycDenial(
                "e_kyc_denial_clicked_reapplication",
                emptyMap()
            )

            /**
             * 銀行口座を登録 タップ
             */
            @Parcelize
            public data object ClickedBankRegistration : EKycDenial(
                "e_kyc_denial_clicked_bank",
                emptyMap()
            )
        }

        /**
         * ユーザーダイアログ
         */
        public sealed class UserDialog(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 送金 タップ
             */
            @Parcelize
            public data object ClickedSend : UserDialog("user_dialog_clicked_send", emptyMap())

            /**
             * 請求 タップ
             */
            @Parcelize
            public data object ClickedRequest :
                UserDialog("user_dialog_clicked_request", emptyMap())
        }

        /**
         * Kyashカード友達招待
         */
        public sealed class KyashCardFriendInvitation(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 注意事項 タップ
             */
            @Parcelize
            public data object ClickedHelp : KyashCardFriendInvitation(
                "card_invitation_clicked_help",
                emptyMap()
            )

            /**
             * リンクシェア タップ
             */
            @Parcelize
            public data object ClickedShare : KyashCardFriendInvitation(
                "card_invitation_clicked_share",
                emptyMap()
            )
        }

        /**
         * 残高利息率半モーダル
         */
        public sealed class InterestRateHalfModal(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * FAQリンク タップ
             */
            @Parcelize
            public data object ClickedFaq : InterestRateHalfModal("interest_rate_half_modal_click_faq",
                emptyMap())
        }

        /**
         * Kyashポイント画面
         */
        public sealed class KyashPointCharge(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 画面表示
             */
            @Parcelize
            public data object Shown : KyashPointCharge("point_shown", emptyMap())

            /**
             * iボタン タップ
             */
            @Parcelize
            public data object ClickedInformation : KyashPointCharge("point_clicked_about_point_icon",
                emptyMap())

            /**
             * Kyashポイントとは？ タップ
             */
            @Parcelize
            public data object ClickedAbout : KyashPointCharge("point_clicked_about_point", emptyMap())

            /**
             * ポイント履歴 タップ
             */
            @Parcelize
            public data object ClickedPointHistory : KyashPointCharge("point_clicked_point_history",
                emptyMap())

            /**
             * 全てのポイントを入力 タップ
             */
            @Parcelize
            public data object ClickedAllPoint : KyashPointCharge("point_clicked_point_all", emptyMap())

            /**
             * ポイント入力欄 タップ
             */
            @Parcelize
            public data object ClickedPointInput : KyashPointCharge("point_clicked_point_input",
                emptyMap())

            /**
             * 入金 タップ
             */
            @Parcelize
            public data object ClickedDeposit : KyashPointCharge("point_clicked_point_charge", emptyMap())
        }

        /**
         * ポイントチャージ シート
         */
        public sealed class PointCharge(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 画面表示
             */
            @Parcelize
            public data object Shown : PointCharge("point_charge_shown", emptyMap())

            /**
             * チャージ タップ
             */
            @Parcelize
            public data object ClickedPointCharge : PointCharge("point_charge_clicked_point_charge",
                emptyMap())
        }

        /**
         * ポイント履歴
         */
        public sealed class PointHistory(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 画面表示
             */
            @Parcelize
            public data object Shown : PointHistory("point_history_shown", emptyMap())

            /**
             * オファーウォールバナー タップ
             */
            @Parcelize
            public data object ClickOfferWall : PointHistory("point_history_click_offerwall", emptyMap())
        }

        /**
         * ポイント説明
         */
        public sealed class PointAbout(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 画面表示
             */
            @Parcelize
            public data object Shown : PointAbout("point_about_shown", emptyMap())

            /**
             * 詳しくはこちら タップ
             */
            @Parcelize
            public data object ClickedDetail : PointAbout("point_about_clicked_detail", emptyMap())
        }

        /**
         * 入金用銀行口座登録時の利用規約同意画面 （Kyashマネーアカウント）
         */
        public sealed class KyashMoneyDepositBankAccountRegisterTerms(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 銀行口座を登録 タップ
             */
            @Parcelize
            public data object ClickedRegister :
                KyashMoneyDepositBankAccountRegisterTerms("money_bank_terms_click_regist", emptyMap())
        }

        /**
         * 入金用銀行口座登録時の利用規約同意画面 （Kyashバリューアカウント）
         */
        public sealed class KyashValueDepositBankAccountRegisterTerms(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 利用規約を確認 タップ
             */
            @Parcelize
            public data object ClickedTerms :
                KyashValueDepositBankAccountRegisterTerms("value_bank_terms_click_terms", emptyMap())

            /**
             * 同意して銀行口座を登録 タップ
             */
            @Parcelize
            public data object ClickedAccept :
                KyashValueDepositBankAccountRegisterTerms("value_bank_terms_click_accept", emptyMap())
        }

        /**
         * Kyash残高に入金
         */
        public sealed class Deposit(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 口座 タップ
             */
            @Parcelize
            public data object ClickedBank : Deposit("deposit_clicked_bank", emptyMap())
        }

        /**
         * 入金用銀行口座登録時の銀行選択画面
         */
        public sealed class DepositBankSelect(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 銀行 タップ
             */
            @Parcelize
            public data class ClickedBank(
                public val bankCode: String,
            ) : DepositBankSelect("deposit_bank_select_clicked_bank", mapOf(
                "bank_code" to bankCode,
            )
            )
        }

        /**
         * 入金半モーダル（ファンドソースなし）
         */
        public sealed class DepositHalfModalNoFundSource(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 銀行口座を登録 タップ
             */
            @Parcelize
            public data object ClickedRegisterBankAccount :
                DepositHalfModalNoFundSource("deposit_modal_no_bank_click_regist", emptyMap())

            /**
             * その他の方法で入金 タップ
             */
            @Parcelize
            public data object ClickedChargeOption :
                DepositHalfModalNoFundSource("deposit_modal_no_bank_click_opt", emptyMap())

            /**
             * カード登録 タップ
             */
            @Parcelize
            public data object ClickedRegisterCard :
                DepositHalfModalNoFundSource("deposit_modal_no_bank_click_card", emptyMap())

            /**
             * イマすぐ入金 タップ
             */
            @Parcelize
            public data object ClickedDepositBnpl :
                DepositHalfModalNoFundSource("deposit_modal_no_bank_tap_bnpl", emptyMap())

            /**
             * セブン銀行 タップ
             */
            @Parcelize
            public data object ClickedDepositSevenBank :
                DepositHalfModalNoFundSource("deposit_modal_no_bank_click_seven", emptyMap())

            /**
             * ローソン銀行 タップ
             */
            @Parcelize
            public data object ClickedDepositLawsonBank :
                DepositHalfModalNoFundSource("deposit_modal_no_bank_click_lawson", emptyMap())

            /**
             * コンビニ タップ
             */
            @Parcelize
            public data object ClickedDepositCVS :
                DepositHalfModalNoFundSource("deposit_modal_no_bank_click_cvs", emptyMap())

            /**
             * ペイジー タップ
             */
            @Parcelize
            public data object ClickedDepositPayEasy :
                DepositHalfModalNoFundSource("deposit_modal_no_bank_tap_payeasy", emptyMap())
        }

        /**
         * ローソン銀行入金説明画面
         */
        public sealed class DepositLawsonInst(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 現在の入金可能金額タップ
             */
            @Parcelize
            public data object TapAvailable : DepositLawsonInst("deposit_lawson_ins_tap_available",
                emptyMap())

            /**
             * 周辺のローソン銀行ATMを探すタップ
             */
            @Parcelize
            public data object TapAtm : DepositLawsonInst("deposit_lawson_ins_tap_atm", emptyMap())

            /**
             * QRコードをスキャンタップ
             */
            @Parcelize
            public data object TapQr : DepositLawsonInst("deposit_lawson_ins_tap_qr", emptyMap())

            /**
             * 閉じるタップ
             */
            @Parcelize
            public data object TapClose : DepositLawsonInst("deposit_lawson_ins_tap_close", emptyMap())
        }

        /**
         * ローソン銀行入金QR読み取り画面
         */
        public sealed class DepositLawsonQr(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * QR読み取り完了イベント
             */
            @Parcelize
            public data object Success : DepositLawsonQr("deposit_lawson_qr_success", emptyMap())

            /**
             * QR読み取り失敗イベント
             */
            @Parcelize
            public data object Failure : DepositLawsonQr("deposit_lawson_qr_failure", emptyMap())

            /**
             * 閉じるタップ
             */
            @Parcelize
            public data object TapClose : DepositLawsonQr("deposit_lawson_qr_tap_close", emptyMap())
        }

        /**
         * ローソン銀行入金QR読み取り完了画面
         */
        public sealed class DepositLawsonComplete(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 閉じる（ナビゲーション）タップ
             */
            @Parcelize
            public data object TapCloseNav : DepositLawsonComplete("deposit_lawson_comp_tap_close_nav",
                emptyMap())

            /**
             * 閉じる（最下部）タップ
             */
            @Parcelize
            public data object TapCloseBtm : DepositLawsonComplete("deposit_lawson_comp_tap_close_btm",
                emptyMap())
        }

        /**
         * ローソン銀行出金説明画面
         */
        public sealed class WithdrawalLawsonInst(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 周辺のローソン銀行ATMを探すタップ
             */
            @Parcelize
            public data object TapAtm : WithdrawalLawsonInst("withdrawal_lawson_ins_tap_atm", emptyMap())

            /**
             * QRコードをスキャンタップ
             */
            @Parcelize
            public data object TapQr : WithdrawalLawsonInst("withdrawal_lawson_ins_tap_qr", emptyMap())

            /**
             * 閉じるタップ
             */
            @Parcelize
            public data object TapClose : WithdrawalLawsonInst("withdrawal_lawson_ins_tap_close",
                emptyMap())
        }

        /**
         * ローソン銀行出金QR読み取り画面
         */
        public sealed class WithdrawalLawsonQr(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * QR読み取り完了イベント
             */
            @Parcelize
            public data object Success : WithdrawalLawsonQr("withdrawal_lawson_qr_success", emptyMap())

            /**
             * QR読み取り失敗イベント
             */
            @Parcelize
            public data object Failure : WithdrawalLawsonQr("withdrawal_lawson_qr_failure", emptyMap())

            /**
             * 閉じるタップ
             */
            @Parcelize
            public data object TapClose : WithdrawalLawsonQr("withdrawal_lawson_qr_tap_close", emptyMap())
        }

        /**
         * ローソン銀行出金QR読み取り完了画面
         */
        public sealed class WithdrawalLawsonComplete(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 閉じる（ナビゲーション）タップ
             */
            @Parcelize
            public data object TapClsNav : WithdrawalLawsonComplete("withdrawal_lawson_comp_tap_cls_nav",
                emptyMap())

            /**
             * 閉じる（最下部）タップ
             */
            @Parcelize
            public data object ClsBtm : WithdrawalLawsonComplete("withdrawal_lawson_comp_cls_btm",
                emptyMap())
        }

        /**
         * クレジットカード入金前3Dセキュア認証画面
         */
        public sealed class CreditCard3DSecure(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 成功
             */
            @Parcelize
            public data object Success : CreditCard3DSecure("credit_card_3d_secure_success", emptyMap())

            /**
             * 失敗
             */
            @Parcelize
            public data object Failure : CreditCard3DSecure("credit_card_3d_secure_failure", emptyMap())

            /**
             * キャンセル
             */
            @Parcelize
            public data object Cancel : CreditCard3DSecure("credit_card_3d_secure_cancel", emptyMap())
        }

        /**
         * 入金半モーダル（ファンドソースあり）
         */
        public sealed class DepositHalfModalHasFundSource(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * Primary銀行セル タップ
             */
            @Parcelize
            public data object ClickedPrimaryBank :
                DepositHalfModalHasFundSource("deposit_modal_has_bank_click_bank", emptyMap())

            /**
             * 入金 タップ
             */
            @Parcelize
            public data object ClickedDeposit :
                DepositHalfModalHasFundSource("deposit_modal_has_bank_click_depo", emptyMap())

            /**
             * 金額候補（金額あり） タップ
             */
            @Parcelize
            public data class ClickedPresetAmount(
                public val amount: Long,
            ) : DepositHalfModalHasFundSource("deposit_modal_has_bank_click_list", mapOf(
                "amount" to amount.toString(),
            )
            )

            /**
             * 金額入力欄 タップ
             */
            @Parcelize
            public data object ClickedAmount :
                DepositHalfModalHasFundSource("deposit_modal_has_bank_click_input", emptyMap())

            /**
             * 金額候補（その他） タップ
             */
            @Parcelize
            public data object ClickedOtherAmount :
                DepositHalfModalHasFundSource("deposit_modal_has_bank_click_other", emptyMap())

            /**
             * その他の方法で入金 タップ
             */
            @Parcelize
            public data object ClickedChargeOption :
                DepositHalfModalHasFundSource("deposit_modal_has_bank_click_opt", emptyMap())
        }

        /**
         * 入金確認ダイアログ（iOSはActionSheet）
         */
        public sealed class DepositConfirmDialog(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 入金 タップ
             */
            @Parcelize
            public data object ClickedDeposit : DepositConfirmDialog("deposit_confirm_clicked_ok",
                emptyMap())

            /**
             * キャンセル タップ
             */
            @Parcelize
            public data object ClickedCancel : DepositConfirmDialog("deposit_confirm_click_cancel",
                emptyMap())
        }

        /**
         * 入金方法一覧画面
         */
        public sealed class DepositMethod(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * チェックボックス タップ
             */
            @Parcelize
            public data class ClickedCheckBox(
                public val bankCode: String,
            ) : DepositMethod("fundsource_click_check_box", mapOf(
                "bank_code" to bankCode,
            )
            )

            /**
             * 編集ボタン タップ
             */
            @Parcelize
            public data object ClickedEditButton : DepositMethod("fundsource_clicked_edit", emptyMap())

            /**
             * 削除ボタン タップ
             */
            @Parcelize
            public data class ClickedDeleteButton(
                public val bankCode: String,
            ) : DepositMethod("fundsource_click_delete", mapOf(
                "bank_code" to bankCode,
            )
            )

            /**
             * 表示名変更ボタン タップ
             */
            @Parcelize
            public data class ClickedNicknameButton(
                public val bankCode: String,
            ) : DepositMethod("fundsource_click_nickname", mapOf(
                "bank_code" to bankCode,
            )
            )

            /**
             * 銀行口座を登録ボタン タップ
             */
            @Parcelize
            public data object ClickedBankAccountRegistration :
                DepositMethod("fundsource_clicked_add_bank", emptyMap())

            /**
             * カードリンクトグル　タップ
             */
            @Parcelize
            public data class ClickedCardLinkToggle(
                public val isLinked: Boolean,
            ) : DepositMethod("fundsource_clicked_toggle", mapOf(
                "is_linked" to isLinked.toString(),
            )
            )

            /**
             * カードを登録ボタン タップ
             */
            @Parcelize
            public data object ClickedCardRegistration : DepositMethod("fundsource_clicked_add_card",
                emptyMap())

            /**
             * 自動入金ボタン（銀行） タップ
             */
            @Parcelize
            public data object ClickedBankAutoDepositSetting :
                DepositMethod("fundsource_clicked_auto_deposit", emptyMap())

            /**
             * 自動入金ボタン（カード） タップ
             */
            @Parcelize
            public data object ClickedCardAutoDepositSetting :
                DepositMethod("fundsource_click_auto_deposit_card", emptyMap())

            /**
             * イマすぐ入金 タップ
             */
            @Parcelize
            public data object ClickedBnpl : DepositMethod("fundsource_clicked_bnpl", emptyMap())
        }

        /**
         * カードリンク解除ダイアログ（入金）
         */
        public sealed class DisableAutoChargeDialogByDeposit(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 解除して入金 タップ
             */
            @Parcelize
            public data object ClickedUnlinkAndDeposit :
                DisableAutoChargeDialogByDeposit("unlink_card_deposit_click_unlink", emptyMap())

            /**
             * キャンセル タップ
             */
            @Parcelize
            public data object ClickedCancel :
                DisableAutoChargeDialogByDeposit("unlink_card_deposit_click_cancel", emptyMap())
        }

        /**
         * カードリンク解除ダイアログ（リンク解除）
         */
        public sealed class DisableAutoChargeDialogByUnlink(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 解除 タップ
             */
            @Parcelize
            public data object ClickedUnlink :
                DisableAutoChargeDialogByUnlink("unlink_card_by_unlink_click_unlink", emptyMap())

            /**
             * キャンセル タップ
             */
            @Parcelize
            public data object ClickedCancel :
                DisableAutoChargeDialogByUnlink("unlink_card_by_unlink_click_cancel", emptyMap())
        }

        /**
         * カードをリンク画面
         */
        public sealed class CardLink(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * カードを登録 タップ
             */
            @Parcelize
            public data object ClickedRegisterCard : CardLink("card_guide_clicked_next", emptyMap())
        }

        /**
         * カード情報入力画面
         */
        public sealed class AutoChargeCardRegistration(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 登録 タップ
             */
            @Parcelize
            public data object ClickedSubmit :
                AutoChargeCardRegistration("card_registration_clicked_submit", emptyMap())

            /**
             * 閉じるボタン タップ
             */
            @Parcelize
            public data object ClickedClose :
                AutoChargeCardRegistration("card_registration_clicked_close", emptyMap())
        }

        /**
         * カード登録完了画面
         */
        public sealed class CardRegistrationSucceeded(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 入金 タップ
             */
            @Parcelize
            public data object ClickedDeposit : CardRegistrationSucceeded("card_regist_ok_click_deposit",
                emptyMap())

            /**
             * 閉じる タップ
             */
            @Parcelize
            public data object ClickedDismiss : CardRegistrationSucceeded("card_regist_ok_click_dismiss",
                emptyMap())

            /**
             * 自動入金設定 タップ
             */
            @Parcelize
            public data object ClickedAutoDepositConfig :
                CardRegistrationSucceeded("card_regist_ok_click_auto_deposit", emptyMap())
        }

        /**
         * Linked Card一覧画面
         */
        public sealed class LinkedCardSelect(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * チェックボックス タップ
             */
            @Parcelize
            public data class ClickedCheckBox(
                public val uuid: String,
            ) : LinkedCardSelect("linked_card_select_click_check_box", mapOf(
                "uuid" to uuid,
            )
            )

            /**
             * 編集ボタン タップ
             */
            @Parcelize
            public data object ClickedEditButton : LinkedCardSelect("linked_card_select_click_edit",
                emptyMap())

            /**
             * 削除ボタン タップ
             */
            @Parcelize
            public data class ClickedDeleteButton(
                public val uuid: String,
            ) : LinkedCardSelect("linked_card_select_click_delete", mapOf(
                "uuid" to uuid,
            )
            )

            /**
             * 表示名変更ボタン タップ
             */
            @Parcelize
            public data class ClickedNicknameButton(
                public val uuid: String,
            ) : LinkedCardSelect("linked_card_select_click_nickname", mapOf(
                "uuid" to uuid,
            )
            )
        }

        /**
         * Linked Card 削除ダイアログ
         */
        public sealed class LinkedCardDeleteDialog(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 削除 タップ
             */
            @Parcelize
            public data class ClickedDelete(
                public val isLinked: Boolean,
            ) : LinkedCardDeleteDialog("card_delete_clicked_ok", mapOf(
                "is_linked" to isLinked.toString(),
            )
            )

            /**
             * キャンセル タップ
             */
            @Parcelize
            public data class ClickedCancel(
                public val isLinked: Boolean,
            ) : LinkedCardDeleteDialog("card_delete_clicked_cancel", mapOf(
                "is_linked" to isLinked.toString(),
            )
            )
        }

        /**
         * 入金用銀行削除ダイアログ
         */
        public sealed class DepositBankDeleteDialog(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 削除 タップ
             */
            @Parcelize
            public data object ClickedDelete : DepositBankDeleteDialog("bank_delete_clicked_ok",
                emptyMap())

            /**
             * キャンセル タップ
             */
            @Parcelize
            public data object ClickedCancel : DepositBankDeleteDialog("bank_delete_clicked_cancel",
                emptyMap())
        }

        /**
         * 表示名変更画面
         */
        public sealed class ChangeNickname(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 変更を保存 タップ
             */
            @Parcelize
            public data object ClickedSubmit : ChangeNickname("change_nickname_clicked_submit",
                emptyMap())
        }

        /**
         * 出金口座登録
         */
        public sealed class WithdrawBank(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * searchbar タップ
             */
            @Parcelize
            public data object ClickedSearch : WithdrawBank("withdraw_bank_clicked_search", emptyMap())

            /**
             * 選りすぐり6行 タップ
             */
            @Parcelize
            public data object ClickedBank : WithdrawBank("withdraw_bank_clicked_bank", emptyMap())

            /**
             * 他の銀行を探す タップ
             */
            @Parcelize
            public data object ClickedOthers : WithdrawBank("withdraw_bank_clicked_others", emptyMap())

            /**
             * 支店コード・支店名 タップ
             */
            @Parcelize
            public data object ClickedBranch : WithdrawBank("withdraw_bank_clicked_branch", emptyMap())

            /**
             * 口座番号 タップ
             */
            @Parcelize
            public data object ClickedAccountNumber : WithdrawBank("withdraw_bank_clicked_account_num",
                emptyMap())

            /**
             * 登録する タップ
             */
            @Parcelize
            public data object ClickedNext : WithdrawBank("withdraw_bank_clicked_next", emptyMap())

            /**
             * 完了 タップ
             */
            @Parcelize
            public data object ClickedDone : WithdrawBank("withdraw_bank_clicked_done", emptyMap())

            /**
             * 今すぐ出金 タップ
             */
            @Parcelize
            public data object ClickedWithdraw : WithdrawBank("withdraw_bank_clicked_withdraw",
                emptyMap())
        }

        /**
         * 出金銀行検索
         */
        public sealed class WithdrawBankSearch(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 銀行一覧から探す タップ
             */
            @Parcelize
            public data object ClickedNext : WithdrawBankSearch("withdraw_bank_search_clicked_next",
                emptyMap())
        }

        /**
         * 銀行検索 金融機関リスト
         */
        public sealed class WithdrawBankOther(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 金融機関リストのどれか タップ
             */
            @Parcelize
            public data object ClickedBank : WithdrawBankOther("withdraw_bank_other_clicked_bank",
                emptyMap())
        }

        /**
         * 銀行口座を登録
         */
        public sealed class WithdrawBankGuide(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 銀行口座を登録 タップ
             */
            @Parcelize
            public data object ClickedNext : WithdrawBankGuide("withdraw_bank_guide_clicked_next",
                emptyMap())
        }

        /**
         * 残高から出金画面
         */
        public sealed class WithdrawBankMethod(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 銀行 タップ
             */
            @Parcelize
            public data class ClickedBank(
                public val uuid: String,
            ) : WithdrawBankMethod("withdraw_bank_method_click_bank", mapOf(
                "uuid" to uuid,
            )
            )

            /**
             * ローソン銀行ATMで出金タップ
             */
            @Parcelize
            public data object ClickLawson : WithdrawBankMethod("withdraw_bank_method_click_lawson",
                emptyMap())

            /**
             * セブン銀行ATMで出金タップ
             */
            @Parcelize
            public data object ClickSeven : WithdrawBankMethod("withdraw_bank_method_click_seven",
                emptyMap())

            /**
             * 編集 タップ
             */
            @Parcelize
            public data object ClickedEdit : WithdrawBankMethod("withdraw_bank_method_click_edit",
                emptyMap())

            /**
             * 銀行口座を登録 タップ
             */
            @Parcelize
            public data object ClickedRegisterBank :
                WithdrawBankMethod("withdraw_bank_method_click_regist", emptyMap())

            /**
             * 削除 タップ
             */
            @Parcelize
            public data class ClickedDelete(
                public val uuid: String,
            ) : WithdrawBankMethod("withdraw_bank_method_click_delete", mapOf(
                "uuid" to uuid,
            )
            )

            /**
             * 出金手数料無料iボタンタップ
             */
            @Parcelize
            public data object ClickedFeeFree : WithdrawBankMethod("withdraw_bank_method_tap_fee_free",
                emptyMap())
        }

        /**
         * 出金用銀行削除ダイアログ
         */
        public sealed class WithdrawBankDeleteDialog(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 削除 タップ
             */
            @Parcelize
            public data object ClickedDelete :
                WithdrawBankDeleteDialog("withdraw_bank_delete_click_delete", emptyMap())

            /**
             * キャンセル タップ
             */
            @Parcelize
            public data object ClickedCancel :
                WithdrawBankDeleteDialog("withdraw_bank_delete_click_cancel", emptyMap())
        }

        /**
         * 出金画面
         */
        public sealed class Withdraw(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 出金完了予定日について タップ
             */
            @Parcelize
            public data object ClickedAboutDate : Withdraw("withdraw_clicked_about_date", emptyMap())

            /**
             * 確認画面へ タップ
             */
            @Parcelize
            public data object ClickedNext : Withdraw("withdraw_clicked_next", emptyMap())

            /**
             * 手数料無料バナー タップ
             */
            @Parcelize
            public data object ClickedFeeFreeBanner : Withdraw("withdraw_clicked_fee_free_banner",
                emptyMap())

            /**
             * iボタン タップ
             */
            @Parcelize
            public data object ClickedInformation : Withdraw("withdraw_clicked_information", emptyMap())
        }

        /**
         * 出金確認画面
         */
        public sealed class WithdrawConfirm(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 出金する タップ
             */
            @Parcelize
            public data object ClickedWithdraw : WithdrawConfirm("withdraw_confirm_clicked_ok",
                emptyMap())

            /**
             * iボタン タップ
             */
            @Parcelize
            public data object ClickedInformation : WithdrawConfirm("withdraw_confirm_clicked_info",
                emptyMap())
        }

        /**
         * 自動入金設定画面
         */
        public sealed class AutoDepositSetting(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * トグル タップ
             */
            @Parcelize
            public data class ClickedToggle(
                public val enabledAutoDeposit: Boolean,
                public val fundSource: String,
            ) : AutoDepositSetting("auto_deposit_clicked_toggle", mapOf(
                "enabled_deposit" to enabledAutoDeposit.toString(),
                "fund_source" to fundSource,
            )
            )

            /**
             * 閉じる タップ
             */
            @Parcelize
            public data class ClickedClose(
                public val fundSource: String,
            ) : AutoDepositSetting("auto_deposit_clicked_close", mapOf(
                "fund_source" to fundSource,
            )
            )

            /**
             * 設定を保存 タップ
             */
            @Parcelize
            public data class ClickedSave(
                public val fundSource: String,
            ) : AutoDepositSetting("auto_deposit_clicked_save", mapOf(
                "fund_source" to fundSource,
            )
            )
        }

        /**
         * 自動入金設定 内容破棄ダイアログ
         */
        public sealed class BankAutoDepositDiscardDialog(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * キャンセル タップ
             */
            @Parcelize
            public data object ClickedCancel :
                BankAutoDepositDiscardDialog("deposit_discard_clicked_cancel", emptyMap())

            /**
             * OK タップ
             */
            @Parcelize
            public data object ClickedOk : BankAutoDepositDiscardDialog("deposit_discard_clicked_ok",
                emptyMap())
        }

        /**
         * 自動入金画面 残高なし入金ダイアログ
         */
        public sealed class AutoDepositNoBalanceDialog(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 入金する タップ
             */
            @Parcelize
            public data object ClickedDeposit : AutoDepositNoBalanceDialog("ad_no_balance_click_deposit",
                emptyMap())

            /**
             * 入金しない タップ
             */
            @Parcelize
            public data object ClickedNotDeposit :
                AutoDepositNoBalanceDialog("ad_no_balance_click_not_deposit", emptyMap())
        }

        /**
         * 自動入金画面 残高不足入金ダイアログ
         */
        public sealed class AutoDepositLackingBalanceDialog(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 入金する タップ
             */
            @Parcelize
            public data object ClickedDeposit :
                AutoDepositLackingBalanceDialog("ad_lack_balance_click_deposit", emptyMap())

            /**
             * 入金しない タップ
             */
            @Parcelize
            public data object ClickedNotDeposit :
                AutoDepositLackingBalanceDialog("ad_lack_balance_click_not_deposit", emptyMap())
        }

        /**
         * KyashCard申込時のKYC方法選択画面
         */
        public sealed class CardApplicationKYCMethodSelect(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * スマホ本人確認 タップ
             */
            @Parcelize
            public data object ClickedEkyc :
                CardApplicationKYCMethodSelect("card_app_kyc_select_clicked_ekyc", emptyMap())

            /**
             * 書類２点をアップロード タップ
             */
            @Parcelize
            public data object ClickedDocs :
                CardApplicationKYCMethodSelect("card_app_kyc_select_clicked_docs", emptyMap())
        }

        /**
         * KyashCard再申請時のKYC方法選択画面
         */
        public sealed class CardReapplicationKYCMethodSelect(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * スマホ本人確認 タップ
             */
            @Parcelize
            public data object ClickedEkyc :
                CardReapplicationKYCMethodSelect("card_reapp_kyc_select_clicked_ekyc", emptyMap())

            /**
             * 書類２点をアップロード タップ
             */
            @Parcelize
            public data object ClickedDocs :
                CardReapplicationKYCMethodSelect("card_reapp_kyc_select_clicked_docs", emptyMap())
        }

        /**
         * 共有口座を作成画面
         */
        public sealed class CreateShareWallet(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * FAQリンク タップ
             */
            @Parcelize
            public data object ClickedFAQ : CreateShareWallet("create_share_wallet_clicked_faq",
                emptyMap())

            /**
             * 共有口座を作る タップ
             */
            @Parcelize
            public data object ClickedCreateShareWallet :
                CreateShareWallet("create_share_wallet_clicked_create", emptyMap())
        }

        /**
         * 口座情報の設定画面
         */
        public sealed class ShareWalletSetting(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 作成 タップ
             */
            @Parcelize
            public data object ClickedCreate : ShareWalletSetting("share_wallet_setting_click_create",
                emptyMap())
        }

        /**
         * イマすぐ入金説明画面
         */
        public sealed class BnplInfo(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 申し込みに進む タップ
             */
            @Parcelize
            public data object ClickedApplication : BnplInfo("bnpl_info_clicked_application", emptyMap())

            /**
             * 条件達成でポイントプレゼント タップ
             */
            @Parcelize
            public data object ClickedFirstTimeBonus : BnplInfo("bnpl_info_first_time_bonus", emptyMap())
        }

        /**
         * イマすぐ入金の申し込み情報（氏名、生年月日、電話番号などの入力）
         */
        public sealed class BnplKyc(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 氏名（漢字） タップ
             */
            @Parcelize
            public data object ClickedName : BnplKyc("bnpl_kyc_clicked_name", emptyMap())

            /**
             * 氏名（カナ） タップ
             */
            @Parcelize
            public data object ClickedKana : BnplKyc("bnpl_kyc_clicked_kana", emptyMap())

            /**
             * 生年月日 タップ
             */
            @Parcelize
            public data object ClickedBirthday : BnplKyc("bnpl_kyc_clicked_birthday", emptyMap())

            /**
             * 次へ タップ
             */
            @Parcelize
            public data object ClickedNext : BnplKyc("bnpl_kyc_clicked_next", emptyMap())
        }

        /**
         * イマすぐ入金の氏名（漢字）入力
         */
        public sealed class BnplName(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 変更を保存 タップ
             */
            @Parcelize
            public data object ClickedSave : BnplName("bnpl_name_clicked_save", emptyMap())
        }

        /**
         * イマすぐ入金の氏名（カナ）入力
         */
        public sealed class BnplKana(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 変更を保存 タップ
             */
            @Parcelize
            public data object ClickedSave : BnplKana("bnpl_kana_clicked_save", emptyMap())
        }

        /**
         * イマすぐ入金の生年月日入力
         */
        public sealed class BnplBirthday(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 変更を保存 タップ
             */
            @Parcelize
            public data object ClickedSave : BnplBirthday("bnpl_birthday_clicked_save", emptyMap())
        }

        /**
         * 利用規約(いますぐ入金)
         */
        public sealed class BnplTermsKyash(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 同意して次へ タップ
             */
            @Parcelize
            public data object ClickedNext : BnplTermsKyash("bnpl_terms_kyash_clicked_next", emptyMap())
        }

        /**
         * 利用規約(ミライバライ)
         */
        public sealed class BnplTerms(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 同意して申し込みに進む タップ
             */
            @Parcelize
            public data object ClickedUnderstand : BnplTerms("bnpl_terms_clicked_understand", emptyMap())
        }

        /**
         * イマすぐ入金の金額選択
         */
        public sealed class BnplSelectAmount(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 画面表示(与信枠の表示)
             */
            @Parcelize
            public data class Show(
                public val availableAmount: Long,
            ) : BnplSelectAmount("bnpl_select_show", mapOf(
                "available_amount" to availableAmount.toString(),
            )
            )

            /**
             * 金額確定 タップ
             */
            @Parcelize
            public data class ClickedDecisionAmount(
                public val amount: Long,
                public val availableAmount: Long,
            ) : BnplSelectAmount("bnpl_select_clicked_decision", mapOf(
                "amount" to amount.toString(),
                "available_amount" to availableAmount.toString(),
            )
            )
        }

        /**
         * イマすぐ入金申し込みの確認
         */
        public sealed class BnplConfirmationAmount(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 入金申請 タップ
             */
            @Parcelize
            public data object ClickedDeposit : BnplConfirmationAmount("bnpl_confirm_clicked_deposit",
                emptyMap())
        }

        /**
         * イマすぐ入金申し込みの最終確認シートまたはダイアログ
         */
        public sealed class BnplConfirmationBottomSheet(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 確定 タップ
             */
            @Parcelize
            public data object ClickedConfirm : BnplConfirmationBottomSheet("bnpl_modal_clicked_confirm",
                emptyMap())

            /**
             * パスコード入力完了(口座引落し設定の送信(有効フラグ初期値, 設定可能, 一度スイッチをON, 最終的に有効で送信))
             */
            @Parcelize
            public data class PasscodePassed(
                public val autoRepaymentSettingEnabledFirst: Boolean,
                public val autoRepaymentSettingShow: Boolean,
                public val autoRepaymentSettingEnabledOnce: Boolean,
                public val autoRepaymentSettingEnabledLast: Boolean,
            ) : BnplConfirmationBottomSheet("bnpl_modal_passcode_passed", mapOf(
                "auto_repayment_setting_enabled_first" to autoRepaymentSettingEnabledFirst.toString(),
                "auto_repayment_setting_show" to autoRepaymentSettingShow.toString(),
                "auto_repayment_setting_enabled_once" to autoRepaymentSettingEnabledOnce.toString(),
                "auto_repayment_setting_enabled_last" to autoRepaymentSettingEnabledLast.toString(),
            )
            )
        }

        /**
         * イマすぐ入金再申請画面
         */
        public sealed class BnplRetry(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 再申請する タップ
             */
            @Parcelize
            public data object ClickedRetry : BnplRetry("bnpl_retry_clicked_retry", emptyMap())

            /**
             * 閉じる タップ
             */
            @Parcelize
            public data object ClickedClose : BnplRetry("bnpl_retry_clicked_close", emptyMap())
        }

        /**
         * イマすぐ入金のウォレットタブでの自動引落しの提案ボトムシート
         */
        public sealed class BnplAutoRepaymentSuggestionModal(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 口座引落し設定 タップ
             */
            @Parcelize
            public data object NavigateToAutoRepaymentSetting :
                BnplAutoRepaymentSuggestionModal("bnpl_auto_repayment_suggest_next", emptyMap())
        }

        /**
         * イマすぐ入金の支払い方法を選択
         */
        public sealed class BnplSelectRepaymentMethod(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * ヘルプ タップ
             */
            @Parcelize
            public data object ClickedHelp : BnplSelectRepaymentMethod("bnpl_select_repayment_help",
                emptyMap())

            /**
             * ポイントプレゼント タップ
             */
            @Parcelize
            public data object ClickedFirstTimeBonus :
                BnplSelectRepaymentMethod("bnpl_select_repayment_bonus", emptyMap())

            /**
             * 口座引落し タップ
             */
            @Parcelize
            public data object ClickedAutoRepayment :
                BnplSelectRepaymentMethod("bnpl_select_repayment_auto_deposit", emptyMap())

            /**
             * アプリ残高 タップ
             */
            @Parcelize
            public data object ClickedKyashMoneyRepayment :
                BnplSelectRepaymentMethod("bnpl_select_repayment_kyash_money", emptyMap())

            /**
             * コンビニ・ATM タップ
             */
            @Parcelize
            public data object ClickedCvsAtmRepayment :
                BnplSelectRepaymentMethod("bnpl_select_repayment_cvs_atm", emptyMap())
        }

        /**
         * Wallet画面の告知枠
         */
        public sealed class WalletAnnouncement(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 告知枠 タップ
             */
            @Parcelize
            public data class ClickedOpen(
                public val announcementKey: String,
            ) : WalletAnnouncement("wallet_announcement_clicked_open", mapOf(
                "key" to announcementKey,
            )
            )

            /**
             * 閉じるボタン タップ
             */
            @Parcelize
            public data class ClickedClose(
                public val announcementKey: String,
            ) : WalletAnnouncement("wallet_announcement_clicked_close", mapOf(
                "key" to announcementKey,
            )
            )
        }

        /**
         * 主口座詳細画面
         */
        public sealed class PrimaryWalletDetail(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 残高 タップ
             */
            @Parcelize
            public data object ClickedBalance : PrimaryWalletDetail("p_wallet_detail_clicked_balance",
                emptyMap())

            /**
             * ポイント タップ
             */
            @Parcelize
            public data object ClickedPoint : PrimaryWalletDetail("p_wallet_detail_clicked_point",
                emptyMap())

            /**
             * 出金 タップ
             */
            @Parcelize
            public data object ClickedWithdraw : PrimaryWalletDetail("p_wallet_detail_clicked_withdraw",
                emptyMap())

            /**
             * 入金 タップ
             */
            @Parcelize
            public data class ClickedDeposit(
                public val isBankRegistered: Boolean,
                public val enableCardAutoCharge: Boolean,
            ) : PrimaryWalletDetail("p_wallet_detail_clicked_deposit", mapOf(
                "is_bank_registered" to isBankRegistered.toString(),
                "enable_card_auto_charge" to enableCardAutoCharge.toString(),
            )
            )
        }

        /**
         * 共有口座詳細画面
         */
        public sealed class ShareWalletDetail(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 出金(振替) タップ
             */
            @Parcelize
            public data object ClickedWithdraw : ShareWalletDetail("s_wallet_detail_clicked_withdraw",
                emptyMap())

            /**
             * 入金(振替) タップ
             */
            @Parcelize
            public data object ClickedDeposit : ShareWalletDetail("s_wallet_detail_clicked_deposit",
                emptyMap())
        }

        /**
         * Wallet画面のプロモーション用バナー
         */
        public sealed class WalletPromotionBanner(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * バナー タップ
             */
            @Parcelize
            public data class ClickedOpen(
                public val identifier: String,
            ) : WalletPromotionBanner("wallet_promo_banner_clicked_open", mapOf(
                "identifier" to identifier,
            )
            )
        }

        /**
         * アプリ開始画面
         */
        public sealed class Landing(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * ログイン タップ
             */
            @Parcelize
            public data object ClickedLogin : Landing("landing_clicked_login", emptyMap())

            /**
             * Kyashを始める タップ
             */
            @Parcelize
            public data object ClickedBeginKyash : Landing("landing_clicked_begin_kyash", emptyMap())

            /**
             * 会員登録ボタン（〇〇で続ける） タップ
             */
            @Parcelize
            public data class ClickedRegister(
                public val source: String,
            ) : Landing("landing_clicked_register", mapOf(
                "source" to source,
            )
            )
        }

        /**
         * 会員登録画面（情報入力）
         */
        public sealed class SignupRegister(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 次へ タップ
             */
            @Parcelize
            public data object ClickedNext : SignupRegister("signup_register_clicked_next", emptyMap())
        }

        /**
         * 会員登録画面（本名の確認ダイアログ）
         */
        public sealed class SignupNameConfirm(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * はい タップ
             */
            @Parcelize
            public data object ClickedYes : SignupNameConfirm("signup_name_confirm_clicked_yes",
                emptyMap())

            /**
             * 修正 タップ
             */
            @Parcelize
            public data object ClickedEdit : SignupNameConfirm("signup_name_confirm_clicked_edit",
                emptyMap())
        }

        /**
         * 会員登録画面（生まれた年選択）
         */
        public sealed class SignupBirthYear(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 次へ タップ
             */
            @Parcelize
            public data object ClickedNext : SignupBirthYear("signup_birth_year_clicked_next", emptyMap())

            /**
             * 生まれた年が必要な理由は？ タップ
             */
            @Parcelize
            public data object ClickedFaq : SignupBirthYear("signup_birth_year_clicked_faq", emptyMap())
        }

        /**
         * 会員登録画面（電話番号登録）
         */
        public sealed class SignupPhoneNumberRegister(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 登録 タップ
             */
            @Parcelize
            public data object ClickedRegister :
                SignupPhoneNumberRegister("signup_phone_number_click_register", emptyMap())

            /**
             * 電話番号をサーバーに送ったら重複等でエラーが返却された
             */
            @Parcelize
            public data object Failed : SignupPhoneNumberRegister("signup_phone_number_failed",
                emptyMap())

            /**
             * 携帯電話番号が必要な理由は？ タップ
             */
            @Parcelize
            public data object ClickedFaq : SignupPhoneNumberRegister("signup_phone_number_click_faq",
                emptyMap())
        }

        /**
         * 会員登録画面（SMS認証）
         */
        public sealed class SignupSmsAuth(
            eventName: String,
            parameters: Map<String, String>,
        ) : Action(eventName, parameters) {
            /**
             * 認証成功
             */
            @Parcelize
            public data object Success : SignupSmsAuth("signup_sms_auth_success", emptyMap())

            /**
             * 利用規約 タップ
             */
            @Parcelize
            public data object ClickedTerms : SignupSmsAuth("signup_sms_auth_clicked_terms", emptyMap())

            /**
             * 届かない場合は？ タップ
             */
            @Parcelize
            public data object ClickedCantReceive : SignupSmsAuth("signup_sms_auth_click_cant_receive",
                emptyMap())

            /**
             * SMSを再送する タップ
             */
            @Parcelize
            public data object ClickedResend : SignupSmsAuth("signup_sms_auth_clicked_resend", emptyMap())

            /**
             * 音声電話で確認する（無料） タップ
             */
            @Parcelize
            public data object ClickedCall : SignupSmsAuth("signup_sms_auth_clicked_call", emptyMap())

            /**
             * チャレンジ詳細
             */
            public sealed class RewardDetail(
                eventName: String,
                parameters: Map<String, String>,
            ) : Action(eventName, parameters) {
                /**
                 * チャレンジ詳細表示
                 */
                @Parcelize
                public data class ClickChallenge(
                    public val prizeId: String,
                    public val prizeType: String,
                ) : RewardDetail("reward_detail_click_challenge", mapOf(
                    "id" to prizeId,
                    "type" to prizeType,
                )
                ) {
                    public object PrizeType {
                        public const val weekly: String = "weekly"

                        public const val daily: String = "daily"

                        public const val welcome: String = "welcome"
                    }
                }

                /**
                 * チャレンジ確認ダイアログタップ
                 */
                @Parcelize
                public data class ClickedConfirm(
                    public val prizeId: String,
                    public val prizeType: String,
                    public val action: String,
                ) : RewardDetail("reward_detail_click_confirm", mapOf(
                    "id" to prizeId,
                    "type" to prizeType,
                    "action" to action,
                )
                ) {
                    public object PrizeType {
                        public const val weekly: String = "weekly"

                        public const val daily: String = "daily"

                        public const val welcome: String = "welcome"
                    }

                    public object Action {
                        public const val cancel: String = "cancel"

                        public const val close: String = "close"
                    }
                }

                /**
                 * Kyashコインの貯め方 タップ
                 */
                @Parcelize
                public data class ClickedHelpCoin(
                    public val prizeId: String,
                    public val prizeType: String,
                ) : RewardDetail("reward_detail_click_help_coin", mapOf(
                    "id" to prizeId,
                    "type" to prizeType,
                )
                ) {
                    public object PrizeType {
                        public const val weekly: String = "weekly"

                        public const val daily: String = "daily"

                        public const val welcome: String = "welcome"
                    }
                }

                /**
                 * 通知をオンにする タップ
                 */
                @Parcelize
                public data class ClickedNotification(
                    public val prizeId: String,
                    public val prizeType: String,
                ) : RewardDetail("reward_detail_click_notification", mapOf(
                    "id" to prizeId,
                    "type" to prizeType,
                )
                ) {
                    public object PrizeType {
                        public const val weekly: String = "weekly"

                        public const val daily: String = "daily"

                        public const val welcome: String = "welcome"
                    }
                }
            }

            /**
             * チャレンジ応募完了画面
             */
            public sealed class RewardResult(
                eventName: String,
                parameters: Map<String, String>,
            ) : Action(eventName, parameters) {
                /**
                 * SNSシェア タップ
                 */
                @Parcelize
                public data class PendingClickShare(
                    public val prizeId: String,
                ) : RewardResult("reward_result_pending_click_share", mapOf(
                    "id" to prizeId,
                )
                )

                /**
                 * 閉じる タップ
                 */
                @Parcelize
                public data class ClickedClose(
                    public val prizeId: String,
                ) : RewardResult("reward_result_click_close", mapOf(
                    "id" to prizeId,
                )
                )
            }

            /**
             * チャレンジ当選画面
             */
            public sealed class RewardWon(
                eventName: String,
                parameters: Map<String, String>,
            ) : Action(eventName, parameters) {
                /**
                 * 受取に進む タップ
                 */
                @Parcelize
                public data class ClickedReceive(
                    public val prizeId: String,
                    public val prizeType: String,
                ) : RewardWon("reward_won_click_receive", mapOf(
                    "id" to prizeId,
                    "type" to prizeType,
                )
                ) {
                    public object PrizeType {
                        public const val weekly: String = "weekly"

                        public const val daily: String = "daily"

                        public const val welcome: String = "welcome"
                    }
                }

                /**
                 * SNSシェア タップ
                 */
                @Parcelize
                public data class ClickedShare(
                    public val prizeId: String,
                    public val prizeType: String,
                ) : RewardWon("reward_won_click_share", mapOf(
                    "id" to prizeId,
                    "type" to prizeType,
                )
                ) {
                    public object PrizeType {
                        public const val weekly: String = "weekly"

                        public const val daily: String = "daily"

                        public const val welcome: String = "welcome"
                    }
                }
            }

            /**
             * チャレンジ落選画面
             */
            public sealed class RewardLost(
                eventName: String,
                parameters: Map<String, String>,
            ) : Action(eventName, parameters) {
                /**
                 * 他のチャレンジを見る タップ
                 */
                @Parcelize
                public data class ClickedOther(
                    public val prizeId: String,
                    public val prizeType: String,
                ) : RewardLost("reward_lost_click_other", mapOf(
                    "id" to prizeId,
                    "type" to prizeType,
                )
                ) {
                    public object PrizeType {
                        public const val weekly: String = "weekly"

                        public const val daily: String = "daily"

                        public const val welcome: String = "welcome"
                    }
                }

                /**
                 * 閉じる タップ
                 */
                @Parcelize
                public data class ClickedClose(
                    public val prizeId: String,
                    public val prizeType: String,
                ) : RewardLost("reward_lost_click_close", mapOf(
                    "id" to prizeId,
                    "type" to prizeType,
                )
                ) {
                    public object PrizeType {
                        public const val weekly: String = "weekly"

                        public const val daily: String = "daily"

                        public const val welcome: String = "welcome"
                    }
                }

                /**
                 * オファーウォールバナー タップ
                 */
                @Parcelize
                public data class ClickOfferWall(
                    public val prizeId: String,
                    public val prizeType: String,
                ) : RewardLost("reward_lost_click_offerwall", mapOf(
                    "id" to prizeId,
                    "type" to prizeType,
                )
                ) {
                    public object PrizeType {
                        public const val weekly: String = "weekly"

                        public const val daily: String = "daily"

                        public const val welcome: String = "welcome"
                    }
                }
            }

            /**
             * リワードオファーウォール
             */
            public sealed class RewardOfferWallAgreement(
                eventName: String,
                parameters: Map<String, String>,
            ) : Action(eventName, parameters) {
                /**
                 * 個人関連情報提供同意画面 同意するタップ
                 */
                @Parcelize
                public data object ClickAgree : RewardOfferWallAgreement("reward_ow_agreement_click_agree",
                    emptyMap())

                /**
                 * 個人関連情報提供同意画面 キャンセルタップ
                 */
                @Parcelize
                public data object ClickCancel : RewardOfferWallAgreement("reward_ow_agreement_click_cancel",
                    emptyMap())

                /**
                 * 個人関連情報提供同意画面 閉じるタップ
                 */
                @Parcelize
                public data object ClickClose : RewardOfferWallAgreement("reward_ow_agreement_click_close",
                    emptyMap())

                /**
                 * 個人関連情報提供同意画面 プライバシーポリシータップ
                 */
                @Parcelize
                public data object ClickPrivacy :
                    RewardOfferWallAgreement("reward_ow_agreement_click_privacy", emptyMap())
            }

            /**
             * リワードオファーウォールメンテナンス
             */
            public sealed class RewardOfferWallMaintenance(
                eventName: String,
                parameters: Map<String, String>,
            ) : Action(eventName, parameters) {
                /**
                 * メンテナンス画面
                 */
                @Parcelize
                public data object ClickClose :
                    RewardOfferWallMaintenance("reward_ow_maintenance_click_close", emptyMap())
            }

            /**
             * オンボーディングウェルカムチャレンジ
             */
            public sealed class OnboardingWelcomeChallenge(
                eventName: String,
                parameters: Map<String, String>,
            ) : Action(eventName, parameters) {
                /**
                 * 応募
                 */
                @Parcelize
                public data object ClickedApply : OnboardingWelcomeChallenge("onboarding_welcome_click_apply",
                    emptyMap())
            }
        }
    }

    public sealed class Tag(
        public val parameters: Map<String, Any>,
    ) : Parcelable {
        /**
         * 最後に送金した時間
         */
        @Parcelize
        public data class LastSendDate(
            public val date: Date,
        ) : Tag(buildMap {
            put("LastSendDate", convertDateToString(date))
        }
        )

        /**
         * 最後に請求した時間
         */
        @Parcelize
        public data class LastRequestDate(
            public val date: Date,
        ) : Tag(buildMap {
            put("LastRequestDate", convertDateToString(date))
        }
        )
    }

    public sealed class Event(
        public val eventName: String,
        public val parameters: Map<String, Any>,
    ) : Parcelable {
        /**
         * ユーザ登録完了
         */
        @Parcelize
        public data object Registration : Event("Registration", emptyMap())

        /**
         * Kyash Card Virtualの発行
         */
        @Parcelize
        public data class IssueKyashCardVirtual(
            public val partner: String,
        ) : Event("IssueCardVirtual", buildMap {
            put("Partner", partner)
        }
        )

        /**
         * リアルカードの有効化
         */
        @Parcelize
        public data class ActivatePhysicalCard(
            public val partner: String,
            public val cardType: String,
        ) : Event("ActivatePhysicalCard", buildMap {
            put("Partner", partner)
            put("CardType", cardType)
        }
        )

        /**
         * Kyash Card Liteの再有効化
         */
        @Parcelize
        public data class ReactivateKyashCardLite(
            public val partner: String,
        ) : Event("ReActivateCardLite", buildMap {
            put("Partner", partner)
        }
        )

        /**
         * 入金方法登録完了
         */
        @Parcelize
        public data class AddDepositMethod(
            public val type: String,
        ) : Event("AddDepositMethod", buildMap {
            put("Type", type)
        }
        ) {
            public object Type {
                public val BankAccount: String = "BankAccount"

                public val CreditCard: String = "CreditCard"
            }
        }

        /**
         * 送金完了
         */
        @Parcelize
        public data class CompleteSend(
            public val amount: Long,
            public val currency: String,
        ) : Event("Send", buildMap {
            put("Amount", amount)
            put("Currency", currency)
        }
        )

        /**
         * 請求完了
         */
        @Parcelize
        public data class CompleteRequest(
            public val amount: Long,
            public val currency: String,
        ) : Event("Request", buildMap {
            put("Amount", amount)
            put("Currency", currency)
        }
        )

        /**
         * その他の方法での入金完了
         */
        @Parcelize
        public data class CompleteDepositByOthersWay(
            public val type: String,
        ) : Event("Charge", buildMap {
            put("Type", type)
        }
        )

        /**
         * アプリ起動時の最初の画面表示
         */
        @Parcelize
        public data object OpenFirstView : Event("OpenWallet", emptyMap())

        /**
         * ギフト受け取り完了
         */
        @Parcelize
        public data class RedeemGift(
            public val code: String,
        ) : Event("Redeem", buildMap {
            put("Code", code)
        }
        )

        /**
         * KYC申込み完了
         */
        @Parcelize
        public data object KycApplicationComplete : Event("KycApplicationComplete", emptyMap())

        /**
         * 入金完了
         */
        @Parcelize
        public data object Topup : Event("Topup", emptyMap())

        /**
         * カードチャージ3Dセキュア認証成功
         */
        @Parcelize
        public data object CreditCard3DSecureSuccess : Event("CreditCard3DSecureSuccess", emptyMap())

        /**
         * カードチャージ3Dセキュア認証失敗
         */
        @Parcelize
        public data object CreditCard3DSecureFailure : Event("CreditCard3DSecureFailure", emptyMap())
    }
}
