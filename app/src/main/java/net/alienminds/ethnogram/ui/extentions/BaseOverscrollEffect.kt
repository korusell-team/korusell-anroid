package net.alienminds.ethnogram.ui.extentions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
class OverscrollState(
    private val scope: CoroutineScope,
    private val maxOverscroll: Float,
    private val animationSpec: AnimationSpec<Float>
): OverscrollEffect{

    private val animatable = Animatable(0f)
    val state = animatable.asState()

    override val effectModifier: Modifier
        get() = Modifier

    override val isInProgress: Boolean
        get() = animatable.value != 0f

    override suspend fun applyToFling(
        velocity: Velocity,
        performFling: suspend (Velocity) -> Velocity
    ) {
        performFling(velocity)
        scope.launch {
            animatable.animateTo(
                targetValue = 0f,
                animationSpec = animationSpec
            )
        }
    }

    override fun applyToScroll(
        delta: Offset,
        source: NestedScrollSource,
        performScroll: (Offset) -> Offset
    ): Offset {
        scope.launch {
            animatable.stop()
        }
        val consumed = performScroll(delta)
        val overscroll = delta - consumed
        if (overscroll != Offset.Zero) {
            scope.launch {
                val newOverscrollValue = animatable.value + overscroll.y
                animatable.snapTo(newOverscrollValue.coerceIn(-maxOverscroll, maxOverscroll))
            }
        }
        return consumed
    }

}

@Composable
fun rememberOverscrollState(
    maxOverscroll: Float,
    animationSpec: AnimationSpec<Float>,
    scope: CoroutineScope = rememberCoroutineScope()
) = remember { OverscrollState(
    scope = scope,
    maxOverscroll = maxOverscroll,
    animationSpec = animationSpec
) }