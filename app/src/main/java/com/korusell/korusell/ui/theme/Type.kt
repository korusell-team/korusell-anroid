package com.korusell.korusell.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.korusell.korusell.R


private val openSansFamily = FontFamily(Font(R.font.open_sans))

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = openSansFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Center
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = openSansFamily,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center
    )
)