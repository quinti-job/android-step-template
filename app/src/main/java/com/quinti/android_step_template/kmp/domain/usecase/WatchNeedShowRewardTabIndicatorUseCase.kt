package com.quinti.android_step_template.kmp.domain.usecase

import android.util.Log
import com.quinti.android_step_template.kmp.data.repository.KyashCoinRepository
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * リワードタブのインジケーターを表示するかどうかをFlowで返す
 *
 * * デイリールーレットが利用可能な場合はtrue
 * * 未確認の懸賞当落結果がある場合はtrue
 *
 * @return リワードタブのインジケーターを表示するかどうか
 */
interface WatchNeedShowRewardTabIndicatorUseCase {

    @Throws(IOException::class, CancellationException::class)
    @FlowInterop.Enabled
    operator fun invoke(): Flow<Boolean>
}

internal class WatchNeedShowRewardTabIndicator(
    private val kyashCoinRepository: KyashCoinRepository,
    private val prizeRepository: PrizeRepository,
    private val debounceTimeMillis: Long = RequestDebounceTimeMillis,
) : WatchNeedShowRewardTabIndicatorUseCase {
    @OptIn(FlowPreview::class)
    @Throws(IOException::class, CancellationException::class)
    @FlowInterop.Enabled
    override fun invoke(): Flow<Boolean> {
        // 指定秒数(ミリ秒)以内の連続リクエストは無視する
        // 例えば、リワードタブをタップしてすぐルーレット回したときなど
        return kyashCoinRepository.requestUpdateRewardTabIndicatorFlow
            .debounce(
                debounceTimeMillis,
            ).map {
                getNeedShowRewardTabIndicator()
            }
    }

    private suspend fun getNeedShowRewardTabIndicator(): Boolean {
        try {
            // ルーレットをまだ回していない場合はAvailableとなるので、インジケーターを表示する
            val dailyRouletteAvailable = kyashCoinRepository.getDailyRouletteStatus().available
            if (dailyRouletteAvailable) {
                return true
            }

            // 未確認の懸賞当落結果がある場合はインジケーターを表示する
            val uncheckedPrizes = prizeRepository.getUncheckedPrizes()?.count ?: 0
            if (uncheckedPrizes > 0) {
                return true
            }
            return false
        } catch (e: Exception) {
            Log.e("", "Failed to get need show reward tab indicator, ${e.message}")
            return false
        }
    }

    companion object {
        const val RequestDebounceTimeMillis = 1000L
    }
}