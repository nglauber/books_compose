package com.nglauber.architecture_sample.core_android.ui.theme.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

// Custom Application Theme
object AppTheme {
    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current
}