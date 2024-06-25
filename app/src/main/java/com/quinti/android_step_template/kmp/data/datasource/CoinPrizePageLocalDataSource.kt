package com.quinti.android_step_template.kmp.data.datasource

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface CoinPrizePageLocalDataSource {
    val activeWeeklyPrizesNextPage: StateFlow<Int?>
    val activeDailyPrizesNextPage: StateFlow<Int?>
    val upcomingWeeklyPrizesNextPage: StateFlow<Int?>
    val appliedWeeklyPrizesNextPage: StateFlow<Int?>
    val appliedDailyPrizesNextPage: StateFlow<Int?>
    suspend fun updateActiveWeeklyPrizesNextPage(page: Int?)
    suspend fun updateActiveDailyPrizesNextPage(page: Int?)
    suspend fun updateUpcomingWeeklyPrizesNextPage(page: Int?)
    suspend fun updateAppliedWeeklyPrizesNextPage(page: Int?)
    suspend fun updateAppliedDailyPrizesNextPage(page: Int?)
}

class CoinPrizePageInMemoryDataSource : CoinPrizePageLocalDataSource {
    private val _activeWeeklyPrizesNextPage = MutableStateFlow<Int?>(null)
    override val activeWeeklyPrizesNextPage: StateFlow<Int?> =
        _activeWeeklyPrizesNextPage.asStateFlow()

    private val _activeDailyPrizesNextPage = MutableStateFlow<Int?>(null)
    override val activeDailyPrizesNextPage: StateFlow<Int?> =
        _activeDailyPrizesNextPage.asStateFlow()

    private val _upcomingWeeklyPrizesNextPage = MutableStateFlow<Int?>(null)
    override val upcomingWeeklyPrizesNextPage: StateFlow<Int?> =
        _upcomingWeeklyPrizesNextPage.asStateFlow()

    private val _appliedWeeklyPrizesNextPage = MutableStateFlow<Int?>(null)
    override val appliedWeeklyPrizesNextPage: StateFlow<Int?> =
        _appliedWeeklyPrizesNextPage.asStateFlow()

    private val _appliedDailyPrizesNextPage = MutableStateFlow<Int?>(null)
    override val appliedDailyPrizesNextPage: StateFlow<Int?> =
        _appliedDailyPrizesNextPage.asStateFlow()

    override suspend fun updateActiveWeeklyPrizesNextPage(page: Int?) {
        _activeWeeklyPrizesNextPage.emit(page)
    }

    override suspend fun updateActiveDailyPrizesNextPage(page: Int?) {
        _activeDailyPrizesNextPage.emit(page)
    }

    override suspend fun updateUpcomingWeeklyPrizesNextPage(page: Int?) {
        _upcomingWeeklyPrizesNextPage.emit(page)
    }

    override suspend fun updateAppliedWeeklyPrizesNextPage(page: Int?) {
        _appliedWeeklyPrizesNextPage.emit(page)
    }

    override suspend fun updateAppliedDailyPrizesNextPage(page: Int?) {
        _appliedDailyPrizesNextPage.emit(page)
    }
}