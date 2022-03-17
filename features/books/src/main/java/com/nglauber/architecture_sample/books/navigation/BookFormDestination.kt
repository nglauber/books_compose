package com.nglauber.architecture_sample.books.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.nglauber.architecture_sample.books.screens.BookFormScreen
import com.nglauber.architecture_sample.books.viewmodel.BookFormViewModel
import com.nglauber.architecture_sample.domain.navigation.Router

@ExperimentalComposeUiApi
@Composable
fun BookFormDestination(
    router: Router<*>,
    backStackEntry: NavBackStackEntry,
) {
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