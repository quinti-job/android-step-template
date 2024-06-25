package com.quinti.android_step_template.ui.screen.agreement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.kmp.domain.analytics.TrackScreenEventV2
import com.quinti.android_step_template.kmp.domain.analytics.Tracking
import com.quinti.android_step_template.ui.component.KyashCloseTopAppBar
import com.quinti.android_step_template.ui.component.KyashScaffold
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun ServiceProviderAgreementScreen(
    onTapAgreementClose: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
    onAgree: () -> Unit,
    onTapAgreementCancel: () -> Unit,
) {
    TrackScreenEventV2(screen = Tracking.Screen.OfferWallAgreement)
    KyashScaffold(
        topBar = {
            KyashCloseTopAppBar(
                title = "",
                onNavigationClick = onTapAgreementClose,
                elevation = 0.dp,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .navigationBarsPadding()
                .padding(SocialNetworkTheme.spacing.m),
        ) {
            Spacer(modifier = Modifier.weight(1f))
            SkyFallServiceNotifySection(
                modifier = Modifier
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.weight(1.2f))
            PrivacyPolicySection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = SocialNetworkTheme.spacing.l),
                onPrivacyPolicyClick,
            )
            AgreeButton(
                modifier = Modifier.padding(bottom = SocialNetworkTheme.spacing.xs),
                onAgree = onAgree,
            )
            CanselButton(
                onCansel = onTapAgreementCancel,
            )
        }
    }
}

@Preview
@Composable
private fun AgreePersonalInformationDialogScreenPreview() {
    SocialNetworkTheme {
        ServiceProviderAgreementScreen(
            onTapAgreementClose = { },
            onPrivacyPolicyClick = { },
            onAgree = { },
            onTapAgreementCancel = { },
        )
    }
}
