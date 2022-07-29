package com.nglauber.architecture_sample.books.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.nglauber.architecture_sample.books.screens.BookListScreen
import com.nglauber.architecture_sample.books.viewmodel.BookListViewModel
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.domain.navigation.Router

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun BookListDestination(
    router: Router<*>,
    auth: Auth<*, *>,
) {
    val booksListViewModel = hiltViewModel<BookListViewModel>()
    LaunchedEffect(auth) {
        booksListViewModel.authUseCase.auth = auth
    }
    val booksListUiState by booksListViewModel.uiState.collectAsState()
    BookListScreen(
        booksListState = booksListUiState.bookListState,
        removeBookState = booksListUiState.removeBookState,
        onNewBookClick = {
            router.showBookForm()
        },
        onLogoutClick = booksListViewModel::logout,
        onSettingsClick = {
            router.showSettings()
        },
        onBookClick = {
            router.showBookDetails(it)
        },
        onDeleteBook = booksListViewModel::remove,
        onDeleteBookConfirmed = booksListViewModel::resetRemoveBookState,
        reloadBooks = booksListViewModel::loadBooks
    )
}