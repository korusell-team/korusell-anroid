package com.korusell.korusell.ui.auth.code

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.korusell.korusell.ui.theme.KORUSELLTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CodeField(
    modifier: Modifier = Modifier,
    length: Int,
    codeState: MutableState<String>,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
){
    val localDensity = LocalDensity.current
    var containerWidthDp by remember{ mutableStateOf(16.dp) }
    Box(
        modifier = modifier.onGloballyPositioned { coordinates ->
            containerWidthDp = with(localDensity) { coordinates.size.width.toDp() }
        },
        contentAlignment = Alignment.Center
    ) {
        DrawRectangles(
            containerWidthDp = containerWidthDp,
            length = length
        )
        BasicTextField(
            modifier = Modifier
                .horizontalScroll(rememberScrollState(), false)
                .fillMaxWidth(),
            value = codeState.value,
            onValueChange = {
                if (it.length <= length) {
                    codeState.value = it
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.NumberPassword
            ),
            cursorBrush = SolidColor(Transparent),
            textStyle = textStyle.copy(
                letterSpacing = with(localDensity) {
                    (containerWidthDp - textStyle.fontSize.toDp()*3.5f).toSp()/length
                },
                textAlign = TextAlign.Justify
            )
        )
    }
}

@Composable
private fun DrawRectangles(
    containerWidthDp: Dp,
    length: Int
) = Row(
    modifier = Modifier
        .fillMaxWidth()
) {
    repeat(length){
        Box(modifier = Modifier
            .size(containerWidthDp / length)
            .padding(horizontal = 4.dp)
        ) {
            Spacer(
                Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onBackground,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .fillMaxSize()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewCodeField() = KORUSELLTheme {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val state = remember { mutableStateOf("285123") }
        CodeField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            length = 6,
            codeState = state
        )
    }
}