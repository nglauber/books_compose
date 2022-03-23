package com.nglauber.architecture_sample.books.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.usecases.BookUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookDetailsViewModel @AssistedInject constructor(
    private val bookUseCase: BookUseCase,
    @Assisted private val bookId: String,
) : ViewModel() {

    private val _booksDetailsState = MutableStateFlow<ResultState<Book?>>(ResultState.Loading)
    val booksDetailsState = _booksDetailsState.asStateFlow()

    private var loadBookJob: Job? = null

    init {
        loadBook(bookId)
    }

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

    @AssistedFactory
    interface Factory {
        fun create(bookId: String): BookDetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            bookId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(bookId) as T
            }
        }
    }
}