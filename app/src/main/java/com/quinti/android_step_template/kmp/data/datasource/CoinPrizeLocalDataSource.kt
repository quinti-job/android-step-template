package com.quinti.android_step_template.kmp.data.datasource

import com.quinti.android_step_template.kmp.data.entity.Prize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface CoinPrizeLocalDataSource {

    /**
     * Welcomeチャレンジ一覧
     */
    val welcomePrizes: Flow<List<Prize>>

    /**
     * 今週のウィークリーチャレンジ一覧
     */
    val activeWeeklyPrizes: Flow<List<Prize>>

    /**
     * 今日のデイリーチャレンジ一覧
     */
    val activeDailyPrizes: Flow<List<Prize>>

    /**
     * 来週のウィークリーチャレンジ一覧
     */
    val upcomingWeeklyPrizes: Flow<List<Prize>>

    /**
     * 応募済みのウィークリーチャレンジ一覧
     */
    val appliedWeeklyPrizes: Flow<List<Prize>>

    /**
     * 応募済みのデイリーチャレンジ一覧
     */
    val appliedDailyPrizes: Flow<List<Prize>>

    /**
     * 内部的に指定の懸賞の状態を更新する
     */
    suspend fun updatePrize(prize: Prize)

    suspend fun updateActiveWeeklyPrizes(prizes: List<Prize>, append: Boolean = false)
    suspend fun updateActiveDailyPrizes(prizes: List<Prize>, append: Boolean = false)
    suspend fun updateUpcomingWeeklyPrizes(prizes: List<Prize>, append: Boolean = false)
    suspend fun updateAppliedWeeklyPrizes(prizes: List<Prize>, append: Boolean = false)
    suspend fun updateAppliedDailyPrizes(prizes: List<Prize>, append: Boolean = false)
    suspend fun updateWelcomePrizes(prizes: List<Prize>, append: Boolean = false)
}

class CoinPrizeInMemoryDataSource : CoinPrizeLocalDataSource {

    private val _activeWeeklyPrizes = MutableStateFlow<List<Prize>>(emptyList())
    private val _activeDailyPrizes = MutableStateFlow<List<Prize>>(emptyList())
    private val _upcomingWeeklyPrizes = MutableStateFlow<List<Prize>>(emptyList())
    private val _appliedWeeklyPrizes = MutableStateFlow<List<Prize>>(emptyList())
    private val _appliedDailyPrizes = MutableStateFlow<List<Prize>>(emptyList())
    private val _welcomePrizes = MutableStateFlow<List<Prize>>(emptyList())

    override val activeWeeklyPrizes: StateFlow<List<Prize>> = _activeWeeklyPrizes.asStateFlow()
    override val activeDailyPrizes: StateFlow<List<Prize>> = _activeDailyPrizes.asStateFlow()
    override val upcomingWeeklyPrizes: StateFlow<List<Prize>> = _upcomingWeeklyPrizes.asStateFlow()
    override val appliedWeeklyPrizes: StateFlow<List<Prize>> = _appliedWeeklyPrizes.asStateFlow()
    override val appliedDailyPrizes: StateFlow<List<Prize>> = _appliedDailyPrizes.asStateFlow()
    override val welcomePrizes = _welcomePrizes.asStateFlow()

    override suspend fun updatePrize(prize: Prize) {
        _activeWeeklyPrizes.overridePrize(prize)
        _activeDailyPrizes.overridePrize(prize)
        _upcomingWeeklyPrizes.overridePrize(prize)
        _appliedWeeklyPrizes.overridePrize(prize)
        _appliedDailyPrizes.overridePrize(prize)
        _welcomePrizes.overridePrize(prize)
    }

    override suspend fun updateActiveWeeklyPrizes(prizes: List<Prize>, append: Boolean) {
        if (append) {
            _activeWeeklyPrizes.appendAll(prizes)
        } else {
            _activeWeeklyPrizes.replaceAll(prizes)
        }
    }

    override suspend fun updateActiveDailyPrizes(prizes: List<Prize>, append: Boolean) {
        if (append) {
            _activeDailyPrizes.appendAll(prizes)
        } else {
            _activeDailyPrizes.replaceAll(prizes)
        }
    }

    override suspend fun updateUpcomingWeeklyPrizes(prizes: List<Prize>, append: Boolean) {
        if (append) {
            _upcomingWeeklyPrizes.appendAll(prizes)
        } else {
            _upcomingWeeklyPrizes.replaceAll(prizes)
        }
    }

    override suspend fun updateAppliedDailyPrizes(prizes: List<Prize>, append: Boolean) {
        if (append) {
            _appliedDailyPrizes.appendAll(prizes)
        } else {
            _appliedDailyPrizes.replaceAll(prizes)
        }
    }

    override suspend fun updateAppliedWeeklyPrizes(prizes: List<Prize>, append: Boolean) {
        if (append) {
            _appliedWeeklyPrizes.appendAll(prizes)
        } else {
            _appliedWeeklyPrizes.replaceAll(prizes)
        }
    }

    override suspend fun updateWelcomePrizes(prizes: List<Prize>, append: Boolean) {
        if (append) {
            _welcomePrizes.appendAll(prizes)
        } else {
            _welcomePrizes.replaceAll(prizes)
        }
    }
}

private fun MutableStateFlow<List<Prize>>.replaceAll(list: List<Prize>) {
    update { list }
}

private fun MutableStateFlow<List<Prize>>.appendAll(list: List<Prize>) {
    update { current ->
        current.toMutableList().also { mutableList ->
            mutableList.addAll(list)
        }
    }
}

private fun MutableStateFlow<List<Prize>>.overridePrize(prize: Prize) {
    update { current ->
        current.toMutableList().map {
            if (it.id == prize.id) {
                prize
            } else {
                it
            }
        }
    }
}