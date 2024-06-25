package com.quinti.android_step_template.kmp.exception

sealed class RewardApplicationException : Exception() {
    data class CoinShortageException(
        override val message: String? = null,
    ) : RewardApplicationException()

    data class ExpiredPrizeException(
        override val message: String? = null,
    ) : RewardApplicationException()

    data class AlreadyAppliedException(
        override val message: String? = null,
    ) : RewardApplicationException()

    data class ReachedMaximumWinnersException(
        override val message: String? = null,
    ) : RewardApplicationException()

    data class NotFoundException(
        override val message: String? = null,
    ) : RewardApplicationException()
}