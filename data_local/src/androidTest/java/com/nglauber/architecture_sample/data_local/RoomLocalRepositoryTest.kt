package com.nglauber.architecture_sample.data_local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.core.takeTwo
import com.nglauber.architecture_sample.data_local.files.LocalFileHelper
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.entities.MediaType
import com.nglauber.architecture_sample.domain.entities.Publisher
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.util.*

@RunWith(AndroidJUnit4::class)
class RoomLocalRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repo: RoomLocalRepository
    private lateinit var dummyBook: Book

    private val context = InstrumentationRegistry.getInstrumentation().context

    @Before
    fun initDb() {
        repo = RoomLocalRepository(context, LocalFileHelper(), inMemory = true)
        dummyBook = Book(
            id = UUID.randomUUID().toString(),
            title = "Dominando o Android",
            author = "Nelson Glauber",
            available = true,
            coverUrl = "",
            pages = 954,
            publisher = Publisher(UUID.randomUUID().toString(), "Novatec"),
            year = 2018,
            mediaType = MediaType.EBOOK,
            rating = 5f
        )
    }

    @Test
    fun insertBook() {
        runBlocking {
            val result = repo.saveBook(dummyBook).takeTwo()
            assert(result.first() is ResultState.Loading)
            assert(result.last() is ResultState.Success)
        }
    }

    @Test
    fun insertTheSameBookIdMustUpdateTheRecord() {
        runBlocking {
            val id = dummyBook.id

            val insertResult1 = repo.saveBook(dummyBook).takeTwo()
            assert(insertResult1.first() is ResultState.Loading)
            assert(insertResult1.last() is ResultState.Success)

            val loadBookResult1 = repo.loadBook(id).takeTwo()
            assert(loadBookResult1.first() is ResultState.Loading)
            assert(loadBookResult1.last() is ResultState.Success)

            val insertResult2 = repo.saveBook(dummyBook).takeTwo()
            assert(insertResult2.first() is ResultState.Loading)
            assert(insertResult2.last() is ResultState.Success)

            val loadBookResult2 = repo.loadBook(id).takeTwo()
            assert(loadBookResult2.first() is ResultState.Loading)
            assert(loadBookResult2.last() is ResultState.Success)

            val book1 = (loadBookResult1.last() as ResultState.Success).data
            val book2 = (loadBookResult2.last() as ResultState.Success).data
            assertEquals(book1, book2)
            assertEquals(book1, dummyBook)
            assertEquals(book2, dummyBook)
        }
    }

    @Test
    fun insertedBookMustBeReturned() {
        runBlocking {
            val bookId = dummyBook.id

            val insertResult = repo.saveBook(dummyBook).takeTwo()
            assert(insertResult.first() is ResultState.Loading)
            assert(insertResult.last() is ResultState.Success)

            val loadBookResult = repo.loadBook(bookId).takeTwo()
            assert(loadBookResult.first() is ResultState.Loading)
            assert(loadBookResult.last() is ResultState.Success)

            val loadedBook = (loadBookResult.last() as ResultState.Success).data
            assertEquals(dummyBook, loadedBook)
        }
    }

    @Test
    fun updateAllBookFields() {
        runBlocking {
            val bookId = dummyBook.id

            val insertResult = repo.saveBook(dummyBook).takeTwo()
            assert(insertResult.first() is ResultState.Loading)
            assert(insertResult.last() is ResultState.Success)

            val loadBookResult = repo.loadBook(bookId).takeTwo()
            assert(loadBookResult.first() is ResultState.Loading)
            assert(loadBookResult.last() is ResultState.Success)
            val loadedBook = (loadBookResult.last() as ResultState.Success).data
            assertNotNull(loadedBook)

            val newTitle = "New Title"
            val newAuthor = "New Author"
            val newCoverUrl = "New Url"
            val newPages = 1201
            val newYear = 2022
            val newPublisher = Publisher("1", "New Publisher")
            val newAvailable = false
            val newMediaType = MediaType.EBOOK
            val newRating = 4.5f

            loadedBook?.apply {
                title = newTitle
                author = newAuthor
                coverUrl = newCoverUrl
                pages = newPages
                year = newYear
                publisher = newPublisher
                available = newAvailable
                mediaType = newMediaType
                rating = newRating
            }

            val updatedResult = repo.saveBook(loadedBook!!).takeTwo()
            assert(updatedResult.first() is ResultState.Loading)
            assert(updatedResult.last() is ResultState.Success)

            val loadUpdatedBookResult = repo.loadBook(bookId).takeTwo()
            assert(loadUpdatedBookResult.first() is ResultState.Loading)
            assert(loadUpdatedBookResult.last() is ResultState.Success)
            val loadedUpdatedBook = (loadUpdatedBookResult.last() as ResultState.Success).data
            assertEquals(loadedBook, loadedUpdatedBook)
        }
    }

    @Test
    fun loadAllInsertedBooks() {
        runBlocking {
            val allBooks = listOf(
                newBook("1"),
                newBook("2"),
                newBook("3")
            )
            allBooks.forEach {
                repo.saveBook(it).takeTwo()
            }

            val listBooksResult = repo.loadBooks().takeTwo()
            assert(listBooksResult.first() is ResultState.Loading)
            assert(listBooksResult.last() is ResultState.Success)

            val loadedBooks = (listBooksResult.last() as ResultState.Success).data
            assertEquals(allBooks.size, loadedBooks.size)
            assert(loadedBooks.containsAll(allBooks))
        }
    }

    @Test
    fun removeBook() {
        runBlocking {
            val bookId = dummyBook.id
            val insertResult = repo.saveBook(dummyBook).takeTwo()
            assert(insertResult.first() is ResultState.Loading)
            assert(insertResult.last() is ResultState.Success)

            val loadBookResult = repo.loadBook(bookId).takeTwo()
            assert(loadBookResult.first() is ResultState.Loading)
            assert(loadBookResult.last() is ResultState.Success)
            val insertedBook = (loadBookResult.last() as ResultState.Success).data
            assertEquals(dummyBook, insertedBook)

            val removeResult = repo.remove(dummyBook).takeTwo()
            assert(removeResult.first() is ResultState.Loading)
            assert(removeResult.last() is ResultState.Success)

            val loadDeletedBookResult = repo.loadBook(bookId).takeTwo()
            assert(loadDeletedBookResult.first() is ResultState.Loading)
            assert(loadDeletedBookResult.last() is ResultState.Success)
            val nullBook = (loadDeletedBookResult.last() as ResultState.Success).data
            assertNull(nullBook)
        }
    }

    @Test
    fun removeBookWithCoverRemovesTheFile() {
        runBlocking {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val cover = File(context.filesDir, "${dummyBook.id}.jpg")
            assertTrue(cover.createNewFile())
            assertTrue(cover.exists())
            val book = dummyBook.copy(coverUrl = "file://${cover.absolutePath}")
            val bookId = dummyBook.id

            val insertResult = repo.saveBook(book).takeTwo()
            assert(insertResult.first() is ResultState.Loading)
            assert(insertResult.last() is ResultState.Success)

            val loadBookResult = repo.loadBook(bookId).takeTwo()
            assert(loadBookResult.first() is ResultState.Loading)
            assert(loadBookResult.last() is ResultState.Success)
            val insertedBook = (loadBookResult.last() as ResultState.Success).data
            assertEquals(book, insertedBook)

            val removeResult = repo.remove(book).takeTwo()
            assert(removeResult.first() is ResultState.Loading)
            assert(removeResult.last() is ResultState.Success)

            val loadDeletedBookResult = repo.loadBook(bookId).takeTwo()
            assert(loadDeletedBookResult.first() is ResultState.Loading)
            assert(loadDeletedBookResult.last() is ResultState.Success)
            val nullBook = (loadDeletedBookResult.last() as ResultState.Success).data
            assertNull(nullBook)
        }
    }

    private fun newBook(bookId: String) = Book().apply {
        id = bookId
        title = "Novo"
        author = "Nilson"
        coverUrl = ""
        pages = 1000
        year = 2016
        publisher = Publisher(UUID.randomUUID().toString(), "Test")
        available = false
        mediaType = MediaType.PAPER
        rating = 2.5f
    }
}
