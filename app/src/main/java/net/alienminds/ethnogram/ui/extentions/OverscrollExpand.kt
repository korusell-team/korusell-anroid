package net.alienminds.ethnogram.ui.extentions

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverscrollExpand(
    modifier: Modifier = Modifier,
    threshold: Dp = 80.dp,
    verticalArrangement: Arrangement.Vertical = Arrangement. Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable() (ColumnScope.(Float) -> Unit),
){
    val density = LocalDensity.current
    val pullToRefreshState = rememberPullToRefreshState()

    var dragOffset by remember { mutableFloatStateOf(0f) }

    var isExpanded by remember { mutableStateOf(false) }

    val expandedRatio = 1f - (-dragOffset / with(density){ threshold.toPx() })
    val nonExpandedRatio = pullToRefreshState.distanceFraction

    val ratio = when(isExpanded){
        true -> 1f - (-dragOffset / with(density){ threshold.toPx() })
        false -> pullToRefreshState.distanceFraction
    }.coerceIn(0f, 1f)

    println("$ratio | $expandedRatio | $nonExpandedRatio")

    Column(
        modifier
            .pullToRefresh(
                isRefreshing = false,
                state = pullToRefreshState,
                threshold = threshold,
                onRefresh = { isExpanded = true }
            )
            .verticalScroll(
                state = scrollState,
                enabled = isExpanded.not()
            )
            .draggable(
                enabled = isExpanded,
                state = rememberDraggableState { dragOffset += it },
                startDragImmediately = true,
                onDragStopped = { velocity ->
                    val velocityDp = with(density){ velocity.toDp() }
                    if (velocityDp <= -threshold){
                        isExpanded = false
                    } else{
                        dragOffset = 0f
                    }
                },
                orientation = Orientation.Vertical
            ),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        content = {
            content(ratio)
        }
    )
}