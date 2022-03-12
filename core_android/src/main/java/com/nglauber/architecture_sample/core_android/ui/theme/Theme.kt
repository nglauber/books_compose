package com.nglauber.architecture_sample.core_android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.nglauber.architecture_sample.core_android.ui.theme.custom.*
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppColors.Companion.appColors
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppShapes.Companion.appShapes
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppTypography.Companion.appTypography

@Composable
fun BookAppTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val appColors: AppColors = remember(isDark) {
        appColors(isDark)
    }
    val appShapes: AppShapes = appShapes()
    val appTypography: AppTypography = appTypography()

    CompositionLocalProvider(
        LocalAppTypography provides appTypography,
        LocalAppColors provides appColors,
        LocalAppShapes provides appShapes,
    ) {
        MaterialTheme(
            colors = appColors.materialColors,
            typography = appTypography.materialTypography,
            shapes = appShapes.materialShapes,
            content = content
        )
    }
}

