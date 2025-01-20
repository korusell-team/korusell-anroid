package net.alienminds.ethnogram.ui.extentions.transitions

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.StackEvent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.ScreenTransition
import cafe.adriel.voyager.transitions.ScreenTransitionContent


internal interface PageTransitionScreen: Screen{
    val position: Int
}


@Composable
internal fun SlidePageTransition(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    content: ScreenTransitionContent = { it.Content() }
){
    ScreenTransition(
        navigator = navigator,
        modifier = modifier,
        content = content,
        transition = { calculateTransition(navigator.lastEvent) }
    )
}

private fun AnimatedContentTransitionScope<Screen>.calculateTransition(
    lastEvent: StackEvent
): ContentTransform {

    val targetPosition = (targetState as? PageTransitionScreen)?.position?: when(lastEvent){
        StackEvent.Pop -> 0
        else -> 1
    }
    val initialPosition = (initialState as? PageTransitionScreen)?.position?: when(lastEvent){
        StackEvent.Pop -> 1
        else -> 0
    }

    return when{
        targetPosition > initialPosition -> slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
        else -> slideInHorizontally { -it } togetherWith slideOutHorizontally { it }
    }
}

