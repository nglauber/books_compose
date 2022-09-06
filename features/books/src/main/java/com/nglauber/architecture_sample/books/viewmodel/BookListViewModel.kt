package com.nglauber.architecture_sample.books.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.usecases.AuthUseCase
import com.nglauber.architecture_sample.domain.usecases.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookUseCase: BookUseCase,
    private val authUseCase: AuthUseCase,
) : ViewModel(), LifecycleObserver {

    private var loadBooksJob: Job? = null
    private var removeBookJob: Job? = null

    private val _uiState = MutableStateFlow(BookListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        loadBooksJob?.cancel()
        loadBooksJob = bookUseCase.listBooks().onEach { resultState ->
            _uiState.update {
                it.copy(bookListState = resultState)
            }
        }.launchIn(viewModelScope)
    }

    fun remove(book: Book) {
        removeBookJob?.cancel()
        removeBookJob = bookUseCase.removeBook(book).onEach { resultState ->
            _uiState.update {
                it.copy(removeBookState = resultState)
            }
        }.launchIn(viewModelScope)
    }

    fun resetRemoveBookState() {
        _uiState.update {
            it.copy(removeBookState = null)
        }
    }

    fun logout() {
        authUseCase.logout()
    }
}