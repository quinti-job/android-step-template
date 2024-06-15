package com.quinti.android_step_template.kmp.data.repository

import co.kyash.mobile.data.api.KyashCoinApi
import co.kyash.mobile.data.api.exception.ApiErrorResponseException
import co.kyash.mobile.data.datasource.CoinPrizeLocalDataSource
import co.kyash.mobile.data.datasource.CoinPrizePageLocalDataSource
import co.kyash.mobile.model.reward.ApplyCoinPrizeResult
import co.kyash.mobile.model.reward.Prize
import co.kyash.mobile.model.reward.PrizeList
import co.kyash.mobile.model.reward.ReceivePrizeType
import co.kyash.mobile.model.reward.RewardApplicationException
import co.kyash.mobile.model.reward.UncheckedPrizeSummary
import co.kyash.mobile.repository.PrizeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class PrizeRepositoryImpl(
    private val prizeLocalDataSource: CoinPrizeLocalDataSource,
    private val prizePageLocalDataSource: CoinPrizePageLocalDataSource,
    private val prizeApi: KyashCoinApi,
) : PrizeRepository {

    override val activeWeeklyPrizes: Flow<PrizeList> = combine(
        prizeLocalDataSource.activeWeeklyPrizes,
        prizePageLocalDataSource.activeWeeklyPrizesNextPage,
    ) { prizes, nextPage ->
        PrizeList(
            hasNext = nextPage != null,
            prizes = prizes,
        )
    }
    override val activeDailyPrizes: Flow<PrizeList> = combine(
        prizeLocalDataSource.activeDailyPrizes,
        prizePageLocalDataSource.activeDailyPrizesNextPage,
    ) { prizes, nextPage ->
        PrizeList(
            hasNext = nextPage != null,
            prizes = prizes,
        )
    }
    override val upcomingWeeklyPrizes: Flow<PrizeList> = combine(
        prizeLocalDataSource.upcomingWeeklyPrizes,
        prizePageLocalDataSource.upcomingWeeklyPrizesNextPage,
    ) { prizes, nextPage ->
        PrizeList(
            hasNext = nextPage != null,
            prizes = prizes,
        )
    }
    override val appliedWeeklyPrizes: Flow<PrizeList> = combine(
        prizeLocalDataSource.appliedWeeklyPrizes,
        prizePageLocalDataSource.appliedWeeklyPrizesNextPage,
    ) { prizes, nextPage ->
        PrizeList(
            hasNext = nextPage != null,
            prizes = prizes,
        )
    }
    override val appliedDailyPrizes: Flow<PrizeList> = combine(
        prizeLocalDataSource.appliedDailyPrizes,
        prizePageLocalDataSource.appliedDailyPrizesNextPage,
    ) { prizes, nextPage ->
        PrizeList(
            hasNext = nextPage != null,
            prizes = prizes,
        )
    }

    override val welcomePrizes: Flow<PrizeList> = prizeLocalDataSource.welcomePrizes.map { prizes ->
        PrizeList(
            hasNext = false,
            prizes = prizes,
        )
    }

    @Suppress("LongParameterList")
    private suspend fun loadAndUpdatePrizes(
        refresh: Boolean,
        limit: Int,
        nextPage: Int?,
        type: PrizeRepository.PrizeTypeFilter,
        status: PrizeRepository.PrizeStatusFilter,
        updateNextPage: suspend (Int?) -> Unit,
        updatePrizes: suspend (List<Prize>, Boolean) -> Unit,
    ) {
        val targetPage = if (refresh) {
            1
        } else {
            nextPage ?: 1
        }

        val result = prizeApi.getPrizes(
            type = type.value,
            status = status.value,
            page = targetPage,
            limit = limit,
        ).result.data

        // リフレッシュ時は一旦Flowをクリアする
        if (refresh) {
            updatePrizes(emptyList(), false)
            updateNextPage(null)
        }

        updatePrizes(
            result.coinPrizes.map { it.toEntity() },
            targetPage != 1,
        )
        updateNextPage(result.nextPage)
    }

    @Suppress("LongParameterList")
    private suspend fun loadAndUpdateAppliedPrizes(
        refresh: Boolean,
        limit: Int,
        nextPage: Int?,
        type: PrizeRepository.PrizeTypeFilter,
        updateNextPage: suspend (Int?) -> Unit,
        updatePrizes: suspend (List<Prize>, Boolean) -> Unit,
    ) {
        val targetPage = if (refresh) {
            1
        } else {
            nextPage ?: 1
        }

        val result = prizeApi.getAppliedPrizes(
            type = type.value,
            page = targetPage,
            limit = limit,
        ).result.data

        // リフレッシュ時は一旦Flowをクリアする
        if (refresh) {
            updatePrizes(emptyList(), false)
            updateNextPage(null)
        }

        updatePrizes(
            result.coinPrizes.map { it.toEntity() },
            targetPage != 1,
        )
        updateNextPage(result.nextPage)
    }

    override suspend fun loadActiveWeeklyPrizes(refresh: Boolean, limit: Int) {
        loadAndUpdatePrizes(
            refresh = refresh,
            limit = limit,
            type = PrizeRepository.PrizeTypeFilter.Weekly,
            status = PrizeRepository.PrizeStatusFilter.Active,
            nextPage = prizePageLocalDataSource.activeWeeklyPrizesNextPage.value,
            updateNextPage = prizePageLocalDataSource::updateActiveWeeklyPrizesNextPage,
            updatePrizes = prizeLocalDataSource::updateActiveWeeklyPrizes,
        )
    }

    override suspend fun loadActiveDailyPrizes(refresh: Boolean, limit: Int) {
        loadAndUpdatePrizes(
            refresh = refresh,
            limit = limit,
            nextPage = prizePageLocalDataSource.activeDailyPrizesNextPage.value,
            type = PrizeRepository.PrizeTypeFilter.Daily,
            status = PrizeRepository.PrizeStatusFilter.Active,
            updateNextPage = prizePageLocalDataSource::updateActiveDailyPrizesNextPage,
            updatePrizes = prizeLocalDataSource::updateActiveDailyPrizes,
        )
    }

    override suspend fun loadUpcomingWeeklyPrizes(refresh: Boolean, limit: Int) {
        loadAndUpdatePrizes(
            refresh = refresh,
            limit = limit,
            nextPage = prizePageLocalDataSource.upcomingWeeklyPrizesNextPage.value,
            type = PrizeRepository.PrizeTypeFilter.Weekly,
            status = PrizeRepository.PrizeStatusFilter.Upcoming,
            updateNextPage = prizePageLocalDataSource::updateUpcomingWeeklyPrizesNextPage,
            updatePrizes = prizeLocalDataSource::updateUpcomingWeeklyPrizes,
        )
    }

    override suspend fun loadAppliedWeeklyPrizes(refresh: Boolean, limit: Int) {
        loadAndUpdateAppliedPrizes(
            refresh = refresh,
            limit = limit,
            nextPage = prizePageLocalDataSource.appliedWeeklyPrizesNextPage.value,
            type = PrizeRepository.PrizeTypeFilter.Weekly,
            updateNextPage = prizePageLocalDataSource::updateAppliedWeeklyPrizesNextPage,
            updatePrizes = prizeLocalDataSource::updateAppliedWeeklyPrizes,
        )
    }

    override suspend fun loadAppliedDailyPrizes(refresh: Boolean, limit: Int) {
        loadAndUpdateAppliedPrizes(
            refresh = refresh,
            limit = limit,
            nextPage = prizePageLocalDataSource.appliedDailyPrizesNextPage.value,
            type = PrizeRepository.PrizeTypeFilter.Daily,
            updateNextPage = prizePageLocalDataSource::updateAppliedDailyPrizesNextPage,
            updatePrizes = prizeLocalDataSource::updateAppliedDailyPrizes,
        )
    }

    override suspend fun loadWelcomePrizes(refresh: Boolean, limit: Int) {
        loadAndUpdatePrizes(
            refresh = refresh,
            limit = limit,
            nextPage = 1,
            type = PrizeRepository.PrizeTypeFilter.Welcome,
            status = PrizeRepository.PrizeStatusFilter.Active,
            updateNextPage = {},
            updatePrizes = prizeLocalDataSource::updateWelcomePrizes,
        )
    }

    override suspend fun getPrize(prizeId: String): Prize {
        try {
            val entity = prizeApi.getPrize(prizeId).result.data.toEntity()
            prizeLocalDataSource.updatePrize(entity.copy(id = prizeId))
            return entity
        } catch (apiErrorResponseException: ApiErrorResponseException) {
            val errorResponse =
                apiErrorResponseException.response ?: throw apiErrorResponseException
            if (errorResponse.code == 404) {
                throw RewardApplicationException.NotFoundException(
                    errorResponse.error.message,
                )
            } else {
                throw apiErrorResponseException
            }
        }
    }

    private suspend fun refreshPrize(prizeId: String) {
        val entity = prizeApi.getPrize(prizeId).result.data.toEntity()
        prizeLocalDataSource.updatePrize(entity.copy(id = prizeId))
    }

    override suspend fun applyPrize(prizeId: String): ApplyCoinPrizeResult {
        try {
            val result = prizeApi.applyCoinPrize(prizeId).result.data.toEntity()
            prizeLocalDataSource.updatePrize(result.prize.copy(id = prizeId))
            return result
        } catch (apiErrorResponseException: ApiErrorResponseException) {
            val errorResponse =
                apiErrorResponseException.response ?: throw apiErrorResponseException
            when (errorResponse.detailCode) {
                40001 -> throw RewardApplicationException.CoinShortageException(
                    errorResponse.error.message,
                )

                40002 -> throw RewardApplicationException.ExpiredPrizeException(
                    errorResponse.error.message,
                )

                40003 -> throw RewardApplicationException.AlreadyAppliedException(
                    errorResponse.error.message,
                )

                40004 -> throw RewardApplicationException.ReachedMaximumWinnersException(
                    errorResponse.error.message,
                )

                else -> throw apiErrorResponseException
            }
        }
    }

    override suspend fun sharedToSns(prizeId: String) {
        prizeApi.sharedToSns(prizeId)
    }

    override suspend fun receivePrize(applicationId: String): ReceivePrizeType {
        return prizeApi.receivePrize(applicationId).result.data.toEntity()
    }

    override suspend fun checkLotteryResult(prizeId: String) {
        prizeApi.checkLotteryResult(prizeId)
        refreshPrize(prizeId)
    }

    override suspend fun getWelcomeChallenge(): Prize? {
        val result = prizeApi.getPrizes(
            type = PrizeRepository.PrizeTypeFilter.Welcome.value,
            status = PrizeRepository.PrizeStatusFilter.Active.value,
            page = 1,
            limit = 1,
        ).result.data
        return result.coinPrizes.firstOrNull()?.toEntity()
    }

    override suspend fun getUncheckedPrizes(): UncheckedPrizeSummary? {
        val result = prizeApi.getUncheckedPrizes()
        return result.result.data.let { data ->
            data.displayCoinPrize?.let {
                UncheckedPrizeSummary(
                    data.count,
                    UncheckedPrizeSummary.PrizePreview(
                        prizeId = it.id,
                        imageUrl = it.imageUrl,
                        title = it.title,
                        entryCoinAmount = it.entryCoinAmount,
                    ),
                )
            }
        }
    }
}