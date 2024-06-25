package com.quinti.android_step_template.ui.config

interface RewardConfig {
    /**
     * リワードトップ初期化時に自動的にルーレット画面に遷移する機能が有効化か
     */
    val rewardTopAutoNavigateToRouletteEnabled: Boolean
}

data class RewardConfigData(
    override val rewardTopAutoNavigateToRouletteEnabled: Boolean,
) : RewardConfig

