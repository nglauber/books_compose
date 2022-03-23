package com.nglauber.architecture_sample.books.viewmodel

import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.entities.Publisher

data class BookFormUiState(
    val publishers: List<Publisher> = emptyList(),
    val bookDetailsState: ResultState<Book?> = ResultState.Loading,
    val saveBookState: ResultState<Unit>? = null,
    val areAllFieldsValid: Boolean = false,
)