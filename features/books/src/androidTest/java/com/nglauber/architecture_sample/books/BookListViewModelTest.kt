package com.nglauber.architecture_sample.books

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nglauber.architecture_sample.books.viewmodel.BookListViewModel
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.usecases.AuthUseCase
import com.nglauber.architecture_sample.domain.usecases.BookUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class BookListViewModelTest {

    private lateinit var dummyBookList: MutableList<Book>
    private val useCase: BookUseCase = mockk()
    private val authUseCase: AuthUseCase = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        dummyBookList = DataFactory.dummyBookList().toMutableList()
        coEvery { useCase.listBooks() } returns flow {
            emit(ResultState.Loading)
            emit(ResultState.Success(dummyBookList))
        }
        val bookParam = slot<Book>()
        coEvery { useCase.removeBook(capture(bookParam)) } returns flow {
            emit(ResultState.Loading)
            dummyBookList.remove(bookParam.captured)
            emit(ResultState.Success(Unit))
        }
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loading_books_successfully() = runTest {
        // Given
        val states = mutableListOf<ResultState<List<Book>>>()
        val viewModel = BookListViewModel(useCase, authUseCase)
        // When
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState
                .map {
                    it.bookListState
                }
                .toList(states)
        }
        viewModel.loadBooks()
        runCurrent()
        // Then
        assert(states.first() is ResultState.Loading)
        assert(states.last() is ResultState.Success)
        val list = (states.last() as ResultState.Success).data
        assertEquals(list.size, dummyBookList.size)
        assert(list.containsAll(dummyBookList))
        job.cancel()
    }

    @Test
    fun removing_a_book_successfully() = runTest {
        // Given
        val firstBook = dummyBookList.first()
        val viewModel = BookListViewModel(useCase, authUseCase)
        val loadStates = mutableListOf<ResultState<List<Book>>>()
        val removeStates = mutableListOf<ResultState<Unit>?>()
        // When
        val jobRemoveBook = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState
                .map { it.removeBookState }
                .toList(removeStates)
        }
        val jobListBooks = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.uiState
                .map { it.bookListState }
                .toList(loadStates)
        }
        viewModel.remove(firstBook)
        viewModel.loadBooks()
        runCurrent()
        // Then
        assert(removeStates.filterNotNull().first() is ResultState.Loading)
        assert(removeStates.filterNotNull().last() is ResultState.Success)
        assert(loadStates.first() is ResultState.Loading)
        assert(loadStates.last() is ResultState.Success)
        val loadedListAfterDeletion = (loadStates.last() as ResultState.Success).data
        //assertEquals(loadedListAfterDeletion.size, dummyBookList.size - 1)
        assertFalse(loadedListAfterDeletion.contains(firstBook))
        jobRemoveBook.cancel()
        jobListBooks.cancel()
    }
}