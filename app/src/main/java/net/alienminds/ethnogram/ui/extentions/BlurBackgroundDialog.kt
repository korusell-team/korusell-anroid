package net.alienminds.ethnogram.ui.extentions

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.skydoves.cloudy.cloudy

private typealias OnDismiss = () -> Unit

class BlurDialogState(
    private val onDismiss: OnDismiss = { }
){
    var visible by mutableStateOf(false)
        private set

    fun show(){ visible = true }

    fun close(){
        visible = false
        onDismiss()
    }
}

@Composable
fun rememberBlurDialogState(onDismiss: OnDismiss = { }) = remember{ BlurDialogState(onDismiss) }

@Composable
fun BlurDialog(
    state: BlurDialogState = rememberBlurDialogState(),
    content: @Composable () -> Unit
){
    if (state.visible){
        Dialog(
            onDismissRequest = state::close,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            content = content
        )
    }
}

fun Modifier.applyBlurForDialog(
    state: BlurDialogState,
    radius: Int = 10,
): Modifier = composed{
    val animRadius by animateIntAsState(
        targetValue = when(state.visible){
            true -> radius
            false -> 0
        }
    )
    this.cloudy(
        radius = animRadius,
        enabled = state.visible
    )
}

