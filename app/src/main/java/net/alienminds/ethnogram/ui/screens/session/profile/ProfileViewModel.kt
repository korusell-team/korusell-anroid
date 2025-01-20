package net.alienminds.ethnogram.ui.screens.session.profile

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.Navigator
import net.alienminds.ethnogram.service.API
import net.alienminds.ethnogram.ui.extentions.getRoot
import net.alienminds.ethnogram.ui.screens.auth.AuthScreen
import net.alienminds.ethnogram.utils.AppScreenModel

class ProfileViewModel(
    userId: String?
): AppScreenModel() {


    private val allCities by API.cities.all.toState(emptyList())
    private val allCategories by API.categories.all.toState(emptyList())
    private val meState = API.users.me
    private val me by meState.toState(null)

    val isMe by derivedStateOf { userId.isNullOrEmpty() || me?.uid == userId }

    val user by when(isMe){
        true -> meState
        false -> API.users.getUser(userId.orEmpty())
    }.toState(null)

    val city by derivedStateOf {
        user?.cities?.mapNotNull{ id ->
            allCities.find { it.id == id }
        }?: emptyList()
    }

    val categories by derivedStateOf {
        user?.categories?.mapNotNull { id ->
            allCategories.find { it.id == id }
        }?: emptyList()
    }

    val isFavorite by derivedStateOf { user?.likes?.any { it == me?.uid }?: false }


    fun changeFavorite(value: Boolean) = withLoadingScope{
        user?.uid?.let {
            API.users.favoriteUser(it, value)
        }
    }

    fun blockUser(navigator: Navigator?) = withLoadingScope{
        user?.uid?.let {
            val isSuccess = API.users.blockUser(it).data?: false
            if (isSuccess){
                navigator?.pop()
            }
        }

    }

    fun reportUser(navigator: Navigator?) = withLoadingScope{
        user?.uid?.let {
            val isSuccess = API.users.reportUser(it).data?: false
            if (isSuccess){
                navigator?.pop()
            }
        }
    }

    fun logout(navigator: Navigator?) = withLoadingScope{
        API.auth.logout()
        navigator?.getRoot()?.replaceAll(AuthScreen())
    }

}