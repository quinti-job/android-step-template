package com.quinti.android_step_template.kmp.data.api.exception

import com.quinti.android_step_template.kmp.data.api.response.base.ErrorResponse
import io.ktor.http.HttpStatusCode

/**
 * UI 側で特殊なハンドリングが必要な API エラー
 */
class ApiFatalErrorException(val error: ApiFatalError) : RuntimeException()

sealed class ApiFatalError {
    companion object {

        /**
         * Forbidden(403), Unauthorized(401), Gone(410), ServiceUnavailable(503),
         * PayloadTooLarge(413) のAPIエラーレスポンスの場合にApiFatalErrorを生成する。
         * その他のエラーの場合はnullを返却する。
         */
        fun from(statusCode: HttpStatusCode, response: ErrorResponse): ApiFatalError? {
            return when (statusCode) {
                HttpStatusCode.Forbidden -> if (response.detailCode == 40301) Banned else null
                HttpStatusCode.Unauthorized -> Unauthorized
                HttpStatusCode.Gone -> ForceUpdate(response.error.message)
                HttpStatusCode.ServiceUnavailable -> UnderMaintenance
                HttpStatusCode.PayloadTooLarge -> SizeLimitOver
                else -> null
            }
        }
    }

    data object Banned : ApiFatalError()
    data object Unauthorized : ApiFatalError()
    data class ForceUpdate(val message: String) : ApiFatalError()
    data object UnderMaintenance : ApiFatalError()
    data object SizeLimitOver : ApiFatalError()
}