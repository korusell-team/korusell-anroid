package net.alienminds.ethnogram.ui.extentions.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import net.alienminds.ethnogram.ui.theme.AppColor

interface DropdownButtonScope{

    @Composable
    fun ItemButton(
        text: String,
        icon: Painter,
        onClick: () -> Unit
    )
}

private class DropdownButtonImpl: DropdownButtonScope{

    @Composable
    override fun ItemButton(
        text: String,
        icon: Painter,
        onClick: () -> Unit
    ) = DropdownMenuItem(
        text = { Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = AppColor.gray800
        ) },
        trailingIcon = { Icon(
            modifier = Modifier.size(20.dp),
            painter = icon,
            tint = AppColor.gray800,
            contentDescription = null
        ) },
        onClick = onClick
    )

}

@Composable
fun DropdownButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    tint: Color = LocalContentColor.current,
    containerColor: Color = AppColor.gray100,
    content: @Composable DropdownButtonScope.() -> Unit
) = Box(
    modifier = modifier
){
    val scope = remember { DropdownButtonImpl() }
    var expanded by remember { mutableStateOf(false) }
    ActionButton(
        painter = painter,
        tint = tint,
        onClick = { expanded = expanded.not() }
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        containerColor = containerColor,
        shape = MaterialTheme.shapes.medium,
        content = { content(scope) }
    )
}
