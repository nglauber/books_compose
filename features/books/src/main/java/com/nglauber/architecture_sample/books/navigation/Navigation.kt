package com.nglauber.architecture_sample.books.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.core_android.ui.navigation.composablePopup
import com.nglauber.architecture_sample.core_android.ui.navigation.composableScreen
import com.nglauber.architecture_sample.domain.navigation.Router

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
fun NavGraphBuilder.booksGraph(
    auth: Auth<*, *>,
    router: Router<*>
) {
    val popupScreens = { route: String? ->
        route == BookForm.route
    }
    navigation(
        route = BooksFeature.route,
        startDestination = BooksList.route,
    ) {
        composableScreen(
            BooksList.route,
            targetIsPopup = popupScreens
        ) {
            BookListDestination(router = router, auth = auth)
        }
        composableScreen(
            BookDetails.route,
            arguments = BookDetails.navArguments,
            targetIsPopup = popupScreens
        ) { backStackEntry ->
            BookDetailsDestination(router = router, backStackEntry = backStackEntry)
        }
        composablePopup(
            BookForm.route,
            arguments = BookForm.navArguments
        ) { backStackEntry ->
            BookFormDestination(router = router, backStackEntry = backStackEntry)
        }
    }
}
