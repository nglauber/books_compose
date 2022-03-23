package com.nglauber.architecture_sample.books.viewmodel

import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.domain.entities.Book

data class BookListUiState(
    val bookListState: ResultState<List<Book>> = ResultState.Loading,
    val removeBookState: ResultState<Unit>? = null
)