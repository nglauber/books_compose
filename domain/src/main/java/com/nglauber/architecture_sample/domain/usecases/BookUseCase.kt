package com.nglauber.architecture_sample.domain.usecases

import com.nglauber.architecture_sample.core.ErrorEntity
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.entities.Publisher
import com.nglauber.architecture_sample.domain.repositories.BooksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import java.util.*
import javax.inject.Inject

class BookUseCase @Inject constructor(
    private val repository: BooksRepository
) {
    fun listPublishers(): Flow<ResultState<List<Publisher>>> {
        return flow {
            emit(ResultState.Loading)
            emit(
                ResultState.Success(
                    listOf(
                        Publisher("1", "Novatec"),
                        Publisher("2", "Packt"),
                        Publisher("3", "Bookman"),
                        Publisher("4", "Pearson"),
                        Publisher("5", "Prentice Hall"),
                    )
                )
            )
        }
    }

    fun listBooks(): Flow<ResultState<List<Book>>> {
        return repository.loadBooks()
    }

    fun loadBookDetails(bookId: String): Flow<ResultState<Book?>> {
        return repository.loadBook(bookId)
    }

    fun removeBook(book: Book): Flow<ResultState<Unit>> {
        return repository.remove(book)
    }

    fun saveBook(book: Book): Flow<ResultState<Unit>> {
        return if (isBookValid(book)) {
            repository.saveBook(book)
        } else {
            flowOf(
                ResultState.Error(
                    ErrorEntity(IllegalStateException(), "-1", "Book is not valid.")
                )
            )
        }
    }

    fun isTitleValid(book: Book) = book.title.isNotBlank()

    fun isAuthorValid(book: Book) = book.author.isNotBlank()

    fun isPagesValid(book: Book) = book.pages > 0

    fun isYearValid(book: Book) = book.year > 1900 &&
            book.year <= Calendar.getInstance().get(Calendar.YEAR)

    fun isPublisherValid(book: Book) = book.publisher != null

    fun isBookValid(book: Book): Boolean {
        return (
                isTitleValid(book) &&
                        isAuthorValid(book) &&
                        isPagesValid(book) &&
                        isYearValid(book) &&
                        isPublisherValid(book)
                )
    }
}