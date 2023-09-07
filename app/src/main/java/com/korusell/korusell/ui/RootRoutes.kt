package com.korusell.korusell.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.korusell.korusell.ui.auth.Auth
import com.korusell.korusell.ui.content.Content

enum class RootRoutes(
    val route: String,
    val content: @Composable (rootNav: NavHostController) -> Unit
) {
    AUTH(
        route = "auth",
        content = { Auth(it) }
    ),
    CONTENT(
        route = "content",
        content = { Content(it) }
    )
}