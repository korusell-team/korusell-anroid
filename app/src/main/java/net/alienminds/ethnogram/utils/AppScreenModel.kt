package net.alienminds.ethnogram.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class AppScreenModel: ScreenModel {

    var loading by mutableStateOf(false)
        protected set

    var error by mutableStateOf<Exception?>(null)
        protected set


    fun withLoadingScope(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit
    ) = screenModelScope.launch(context){
        loading = true
        block()
        loading = false
    }

    fun <T>Flow<T>.toState(initialValue: T): State<T>{
        val state = mutableStateOf(initialValue)
        screenModelScope.launch {
            collect{ state.value = it }
        }
        return state
    }

}