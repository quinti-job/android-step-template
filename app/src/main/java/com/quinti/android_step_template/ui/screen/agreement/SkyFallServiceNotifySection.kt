package com.quinti.android_step_template.ui.screen.agreement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.quinti.android_step_template.R
import com.quinti.android_step_template.ui.component.KyashButton
import com.quinti.android_step_template.ui.component.KyashTextButton
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun SkyFallServiceNotifySection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .padding(bottom = SocialNetworkTheme.spacing.xxl),
        ) {
            Image(
                painter = painterResource(id = R.drawable.reward_img_offer_wall_logo),
                contentDescription = null,
            )
        }
        Text(
            text = stringResource(
                id = R.string.reward_offerwall_service_provider_agreement_title,
            ),
            style = SocialNetworkTheme.typography.title3Bold,
            color = SocialNetworkTheme.colors.textColorPrimary,
            modifier = Modifier.padding(bottom = SocialNetworkTheme.spacing.m),
            textAlign = TextAlign.Center,
        )
        Text(
            text = stringResource(
                id = R.string.reward_offerwall_service_provider_agreement_description,
            ),
            style = SocialNetworkTheme.typography.caption1,
            color = SocialNetworkTheme.colors.textColorSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun PrivacyPolicySection(
    modifier: Modifier = Modifier,
    onPrivacyPolicyClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(
                SocialNetworkTheme.colors.backgroundVariant,
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(SocialNetworkTheme.spacing.m)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                SocialNetworkTheme.spacing.xxs,
            ),
        ) {
            Text(
                text = stringResource(
                    id = R.string.reward_offerwall_personal_information_agreement_description_1,
                ),
                style = SocialNetworkTheme.typography.caption2,
                color = SocialNetworkTheme.colors.textColorSecondary,
            )
            VignetteText(
                text = stringResource(
                    id = R.string.reward_offerwall_personal_information_agreement_description_2,
                ),
            )
            VignetteText(
                text = stringResource(
                    id = R.string.reward_offerwall_personal_information_agreement_description_3,
                ),
            )
            LinkText(
                onPrivacyPolicyClick = onPrivacyPolicyClick,
            )
        }
    }
}

@Composable
private fun VignetteText(
    text: String,
) {
    Row {
        Text(
            text = stringResource(id = R.string.reward_prize_detail_notice_dot),
            style = SocialNetworkTheme.typography.caption2,
            color = SocialNetworkTheme.colors.textColorSecondary,
        )
        Text(
            text = text,
            style = SocialNetworkTheme.typography.caption2,
            color = SocialNetworkTheme.colors.textColorSecondary,
        )
    }
}

@Composable
private fun LinkText(
    onPrivacyPolicyClick: () -> Unit,
) {
    val description = stringResource(
        id = R.string.reward_offerwall_personal_information_agreement_description_4,
    )
    val link = stringResource(
        id = R.string.reward_offerwall_personal_information_agreement_description_link,
    )
    val startIndex = description.indexOf(link)
    val endIndex = startIndex + link.length

    val text = buildAnnotatedString {
        append(description)
        addStyle(
            SpanStyle(
                color = SocialNetworkTheme.colors.primary,
            ),
            start = startIndex,
            end = endIndex,
        )
    }

    ClickableText(
        text = text,
        onClick = {
            if (it in (startIndex + 1) until endIndex) {
                onPrivacyPolicyClick()
            }
        },
        style = SocialNetworkTheme.typography.caption2.copy(
            color = SocialNetworkTheme.colors.textColorSecondary,
        ),
    )
}

@Composable
fun AgreeButton(
    modifier: Modifier = Modifier,
    onAgree: () -> Unit,
) {
    KyashButton(
        modifier = modifier
            .fillMaxWidth(),
        text = stringResource(
            id = R.string.reward_offerwall_personal_information_agreement_button,
        ),
        onClick = onAgree,
    )
}

@Composable
fun CanselButton(
    modifier: Modifier = Modifier,
    onCansel: () -> Unit,
) {
    KyashTextButton(
        modifier = modifier
            .fillMaxWidth(),
        text = stringResource(
            id = R.string.reward_offerwall_personal_information_agreement_cansel_button,
        ),
        onClick = onCansel,
    )
}

@Preview
@Composable
private fun SkyFallServiceNotifySectionPreview() {
    SocialNetworkTheme {
        SkyFallServiceNotifySection(
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun PrivacyPolicySectionPreview() {
    SocialNetworkTheme {
        PrivacyPolicySection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = SocialNetworkTheme.spacing.l)
                .background(SocialNetworkTheme.colors.backgroundVariant),
            onPrivacyPolicyClick = {},
        )
    }
}