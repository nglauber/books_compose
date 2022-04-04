package com.nglauber.architecture_sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.core_android.ui.theme.BookAppTheme
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppTheme
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.ui.navigation.MainNavigation
import com.nglauber.architecture_sample.ui.navigation.RouterImpl
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

    private lateinit var router: Router<NavHostController>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeMode by mainViewModel.currentTheme.collectAsState()
            val isDark = mainViewModel.isDarkMode(themeMode) ?: isSystemInDarkTheme()
            BookAppTheme(
                isDark = isDark
            ) {
                val navController = rememberAnimatedNavController()
                router = RouterImpl(navController)
                mainViewModel.auth = auth
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppTheme.colors.background
                ) {
                    MainNavigation(mainViewModel, router)
                }
                LaunchedEffect(this@MainActivity) {
                    launchLoginObserver()
                }
            }
        }

    }

    private fun launchLoginObserver() {
        lifecycleScope.launch(Dispatchers.Main) {
            mainViewModel.isLoggedIn.collect { isLoggedInState ->
                if (isLoggedInState == false) {
                    router.showLogin()
                }
            }
        }
    }
}