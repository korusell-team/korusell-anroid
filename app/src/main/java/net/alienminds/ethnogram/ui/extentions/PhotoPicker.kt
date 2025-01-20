package net.alienminds.ethnogram.ui.extentions

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlin.math.max


interface PhotoPicker{
    fun launch()
}

private class PhotoPickerImpl(
    private val maxPhoto: Int = 1,
    private val single: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    private val multiple: ManagedActivityResultLauncher<PickVisualMediaRequest, List<Uri>>,
): PhotoPicker{

    override fun launch(){
        when{
            maxPhoto > 1 -> multiple
            maxPhoto == 1 -> single
            else -> return
        }.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

}

@Composable
fun rememberPhotoPicker(
    maxPhoto: Int = 1,
    onSelect: (uris: List<Uri>) -> Unit
): PhotoPicker{

    val single = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { it?.let { onSelect(listOf(it)) } }
    )

    val multiple = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(max(2, maxPhoto)),
        onResult = onSelect
    )

    return remember(maxPhoto, single, multiple){ PhotoPickerImpl(maxPhoto, single, multiple) }
}