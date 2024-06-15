package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.kyash.kig.compose.theme.KyashTheme
import co.kyash.reward.R
import com.quinti.android_step_template.R

/**
 * Kyashコインの表示
 *
 * アイコンと一緒に表示する場合のコンポーネント。
 */
@Composable
fun KyashCoinWithIcon(
    amount: Long?,
    modifier: Modifier = Modifier,
) {
    val fontScale = LocalDensity.current.fontScale
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        KyashCoinIcon(
            modifier = Modifier.size(18.dp * fontScale), // fontの大きさと合わせる
        )
        if (amount != null) {
            Text(
                text = stringResource(
                    id = R.string.reward_kyach_coin_balance,
                    amount,
                ),
                style = KyashTheme.typography.bodyBold,
            )
        } else {
            Text(
                text = "-",
                style = KyashTheme.typography.bodyBold,
            )
        }
    }
}

@Composable
fun DisabledKyashCoinWithIcon(
    amount: Long?,
    modifier: Modifier = Modifier,
) {
    val foregroundColor = KyashTheme.colors.textColorExtraLight
    val fontScale = LocalDensity.current.fontScale
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        DisabledKyashCoinIcon(
            modifier = Modifier.size(16.dp * fontScale), // fontの大きさと合わせる
        )
        if (amount != null) {
            Text(
                text = stringResource(
                    id = R.string.reward_kyach_coin_balance,
                    amount,
                ),
                style = KyashTheme.typography.bodyBold,
                color = foregroundColor,
            )
        } else {
            Text(
                text = "-",
                style = KyashTheme.typography.bodyBold,
                color = foregroundColor,
            )
        }
    }
}

@Preview
@Composable
private fun KyashCoinWithIconPreview() {
    KyashTheme {
        Surface {
            KyashCoinWithIcon(
                amount = 6000,
                modifier = Modifier,
            )
        }
    }
}

@Preview
@Composable
private fun KyashCoinWithIconPreview_null() {
    KyashTheme {
        Surface {
            KyashCoinWithIcon(
                amount = null,
                modifier = Modifier,
            )
        }
    }
}