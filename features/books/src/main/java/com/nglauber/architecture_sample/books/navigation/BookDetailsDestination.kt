package com.nglauber.architecture_sample.books.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.nglauber.architecture_sample.books.di.bookDetailsViewModel
import com.nglauber.architecture_sample.books.screens.BookDetailsScreen
import com.nglauber.architecture_sample.domain.navigation.Router

@Composable
fun BookDetailsDestination(
    router: Router<*>,
    backStackEntry: NavBackStackEntry
) {
    val bookId = BookDetails.getBookId(backStackEntry)
    val bookDetailsViewModel = bookDetailsViewModel(bookId ?: "")
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