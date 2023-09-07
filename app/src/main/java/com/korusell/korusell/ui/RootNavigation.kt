package com.korusell.korusell.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavigation(
    startScreen: RootRoutes = RootRoutes.AUTH
){
    val rootNav = rememberNavController()
    NavHost(
        navController = rootNav,
        startDestination = startScreen.route
    ) {
        RootRoutes.values().forEach { screen ->
            composable(
                route = screen.route,
                content = { screen.content.invoke(rootNav) }
            )
        }
    }

}