package com.korusell.korusell.ui.content.extentions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentTopBar(
    title: String,
    onLogout: () -> Unit,
    onProfile: () -> Unit
) = CenterAlignedTopAppBar(
    navigationIcon = { LogoutIcon(onLogout) },
    title = { TitleBar(title) },
    actions = { ProfileIcon(onProfile) }
)

@Composable
private fun TitleBar(title: String) = Text(
    text = title,
    style = MaterialTheme.typography.titleLarge//TODO
)

@Composable
private fun LogoutIcon(
    onLogout: () -> Unit
) = IconButton(onClick = onLogout) {
    Icon(
        modifier = Modifier
            .graphicsLayer {
                rotationZ = 90F
            },
        imageVector = Icons.Filled.Key,
        contentDescription = null
    )
}

@Composable
private fun ProfileIcon(
    onProfile: () -> Unit
) = IconButton(onClick = onProfile) {
    Icon(
        imageVector = Icons.Outlined.AccountCircle,
        contentDescription = null
    )
}