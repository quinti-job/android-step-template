package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import co.kyash.reward.R
import com.quinti.android_step_template.R

@Composable
fun KyashCoinIcon(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.reward_ic_kyash_coin),
        contentDescription = null,
        modifier = modifier
            .padding(top = 1.dp),
    )
}

@Composable
fun DisabledKyashCoinIcon(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.reward_ic_disabled_kyash_coin),
        contentDescription = null,
        modifier = modifier
            .padding(top = 1.dp),
    )
}