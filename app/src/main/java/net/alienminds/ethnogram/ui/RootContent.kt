package net.alienminds.ethnogram.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import net.alienminds.ethnogram.service.API
import net.alienminds.ethnogram.ui.screens.auth.AuthScreen
import net.alienminds.ethnogram.ui.screens.session.SessionScreen
import net.alienminds.ethnogram.ui.theme.EthnogramTheme

@Composable
internal fun RootContent(){
    EthnogramTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ){
            Navigator(
                screen = when{
                    API.auth.isSignIn -> SessionScreen()
                    else -> AuthScreen()
                }
            )
        }
    }
}