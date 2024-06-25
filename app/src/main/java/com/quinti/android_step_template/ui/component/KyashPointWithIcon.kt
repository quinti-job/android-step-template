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
import com.quinti.android_step_template.R
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

/**
 * Kyashコインの表示
 *
 * アイコンと一緒に表示する場合のコンポーネント。
 */
@Composable
fun KyashPointWithIcon(
    amount: PointBalance?,
    modifier: Modifier = Modifier,
) {
    val fontScale = LocalDensity.current.fontScale
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        PointIcon(
            modifier = Modifier.size(18.dp * fontScale), // fontの大きさと合わせる
        )
        if (amount != null) {
            Text(
                text = stringResource(
                    id = R.string.reward_kyach_coin_balance,
                    amount.availableAmount,
                ),
                style = SocialNetworkTheme.typography.bodyBold,
            )
        } else {
            Text(
                text = "-",
                style = SocialNetworkTheme.typography.bodyBold,
            )
        }
    }
}

@Preview
@Composable
fun KyashPointWithIconPreview() {
    SocialNetworkTheme {
        Surface {
            KyashPointWithIcon(
                amount = PointBalance(
                    availableAmount = 100,
                    pendingAmount = 100,
                ),
            )
        }
    }
}