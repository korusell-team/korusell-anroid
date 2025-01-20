package net.alienminds.ethnogram.ui.screens.session.constacts.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.alienminds.ethnogram.ui.extentions.BlurDialog
import net.alienminds.ethnogram.ui.extentions.BlurDialogState
import net.alienminds.ethnogram.ui.theme.AppColor


data class FilterDialogItem<T>(
    val item: T,
    val selected: Boolean,
    val title: String,
){
    constructor(item: T, current: T?, title: String): this(
        item = item,
        selected = item == current,
        title = title
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T>FilterDialog(
    state: BlurDialogState,
    title: String,
    items: List<FilterDialogItem<T>>,
    onClick: (T) -> Unit
) = BlurDialog(
    state = state
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .padding(top = 48.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(AppColor.brown50, MaterialTheme.shapes.extraLarge)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(title)
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { state.close() },
                    imageVector = Icons.Default.Close,
                    tint = AppColor.gray700,
                    contentDescription = null
                )
            }
            FlowRow(
                modifier = Modifier

                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items.forEach { item ->
                    val backgroundColor by animateColorAsState(when (item.selected) {
                        true -> AppColor.blueGray700
                        false -> AppColor.gray100
                    })
                    val textColor by animateColorAsState(when (item.selected) {
                        true -> AppColor.blueGray100
                        false -> AppColor.gray700
                    })
                    TextButton(
                        modifier = Modifier.height(34.dp),
                        shape = CircleShape,
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp
                        ),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = backgroundColor
                        ),
                        onClick = { onClick(item.item) }
                    ) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelLarge,
                            color = textColor
                        )
                    }
                }
            }
        }
    }
}