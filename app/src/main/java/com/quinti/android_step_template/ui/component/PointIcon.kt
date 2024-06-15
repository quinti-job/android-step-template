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
fun PointIcon(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.reward_ic_point),
        contentDescription = null,
        modifier = modifier
            .padding(top = 1.dp),
    )
}

@Composable
fun DisabledPointIcon(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.reward_ic_disabled_point),
        contentDescription = null,
        modifier = modifier
            .padding(top = 1.dp),
    )
}