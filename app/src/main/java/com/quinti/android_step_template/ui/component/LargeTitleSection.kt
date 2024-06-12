package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
//import co.kyash.kig.compose.dimen16dp
//import coil.compose.rememberAsyncImagePainter

private const val HeaderImageAspectRatio = 1f
private val MaxWidth = 400.dp
private val CautionBackgroundColor = Color(0xFFFAE9E9)

/**
 * 未スクロール時のタイトルと景品画像
 */
@Composable
fun LargeTitleSection(
    title: String,
    imageUrl: String?,
    modifier: Modifier = Modifier,
) {
    val height = LocalConfiguration.current.screenWidthDp.dp / HeaderImageAspectRatio
    Column(
        modifier.fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .aspectRatio(HeaderImageAspectRatio),
        ) {
//            Image(
//                painter = rememberAsyncImagePainter(
//                    model = imageUrl,
//                ),
//                contentDescription = null,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.BottomCenter),
//                contentScale = ContentScale.Crop,
//            )
        }
//        Text(
//            text = title,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(
//                    top = dimen16dp,
//                    start = dimen16dp,
//                    end = dimen16dp,
//                ),
//            style = LargeTitleTopAppBarDefaults.LargeTitleTextStyle,
//        )
    }
}