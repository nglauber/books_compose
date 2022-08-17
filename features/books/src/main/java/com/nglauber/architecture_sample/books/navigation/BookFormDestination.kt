package com.nglauber.architecture_sample.books.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavBackStackEntry
import com.nglauber.architecture_sample.books.di.bookFormViewModel
import com.nglauber.architecture_sample.books.screens.BookFormScreen
import com.nglauber.architecture_sample.books.viewmodel.BookFormViewModel
import com.nglauber.architecture_sample.domain.navigation.Router

@ExperimentalComposeUiApi
@Composable
fun BookFormDestination(
    router: Router,
    backStackEntry: NavBackStackEntry,
) {
    val bookId = BookForm.getBookId(backStackEntry)
    val bookFormViewModel: BookFormViewModel = bookFormViewModel(backStackEntry, bookId)

    val bookFormUiState by bookFormViewModel.uiState.collectAsState()

    BookFormScreen(
        bookFormUiState,
        bookFormViewModel.currentBook,
        onBookSaved = router::back,
        onBackPressed = router::back,
        resetSaveState = bookFormViewModel::resetSaveState,
        onTitleChanged = bookFormViewModel::setTitle,
        onAuthorChanged = bookFormViewModel::setAuthor,
        onPagesChanged = bookFormViewModel::setPages,
        onYearChanged = bookFormViewModel::setYear,
        onAvailabilityChange = bookFormViewModel::setAvailable,
        onRatingChanged = bookFormViewModel::setRating,
        onMediaTypeChanged = bookFormViewModel::setMediaType,
        onPublisherChanged = bookFormViewModel::setPublisher,
        onCreateCoverImageUri = bookFormViewModel::createTempImageFile,
        onConfirmCoverImage = bookFormViewModel::assignCoverImage,
        onDeleteCoverImage = { bookFormViewModel.setCoverImageUri("") },
        onSaveClick = bookFormViewModel::saveBook
    )
}