package com.korusell.korusell.ui.extentions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun NetImage(
    modifier: Modifier = Modifier,
    url: String?,
    contentScale: ContentScale = ContentScale.Crop,
    placeHolder: @Composable () -> Unit
) = Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
){
    placeHolder()
    AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = url,
        contentScale = contentScale,
        contentDescription = null
    )
}