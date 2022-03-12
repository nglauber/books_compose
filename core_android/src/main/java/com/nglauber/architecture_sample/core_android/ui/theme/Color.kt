package com.nglauber.architecture_sample.core_android.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppColors

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

internal val DarkColorPalette =
    AppColors(
        materialColors = darkColors(
            primary = Purple200,
            primaryVariant = Purple700,
            secondary = Teal200
        ),
        someCustomColor = Color.Red,
    )

internal val LightColorPalette =
    AppColors(
        materialColors = lightColors(
            primary = Purple500,
            primaryVariant = Purple700,
            secondary = Teal200

            /* Other default colors to override
            background = Color.White,
            surface = Color.White,
            onPrimary = Color.White,
            onSecondary = Color.Black,
            onBackground = Color.Black,
            onSurface = Color.Black,
            */
        ),
        someCustomColor = Color.Blue,
    )