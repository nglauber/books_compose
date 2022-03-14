package com.nglauber.architecture_sample.domain.usecases

import com.nglauber.architecture_sample.core.theme.ThemeManager
import com.nglauber.architecture_sample.core.theme.ThemeMode
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ThemeUseCase @Inject constructor(
    private val themeManager: ThemeManager
) {
    val themeMode: StateFlow<ThemeMode> = themeManager.themeMode

    fun setTheme(theme: ThemeMode) {
        themeManager.setTheme(theme)
    }

    fun isDark(theme: ThemeMode): Boolean? {
        return themeManager.isDark(theme)
    }
}