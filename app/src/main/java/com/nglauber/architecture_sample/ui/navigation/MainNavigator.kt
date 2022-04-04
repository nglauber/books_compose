package com.nglauber.architecture_sample.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.nglauber.architecture_sample.books.navigation.BooksFeature
import com.nglauber.architecture_sample.books.navigation.booksGraph
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.login.navigation.LoginFeature
import com.nglauber.architecture_sample.login.navigation.loginGraph
import com.nglauber.architecture_sample.settings.navigation.settingsGraph
import com.nglauber.architecture_sample.viewmodel.MainViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainNavigation(
    viewModel: MainViewModel,
    router: Router<NavHostController>,
) {
    val auth = viewModel.auth!!
    val initialRoute =
        if (auth.isLoggedIn()) BooksFeature.route else LoginFeature.route

    AnimatedNavHost(
        router.navigationController,
        startDestination = initialRoute
    ) {
        loginGraph(auth, router)
        booksGraph(auth, router)
        settingsGraph(router)
    }
}