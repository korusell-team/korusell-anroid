package net.alienminds.ethnogram.ui.extentions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun Modifier.shimmerState(
    enable: Boolean,
    customShimmer: Shimmer = rememberShimmer(ShimmerBounds.View)
) = when(enable) {
    true -> this.shimmer(customShimmer)
    false -> this
}