package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetDefaults
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme

@Composable
fun KyashDialogScreen(
    title: @Composable (() -> Unit)?,
    message: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    positiveButton: @Composable (RowScope.() -> Unit)? = null,
    negativeButton: @Composable (RowScope.() -> Unit)? = null,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = SocialNetworkTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = dimen16dp, vertical = dimen32dp),
            verticalArrangement = Arrangement.spacedBy(dimen24dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = SocialNetworkTheme.spacing.xs),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (title != null) {
                    val defaultStyle = SocialNetworkTheme.typography.headline.copy(
                        fontWeight = FontWeight.Bold,
                    )
                    ProvideTextStyle(value = defaultStyle) {
                        title()
                    }
                }
                if (message != null) {
                    val defaultStyle = SocialNetworkTheme.typography.paragraph2
                    ProvideTextStyle(value = defaultStyle) {
                        message()
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimen8dp),
            ) {
                if (negativeButton != null) {
                    negativeButton()
                }
                if (positiveButton != null) {
                    positiveButton()
                }
            }
        }
    }
}

@Composable
fun KyashDialogScreen(
    titleText: String?,
    messageText: String?,
    modifier: Modifier = Modifier,
    positiveButton: @Composable (RowScope.() -> Unit)? = null,
    negativeButton: @Composable (RowScope.() -> Unit)? = null,
) = KyashDialogScreen(
    title = titleText?.let { { Text(text = titleText) } },
    message = messageText?.let { { Text(text = messageText) } },
    modifier = modifier,
    positiveButton = positiveButton,
    negativeButton = negativeButton,
)

/**
 * マテリアルデザインのアラートダイアログを表示する
 *
 * タイトルやボディがテキストのみでスタイルを変更する必要がない場合は、
 * titleTextをパラメータに受け取る関数を検討してください。
 */
@Composable
fun KyashAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    body: @Composable (() -> Unit)? = null,
    shape: Shape = SocialNetworkTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier.fillMaxWidth(),
        dismissButton = dismissButton,
        title = title?.let {
            {
                ProvideTextStyle(
                    TextStyle(fontWeight = FontWeight.Bold),
                    title,
                )
            }
        },
        text = body,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        properties = properties,
    )
}

/**
 * マテリアルデザインのアラートダイアログを表示する
 *
 * タイトルやボディにスタイルを適用する場合やテキスト以外の表示をする場合は、
 * titleとbodyを Composable 関数で受け取る関数を検討してください。
 */
@Composable
fun KyashAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    titleText: String? = null,
    bodyText: String? = null,
    shape: Shape = SocialNetworkTheme.shapes.large,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
) = KyashAlertDialog(
    onDismissRequest = onDismissRequest,
    confirmButton = confirmButton,
    modifier = modifier,
    dismissButton = dismissButton,
    title = titleText?.let { { Text(titleText) } },
    body = bodyText?.let { { Text(bodyText) } },
    shape = shape,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    properties = properties,
)

@Composable
fun SimpleDialogScreen(
    modifier: Modifier = Modifier,
    title: String?,
    description: String?,
    descriptionAlign: TextAlign = TextAlign.Center,
    positiveText: String?,
    onPositiveClick: () -> Unit = {},
    negativeText: String?,
    onNegativeClick: () -> Unit = {},
) {
    Surface(
        shape = SocialNetworkTheme.shapes.medium,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(start = dimen16dp, top = dimen32dp, end = dimen16dp, bottom = dimen16dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (title != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = SocialNetworkTheme.typography.headline.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    ),
                )
                Spacer(modifier = Modifier.size(dimen16dp))
            }
            if (description != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    style = SocialNetworkTheme.typography.caption1,
                    textAlign = descriptionAlign,
                )
                Spacer(modifier = Modifier.size(dimen16dp))
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                if (negativeText != null) {
                    KyashCornerRound8dpMaterialSaxButton(
                        modifier = Modifier.weight(1f),
                        onClick = onNegativeClick,
                        text = negativeText,
                    )
                    Spacer(modifier = Modifier.size(dimen12dp))
                }
                if (positiveText != null) {
                    KyashCornerRound8dpMaterialKyashBlueButton(
                        modifier = Modifier.weight(1f),
                        onClick = onPositiveClick,
                        text = positiveText,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeclarativeModalBottomSheetLayout(
    needShow: Boolean,
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    showHandle: Boolean = true,
    sheetShape: Shape = RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp,
    ),
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    scrimColor: Color = ModalBottomSheetDefaults.scrimColor,
    content: @Composable () -> Unit,
) {
    val state = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            if (it == ModalBottomSheetValue.Hidden) {
                onClose()
            }
            true
        },
    )
    LaunchedEffect(
        key1 = needShow,
    ) {
        if (needShow) {
            state.show()
        } else {
            if (state.isVisible) {
                state.hide()
            }
        }
    }

    KyashModalBottomSheetLayout(
        modifier = modifier,
        sheetState = state,
        sheetShape = sheetShape,
        showHandle = showHandle,
        sheetElevation = sheetElevation,
        sheetContentColor = sheetContentColor,
        scrimColor = scrimColor,
        sheetContent = sheetContent,
        content = content,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun KyashModalBottomSheetLayout(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    showHandle: Boolean = true,
    sheetState: ModalBottomSheetState =
        rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
    sheetShape: Shape = RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 12.dp,
    ),
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    sheetElevation: Dp = ModalBottomSheetDefaults.Elevation,
    sheetContentColor: Color = contentColorFor(sheetBackgroundColor),
    scrimColor: Color = ModalBottomSheetDefaults.scrimColor,
    content: @Composable () -> Unit,
) {
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        sheetShape = sheetShape,
        sheetBackgroundColor = sheetBackgroundColor,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding(),
            ) {
                if (showHandle) {
                    HalfModalBottomSheetHandle(
                        modifier = Modifier
                            .padding(dimen8dp)
                            .align(Alignment.CenterHorizontally),
                    )
                }
                sheetContent()
            }
        },
        sheetElevation = sheetElevation,
        sheetContentColor = sheetContentColor,
        scrimColor = scrimColor,
        content = content,
    )
}

@Composable
fun HalfModalBottomSheetHandle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(height = dimen4dp, width = dimen24dp)
            .background(
                color = SocialNetworkTheme.colors.backgroundDark,
                shape = RoundedCornerShape(dimen4dp),
            ),
    )
}

// region preview
@Preview
@Composable
private fun SimpleDialogWithoutTitleScreenPreview() {
    SimpleDialogScreen(
        title = null,
        description = "description",
        negativeText = "閉じる",
        positiveText = "OK",
    )
}

@Preview
@Composable
private fun SimpleDialogWithoutDescriptionScreenPreview() {
    SimpleDialogScreen(
        title = "Title",
        description = null,
        negativeText = "閉じる",
        positiveText = "OK",
    )
}

@Preview
@Composable
private fun SimpleDialogWithoutNegativeButtonScreenPreview() {
    SimpleDialogScreen(
        title = "Title",
        description = "description",
        negativeText = null,
        positiveText = "OK",
    )
}

@Preview
@Composable
private fun SimpleDialogWithoutPositiveButtonScreenPreview() {
    SimpleDialogScreen(
        title = "Title",
        description = "description",
        negativeText = "閉じる",
        positiveText = null,
    )
}

@Preview
@Composable
fun HalfModalBottomSheetHandlePreview() {
    HalfModalBottomSheetHandle()
}

@Preview
@Composable
private fun KyashAlertDialogPreview() {
    SocialNetworkTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            KyashAlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    KyashTextButton(
                        text = "閉じる",
                        onClick = {},
                    )
                },
                dismissButton = {
                    KyashTextButton(
                        text = "キャンセル",
                        onClick = {},
                    )
                },
                title = {
                    Text("集計対象について")
                },
                body = {
                    Text("集計対象項目をオフにすると、カテゴリ・予算ページでの集計の対象外となります。")
                },
            )
        }
    }
}
