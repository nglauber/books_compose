package com.nglauber.architecture_sample.books.viewmodel

import android.net.Uri
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.core_android.files.FilePicker
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.entities.MediaType
import com.nglauber.architecture_sample.domain.entities.Publisher
import com.nglauber.architecture_sample.domain.usecases.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class BookFormViewModel @Inject constructor(
    private val bookUseCase: BookUseCase,
    private val filePicker: FilePicker,
) : ViewModel(), LifecycleObserver {

    // controls if the image file must be deleted after the screen is closed
    private var mustDeleteCoverImage: Boolean = true

    // temporary image file used by the camera app
    private var tempImageFile: File? = null

    // copy of the book object obtained in the loadBook.
    // It is used to check if the cover image must be deleted
    private var loadedBook: Book? = null

    var publishers: List<Publisher> = emptyList()
        private set

    private val _booksDetailsState = MutableStateFlow<ResultState<Book?>>(ResultState.Loading)
    val booksDetailsState = _booksDetailsState.asStateFlow()

    private val _saveBookState = MutableStateFlow<ResultState<Unit>?>(null)
    val saveBookState = _saveBookState.asStateFlow()

    val areAllFieldsValid: StateFlow<Boolean> = _booksDetailsState
        .map { bookState ->
            if (bookState is ResultState.Success)
                bookState.data?.let { bookUseCase.isBookValid(it) } ?: false
            else false
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    // Helper property to access the book object loaded from the repository
    var currentBook: Book?
        get() {
            return _booksDetailsState.value.let {
                if (it is ResultState.Success) it.data else null
            }
        }
        private set(value) {
            _booksDetailsState.value = ResultState.Success(value)
        }

    init {
        viewModelScope.launch {
            bookUseCase.listPublishers().collect { publishersState ->
                if (publishersState is ResultState.Success) {
                    publishers = publishersState.data
                }
            }
        }
    }

    // Helper function just to avoid expose currentBook setter
    fun createNewBook() {
        currentBook = Book()
    }

    fun loadBook(bookId: String) {
        if (bookId == currentBook?.id) return
        viewModelScope.launch {
            bookUseCase.loadBookDetails(bookId).collect { bookState ->
                _booksDetailsState.value = bookState
                if (bookState is ResultState.Success) {
                    loadedBook = bookState.data
                    currentBook = bookState.data
                }
            }
        }
    }

    // This function is used to dismiss the error message during the saving process.
    fun resetSaveState() {
        _saveBookState.value = null
    }

    fun saveBook() {
        val state = _booksDetailsState.value
        if (state is ResultState.Success) {
            state.data?.let { book ->
                viewModelScope.launch {
                    bookUseCase.saveBook(book).collect {
                        _saveBookState.value = it
                        if (it is ResultState.Success) {
                            mustDeleteCoverImage = false
                            deletePreviousCoverImage()
                        }
                    }
                }
            }
        }
    }

    //region Update field functions
    fun setTitle(title: String) {
        currentBook = currentBook?.copy(title = title)
    }

    fun setAuthor(author: String) {
        currentBook = currentBook?.copy(author = author)
    }

    fun setCoverUrl(coverUrl: String) {
        currentBook = currentBook?.copy(coverUrl = coverUrl)
    }

    fun setPages(pages: String) {
        val newValue = try {
            if (pages.isEmpty()) {
                0
            } else if (pages.length in 1..5) {
                pages.toInt()
            } else {
                return
            }
        } catch (e: Exception) {
            0
        }
        currentBook = currentBook?.copy(pages = newValue)
    }

    fun setYear(year: String) {
        val newValue = try {
            if (year.isEmpty()) {
                0
            } else if (year.length in 1..4) {
                year.toInt()
            } else {
                return
            }
        } catch (e: Exception) {
            0
        }
        currentBook = currentBook?.copy(year = newValue)
    }

    fun setPublisher(publisher: Publisher?) {
        currentBook = currentBook?.copy(publisher = publisher)
    }

    fun setAvailable(available: Boolean) {
        currentBook = currentBook?.copy(available = available)
    }

    fun setMediaType(mediaType: MediaType) {
        currentBook = currentBook?.copy(mediaType = mediaType)
    }

    fun setRating(rating: Float) {
        currentBook = currentBook?.copy(rating = rating)
    }

    fun setCoverImageUri(uri: String) {
        currentBook = currentBook?.copy(coverUrl = uri)
    }
    //endregion

    //region Cover image related functions
    fun createTempImageFile(): Uri {
        val file = filePicker.createTempFile()
        mustDeleteCoverImage = true
        // delete the previous tempImageFile before assign a new one
        deleteTempPhoto()
        tempImageFile = file
        return filePicker.uriFromFile(file)
    }

    fun assignCoverImage() {
        tempImageFile?.let {
            setCoverUrl("file://${it.absolutePath}")
        }
    }

    private fun deleteTempPhoto() {
        if (mustDeleteCoverImage) {
            tempImageFile?.let {
                if (it.exists()) it.delete()
            }
        }
    }

    private fun deletePreviousCoverImage() {
        // Checking if the user has changed the cover image
        val previousCoverUrl = loadedBook?.coverUrl
        val currentCoverUrl = currentBook?.coverUrl
        if (previousCoverUrl != "" && previousCoverUrl != currentCoverUrl) {
            try {
                val file = Uri.parse(previousCoverUrl).path?.let { File(it) }
                if (file?.exists() == true) file.delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    //endregion

    override fun onCleared() {
        super.onCleared()
        deleteTempPhoto()
    }
}