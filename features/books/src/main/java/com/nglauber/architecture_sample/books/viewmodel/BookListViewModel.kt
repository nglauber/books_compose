package com.nglauber.architecture_sample.books.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.usecases.AuthUseCase
import com.nglauber.architecture_sample.domain.usecases.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookUseCase: BookUseCase,
    val authUseCase: AuthUseCase,
) : ViewModel(), LifecycleObserver {

    private val _booksListState = MutableStateFlow<ResultState<List<Book>>>(ResultState.Loading)
    val booksListState = _booksListState.asStateFlow()

    private val _removeBookState = MutableStateFlow<ResultState<Unit>>(ResultState.Loading)
    val removeBookState = _removeBookState.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            bookUseCase.listBooks().collect {
                _booksListState.value = it
            }
        }
    }

    fun remove(book: Book) {
        viewModelScope.launch {
            bookUseCase.removeBook(book).collect {
                _removeBookState.value = it
            }
        }
    }

    fun logout() {
        authUseCase.logout()
    }
}