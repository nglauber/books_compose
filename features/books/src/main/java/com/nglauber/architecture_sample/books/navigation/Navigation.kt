package com.nglauber.architecture_sample.books.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.nglauber.architecture_sample.core_android.ui.navigation.composablePopup
import com.nglauber.architecture_sample.core_android.ui.navigation.composableScreen
import com.nglauber.architecture_sample.domain.navigation.Router

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.booksGraph(
    router: Router
) {
    val popupScreens = { route: String? ->
        route == BookFormRoute.route
    }
    navigation(
        route = BooksFeature.route,
        startDestination = BooksListRoute.route,
    ) {
        composableScreen(
            BooksListRoute.route,
            targetIsPopup = popupScreens
        ) {
            BookListDestination(router = router)
        }
        composableScreen(
            BookDetailsRoute.route,
            arguments = BookDetailsRoute.navArguments,
            targetIsPopup = popupScreens
        ) { backStackEntry ->
            BookDetailsDestination(router = router, backStackEntry = backStackEntry)
        }
        composablePopup(
            BookFormRoute.route,
            arguments = BookFormRoute.navArguments
        ) { backStackEntry ->
            BookFormDestination(router = router, backStackEntry = backStackEntry)
        }
    }
}
