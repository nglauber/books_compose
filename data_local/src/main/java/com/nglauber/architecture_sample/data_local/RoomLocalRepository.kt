package com.nglauber.architecture_sample.data_local

import android.content.Context
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.core.ResultState.Companion.flowMap
import com.nglauber.architecture_sample.core.ResultState.Companion.flowRequest
import com.nglauber.architecture_sample.data_local.database.AppDatabase
import com.nglauber.architecture_sample.data_local.files.FileHelper
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.repositories.BooksRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class RoomLocalRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val fileHelper: FileHelper
) : BooksRepository {

    private val db = AppDatabase.getDatabase(context.applicationContext)
    private val bookDao = db.bookDao()

    override fun saveBook(book: Book): Flow<ResultState<Unit>> {
        return flowRequest {
            if (book.id.isBlank()) {
                book.id = UUID.randomUUID().toString()
            }
            bookDao.save(BookConverter.fromData(book))
        }
    }

    override fun loadBooks(): Flow<ResultState<List<Book>>> {
        return flowMap {
            bookDao.bookByTitle().map { books ->
                ResultState.Success(books.map { book ->
                    BookConverter.toData(book)
                })
            }
        }
    }

    override fun loadBook(bookId: String): Flow<ResultState<Book?>> {
        return flowMap {
            bookDao.bookById(bookId).map { book ->
                ResultState.Success(
                    book?.let { BookConverter.toData(it) }
                )
            }
        }
    }

    override fun remove(book: Book): Flow<ResultState<Unit>> {
        return flowRequest {
            bookDao.delete(BookConverter.fromData(book))
            fileHelper.deleteExistingCover(book)
        }
    }
}
