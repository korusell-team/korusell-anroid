package com.korusell.korusell.ui.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.korusell.korusell.ui.RootRoutes

@Composable
fun Auth(
    rootNav: NavHostController,
    startScreen: AuthRoutes = AuthRoutes.ONBOARDING
){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startScreen.route
    ) {
        AuthRoutes.values().forEach { screen ->
            composable(
                route = screen.route,
                content = {
                    screen.content.invoke(navController){
                        rootNav.navigate(RootRoutes.CONTENT.route)
                    }
                }
            )
        }
    }
}