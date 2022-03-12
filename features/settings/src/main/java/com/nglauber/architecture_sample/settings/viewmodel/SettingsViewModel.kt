package com.nglauber.architecture_sample.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.nglauber.architecture_sample.core_android.ui.theme.DarkModeManager
import com.nglauber.architecture_sample.core_android.ui.theme.custom.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val darkModeManager: DarkModeManager
) : ViewModel() {

    val currentTheme = darkModeManager.themeMode

    fun setTheme(theme: ThemeMode) {
        darkModeManager.setTheme(theme = theme)
    }
}