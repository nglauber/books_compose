package com.nglauber.architecture_sample.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.core_android.ui.theme.BookAppTheme
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppTheme
import com.nglauber.architecture_sample.ui.navigation.MainNavigation

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun BooksApp(
    auth: Auth<Unit, GoogleSignInAccount?>,
    isDark: Boolean,
    content: @Composable (BooksAppState) -> Unit
) {
    BookAppTheme(
        isDark = isDark
    ) {
        val appState = rememberBooksAppState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppTheme.colors.background
        ) {
            MainNavigation(appState, auth)
        }
        content(appState)
    }
}