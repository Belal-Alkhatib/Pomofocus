package com.deepcoder.pomofocus.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.deepcoder.pomofocus.R

val Montserrat = FontFamily(
    Font(R.font.montserrat_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.montserrat_regular, weight = FontWeight.Normal),
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = TextSize80,
        lineHeight = TextHeight28
    ),

    titleMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = TextSize16,
        lineHeight = TextHeight24
    ),

    titleSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = TextSize14,
        lineHeight = TextHeight20
    ),

    bodyLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = TextSize24,
        lineHeight = TextHeight24
    ),

    bodyMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = TextSize14,
        lineHeight = TextHeight20
    ),

    bodySmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = TextSize12,
        lineHeight = TextHeight16
    ),

    labelLarge = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.SemiBold,
        fontSize = TextSize16,
        lineHeight = TextHeight20
    ),

    labelMedium = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = TextSize12,
        lineHeight = TextHeight16
    ),

    labelSmall = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = TextSize11,
        lineHeight = TextHeight16
    )
)
