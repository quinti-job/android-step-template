package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.repository.KyashCoinRepository

/**
 * デイリールーレットの結果を取得する
 *
 * コイン数の選択肢リストの種類を [dailyRouletteRate] で指定する。
 * キャンペーン終了日をまたいでルーレットを回すことを想定し、
 * ルーレット画面を開いたタイミングのコイン数選択肢リスト種類を渡す。
 */
interface GetDailyRouletteResultUseCase {

    /**
     * デイリールーレットの結果を取得する
     *
     * @param dailyRouletteRate コイン数の選択肢リストの種類
     * @return デイリールーレットの結果
     */
    suspend operator fun invoke(): Long
}

internal class GetDailyRouletteResult(
    private val repository: KyashCoinRepository,
) : GetDailyRouletteResultUseCase {
    override suspend fun invoke(): Long {
        return repository.getDailyRouletteResult()
    }
}