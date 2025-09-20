package com.l2code.tmdb

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
            displayLarge = displayLarge.copy(fontFamily = interFont, fontWeight = FontWeight.Bold),
            displayMedium = displayMedium.copy(fontFamily = interFont, fontWeight = FontWeight.Bold),
            displaySmall = displaySmall.copy(fontFamily = interFont, fontWeight = FontWeight.Bold),
            headlineLarge = headlineLarge.copy(fontFamily = interFont, fontWeight = FontWeight.Bold),
            headlineMedium = headlineMedium.copy(fontFamily = interFont, fontWeight = FontWeight.Bold),
            headlineSmall = headlineSmall.copy(fontFamily = interFont, fontWeight = FontWeight.Bold),
            titleLarge = titleLarge.copy(fontFamily = interFont, fontWeight = FontWeight.Bold),
            titleMedium = titleMedium.copy(fontFamily = interFont, fontWeight = FontWeight.Bold),
            titleSmall = titleSmall.copy(fontFamily = interFont, fontWeight = FontWeight.Bold),
            labelLarge = labelLarge.copy(fontFamily = interFont, fontWeight = FontWeight.Normal),
            labelMedium = labelMedium.copy(fontFamily = interFont, fontWeight = FontWeight.Normal),
            labelSmall = labelSmall.copy(fontFamily = interFont, fontWeight = FontWeight.Normal),
            bodyLarge = bodyLarge.copy(fontFamily = interFont, fontWeight = FontWeight.Normal),
            bodyMedium = bodyMedium.copy(fontFamily = interFont, fontWeight = FontWeight.Normal),
            bodySmall = bodySmall.copy(fontFamily = interFont, fontWeight = FontWeight.Normal)
        )
    }
}