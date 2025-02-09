package net.alienminds.ethnogram.ui.screens.session

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import net.alienminds.ethnogram.service.API
import net.alienminds.ethnogram.ui.extentions.transitions.SlidePageTransition
import net.alienminds.ethnogram.ui.screens.session.constacts.ContactsScreen

class SessionScreen: Screen {

    @Composable
    override fun Content() {
        Navigator(ContactsScreen()){
            SlidePageTransition(it)
        }
    }

}