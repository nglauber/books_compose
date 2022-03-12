package com.nglauber.architecture_sample.core_android.ui.theme.custom

import androidx.compose.material.Colors
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.nglauber.architecture_sample.core_android.ui.theme.DarkColorPalette
import com.nglauber.architecture_sample.core_android.ui.theme.LightColorPalette

@Stable
data class AppColors(
    val materialColors: Colors,
    val someCustomColor: Color,
) {
    val primary: Color
        get() = materialColors.primary

    val primaryVariant: Color
        get() = materialColors.primaryVariant

    val secondary: Color
        get() = materialColors.secondary

    val secondaryVariant: Color
        get() = materialColors.secondaryVariant

    val background: Color
        get() = materialColors.background

    val surface: Color
        get() = materialColors.surface

    val error: Color
        get() = materialColors.error

    val onPrimary: Color
        get() = materialColors.onPrimary

    val onSecondary: Color
        get() = materialColors.onSecondary

    val onBackground: Color
        get() = materialColors.onBackground

    val onSurface: Color
        get() = materialColors.onSurface

    val onError: Color
        get() = materialColors.onError

    val isLight: Boolean
        get() = materialColors.isLight

    val isDark: Boolean
        get() = !isLight

    companion object {
        fun appColors(darkTheme: Boolean): AppColors {
            return if (darkTheme) {
                DarkColorPalette
            } else {
                LightColorPalette
            }
        }
    }
}