package com.nglauber.architecture_sample.settings.viewmodel

import androidx.lifecycle.ViewModel
import com.nglauber.architecture_sample.core.theme.ThemeMode
import com.nglauber.architecture_sample.domain.usecases.ThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeUseCase: ThemeUseCase
) : ViewModel() {

    val currentTheme = themeUseCase.themeMode

    fun setTheme(theme: ThemeMode) {
        themeUseCase.setTheme(theme = theme)
    }
}