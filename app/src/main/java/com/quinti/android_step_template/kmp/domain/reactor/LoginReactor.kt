package com.quinti.android_step_template.kmp.domain.reactor

import com.quinti.android_step_template.kmp.data.entity.Login
import com.quinti.android_step_template.kmp.domain.reactor.base.AbstractReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.kmp.domain.usecase.account.LoginUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow


class LoginReactor(
    mainDispatcher: CoroutineDispatcher,
//    private val result: PrizeApplicationResult,
    private val loginUseCase: LoginUseCase,
) : AbstractReactor<Reactor.LoadState<LoginReactor.State>, LoginReactor.Action, LoginReactor.Mutation, LoginReactor.Event>(
    mainDispatcher = mainDispatcher,
    initialState = Reactor.LoadState.Loading(),
) {
    override fun mutate(action: Action): Flow<Mutation> = flow {
        when (action) {
            Action.LoadInitially -> loadInitially()
            Action.TapChangeEmail -> notify(Event.NavigateToChangeEmail)
            Action.TapChangePassword -> notify(Event.NavigateToChangePassword)
            Action.TapCreateAccount -> notify(Event.NavigateToCreateAccount)
            Action.TapLogin -> notify(Event.Login)
            Action.TapPrivacyPolicy -> notify(Event.NavigateToPrivacyPolicy)
            Action.TapTerms -> notify(Event.NavigateToTerms)
        }
//        when (action) {
//            Action.Initialize -> initialize()
//            Action.ShareToSns -> shareToSns()
//            Action.TapClose -> tapClose()
//            Action.TapOtherChallenge -> notify(Event.NavigateToOtherChallenge)
//            Action.TapReceivePrize -> receivePrize()
//            Action.TapOfferWall -> notify(Event.NavigateToOfferWall)
//        }
    }

//    private fun tapClose() {
//        notify(Event.Close)
//    }

    override fun reduce(
        state: Reactor.LoadState<State>,
        mutation: Mutation,
    ): Reactor.LoadState<State> {
        return when (mutation) {
            Mutation.SetError -> TODO()
            Mutation.SetLoading -> Reactor.LoadState.Loading()
            is Mutation.SetLoaded -> Reactor.LoadState.Loaded(
                State.LoginSuccessed(
                    loginSuccess = true,
                )
            )
        }
    }

    private suspend fun FlowCollector<Mutation>.loadInitially() {
        val currentState = state.value
        if (currentState is Reactor.LoadState.Loaded) return

        emit(Mutation.SetLoading)

        runCatching {
            coroutineScope {
                val login = async {
                    loginUseCase(
                        email = "",
                        password = "",
                    )
                }

                Mutation.SetLoaded(
                    loginSuccess = login.await()
                )
            }
        }.onSuccess {

        }.onFailure {
            emit(Mutation.SetError)
        }
    }
//
//    private suspend fun FlowCollector<Mutation>.shareToSns() {
//        notify(Event.ShowProgress)
//        runCatching {
//            when (result) {
//                // ここでは応募しました状態の場合のみSNS投稿導線が表示される。
//                // その場合にSNS投稿を行うことで、当選確率を高めることができる。
//                is PrizeApplicationResult.Pending -> {
//                    notifySharedToSns(result.prizeId)
//                    notify(
//                        Event.ShareToSnsWhenApplied(
//                            title = result.title,
//                            imageUrl = result.imageUrl,
//                            type = result.type,
//                        ),
//                    )
//                }
//
//                else -> {} // NOP
//            }
//        }.onFailure {
//            when (it) {
//                is ApiFatalErrorException -> notify(Event.HandleFatalError(it.error))
//                else -> notify(Event.ShowError(it.asNonFatalError()))
//            }
//        }
//        notify(Event.HideProgress)
//    }
//
//    private suspend fun FlowCollector<Mutation>.receivePrize() {
//        val won = result as? PrizeApplicationResult.Won ?: return
//
//        notify(Event.ShowProgress)
//        runCatching {
//            val prize = receiveCoinPrize(won.applicationId)
//            notify(
//                Event.ShowSnsShareWhenWon(
//                    title = won.title,
//                    imageUrl = won.imageUrl,
//                    type = won.type,
//                ),
//            )
//            when (prize) {
//                is ReceivePrizeType.Redirect ->
//                    notify(Event.NavigateToReceivePrize.Redirect(prize.url))
//
//                ReceivePrizeType.Unknown -> {} // NOP
//            }
//        }.onFailure {
//            when (it) {
//                is ApiFatalErrorException -> notify(Event.HandleFatalError(it.error))
//                else -> notify(Event.ShowError(it.asNonFatalError()))
//            }
//        }
//        notify(Event.HideProgress)
//    }

    sealed class Mutation : AbstractReactor.Mutation {
        data object SetLoading : Mutation()
        data class SetLoaded(
            val loginSuccess: Login,
        ) : Mutation()
        data object SetError : Mutation()
    }






    sealed class State : Reactor.State {

        /**
         * ログインボタン
         */
        abstract val loginBtnDisable: Boolean

        /**
         * メール エラーメッセージ
         */
        abstract val emailErrorMsgVisibility: Boolean

        /**
         * パスワード エラーメッセージ
         */
        abstract val passwordErrorMsgVisibility: Boolean

        /**
         * ログイン エラーメッセージ
         */
        abstract val loginErrorMsgVisibility: Boolean

        /**
         * ネットワークエラーポップアップ
         */
        abstract val netWorkErrorPopUpVisibility: Boolean


        data object Initialized : State() {
            override val loginBtnDisable: Boolean = false
            override val emailErrorMsgVisibility: Boolean = false
            override val passwordErrorMsgVisibility: Boolean = false
            override val loginErrorMsgVisibility: Boolean = false
            override val netWorkErrorPopUpVisibility: Boolean = false

            internal fun toLoginSusccesed() {

            }
        }

        data class LoginSuccessed(
            val loginSuccess: Boolean,
        ) : State() {
            override val loginBtnDisable: Boolean
                get() = TODO("Not yet implemented")
            override val emailErrorMsgVisibility: Boolean
                get() = TODO("Not yet implemented")
            override val passwordErrorMsgVisibility: Boolean
                get() = TODO("Not yet implemented")
            override val loginErrorMsgVisibility: Boolean
                get() = TODO("Not yet implemented")
            override val netWorkErrorPopUpVisibility: Boolean
                get() = TODO("Not yet implemented")

        }

        companion object {
            fun initialized(): State = Initialized
        }

//        data class Pending(
//            override val result: PrizeApplicationResult.Pending,
//        ) : State() {
//            override val giftBoxAnimationEnabled: Boolean = false
//            override val prizeTitle: String = result.title
//            override val prizeImageUrl: String = result.imageUrl
//            override val entryCoinAmount: Long = result.entryCoinAmount
//            override val prizeType: Prize.Type = result.type
//            override val prizeId = result.prizeId
//        }

//        data class Won(
//            override val result: PrizeApplicationResult.Won,
//        ) : State() {
//            override val giftBoxAnimationEnabled: Boolean = true
//            override val prizeTitle: String = result.title
//            override val prizeImageUrl: String = result.imageUrl
//            override val entryCoinAmount: Long = result.entryCoinAmount
//            override val prizeType: Prize.Type = result.type
//            override val prizeId = result.prizeId
//            val titleType = TitleType(result.giftType)
//            val descriptionType = DescriptionType(result.giftType)
//            val buttonType = ButtonType(result.giftType)
//            val noteType = NoteType(result.giftType)
//            val receiveExpireType = ReceiveExpireType(result.giftType, result.receiveExpireAt)

//            sealed class ReceiveExpireType {
//                // 有効期限無し
//                data object None : ReceiveExpireType()
//
//                // 有効期限有り
//                data class Date(val date: DateTimeTz) : ReceiveExpireType()
//
//                companion object {
//                    operator fun invoke(
//                        giftType: Prize.GiftType,
//                        receiveExpireAt: DateTimeTz,
//                    ): ReceiveExpireType {
//                        return when (giftType) {
//                            Prize.GiftType.Redirect -> Date(receiveExpireAt)
//                            is Prize.GiftType.Auto.Point -> None
//                        }
//                    }
//                }
//            }

//            enum class NoteType {
//                Redirect,
//                KyashPoint,
//                ;
//
//                companion object {
//                    operator fun invoke(giftType: Prize.GiftType): NoteType {
//                        return when (giftType) {
//                            Prize.GiftType.Redirect -> Redirect
//                            is Prize.GiftType.Auto.Point -> KyashPoint
//                        }
//                    }
//                }
//            }

//            enum class TitleType {
//                Normal,
//                KyashPoint,
//                ;
//
//                companion object {
//                    operator fun invoke(giftType: Prize.GiftType): TitleType {
//                        return when (giftType) {
//                            Prize.GiftType.Redirect -> Normal
//                            is Prize.GiftType.Auto.Point -> KyashPoint
//                        }
//                    }
//                }
//            }

//            enum class DescriptionType {
//                Normal,
//                None,
//                ;
//
//                companion object {
//                    operator fun invoke(giftType: Prize.GiftType): DescriptionType {
//                        return when (giftType) {
//                            Prize.GiftType.Redirect -> Normal
//                            is Prize.GiftType.Auto.Point -> None
//                        }
//                    }
//                }
//            }

//            enum class ButtonType {
//                ReceivePrize,
//                Close,
//                ;
//
//                companion object {
//                    operator fun invoke(giftType: Prize.GiftType): ButtonType {
//                        return when (giftType) {
//                            Prize.GiftType.Redirect -> ReceivePrize
//                            is Prize.GiftType.Auto -> Close
//                        }
//                    }
//                }
//            }
//        }

//        data class Lost(
//            override val result: PrizeApplicationResult.Lost,
//        ) : State() {
//            val buttonType = ButtonType(result.type)
//
//            override val giftBoxAnimationEnabled: Boolean = false
//            override val prizeTitle: String = result.title
//            override val prizeImageUrl: String = result.imageUrl
//            override val entryCoinAmount: Long = result.entryCoinAmount
//            override val prizeType: Prize.Type = result.type
//            override val prizeId = result.prizeId
//
//            enum class ButtonType {
//                Close,
//                OtherChallenge,
//                ;
//
//                companion object {
//                    operator fun invoke(type: Prize.Type): ButtonType {
//                        return when (type) {
//                            Prize.Type.Weekly -> Close
//                            Prize.Type.Daily -> OtherChallenge
//                            Prize.Type.Welcome -> Close
//                        }
//                    }
//                }
//            }
//        }


    }

    /**
     * Actionは完成
     */
    sealed class Action : Reactor.Action {
        data object LoadInitially : Action()
        data object TapLogin : Action()
        data object TapChangeEmail : Action()
        data object TapChangePassword : Action()
        data object TapCreateAccount : Action()
        data object TapTerms : Action()
        data object TapPrivacyPolicy : Action()
    }

    sealed class Event : Reactor.Event {
        data object Login : Event()
        data object NavigateToChangeEmail : Event()
        data object NavigateToChangePassword : Event()
        data object NavigateToCreateAccount : Event()
        data object NavigateToTerms : Event()
        data object NavigateToPrivacyPolicy : Event()
        data class ShowEmailError(val error: String) : Event()
        data class ShowPasswordError(val error: String) : Event()
        data class ShowLoginError(val error: String) : Event()
        data class ShowNetWorkError(val error: String) : Event()
        data object DisableLoginBtn : Event()
    }

}