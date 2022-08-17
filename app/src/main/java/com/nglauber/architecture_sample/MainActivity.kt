package com.nglauber.architecture_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.ui.BooksApp
import com.nglauber.architecture_sample.ui.rememberBooksAppState
import com.nglauber.architecture_sample.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var auth: Auth<Unit, GoogleSignInAccount?>

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BookApp.instance?.auth = auth
        lifecycle.addObserver(mainViewModel)
        setContent {
            val appState = rememberBooksAppState()
            val themeMode by mainViewModel.currentTheme.collectAsState()
            val isDark = mainViewModel.isDarkMode(themeMode) ?: isSystemInDarkTheme()
            BooksApp(
                appState = appState,
                isLoggedIn = auth.isLoggedIn(),
                isDark = isDark
            )
            LaunchedEffect(this@MainActivity) {
                launchLoginObserver(appState.router)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(mainViewModel)
        BookApp.instance?.auth = null
    }

    private fun launchLoginObserver(router: Router) {
        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.isLoggedIn.collect { isLoggedInState ->
                if (isLoggedInState == false) {
                    router.showLogin()
                }
            }
        }
    }
}