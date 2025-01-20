package net.alienminds.ethnogram.ui.extentions.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import net.alienminds.ethnogram.ui.extentions.BackPressHandler

@Composable
internal fun BackButton(
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    text: String? = null,
    enabled: Boolean = true,
    handleSystemButton: Boolean = false,
    onClick: (() -> Unit)? = null
){
    val navigator = LocalNavigator.current

    val backPress: () -> Unit = {
        onClick?.invoke()?: navigator?.pop()
    }

    Row(
        modifier = modifier
            .defaultMinSize(48.dp, 48.dp)
            .clip(CircleShape)
            .clickable(
                enabled = enabled,
                onClick = backPress
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            tint = tint,
            contentDescription = null
        )
        text?.let { text ->
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = tint
            )
        }
    }
    if (handleSystemButton) {
        BackPressHandler(
            onBackPressed = backPress
        )
    }
}