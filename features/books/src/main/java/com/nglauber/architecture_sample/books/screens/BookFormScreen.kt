package com.nglauber.architecture_sample.books.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nglauber.architecture_sample.books.R
import com.nglauber.architecture_sample.books.viewmodel.BookFormViewModel
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.core_android.ui.components.AsyncData
import com.nglauber.architecture_sample.core_android.ui.components.ComboBox
import com.nglauber.architecture_sample.core_android.ui.components.GenericError
import com.nglauber.architecture_sample.core_android.ui.components.RatingBar
import com.nglauber.architecture_sample.core_android.ui.theme.BookAppTheme
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppTheme
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.entities.MediaType
import com.nglauber.architecture_sample.domain.entities.Publisher
import com.nglauber.architecture_sample.core_android.R as CoreR

@ExperimentalComposeUiApi
@Composable
fun BookFormScreen(
    viewModel: BookFormViewModel,
    onBookSaved: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val bookDetailsState by viewModel.booksDetailsState.collectAsState()
    val formValidationState by viewModel.areAllFieldsValid.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = CoreR.string.app_name))
                },
                actions = {
                    IconButton(
                        onClick = onBackPressed
                    ) {
                        Icon(
                            ImageVector.vectorResource(id = CoreR.drawable.ic_clear),
                            contentDescription = stringResource(id = CoreR.string.cd_back)
                        )
                    }
                },
            )
        }
    ) {
        AsyncData(
            resultState = bookDetailsState,
        ) {
            viewModel.currentBook?.let { book ->
                val bookSaveState by viewModel.saveBookState.collectAsState()
                AsyncData(
                    resultState = bookSaveState,
                    errorContent = {
                        GenericError(
                            onDismissAction = viewModel::resetSaveState
                        )
                    }
                ) {
                    if (bookSaveState is ResultState.Success) {
                        LaunchedEffect(bookSaveState) {
                            onBookSaved()
                        }
                    } else {
                        BookFormScreenContent(
                            book = book,
                            onTitleChanged = viewModel::setTitle,
                            onAuthorChanged = viewModel::setAuthor,
                            onPagesChanged = viewModel::setPages,
                            onYearChanged = viewModel::setYear,
                            onAvailabilityChange = viewModel::setAvailable,
                            onRatingChanged = viewModel::setRating,
                            onMediaTypeChanged = viewModel::setMediaType,
                            publishers = viewModel.publishers,
                            onPublisherChanged = viewModel::setPublisher,
                            onCreateCoverImageUri = viewModel::createTempImageFile,
                            onConfirmCoverImage = viewModel::assignCoverImage,
                            onDeleteCoverImage = { viewModel.setCoverImageUri("") },
                            isFormValid = formValidationState,
                            onSaveClick = viewModel::saveBook
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun BookFormScreenContent(
    book: Book?,
    onTitleChanged: (String) -> Unit,
    onAuthorChanged: (String) -> Unit,
    onPagesChanged: (String) -> Unit,
    onYearChanged: (String) -> Unit,
    onAvailabilityChange: (Boolean) -> Unit,
    onRatingChanged: (Float) -> Unit,
    onMediaTypeChanged: (MediaType) -> Unit,
    publishers: List<Publisher>,
    onPublisherChanged: (Publisher) -> Unit,
    onCreateCoverImageUri: () -> Uri,
    onConfirmCoverImage: () -> Unit,
    onDeleteCoverImage: () -> Unit,
    isFormValid: Boolean,
    onSaveClick: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { result ->
        if (result) {
            onConfirmCoverImage()
        } else {
            if (book?.coverUrl == "") {
                onDeleteCoverImage()
            }
        }
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardActions = KeyboardActions(
        onDone = {
            keyboardController?.hide()
        }
    )
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Row {
                val hasCoverUrl = book?.coverUrl?.isNotEmpty() == true
                val imageModifier = Modifier
                    .background(Color.Gray)
                    .clickable {
                        val uri = onCreateCoverImageUri()
                        launcher.launch(uri)
                    }
                if (hasCoverUrl) {
                    Image(
                        painter = rememberAsyncImagePainter(book?.coverUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp)
                            .then(imageModifier),
                    )
                    IconButton(onClick = onDeleteCoverImage) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = CoreR.drawable.ic_clear),
                            contentDescription = null,
                            tint = AppTheme.colors.primary
                        )
                    }
                } else {
                    Image(
                        imageVector = ImageVector.vectorResource(CoreR.drawable.ic_camera),
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.dp)
                            .then(imageModifier)
                            .padding(40.dp),
                    )
                }
            }
            OutlinedTextField(
                value = book?.title ?: "",
                onValueChange = onTitleChanged,
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.text_book_title)) },
                keyboardActions = keyboardActions,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
            )
            OutlinedTextField(
                value = book?.author ?: "",
                onValueChange = onAuthorChanged,
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.text_book_author)) },
                keyboardActions = keyboardActions,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
            )
            OutlinedTextField(
                value = book?.pages.let { if (it == 0) "" else it.toString() },
                onValueChange = onPagesChanged,
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.text_book_pages)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = keyboardActions,
                modifier = Modifier.fillMaxWidth(.5f),
            )
            OutlinedTextField(
                value = book?.year.let { if (it == 0) "" else it.toString() },
                onValueChange = onYearChanged,
                singleLine = true,
                label = { Text(text = stringResource(id = R.string.text_book_year)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                keyboardActions = keyboardActions,
                modifier = Modifier.fillMaxWidth(.5f),
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = book?.available == true,
                    onCheckedChange = onAvailabilityChange
                )
                Text(text = stringResource(id = R.string.text_book_available))
            }
            Text(text = stringResource(id = R.string.text_book_rating))
            RatingBar(rating = book?.rating ?: 0f, onChange = onRatingChanged)
            Text(text = stringResource(id = R.string.text_book_media_type))
            Row(verticalAlignment = Alignment.CenterVertically) {
                MediaType.values().forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = book?.mediaType == it,
                            onClick = { onMediaTypeChanged(it) })
                        Text(text = stringResource(id = mediaTypeText(it)))
                    }

                }
            }
            ComboBox(
                items = publishers,
                value = book?.publisher,
                label = stringResource(id = R.string.text_book_publisher),
                onItemSelected = onPublisherChanged,
                itemAsString = { it.name }
            )
        }
        Box(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Button(
                onClick = onSaveClick,
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = CoreR.string.button_save))
            }
        }
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
private fun PreviewBookScreenContent() {
    BookAppTheme {
        BookFormScreenContent(
            book = null,
            onTitleChanged = {},
            onAuthorChanged = {},
            onPagesChanged = {},
            onYearChanged = {},
            onAvailabilityChange = {},
            onRatingChanged = {},
            onMediaTypeChanged = {},
            publishers = emptyList(),
            onPublisherChanged = {},
            onCreateCoverImageUri = { Uri.EMPTY },
            onConfirmCoverImage = {},
            onDeleteCoverImage = {},
            isFormValid = true,
            onSaveClick = {}
        )
    }
}