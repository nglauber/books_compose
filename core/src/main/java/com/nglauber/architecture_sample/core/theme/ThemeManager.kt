package com.nglauber.architecture_sample.core.theme

import kotlinx.coroutines.flow.StateFlow

interface ThemeManager {
    val themeMode: StateFlow<ThemeMode>

    fun setTheme(theme: ThemeMode)

    fun isDark(theme: ThemeMode): Boolean?
}