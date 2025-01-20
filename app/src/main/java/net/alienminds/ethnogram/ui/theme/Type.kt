package net.alienminds.ethnogram.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import net.alienminds.ethnogram.R

private val fontFamily = FontFamily(
    Font(R.font.apple_sd_gothic_neo100, FontWeight.Thin),
    Font(R.font.apple_sd_gothic_neo200, FontWeight.ExtraLight),
    Font(R.font.apple_sd_gothic_neo300, FontWeight.Light),
    Font(R.font.apple_sd_gothic_neo400, FontWeight.Normal),
    Font(R.font.apple_sd_gothic_neo500, FontWeight.Medium),
    Font(R.font.apple_sd_gothic_neo600, FontWeight.SemiBold),
    Font(R.font.apple_sd_gothic_neo700, FontWeight.Bold),
    Font(R.font.apple_sd_gothic_neo800, FontWeight.ExtraBold),
    Font(R.font.apple_sd_gothic_neo900, FontWeight.Black),
)

//internal val AppTypography = Typography()

internal val AppTypography = Typography().run { copy(
    displayLarge = displayLarge.copy(fontFamily = fontFamily),
    displayMedium = displayMedium.copy(fontFamily = fontFamily),
    displaySmall = displaySmall.copy(fontFamily = fontFamily),
    headlineLarge = headlineLarge.copy(fontFamily = fontFamily),
    headlineMedium = headlineMedium.copy(fontFamily = fontFamily),
    headlineSmall = headlineSmall.copy(fontFamily = fontFamily),
    titleLarge = titleLarge.copy(fontFamily = fontFamily),
    titleMedium = titleMedium.copy(fontFamily = fontFamily),
    titleSmall = titleSmall.copy(fontFamily = fontFamily),
    bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
    bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
    bodySmall = bodySmall.copy(fontFamily = fontFamily),
    labelLarge = labelLarge.copy(fontFamily = fontFamily),
    labelMedium = labelMedium.copy(fontFamily = fontFamily),
    labelSmall = labelSmall.copy(fontFamily = fontFamily)
) }