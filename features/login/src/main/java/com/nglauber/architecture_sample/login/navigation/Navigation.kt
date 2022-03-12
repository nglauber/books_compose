package com.nglauber.architecture_sample.login.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.login.ui.LoginScreen

@ExperimentalAnimationApi
fun NavGraphBuilder.loginGraph(
    auth: Auth<*, *>,
    router: Router<*>
) {
    navigation(
        route = LoginFeature.route,
        startDestination = LoginScreen.route,
    ) {
        composable(LoginScreen.route) {
            var showLoginError by rememberSaveable {
                mutableStateOf(false)
            }
            LoginScreen(
                showLoginError = showLoginError,
                onDismissLoginError = {
                    showLoginError = false
                },
                onLoginClick = {
                    auth.signIn(
                        onSuccess = {
                            showLoginError = false
                            router.showBooksList()
                        },
                        onError = {
                            showLoginError = true
                        }
                    )
                }
            )
        }
    }
}