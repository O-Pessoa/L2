package com.l2code.tmdb

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import tmdb.composeapp.generated.resources.Inter_VariableFont
import tmdb.composeapp.generated.resources.Res


@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        typography = interTypography(),
        content = content
    )

}


@Composable
fun interTypography(): Typography {
    val interFont = FontFamily(
        Font(Res.font.Inter_VariableFont, weight = FontWeight.Black),
        Font(Res.font.Inter_VariableFont, weight = FontWeight.ExtraBold),
        Font(Res.font.Inter_VariableFont, weight = FontWeight.Bold),
        Font(Res.font.Inter_VariableFont, weight = FontWeight.SemiBold),
        Font(Res.font.Inter_VariableFont, weight = FontWeight.Medium),
        Font(Res.font.Inter_VariableFont, weight = FontWeight.Normal),
        Font(Res.font.Inter_VariableFont, weight = FontWeight.Light),
        Font(Res.font.Inter_VariableFont, weight = FontWeight.ExtraLight),
        Font(Res.font.Inter_VariableFont, weight = FontWeight.Thin)
    )
    return with(MaterialTheme.typography) {
        copy(
            displayLarge = displayLarge.copy(fontFamily = interFont, fontWeight = FontWeight.Bold, fontSize = 57.sp),
            displayMedium = displayMedium.copy(fontFamily = interFont, fontWeight = FontWeight.Bold, fontSize = 45.sp),
            displaySmall = displaySmall.copy(fontFamily = interFont, fontWeight = FontWeight.Bold, fontSize = 36.sp),
            headlineLarge = headlineLarge.copy(fontFamily = interFont, fontWeight = FontWeight.Bold, fontSize = 32.sp),
            headlineMedium = headlineMedium.copy(fontFamily = interFont, fontWeight = FontWeight.Bold, fontSize = 28.sp),
            headlineSmall = headlineSmall.copy(fontFamily = interFont, fontWeight = FontWeight.Bold, fontSize = 24.sp),
            titleLarge = titleLarge.copy(fontFamily = interFont, fontWeight = FontWeight.Bold, fontSize = 24.sp),
            titleMedium = titleMedium.copy(fontFamily = interFont, fontWeight = FontWeight.Bold, fontSize = 20.sp),
            titleSmall = titleSmall.copy(fontFamily = interFont, fontWeight = FontWeight.Bold, fontSize = 16.sp),
            labelLarge = labelLarge.copy(fontFamily = interFont, fontWeight = FontWeight.Normal, fontSize = 16.sp),
            labelMedium = labelMedium.copy(fontFamily = interFont, fontWeight = FontWeight.Normal, fontSize = 14.sp),
            labelSmall = labelSmall.copy(fontFamily = interFont, fontWeight = FontWeight.Normal, fontSize = 12.sp),
            bodyLarge = bodyLarge.copy(fontFamily = interFont, fontWeight = FontWeight.Normal, fontSize = 16.sp),
            bodyMedium = bodyMedium.copy(fontFamily = interFont, fontWeight = FontWeight.Normal, fontSize = 14.sp),
            bodySmall = bodySmall.copy(fontFamily = interFont, fontWeight = FontWeight.Normal, fontSize = 12.sp)
        )
    }
}