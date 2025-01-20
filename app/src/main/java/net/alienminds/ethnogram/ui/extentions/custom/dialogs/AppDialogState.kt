package net.alienminds.ethnogram.ui.extentions.custom.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class AppDialogState {

    var visible by mutableStateOf(false)
        private set

    fun show() { visible = true }

    fun hide() { visible = false }

}

@Composable
fun rememberAppDialogState() = remember {
    AppDialogState()
}