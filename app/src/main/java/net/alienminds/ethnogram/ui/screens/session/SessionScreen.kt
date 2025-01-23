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
        val meState = remember { API.users.me }
        val me by meState.collectAsState(null)
        LaunchedEffect(me) {
            val fbUser = API.auth.currentUser
            println(
                "DB User: ${me.toString()}\n"+
                "FB User: ${fbUser.toString()}\n"+
                "Compare:\n"+
                "   UID: ${me?.uid} == ${fbUser?.uid} = ${me?.uid == fbUser?.uid}\n"+
                "   Phone: ${me?.phone} == ${fbUser?.phoneNumber} = ${me?.phone == fbUser?.phoneNumber}\n\n"
            )
        }
        Navigator(ContactsScreen()){
            SlidePageTransition(it)
        }
    }

}