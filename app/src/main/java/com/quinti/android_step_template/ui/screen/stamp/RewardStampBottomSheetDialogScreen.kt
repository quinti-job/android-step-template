package com.quinti.android_step_template.ui.screen.stamp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.quinti.android_step_template.kmp.data.entity.Stamp
import com.quinti.android_step_template.kmp.data.entity.StampCard
import com.quinti.android_step_template.kmp.data.entity.StampCardUiState
import com.quinti.android_step_template.kmp.domain.analytics.TrackScreenEventV2
import com.quinti.android_step_template.kmp.domain.analytics.Tracking
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import korlibs.time.DateTimeTz

@Composable
fun RewardStampBottomSheetDialogScreen(
    stampCardUiState: StampCardUiState,
    onClose: () -> Unit,
    onHelpClick: () -> Unit,
) {
    TrackScreenEventV2(screen = Tracking.Screen.RewardStampCard)

    RewardStampBottomSheetSection(
        stampCardUiState = stampCardUiState,
        onClose = onClose,
        onHelpClick = onHelpClick,
    )
}

private fun mockStampCardUiState(): StampCardUiState {
    val stamps = listOf(
        Stamp(
            type = Stamp.Type.Normal,
            stampedAt = DateTimeTz.nowLocal(),
            exchangedAt = DateTimeTz.nowLocal(),
            reward = Stamp.Reward.Coin(amount = 100),
        ),
        Stamp(
            type = Stamp.Type.Normal,
            stampedAt = DateTimeTz.nowLocal(),
            exchangedAt = DateTimeTz.nowLocal(),
            reward = Stamp.Reward.Coin(amount = 200),
        ),
        Stamp(
            type = Stamp.Type.Normal,
            stampedAt = DateTimeTz.nowLocal(),
            exchangedAt = DateTimeTz.nowLocal(),
            reward = Stamp.Reward.Coin(amount = 300),
        ),
        Stamp(
            type = Stamp.Type.Bonus("初回ボーナス"),
            stampedAt = DateTimeTz.nowLocal(),
            exchangedAt = null,
            reward = Stamp.Reward.Point(
                amount = 250L,
                options = listOf(1, 100, 2, 4, 5, 1, 350, 250),
            ),
        ),
    )
    val stampCard = StampCard(
        id = "1",
        stamps = stamps,
        isCompleted = false,
    )
    return StampCardUiState(stampCard = stampCard)
}

@Preview
@Composable
private fun RewardStampScreenPreview() {
    val stampCardUiState = mockStampCardUiState()
    SocialNetworkTheme {
        RewardStampBottomSheetDialogScreen(
            stampCardUiState = stampCardUiState,
            onClose = { },
            onHelpClick = { },
        )
    }
}
