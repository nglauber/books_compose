package com.nglauber.architecture_sample.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.ui.navigation.RouterImpl

@ExperimentalAnimationApi
@Composable
fun rememberBooksAppState(
    navController: NavHostController = rememberAnimatedNavController()
): BooksAppState {
    return remember(navController) {
        BooksAppState(RouterImpl(navController), navController)
    }
}

@Stable
data class BooksAppState(
    val router: Router,
    internal val navHostController: NavHostController,
)