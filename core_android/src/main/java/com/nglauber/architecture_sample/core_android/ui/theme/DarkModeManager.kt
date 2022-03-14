package com.nglauber.architecture_sample.core_android.ui.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.*
import com.nglauber.architecture_sample.core.theme.ThemeManager
import com.nglauber.architecture_sample.core.theme.ThemeMode
import com.nglauber.architecture_sample.core_android.preferences.PreferenceUtil
import com.nglauber.architecture_sample.core_android.preferences.PreferenceUtil.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DarkModeManager(
    context: Context
) : ThemeManager {
    private val preferences: SharedPreferences =
        PreferenceUtil.customPrefs(context, PREF_NAME)

    private val _themeMode = MutableStateFlow(getThemeFromPreferences())
    override val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private fun getThemeFromPreferences(): ThemeMode {
        val ordinal = preferences.getInt(KEY_THEME_MODE, ThemeMode.MODE_SYSTEM.ordinal)
        return ThemeMode.values()[ordinal]
    }

    override fun setTheme(theme: ThemeMode) {
        setDefaultNightMode(
            when (theme) {
                ThemeMode.MODE_SYSTEM -> MODE_NIGHT_FOLLOW_SYSTEM
                ThemeMode.MODE_LIGHT -> MODE_NIGHT_NO
                ThemeMode.MODE_DARK -> MODE_NIGHT_YES
            }
        )
        preferences[KEY_THEME_MODE] = theme.ordinal
        _themeMode.value = theme
    }

    override fun isDark(theme: ThemeMode): Boolean? {
        return when (theme) {
            ThemeMode.MODE_SYSTEM -> null
            ThemeMode.MODE_LIGHT -> false
            ThemeMode.MODE_DARK -> true
        }
    }

    companion object {
        private const val PREF_NAME = "darkModeManager"
        private const val KEY_THEME_MODE = "themeMode"
    }
}