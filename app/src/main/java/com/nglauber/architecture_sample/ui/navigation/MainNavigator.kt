package com.nglauber.architecture_sample.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.nglauber.architecture_sample.books.navigation.BooksFeature
import com.nglauber.architecture_sample.books.navigation.booksGraph
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.login.navigation.LoginFeature
import com.nglauber.architecture_sample.login.navigation.loginGraph
import com.nglauber.architecture_sample.settings.navigation.settingsGraph
import com.nglauber.architecture_sample.ui.BooksAppState

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainNavigation(
    appState: BooksAppState,
    auth: Auth<*, *>,
) {
    val initialRoute =
        if (auth.isLoggedIn()) BooksFeature.route else LoginFeature.route

    AnimatedNavHost(
        appState.navHostController,
        startDestination = initialRoute
    ) {
        loginGraph(auth, appState.router)
        booksGraph(auth, appState.router)
        settingsGraph(appState.router)
    }
}