package net.alienminds.ethnogram.ui.extentions.buttons

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    tint: Color = LocalContentColor.current,
    enabled: Boolean = true,
) = ActionButton(
    modifier = modifier,
    painter = rememberVectorPainter(imageVector),
    onClick = onClick,
    tint = tint,
    enabled = enabled
)

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    painter: Painter,
    onClick: () -> Unit,
    tint: Color = LocalContentColor.current,
    enabled: Boolean = true,
) = IconButton(
    modifier = modifier.defaultMinSize(32.dp, 32.dp),
    onClick = onClick,
    enabled = enabled
) {
    Icon(
        modifier = Modifier.padding(4.dp).fillMaxSize(),
        painter = painter,
        tint = tint,
        contentDescription = null
    )
}