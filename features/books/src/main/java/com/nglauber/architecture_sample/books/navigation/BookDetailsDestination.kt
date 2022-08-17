package com.nglauber.architecture_sample.books.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import com.nglauber.architecture_sample.books.di.bookDetailsViewModel
import com.nglauber.architecture_sample.books.screens.BookDetailsScreen
import com.nglauber.architecture_sample.domain.navigation.Router

@Composable
fun BookDetailsDestination(
    router: Router,
    backStackEntry: NavBackStackEntry
) {
    val bookId = BookDetails.getBookId(backStackEntry)
    val bookDetailsViewModel = bookDetailsViewModel(backStackEntry, bookId ?: "")
    val bookDetailsState by bookDetailsViewModel.booksDetailsState.collectAsState()
    BookDetailsScreen(
        bookDetailsState,
        onEditClick = {
            router.showBookForm(it)
        },
        onBackPressed = {
            router.back()
        }
    )
}