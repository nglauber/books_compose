package com.nglauber.architecture_sample.books.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.usecases.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val bookUseCase: BookUseCase
) : ViewModel() {

    private val _booksDetailsState = MutableStateFlow<ResultState<Book?>>(ResultState.Loading)
    val booksDetailsState = _booksDetailsState.asStateFlow()

    private var loadBookJob: Job? = null

    fun loadBook(bookId: String) {
        val currentState = _booksDetailsState.value
        if (currentState is ResultState.Success && bookId == currentState.data?.id)
            return // avoid to reload the same book again

        loadBookJob?.cancel()
        loadBookJob = viewModelScope.launch {
            bookUseCase.loadBookDetails(bookId).collect {
                _booksDetailsState.value = it
            }
        }
    }
}