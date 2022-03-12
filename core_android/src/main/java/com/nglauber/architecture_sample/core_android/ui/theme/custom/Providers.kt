package com.nglauber.architecture_sample.core_android.ui.theme.custom

import androidx.compose.runtime.staticCompositionLocalOf

// Provides Theme Data Across Composables
internal val LocalAppTypography = staticCompositionLocalOf {
    AppTypography.appTypography()
}

internal val LocalAppColors = staticCompositionLocalOf {
    AppColors.appColors(
        darkTheme = false
    )
}

internal val LocalAppShapes = staticCompositionLocalOf {
    AppShapes.appShapes()
}
