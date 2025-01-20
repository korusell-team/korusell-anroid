package net.alienminds.ethnogram.ui.extentions.custom

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(
    modifier: Modifier,
    pagerState: PagerState,
    tint: Color = MaterialTheme.colorScheme.outline
) = PageIndicator(
    modifier = modifier,
    currentPage = pagerState.currentPage,
    pageCount = pagerState.pageCount,
    tint = tint
)

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    currentPage: Int,
    pageCount: Int,
    tint: Color = MaterialTheme.colorScheme.outline
){
    if (pageCount > 1) {
        Row(
            modifier = modifier
        ) {
            repeat(pageCount) {
                val alpha by animateFloatAsState(
                    targetValue = when(currentPage == it){
                        true -> 1f
                        false -> 0.3f
                    },
                    animationSpec = tween(700)
                )
                Box(
                    modifier = Modifier
                        .weight(1f, true)
                        .height(2.dp)
                        .padding(horizontal = 4.dp)
                        .background(
                            color = tint.copy(alpha = alpha),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}