package com.nglauber.architecture_sample.books.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.nglauber.architecture_sample.books.screens.BookDetailsScreen
import com.nglauber.architecture_sample.books.screens.BookFormScreen
import com.nglauber.architecture_sample.books.screens.BookListScreen
import com.nglauber.architecture_sample.books.viewmodel.BookDetailsViewModel
import com.nglauber.architecture_sample.books.viewmodel.BookFormViewModel
import com.nglauber.architecture_sample.books.viewmodel.BookListViewModel
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
            val booksListViewModel = hiltViewModel<BookListViewModel>()
            LaunchedEffect(auth) {
                booksListViewModel.authUseCase.auth = auth
            }
            BookListScreen(
                booksListViewModel,
                onNewBookClick = {
                    router.showBookForm()
                },
                onBookClick = {
                    router.showBookDetails(it)
                },
                onSettingsClick = {
                    router.showSettings()
                }
            )
        }
        composableScreen(
            BookDetails.route,
            arguments = BookDetails.navArguments,
            targetIsPopup = popupScreens
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(BookDetails.paramBookId)
            val bookDetailsViewModel: BookDetailsViewModel = hiltViewModel()
            LaunchedEffect(bookId) {
                bookDetailsViewModel.loadBook(bookId ?: "")
            }
            BookDetailsScreen(
                bookDetailsViewModel,
                onEditClick = {
                    router.showBookForm(it)
                },
                onBackPressed = {
                    router.back()
                }
            )
        }
        composablePopup(
            BookForm.route,
            arguments = BookForm.navArguments
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString(BookDetails.paramBookId)
            val bookFormViewModel: BookFormViewModel = hiltViewModel()
            LaunchedEffect(bookId) {
                if (bookId == BookForm.paramNewBook) {
                    if (bookFormViewModel.currentBook == null) {
                        bookFormViewModel.createNewBook()
                    }
                } else {
                    bookFormViewModel.loadBook(bookId ?: "")
                }
            }
            BookFormScreen(
                bookFormViewModel,
                onBookSaved = {
                    router.back()
                },
                onBackPressed = {
                    router.back()
                },
            )
        }
    }
}
