package com.korusell.korusell.ui.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.korusell.korusell.ui.content.extentions.ContentNavigation
import com.korusell.korusell.ui.content.extentions.ContentTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    rootNav: NavHostController,
    startScreen: ContentRoutes = ContentRoutes.CONTACTS
){
    val navController = rememberNavController()
    val currentScreen = remember{ mutableStateOf(startScreen) }
    navController.currentScreenListener(currentScreen)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ContentTopBar(
                title = stringResource(currentScreen.value.labelId),
                onProfile = {},
                onLogout = {}
            )
        },
        content = {
            val paddings = PaddingValues(
                start = it.calculateStartPadding(LayoutDirection.Rtl),
                end = it.calculateEndPadding(LayoutDirection.Rtl),
                top = it.calculateTopPadding(),
                bottom = 0.dp
            )
            MainContent(
                modifier = Modifier.padding(paddings),
                navController = navController,
                startScreen = startScreen
            )
        },
        bottomBar = {
            ContentNavigation(
                navController = navController,
                currentScreen = currentScreen.value
            )
        }
    )

}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startScreen: ContentRoutes
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startScreen.route
    ) {
        ContentRoutes.values().forEach { screen ->
            composable(
                route = screen.route,
                content = { screen.content.invoke() }
            )
        }
    }
}

private fun NavHostController.currentScreenListener(
    screenState: MutableState<ContentRoutes>
){
    addOnDestinationChangedListener{ _, destination: NavDestination, _ ->
        screenState.value = ContentRoutes.values().find {
            it.route == destination.route
        }?: return@addOnDestinationChangedListener
    }
}