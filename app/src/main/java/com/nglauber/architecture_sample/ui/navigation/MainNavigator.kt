package com.nglauber.architecture_sample.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.nglauber.architecture_sample.books.navigation.BooksFeature
import com.nglauber.architecture_sample.books.navigation.booksGraph
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.core.auth.AuthStateListener
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.login.navigation.LoginFeature
import com.nglauber.architecture_sample.login.navigation.loginGraph
import com.nglauber.architecture_sample.settings.navigation.settingsGraph

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MainNavigation(
    router: Router<NavHostController>,
    auth: Auth<Unit, GoogleSignInAccount?>
) {
    LaunchedEffect(auth) {
        auth.removeAllListeners()
        auth.addAuthChangeListener(object : AuthStateListener {
            override fun onAuthChanged(isLoggedIn: Boolean) {
                if (!isLoggedIn) {
                    router.showLogin()
                }
            }
        })
    }

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