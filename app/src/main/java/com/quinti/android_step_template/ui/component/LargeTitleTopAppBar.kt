package com.quinti.android_step_template.ui.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.quinti.android_step_template.ui.theme.SocialNetworkTheme
import com.quinti.android_step_template.ui.theme.contentColorFor
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * タイトルを大きく表示することのできる TopAppBar 。
 *
 * この LargeTitleTopAppBar は、KIG標準のスタイルでタイトルを表示する。
 * 通常のタイトルとラージタイトルのスタイルや表示コンテンツをカスタマイズしたい場合は、それぞれをスロットとして
 * Composable をパラメータに取る Composable を参照すること。
 *
 * [scrollBehavior] を指定することで、スクロールしたときのラージタイトルの動きをカスタマイズすることができる。
 * [liftOnScroll] を true にすると折りたたみ要素が完全に折りたたまれてから Elevation がつく挙動になる。
 */
@Composable
fun LargeTitleTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    largeTitleTextStyle: TextStyle = LargeTitleTopAppBarDefaults.LargeTitleTextStyle,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    collapsingContent: @Composable ColumnScope.() -> Unit = {},
    pinnedContent: @Composable () -> Unit = {},
    backgroundColor: Color = SocialNetworkTheme.colors.background,
    contentColor: Color = SocialNetworkTheme.colors.contentColorFor(backgroundColor),
    elevation: Dp = LargeTitleTopAppBarDefaults.TopAppBarElevation,
    windowInsets: WindowInsets = LargeTitleTopAppBarDefaults.TopAppBarWindowInsets,
    scrollBehavior: LargeTitleTopAppBarScrollBehavior? = null,
    liftOnScroll: Boolean = false,
) {
    LargeTitleTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        largeTitle = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = CollapsingTitleTopPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = CollapsingTitleHorizontalPadding),
                    style = largeTitleTextStyle,
                )
            }
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        collapsingContent = collapsingContent,
        pinnedContent = pinnedContent,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        windowInsets = windowInsets,
        scrollBehavior = scrollBehavior,
        liftOnScroll = liftOnScroll,
    )
}

/**
 * タイトルを大きく表示することのできる TopAppBar 。
 *
 * この LargeTitleTopAppBar は、通常のタイトルとラージタイトルをスロットで定義しているため、
 * それぞれのスタイルや表示コンテンツをカスタマイズすることができる。
 * 標準のテキストスタイルのタイトルを表示する場合は String をパラメータに持つ Composable を参照すること。
 *
 * [scrollBehavior] を指定することで、スクロールしたときのラージタイトルの動きをカスタマイズすることができる。
 * [liftOnScroll] を true にすると折りたたみ要素が完全に折りたたまれてから Elevation がつく挙動になる。
 */
@Composable
fun LargeTitleTopAppBar(
    title: @Composable (() -> Unit),
    largeTitle: @Composable (() -> Unit),
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    collapsingContent: @Composable ColumnScope.() -> Unit = {},
    pinnedContent: @Composable () -> Unit = {},
    backgroundColor: Color = SocialNetworkTheme.colors.background,
    contentColor: Color = SocialNetworkTheme.colors.contentColorFor(backgroundColor),
    elevation: Dp = LargeTitleTopAppBarDefaults.TopAppBarElevation,
    windowInsets: WindowInsets = LargeTitleTopAppBarDefaults.TopAppBarWindowInsets,
    scrollBehavior: LargeTitleTopAppBarScrollBehavior? = null,
    liftOnScroll: Boolean = false,
) {
    CollapsingTitleTopAppbarLayout(
        modifier = modifier,
        title = title,
        largeTitle = largeTitle,
        navigationIcon = navigationIcon,
        actions = actions,
        collapsingContent = collapsingContent,
        pinnedContent = pinnedContent,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation,
        windowInsets = windowInsets,
        scrollBehavior = scrollBehavior,
        liftOnScroll = liftOnScroll,
    )
}

/**
 * @see [LargeTitleTopAppBarDefaults.exitUntilCollapsedScrollBehavior]
 */
@Stable
interface LargeTitleTopAppBarScrollBehavior {
    val state: LargeTitleTopAppBarState
    val nestedScrollConnection: NestedScrollConnection
    val snapAnimationSpec: AnimationSpec<Float>?
    val flingAnimationSpec: DecayAnimationSpec<Float>?
}

@Composable
fun rememberTopAppBarState(
    initialLargeTitleHeightPx: Int = 0,
    initialHeightOffsetLimit: Float = 0f,
    initialHeightOffset: Float = 0f,
    initialContentOffset: Float = 0f,
): LargeTitleTopAppBarState {
    return rememberSaveable(saver = LargeTitleTopAppBarState.Saver) {
        LargeTitleTopAppBarState(
            initialLargeTitleHeightPx = initialLargeTitleHeightPx,
            initialHeightOffsetLimit = initialHeightOffsetLimit,
            initialHeightOffset = initialHeightOffset,
            initialContentOffset = initialContentOffset,
        )
    }
}

@Stable
class LargeTitleTopAppBarState(
    initialLargeTitleHeightPx: Int,
    initialHeightOffsetLimit: Float,
    initialHeightOffset: Float,
    initialContentOffset: Float,
) {
    /**
     * ラージタイトルの高さを保持する。
     */
    var largeTitleHeightPx by mutableStateOf(initialLargeTitleHeightPx)

    /**
     * TopAppBarをOffsetする最大の高さを保持する。
     * 値の更新は[heightOffsetLimit]を通じて行うことで制御する。
     */
    private var _heightOffsetLimit = mutableStateOf(initialHeightOffsetLimit)

    /**
     * TopAppBarをOffsetする高さを保持する。この高さは[heightOffsetLimit]を超えてはいけないため
     * 値の更新は[heightOffset]を通じて行うことで制御する。
     */
    private var _heightOffset = mutableStateOf(initialHeightOffset)

    /**
     * コンテンツ側のスクロール量を保持する。[collapsedFraction]の計算に利用する。
     */
    var contentOffset by mutableStateOf(initialContentOffset)

    /**
     * [_heightOffsetLimit]の値を参照・更新するためのプロパティ。
     */
    var heightOffsetLimit: Float
        get() = _heightOffsetLimit.value
        set(newOffset) {
            val prevCollapsedFraction = collapsedFraction
            _heightOffsetLimit.value = newOffset
            // ConfigurationChange などで heightOffsetLimit が変わることがある
            // 完全に折りたたまれている状態で高さ制限に更新があった時にその高さに Offset を設定する
            if (prevCollapsedFraction == 1f) {
                heightOffset = newOffset
            }
        }

    /**
     * [_heightOffset]の値を参照・更新するためのプロパティ。
     * [_heightOffset]が[heightOffsetLimit]を超えないように制御する
     */
    var heightOffset: Float
        get() = _heightOffset.value
        set(newOffset) {
            _heightOffset.value = newOffset.coerceIn(
                minimumValue = heightOffsetLimit,
                maximumValue = 0f,
            )
        }

    /**
     * ラージタイトルがどれだけOffsetされたかを示すプロパティ。
     * 1Fのときは折りたたむ部分が完全に折りたたまれている状態を表す。
     */
    val titleCollapsedFraction: Float
        get() = if (largeTitleHeightPx != 0) {
            (-heightOffset / largeTitleHeightPx).coerceIn(
                minimumValue = 0f,
                maximumValue = 1f,
            )
        } else {
            1f
        }

    /**
     * TopAppBarのCollapsing部分がどれだけOffsetされたかを示すプロパティ。
     * 1Fのときは折りたたむ部分が完全に折りたたまれている状態を表す。
     */
    val collapsedFraction: Float
        get() = if (heightOffsetLimit != 0f) {
            heightOffset / heightOffsetLimit
        } else {
            0f
        }

    companion object {

        val Saver: Saver<LargeTitleTopAppBarState, *> = listSaver(
            save = {
                listOf(
                    it.largeTitleHeightPx,
                    it.heightOffsetLimit,
                    it.heightOffset,
                    it.contentOffset,
                )
            },
            restore = {
                LargeTitleTopAppBarState(
                    initialLargeTitleHeightPx = it[0] as Int,
                    initialHeightOffsetLimit = it[1] as Float,
                    initialHeightOffset = it[2] as Float,
                    initialContentOffset = it[3] as Float,
                )
            },
        )
    }
}

object LargeTitleTopAppBarDefaults {

    val LargeTitleTextStyle: TextStyle
        @Composable
        get() = SocialNetworkTheme.typography.title3.copy(
            fontWeight = FontWeight.SemiBold,
        )

    val XLargeTitleTextStyle: TextStyle
        @Composable
        get() = SocialNetworkTheme.typography.title2.copy(
            fontWeight = FontWeight.SemiBold,
        )

    val TopAppBarElevation: Dp = AppBarDefaults.TopAppBarElevation

    val TopAppBarWindowInsets: WindowInsets
        @Composable
        get() = WindowInsets.systemBars
            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)

    @Composable
    fun exitUntilCollapsedScrollBehavior(
        state: LargeTitleTopAppBarState = rememberTopAppBarState(),
        snapAnimationSpec: AnimationSpec<Float>? = spring(stiffness = Spring.StiffnessMediumLow),
        flingAnimationSpec: DecayAnimationSpec<Float>? = rememberSplineBasedDecay(),
    ): LargeTitleTopAppBarScrollBehavior {
        return ExitUntilCollapsedScrollBehavior(
            state = state,
            snapAnimationSpec = snapAnimationSpec,
            flingAnimationSpec = flingAnimationSpec,
        )
    }
}

@Composable
private fun CollapsingTitleTopAppbarLayout(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit),
    largeTitle: @Composable (() -> Unit),
    navigationIcon: @Composable (() -> Unit)?,
    actions: @Composable RowScope.() -> Unit,
    collapsingContent: @Composable ColumnScope.() -> Unit = {},
    pinnedContent: @Composable () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    elevation: Dp,
    windowInsets: WindowInsets,
    scrollBehavior: LargeTitleTopAppBarScrollBehavior?,
    liftOnScroll: Boolean,
) {
    val titleCollapsedFraction = scrollBehavior?.state?.titleCollapsedFraction ?: 0f
    val collapsedFraction = scrollBehavior?.state?.collapsedFraction ?: 0f
    val topTitleAlpha = TopTitleAlphaEasing.transform(titleCollapsedFraction)
    val largeTitleAlpha = 1f - titleCollapsedFraction

    val surfaceElevation: Dp by animateDpAsState(
        targetValue = if (liftOnScroll && collapsedFraction < 1f) 0.dp else elevation,
        label = "Elevation dp animation",
    )

    // AppBarをドラッグしたときに開閉ができるようにする
    val appBarDragModifier = if (scrollBehavior != null) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
            },
            onDragStopped = { velocity ->
                settleAppBar(
                    scrollBehavior.state,
                    velocity,
                    scrollBehavior.flingAnimationSpec,
                    scrollBehavior.snapAnimationSpec,
                )
            },
        )
    } else {
        Modifier
    }

    Surface(
        modifier = modifier.then(appBarDragModifier),
        color = backgroundColor,
        contentColor = contentColor,
        elevation = surfaceElevation,
    ) {
        Layout(
            content = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.graphicsLayer(alpha = topTitleAlpha),
                            content = { title() },
                        )
                    },
                    modifier = Modifier.layoutId("pinnedTopAppBar"),
                    navigationIcon = navigationIcon,
                    actions = actions,
                    backgroundColor = backgroundColor,
                    contentColor = contentColor,
                    elevation = 0.dp,
                )
                Box(
                    modifier = Modifier
                        .alpha(largeTitleAlpha)
                        .layoutId("collapsingTitle"),
                    content = { largeTitle() },
                )
                Column(
                    modifier = Modifier.layoutId("collapsingContent"),
                    content = { collapsingContent() },
                )
                Box(
                    modifier = Modifier.layoutId("pinnedContent"),
                    content = { pinnedContent() },
                )
            },
            modifier = Modifier
                .windowInsetsPadding(windowInsets)
                // WindowInsetsのPadding部分にLayout要素が描画されないようにする
                .clipToBounds(),
        ) { measurables, constraints ->
            val pinnedTopAppBarPlaceable = measurables.first { it.layoutId == "pinnedTopAppBar" }
                .measure(constraints.copy(minWidth = 0))
            val collapsingTitlePlaceable = measurables.first { it.layoutId == "collapsingTitle" }
                .measure(constraints.copy(minWidth = 0))
            val collapsingContentPlaceable =
                measurables.first { it.layoutId == "collapsingContent" }
                    .measure(constraints.copy(minWidth = 0))
            val pinnedContentPlaceable = measurables.first { it.layoutId == "pinnedContent" }
                .measure(constraints.copy(minWidth = 0))

            // ラージタイトルの高さを保持しておく
            scrollBehavior?.state?.largeTitleHeightPx = collapsingTitlePlaceable.height
            // Offset可能な高さを折りたたみ可能な要素の高さによって決定する。
            scrollBehavior?.state?.heightOffsetLimit = -collapsingTitlePlaceable.height.toFloat() -
                    collapsingContentPlaceable.height

            val heightOffset = scrollBehavior?.state?.heightOffset?.roundToInt() ?: 0
            val layoutHeight = pinnedTopAppBarPlaceable.height +
                    collapsingTitlePlaceable.height +
                    collapsingContentPlaceable.height +
                    pinnedContentPlaceable.height +
                    heightOffset

            layout(
                width = constraints.maxWidth,
                height = layoutHeight,
            ) {
                collapsingTitlePlaceable.placeRelative(
                    x = 0,
                    y = pinnedTopAppBarPlaceable.height + heightOffset,
                )
                collapsingContentPlaceable.placeRelative(
                    x = 0,
                    y = pinnedTopAppBarPlaceable.height +
                            collapsingTitlePlaceable.height +
                            heightOffset,
                )
                // 折りたたまないTopAppBarの下に潜らせる挙動にするためにあとに配置
                pinnedTopAppBarPlaceable.placeRelative(
                    x = 0,
                    y = 0,
                )
                pinnedContentPlaceable.placeRelative(
                    x = 0,
                    y = pinnedTopAppBarPlaceable.height +
                            collapsingTitlePlaceable.height +
                            collapsingContentPlaceable.height +
                            heightOffset,
                )
            }
        }
    }
}

/**
 * 子要素が下方向にスクロールするとすぐに折りたたまれ、子要素が完全に上方向にスクロールしきってから
 * コンテンツを開く挙動になる [LargeTitleTopAppBarScrollBehavior] 。
 */
private class ExitUntilCollapsedScrollBehavior(
    override val state: LargeTitleTopAppBarState,
    override val snapAnimationSpec: AnimationSpec<Float>?,
    override val flingAnimationSpec: DecayAnimationSpec<Float>?,
) : LargeTitleTopAppBarScrollBehavior {

    override var nestedScrollConnection = object : NestedScrollConnection {

        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            if (available.y > 0f) return Offset.Zero

            val prevHeightOffset = state.heightOffset
            state.heightOffset = state.heightOffset + available.y
            return if (prevHeightOffset != state.heightOffset) {
                // heightOffset が更新されたらその分だけ消費し実際のスクロールが起きないようにする
                available.copy(x = 0f)
            } else {
                Offset.Zero
            }
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset {
            state.contentOffset += consumed.y

            // 下方向へのスクロールがあるときは常に heightOffset を更新する
            if (available.y < 0f || consumed.y < 0f) {
                val oldHeightOffset = state.heightOffset
                state.heightOffset = state.heightOffset + consumed.y
                return Offset(0f, state.heightOffset - oldHeightOffset)
            }

            // 上方向へのスクロールが子要素の上端まで達したときは contentOffset をリセットする
            if (consumed.y == 0f && available.y > 0) {
                state.contentOffset = 0f
            }

            // 上方向へのスクロールが子要素の上端まで達して consumed.y == 0 のときに available に値が入る。
            // これを利用し available がある分だけ heightOffset に加算する
            if (available.y > 0f) {
                val oldHeightOffset = state.heightOffset
                state.heightOffset = state.heightOffset + available.y
                return Offset(0f, state.heightOffset - oldHeightOffset)
            }
            return Offset.Zero
        }

        override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
            return super.onPostFling(consumed, available) +
                    settleAppBar(
                        state = state,
                        velocity = available.y,
                        flingAnimationSpec = flingAnimationSpec,
                        snapAnimationSpec = snapAnimationSpec,
                    )
        }
    }
}

/**
 * Fling操作による縦方向のVelocityを処理する。
 *
 * ref: https://github.com/androidx/androidx/blob/413e401398ecd6420ded8e83ea79427389ee74fe/compose/material3/material3/src/commonMain/kotlin/androidx/compose/material3/AppBar.kt#L1629
 */
@Suppress("MagicNumber")
private suspend fun settleAppBar(
    state: LargeTitleTopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?,
): Velocity {
    // 折りたたみ部分が完全に閉じている、または完全に開いているときは何もしない。
    // Note: collapsedFraction は Float 制度なので 0f で比較していない
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainingVelocity = velocity

    // Velocityが残っている場合はアニメーションでの開閉を行う
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainingVelocity = this.velocity
                // avoid rounding errors and stop if anything is unconsumed
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
    }
    // AppBarをSnapする
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 &&
            state.heightOffset > state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
                animationSpec = snapAnimationSpec,
            ) { state.heightOffset = value }
        }
    }

    return Velocity(0f, remainingVelocity)
}

private val TopTitleAlphaEasing = CubicBezierEasing(.8f, 0f, .8f, .15f)
private val CollapsingTitleTopPadding = 16.dp
private val CollapsingTitleHorizontalPadding = 16.dp

@Preview
@Composable
private fun LargeTitleTopAppBarPreview() {
    SocialNetworkTheme {
        LargeTitleTopAppBar(
            title = "LargeTitleTopAppBar",
        )
    }
}

@Preview
@Composable
private fun LargeTitleTopAppBarPreview_LongTitle() {
    SocialNetworkTheme {
        LargeTitleTopAppBar(
            title = "LargeTitleTopAppBar LongLongLongLongTitleTitleTitleTitle",
        )
    }
}

@Preview
@Composable
private fun LargeTitleTopAppBarPreview_CollapsingContent() {
    SocialNetworkTheme {
        LargeTitleTopAppBar(
            title = "LargeTitleTopAppBar LongLongLongLongTitleTitleTitleTitle",
            collapsingContent = {
                Text(
                    text = "collapsingContent",
                    modifier = Modifier.padding(16.dp),
                )
            },
        )
    }
}

@Preview
@Composable
private fun LargeTitleTopAppBarPreview_PinnedContent() {
    SocialNetworkTheme {
        LargeTitleTopAppBar(
            title = "LargeTitleTopAppBar LongLongLongLongTitleTitleTitleTitle",
            pinnedContent = {
                Text(
                    text = "pinnedContent",
                    modifier = Modifier.padding(16.dp),
                )
            },
        )
    }
}

@Preview
@Composable
private fun LargeTitleTopAppBarPreview_AllContent() {
    SocialNetworkTheme {
        LargeTitleTopAppBar(
            title = "LargeTitleTopAppBar LongLongLongLongTitleTitleTitleTitle",
            collapsingContent = {
                Text(
                    text = "collapsingContent",
                    modifier = Modifier.padding(16.dp),
                )
            },
            pinnedContent = {
                Text(
                    text = "pinnedContent",
                    modifier = Modifier.padding(16.dp),
                    color = SocialNetworkTheme.colors.primary,
                )
            },
        )
    }
}
