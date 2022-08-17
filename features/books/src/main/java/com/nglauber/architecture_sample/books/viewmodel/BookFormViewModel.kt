package com.nglauber.architecture_sample.books.viewmodel

import android.net.Uri
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.core_android.files.FilePicker
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.entities.MediaType
import com.nglauber.architecture_sample.domain.entities.Publisher
import com.nglauber.architecture_sample.domain.usecases.BookUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class BookFormViewModel @AssistedInject constructor(
    private val bookUseCase: BookUseCase,
    private val filePicker: FilePicker,
    @Assisted private val bookId: String?,
) : ViewModel(), LifecycleObserver {

    // controls if the image file must be deleted after the screen is closed
    private var mustDeleteCoverImage: Boolean = true

    // temporary image file used by the camera app
    private var tempImageFile: File? = null

    // copy of the book object obtained in the loadBook.
    // It is used to check if the cover image must be deleted
    private var loadedBook: Book? = null

    // Keep tracking of the jobs
    private var loadBookJob: Job? = null
    private var saveBookJob: Job? = null

    private val _uiState = MutableStateFlow(BookFormUiState())
    val uiState = _uiState.asStateFlow()

    // Helper property to access the book object loaded from the repository
    var currentBook: Book?
        get() {
            return _uiState.value.bookDetailsState.let {
                if (it is ResultState.Success) it.data else null
            }
        }
        private set(value) {
            _uiState.update {
                it.copy(bookDetailsState = ResultState.Success(value))
            }
            validateFields()
        }

    init {
        viewModelScope.launch {
            bookUseCase.listPublishers().collect { publishersState ->
                if (publishersState is ResultState.Success) {
                    _uiState.update {
                        it.copy(publishers = publishersState.data)
                    }
                }
            }
        }
        if (bookId == null) {
            currentBook = Book()
        } else {
            loadBook(bookId)
        }
    }

    fun loadBook(bookId: String) {
        if (bookId == currentBook?.id) return
        loadBookJob?.cancel()
        loadBookJob = viewModelScope.launch {
            bookUseCase.loadBookDetails(bookId).collect { bookState ->
                _uiState.update {
                    if (bookState is ResultState.Success) {
                        loadedBook = bookState.data
                        currentBook = bookState.data
                    }
                    it.copy(
                        bookDetailsState = bookState
                    )
                }
            }
        }
    }

    // This function is used to dismiss the error message during the saving process.
    fun resetSaveState() {
        _uiState.update {
            it.copy(saveBookState = null)
        }
    }

    fun saveBook() {
        val state = _uiState.value.bookDetailsState
        if (state is ResultState.Success) {
            state.data?.let { book ->
                saveBookJob?.cancel()
                saveBookJob = viewModelScope.launch {
                    bookUseCase.saveBook(book).collect { saveBookState ->
                        _uiState.update {
                            it.copy(saveBookState = saveBookState)
                        }
                        if (saveBookState is ResultState.Success) {
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
        validateFields()
    }

    fun setAuthor(author: String) {
        currentBook = currentBook?.copy(author = author)
        validateFields()
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
        validateFields()
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
        validateFields()
    }

    fun setPublisher(publisher: Publisher?) {
        currentBook = currentBook?.copy(publisher = publisher)
        validateFields()
    }

    fun setAvailable(available: Boolean) {
        currentBook = currentBook?.copy(available = available)
        validateFields()
    }

    fun setMediaType(mediaType: MediaType) {
        currentBook = currentBook?.copy(mediaType = mediaType)
        validateFields()
    }

    fun setRating(rating: Float) {
        currentBook = currentBook?.copy(rating = rating)
        validateFields()
    }

    fun setCoverImageUri(uri: String) {
        currentBook = currentBook?.copy(coverUrl = uri)
    }

    private fun validateFields() {
        val bookDetailsState = uiState.value.bookDetailsState
        val allFieldsAreValid = if (bookDetailsState is ResultState.Success)
            bookDetailsState.data?.let {
                bookUseCase.isBookValid(it)
            } ?: false
        else false
        if (allFieldsAreValid != _uiState.value.areAllFieldsValid) {
            _uiState.update {
                it.copy(areAllFieldsValid = allFieldsAreValid)
            }
        }
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
        if (previousCoverUrl != null &&
            previousCoverUrl != "" &&
            previousCoverUrl != currentCoverUrl
        ) {
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

    @AssistedFactory
    interface Factory {
        fun create(bookId: String?): BookFormViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            bookId: String?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(bookId) as T
            }
        }
    }
}