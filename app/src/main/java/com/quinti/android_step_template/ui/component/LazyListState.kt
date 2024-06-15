package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter

@Composable
fun OnScrollEnd(
    lazyListState: LazyListState,
    onAppearLastItem: () -> Unit,
) {
    val isReachedLast by remember(lazyListState) {
        derivedStateOf {
            lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyListState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(lazyListState) {
        snapshotFlow { isReachedLast }
            .filter { it }
            .collect {
                onAppearLastItem()
            }
    }
}
