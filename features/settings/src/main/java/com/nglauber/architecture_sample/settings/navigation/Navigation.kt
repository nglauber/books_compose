package com.nglauber.architecture_sample.settings.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.settings.screens.SettingsScreen
import com.nglauber.architecture_sample.settings.viewmodel.SettingsViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.settingsGraph(
    router: Router
) {
    navigation(
        route = SettingsFeature.route,
        startDestination = SettingsScreen.route,
    ) {
        composable(SettingsScreen.route) {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val themeMode by settingsViewModel.currentTheme.collectAsState()
            SettingsScreen(
                currentTheme = themeMode,
                onThemeChange = settingsViewModel::setTheme,
                onBackPressed = router::back
            )
        }
    }
}