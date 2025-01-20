package net.alienminds.ethnogram.ui.extentions.custom.dialogs

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.alienminds.ethnogram.ui.theme.AppColor


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun <K, V>ChipPickerSheet(
    modifier: Modifier = Modifier,
    state: AppDialogState,
    itemsMap: Map<K, List<V>>,
    headerContent: (@Composable () -> Unit)? = null,
    groupTitle: (K) -> String? = { null },
    itemTitle: (V) -> String,
    itemSelected: (V) -> Boolean,
    onSelect: (K, V) -> Unit
){
    if (state.visible) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = { state.hide() }
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ){
                headerContent?.run {
                    invoke()
                    HorizontalDivider()
                }

                itemsMap.forEach { (group, items) ->
                    groupTitle(group)?.let { gTitle ->
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 32.dp)
                                .padding(
                                    top = 16.dp,
                                    bottom = 8.dp
                                ),
                            text = gTitle,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    FlowRow(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items.forEach { item ->
                            ChipItem(
                                title = itemTitle(item),
                                selected = itemSelected(item),
                                onClick = { onSelect(group, item) }
                            )
                        }
                    }
                    if (group != itemsMap.entries.last().key) {
                        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                    }
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            }
        }
    }
}

@Composable
private fun ChipItem(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
){
    val backgroundColor by animateColorAsState(when (selected) {
        true -> AppColor.blueGray700
        false -> AppColor.gray100
    })
    val textColor by animateColorAsState(when (selected) {
        true -> AppColor.blueGray100
        false -> AppColor.gray700
    })
    TextButton(
        modifier = modifier.height(34.dp),
        shape = CircleShape,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp
        ),
        colors = ButtonDefaults.textButtonColors(
            containerColor = backgroundColor
        ),
        onClick = onClick
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = textColor
        )
    }
}
