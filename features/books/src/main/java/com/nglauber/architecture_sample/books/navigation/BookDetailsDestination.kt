package com.nglauber.architecture_sample.books.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.nglauber.architecture_sample.books.screens.BookDetailsScreen
import com.nglauber.architecture_sample.books.viewmodel.BookDetailsViewModel
import com.nglauber.architecture_sample.domain.navigation.Router

@Composable
fun BookDetailsDestination(
    router: Router<*>,
    backStackEntry: NavBackStackEntry
) {
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