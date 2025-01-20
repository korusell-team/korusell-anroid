package net.alienminds.ethnogram.ui.extentions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.alienminds.ethnogram.ui.theme.AppColor

@Composable
fun SettingsBlock(
    modifier: Modifier = Modifier,
    containerColor: Color = AppColor.white,
    shape: Shape = MaterialTheme.shapes.medium,
    content: @Composable SettingsBlockScope.() -> Unit
) = Column(
    modifier = modifier
        .background(containerColor, shape)
        .clip(shape)
){
    val scope = remember { SettingsBlockImpl() }
    content(scope)
}

interface SettingsBlockScope{

    @Composable
    fun CheckBoxItem(
        title: String,
        icon: @Composable () -> Unit,
        checked: Boolean,
        onCheck: (Boolean) -> Unit
    )

    @Composable
    fun CheckBoxItem(
        title: String,
        icon: @Composable () -> Unit,
        checked: Boolean,
        textValue: String,
        onChange: (Boolean, String) -> Unit
    )

    @Composable
    fun TextFieldItem(
        value: String,
        placeholder: String?,
        onValueChange: (String) -> Unit
    )

    @Composable
    fun TextFieldItem(
        icon: Painter,
        title: String,
        placeholder: String,
        value: String,
        onValueChange: (String) -> Unit
    )

    @Composable
    fun TextFieldItem(
        value: String,
        onValueChange: (String) -> Unit,
        maxLines: Int,
        minLines: Int,
        limit: Int
    )

    @Composable
    fun ClickableItem(
        title: String,
        value: String,
        onClick: () -> Unit
    )

    @Composable
    fun Divider(color: Color)


}

private class SettingsBlockImpl: SettingsBlockScope{

    @Composable
    override fun CheckBoxItem(
        title: String,
        icon: @Composable () -> Unit,
        checked: Boolean,
        textValue: String,
        onChange: (Boolean, String) -> Unit
    ) = CheckBoxBase(
        title = title,
        icon = icon,
        checked = checked,
        textValue = textValue,
        onCheck = onChange
    )

    @Composable
    override fun CheckBoxItem(
        title: String,
        icon: @Composable () -> Unit,
        checked: Boolean,
        onCheck: (Boolean) -> Unit
    ) = CheckBoxBase(
        title = title,
        icon = icon,
        checked = checked,
        textValue = null,
        onCheck = { b, _ -> onCheck(b) }
    )

    @Composable
    private fun CheckBoxBase(
        title: String,
        icon: @Composable () -> Unit,
        checked: Boolean,
        textValue: String?,
        onCheck: (Boolean, String) -> Unit
    ){
        val focusRequester = remember { FocusRequester() }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = null,
                    indication = null,
                    onClick = {
                        when (textValue == null) {
                            true -> onCheck(checked.not(), "")
                            false -> focusRequester.requestFocus()
                        }
                    }
                )
        ){
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp),
                content = { icon() }
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 44.dp, vertical = 8.dp)
            ){
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    color = AppColor.gray800
                )
                textValue?.let {
                    BasicTextField(
                        modifier = Modifier.focusRequester(focusRequester),
                        value = textValue,
                        onValueChange = { onCheck(checked, it) },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = AppColor.gray600
                        ),
                        singleLine = true
                    )
                }
            }
            Switch(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(8.dp)
                    .padding(end = 8.dp),
                checked = checked,
                onCheckedChange = { onCheck(it, textValue.orEmpty()) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = AppColor.white,
                    uncheckedThumbColor = AppColor.white,
                    checkedTrackColor = AppColor.greenA700,
                    uncheckedTrackColor = AppColor.gray200,
                    checkedBorderColor = Color.Transparent,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
    }


    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun TextFieldItem(
        value: String,
        placeholder: String?,
        onValueChange: (String) -> Unit
    ) = Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
    ){
        BasicTextField(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .imeNestedScroll(),
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            singleLine = true,
            decorationBox = { content ->
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty() && placeholder != null) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppColor.gray400
                        )
                    }
                    content()
                }
            }
        )
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun TextFieldItem(
        icon: Painter,
        title: String,
        placeholder: String,
        value: String,
        onValueChange: (String) -> Unit
    ) = Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier
                .padding(start = 26.dp)
                .height(24.dp),
            painter = icon,
            contentScale = ContentScale.FillHeight,
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )

        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .imeNestedScroll(),
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            singleLine = true,
            decorationBox = { content ->
                Box(
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppColor.gray400
                        )
                    }
                    content()
                }
            }
        )
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun TextFieldItem(
        value: String,
        onValueChange: (String) -> Unit,
        maxLines: Int,
        minLines: Int,
        limit: Int
    ) = Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ){
        BasicTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .imeNestedScroll(),
            value = value,
            onValueChange = { onValueChange(it.take(limit)) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            minLines = minLines,
            maxLines = maxLines
        )
        Text(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 16.dp),
            text = "${value.length}/$limit",
            style = MaterialTheme.typography.bodySmall,
            color = AppColor.gray500
        )
    }

    @Composable
    override fun ClickableItem(
        title: String,
        value: String,
        onClick: () -> Unit
    ) = Box (
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .clickable(onClick = onClick),
    ){
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(
                    start = 16.dp,
                    end = 48.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier.weight(0.4f, true),
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                modifier = Modifier
                    .weight(1f, true)
                    .padding(start = 8.dp),
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = AppColor.gray600
                )
            )
        }
        Icon(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .size(24.dp),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            tint = AppColor.gray600,
            contentDescription = null
        )
    }

    @Composable
    override fun Divider(color: Color) = HorizontalDivider(
        modifier = Modifier.padding(horizontal = 8.dp)
    )

}