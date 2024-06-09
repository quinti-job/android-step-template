package com.quinti.android_step_template.kmp.data.datasource.di

import com.quinti.android_step_template.kmp.data.datasource.AccountLocalDataSource
import com.quinti.android_step_template.kmp.data.datasource.AccountPersistenceDataSource

interface LocalDataSourceFactory {

    fun createAccountDataSource(): AccountLocalDataSource

//    fun createMeLocalDataSource(): MeLocalDataSource
//
//    fun createOtherOwnedBankTransferLocalDataSource(): OtherOwnedBankTransferLocalDataSource
//
//    fun createBudgetSettingLocalDataSource(): BudgetSettingLocalDataSource
//
//    fun createCustomCategoryDraftLocalDataSource(): CustomCategoryDraftLocalDataSource
//
//    fun createCoinPrizeLocalDataSource(): CoinPrizeLocalDataSource
//
//    fun createCoinPrizePaginationLocalDataSource(): CoinPrizePageLocalDataSource
//
//    fun createRewardLocalDataSource(): RewardLocalDataSource
//
//    fun createUserJourneyLocalDataSource(): UserJourneyLocalDataSource

    companion object {
        fun provide(): LocalDataSourceFactory {
            return SingletonLocalDataSourceFactory.instance
        }
    }
}

class SingletonLocalDataSourceFactory private constructor() : LocalDataSourceFactory {

    private val accountDataSource = AccountPersistenceDataSource()

//    private val meLocalDataSource = MeNoCacheDataSource()

//    private val otherOwnedBankTransferLocalDataSource =
//        OtherOwnedBankTransferInMemoryLocalDataSource()

//    private val budgetSettingLocalDataSource = BudgetSettingInMemoryLocalDataSource()
//    private val customCategoryDraftLocalDataSource = CustomCategoryDraftInMemoryLocalDataSource()
//    private val coinPrizeLocalDataSource = CoinPrizeInMemoryDataSource()
//    private val coinPrizePaginationLocalDataSource = CoinPrizePageInMemoryDataSource()
//    private val rewardLocalDataSource = RewardPersistenceDataSource()
//    private val userJourneyLocalDataSource = UserJourneyPersistenceDataSource()

    override fun createAccountDataSource(): AccountLocalDataSource {
        return accountDataSource
    }

//    override fun createMeLocalDataSource(): MeLocalDataSource {
//        return meLocalDataSource
//    }

//    override fun createOtherOwnedBankTransferLocalDataSource():
//            OtherOwnedBankTransferLocalDataSource {
//        return otherOwnedBankTransferLocalDataSource
//    }

//    override fun createBudgetSettingLocalDataSource(): BudgetSettingLocalDataSource {
//        return budgetSettingLocalDataSource
//    }
//
//    override fun createCustomCategoryDraftLocalDataSource(): CustomCategoryDraftLocalDataSource {
//        return customCategoryDraftLocalDataSource
//    }
//
//    override fun createCoinPrizeLocalDataSource(): CoinPrizeLocalDataSource {
//        return coinPrizeLocalDataSource
//    }
//
//    override fun createCoinPrizePaginationLocalDataSource(): CoinPrizePageLocalDataSource {
//        return coinPrizePaginationLocalDataSource
//    }
//
//    override fun createRewardLocalDataSource(): RewardLocalDataSource {
//        return rewardLocalDataSource
//    }
//
//    override fun createUserJourneyLocalDataSource(): UserJourneyLocalDataSource {
//        return userJourneyLocalDataSource
//    }

    companion object {
        val instance = SingletonLocalDataSourceFactory()
    }
}