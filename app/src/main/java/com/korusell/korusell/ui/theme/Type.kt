package com.korusell.korusell.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.korusell.korusell.R


private val openSansFamily = FontFamily(Font(R.font.open_sans))

val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = openSansFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 20.sp,
    )
)