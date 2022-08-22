package com.nglauber.architecture_sample.login.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.login.ui.LoginScreen
import com.nglauber.architecture_sample.login.viewmodel.LoginViewModel

@ExperimentalAnimationApi
fun NavGraphBuilder.loginGraph(
    router: Router
) {
    navigation(
        route = LoginFeature.route,
        startDestination = LoginScreenRoute.route,
    ) {
        composable(LoginScreenRoute.route) {
            val viewModel = hiltViewModel<LoginViewModel>()
            val loginState by viewModel.loginState.collectAsState()
            LoginScreen(
                loginState,
                onLoginClick = viewModel::login,
                onLoginSuccess = router::showBooksList,
                resetLoginState = viewModel::resetLoginState,
            )
        }
    }
}