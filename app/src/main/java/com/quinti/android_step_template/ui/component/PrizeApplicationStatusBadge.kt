package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.entity.PrizeApplicableStatus
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import korlibs.time.DateTimeTz

private val availableBackgroundColor = Color(0xFFE3F2FF)
private val availableForegroundColor = Color(0xFF0D97FF)
private val comingSoonBackgroundColor = Color(0xFFFFF8E2)
private val comingSoonForegroundColor = Color(0xFFFF910F)
private val appliedBackgroundColor = Color(0xFFF7F8F9)
private val appliedForegroundColor = Color(0xFF808084)
private val closedBackgroundColor = Color(0xFF808084)
private val closedForegroundColor = Color(0xFFFFFFFF)

/**
 * 懸賞のステータス表示ラベル
 */
@Composable
fun PrizeApplicationStatusBadge(
    prizeStatusLabel: PrizeApplicableStatus,
    modifier: Modifier = Modifier,
    currentDateTime: DateTimeTz = DateTimeTz.nowLocal(),
) {
    Box(modifier) {
        when (prizeStatusLabel) {
            PrizeApplicableStatus.Applicable -> StatusBadgeApplicable()
            PrizeApplicableStatus.Applied -> StatusBadgeApplied()
            is PrizeApplicableStatus.Ended -> StatusBadgeClosed()
            PrizeApplicableStatus.NotYetApplicable -> StatusBadgeComingSoon()
            is PrizeApplicableStatus.Shortage -> StatusBadgeRemainingAmount(
                prizeStatusLabel.missingAmount,
            )
        }
    }
}

@Composable
private fun StatusBadgeClosed() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(SocialNetworkTheme.spacing.xxs))
            .background(color = closedBackgroundColor)
            .padding(
                vertical = SocialNetworkTheme.spacing.xxs,
                horizontal = SocialNetworkTheme.spacing.xs,
            ),
    ) {
        Text(
            stringResource(id = R.string.reward_prize_detail_status_label_closed),
            style = SocialNetworkTheme.typography.caption2Bold,
            color = closedForegroundColor,
        )
    }
}

@Composable
private fun StatusBadgeApplied() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(SocialNetworkTheme.spacing.xxs))
            .background(color = appliedBackgroundColor)
            .padding(
                vertical = SocialNetworkTheme.spacing.xxs,
                horizontal = SocialNetworkTheme.spacing.xs,
            ),
    ) {
        Text(
            stringResource(id = R.string.reward_prize_detail_status_label_applied),
            style = SocialNetworkTheme.typography.caption2Bold,
            color = appliedForegroundColor,
        )
    }
}

@Composable
private fun StatusBadgeRemainingAmount(
    remainingKyashCoinAmount: Long,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(SocialNetworkTheme.spacing.xxs))
            .padding(
                vertical = SocialNetworkTheme.spacing.xxs,
                horizontal = SocialNetworkTheme.spacing.xs,
            ),
    ) {
        Text(
            stringResource(
                id = R.string.reward_prize_detail_status_label_remaining_coin,
                remainingKyashCoinAmount,
            ),
            style = SocialNetworkTheme.typography.caption1,
            color = SocialNetworkTheme.colors.textColorTertiary,
        )
    }
}

@Composable
private fun StatusBadgeApplicable() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(SocialNetworkTheme.spacing.xxs))
            .background(color = availableBackgroundColor)
            .padding(
                vertical = SocialNetworkTheme.spacing.xxs,
                horizontal = SocialNetworkTheme.spacing.xs,
            ),
    ) {
        Text(
            stringResource(id = R.string.reward_prize_detail_status_label_applicable),
            style = SocialNetworkTheme.typography.caption2Bold,
            color = availableForegroundColor,
        )
    }
}

@Composable
private fun StatusBadgeComingSoon() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(SocialNetworkTheme.spacing.xxs))
            .background(color = comingSoonBackgroundColor)
            .padding(
                vertical = SocialNetworkTheme.spacing.xxs,
                horizontal = SocialNetworkTheme.spacing.xs,
            ),
    ) {
        Text(
            stringResource(id = R.string.reward_prize_detail_status_label_comingSoon),
            style = SocialNetworkTheme.typography.caption2Bold,
            color = comingSoonForegroundColor,
        )
    }
}

@Preview
@Composable
private fun PrizeApplicationStatusBadgePreview_shortage() {
    SocialNetworkTheme {
        Surface {
            PrizeApplicationStatusBadge(
                PrizeApplicableStatus.Shortage(200, 0.5f),
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@Preview
@Composable
private fun PrizeApplicationStatusBadgePreview_applicable() {
    SocialNetworkTheme {
        Surface {
            PrizeApplicationStatusBadge(
                PrizeApplicableStatus.Applicable,
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@Preview
@Composable
private fun PrizeApplicationStatusBadgePreview_comingSoon() {
    SocialNetworkTheme {
        Surface {
            PrizeApplicationStatusBadge(
                PrizeApplicableStatus.NotYetApplicable,
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@Preview
@Composable
private fun PrizeApplicationStatusBadgePreview_applied() {
    SocialNetworkTheme {
        Surface {
            PrizeApplicationStatusBadge(
                PrizeApplicableStatus.Applied,
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}

@Preview
@Composable
private fun PrizeApplicationStatusBadgePreview_closed() {
    SocialNetworkTheme {
        Surface {
            PrizeApplicationStatusBadge(
                PrizeApplicableStatus.Ended(PrizeApplicableStatus.Ended.Reason.LotteryPeriodEnded),
                modifier = Modifier.padding(SocialNetworkTheme.spacing.m),
            )
        }
    }
}