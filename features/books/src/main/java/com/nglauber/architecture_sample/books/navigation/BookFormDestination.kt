package com.nglauber.architecture_sample.books.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavBackStackEntry
import com.nglauber.architecture_sample.books.di.bookFormViewModel
import com.nglauber.architecture_sample.books.screens.BookFormScreen
import com.nglauber.architecture_sample.books.viewmodel.BookFormViewModel
import com.nglauber.architecture_sample.domain.navigation.Router

@ExperimentalComposeUiApi
@Composable
fun BookFormDestination(
    router: Router<*>,
    backStackEntry: NavBackStackEntry,
) {
    val bookId = BookForm.getBookId(backStackEntry)
    val bookFormViewModel: BookFormViewModel = bookFormViewModel(backStackEntry, bookId)
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