package com.nglauber.architecture_sample.login.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.login.ui.LoginScreen
import com.nglauber.architecture_sample.login.viewmodel.LoginViewModel

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
            val viewModel = hiltViewModel<LoginViewModel>()
            LaunchedEffect(auth) {
                viewModel.useCase.auth = auth
            }
            LoginScreen(
                viewModel,
                onLoginSuccess = {
                    router.showBooksList()
                }
            )
        }
    }
}