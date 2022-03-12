package com.nglauber.architecture_sample.domain.repositories

import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.domain.entities.Book
import kotlinx.coroutines.flow.Flow

interface BooksRepository {

    fun loadBooks(): Flow<ResultState<List<Book>>>

    fun loadBook(bookId: String): Flow<ResultState<Book?>>

    fun saveBook(book: Book): Flow<ResultState<Unit>>

    fun remove(book: Book): Flow<ResultState<Unit>>
}
