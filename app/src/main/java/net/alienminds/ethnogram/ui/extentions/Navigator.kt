package net.alienminds.ethnogram.ui.extentions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow

val ProvidableCompositionLocal<Navigator?>.root: Navigator?
    @Composable get() = current?.getRoot()

val ProvidableCompositionLocal<Navigator?>.rootOrThrow: Navigator
    @Composable get() = currentOrThrow.getRoot()

fun Navigator.getRoot(): Navigator {
    var current = this
    while (current.level > 0){
        current = current.parent?: break
    }
    return current
}