package com.nglauber.architecture_sample.domain

import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.core.takeTwo
import com.nglauber.architecture_sample.domain.repositories.BooksRepository
import com.nglauber.architecture_sample.domain.usecases.BookUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BooksUseCaseTest {
    private val repository: BooksRepository = mockk()

    private val dummyBooksList = DomainEntityFactory.dummyBookList()
    private val dummyBook = DomainEntityFactory.dummyBook()

    @Before
    fun init() {
        coEvery { repository.loadBooks() } returns flow {
            emit(ResultState.Loading)
            emit(ResultState.Success(dummyBooksList))
        }
        coEvery { repository.remove(any()) } returns flow {
            emit(ResultState.Loading)
            emit(ResultState.Success(Unit))
        }
        coEvery { repository.saveBook(any()) } returns flow {
            emit(ResultState.Loading)
            emit(ResultState.Success(Unit))
        }
        coEvery { repository.loadBook(any()) } returns flow {
            emit(ResultState.Loading)
            emit(ResultState.Success(dummyBook))
        }
    }

    @Test
    fun testBookDetailsIsLoaded() = runBlocking {
        // Given
        val useCase = BookUseCase(repository)
        // When
        val result = useCase.loadBookDetails("1").takeTwo()
        // Then
        assert(result.first() is ResultState.Loading)
        assert(result.last() is ResultState.Success)
        val book = (result.last() as ResultState.Success).data
        assertEquals(book, dummyBook)
    }

    @Test
    fun testBookIsSaved() {
        runBlocking {
            // Given
            val useCase = BookUseCase(repository)
            // When
            val result = useCase.saveBook(dummyBook).takeTwo()
            // Then
            assert(result.first() is ResultState.Loading)
            assert(result.last() is ResultState.Success)
        }
    }

    @Test
    fun testBookDetailsIsRemoved() {
        runBlocking {
            // Given
            val useCase = BookUseCase(repository)
            // When
            val result = useCase.removeBook(dummyBook).takeTwo()
            // Then
            assert(result.first() is ResultState.Loading)
            assert(result.last() is ResultState.Success)
        }
    }

    @Test
    fun testBooksListIsLoaded() = runBlocking {
        // Given
        val useCase = BookUseCase(repository)
        // When
        val result = useCase.listBooks().takeTwo()
        // Then
        assert(result.first() is ResultState.Loading)
        assert(result.last() is ResultState.Success)
        val list = (result.last() as ResultState.Success).data
        assertEquals(list.size, dummyBooksList.size)
        assert(list.containsAll(dummyBooksList))
    }
}
