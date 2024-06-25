package com.quinti.android_step_template.util

import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    fontScale = 1f,
    showBackground = true,
    name = "Default font",
    group = "Font scales",
)
@Preview(
    fontScale = 2f,
    showBackground = true,
    name = "Large font",
    group = "Font scales",
)
annotation class PreviewFontScales

@Preview(
    locale = "ja",
    showBackground = true,
    name = "Japanese",
    group = "Languages",
)
@Preview(
    locale = "en",
    showBackground = true,
    name = "English",
    group = "Languages",
)
annotation class PreviewLocales

@Preview(
    device = "spec:width=411dp,height=891dp,dpi=420,orientation=landscape",
    showBackground = true,
    name = "Landscape",
    group = "Orientations",
)
@Preview(
    device = Devices.PIXEL_4,
    showBackground = true,
    name = "Portrait",
    group = "Orientations",
)
annotation class PreviewDeviceOrientations

