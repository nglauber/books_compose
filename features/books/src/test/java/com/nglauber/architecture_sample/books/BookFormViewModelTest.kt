package com.nglauber.architecture_sample.books

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.nglauber.architecture_sample.books.viewmodel.BookFormViewModel
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.core_android.files.FilePicker
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.entities.MediaType
import com.nglauber.architecture_sample.domain.entities.Publisher
import com.nglauber.architecture_sample.domain.usecases.BookUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class BookFormViewModelTest {

    private lateinit var instrumentationContext: Context
    private lateinit var filePicker: FilePicker
    private lateinit var dummyBookList: MutableList<Book>
    private lateinit var dummyPublishers: List<Publisher>
    private val useCase: BookUseCase = mockk()

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        filePicker =
            FilePicker(instrumentationContext, "com.nglauber.architecture_sample.fileprovider")
        Dispatchers.setMain(StandardTestDispatcher())
        dummyBookList = DataFactory.dummyBookList().toMutableList()
        dummyPublishers = DataFactory.dummyPublishersList()
        val bookParam = slot<Book>()
        coEvery { useCase.saveBook(capture(bookParam)) } returns flow {
            emit(ResultState.Loading)
            dummyBookList.add(bookParam.captured)
            emit(ResultState.Success(Unit))
        }
        coEvery { useCase.listBooks() } returns flow {
            emit(ResultState.Loading)
            emit(ResultState.Success(dummyBookList))
        }
        coEvery { useCase.listPublishers() } returns flow {
            emit(ResultState.Loading)
            emit(ResultState.Success(dummyPublishers))
        }
        coEvery { useCase.isBookValid(any()) } returns true
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updating fields update book`() = runTest {
        // Given
        val viewModel = BookFormViewModel(useCase, filePicker)
        viewModel.createNewBook()
        val initialBook: Book = viewModel.currentBook!!
        var currentBook: Book? = initialBook
        val job = launch {
            viewModel.uiState.collect {
                val bookDetailsState = it.bookDetailsState
                if (bookDetailsState is ResultState.Success)
                    currentBook = bookDetailsState.data
            }
        }
        // When
        runCurrent()
        assertEquals(initialBook, currentBook)

        viewModel.setTitle("New Title")
        runCurrent()
        assertEquals(currentBook?.title, "New Title")

        viewModel.setAuthor("New Author")
        runCurrent()
        assertEquals(currentBook?.author, "New Author")

        viewModel.setCoverUrl("New Cover Url")
        runCurrent()
        assertEquals(currentBook?.coverUrl, "New Cover Url")

        viewModel.setPages("1234")
        runCurrent()
        assertEquals(currentBook?.pages, 1234)

        viewModel.setYear("2022")
        runCurrent()
        assertEquals(currentBook?.year, 2022)

        val publisher = dummyPublishers[1]
        viewModel.setPublisher(publisher)
        runCurrent()
        assertEquals(currentBook?.publisher, publisher)

        val isAvailable = !(currentBook?.available ?: false)
        viewModel.setAvailable(isAvailable)
        runCurrent()
        assertEquals(currentBook?.available, isAvailable)

        viewModel.setMediaType(MediaType.EBOOK)
        runCurrent()
        assertEquals(currentBook?.mediaType, MediaType.EBOOK)

        viewModel.setRating(5.1f)
        runCurrent()
        assertEquals(currentBook?.rating, 5.1f)

        job.cancel()
    }

    @Test
    fun `insert a book successfully`() = runTest {
        // Given
        val viewModel = BookFormViewModel(useCase, filePicker)
        viewModel.createNewBook()
        val dummyBook = viewModel.currentBook

        val saveStates = mutableListOf<ResultState<Unit>?>()
        // When
        val jobSaveBook = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState
                .map { it.saveBookState }
                .filter { it != null }
                .toList(saveStates) // initial state is null
        }
        viewModel.saveBook()
        runCurrent()
        // Then
        assert(saveStates.first() is ResultState.Loading)
        assert(saveStates.last() is ResultState.Success)
        assertEquals(dummyBookList.last(), dummyBook)
        jobSaveBook.cancel()
    }
}