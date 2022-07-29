package com.nglauber.architecture_sample.books.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nglauber.architecture_sample.books.R
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.core_android.ui.components.AsyncData
import com.nglauber.architecture_sample.core_android.ui.components.GenericError
import com.nglauber.architecture_sample.core_android.ui.components.RatingBar
import com.nglauber.architecture_sample.core_android.ui.theme.BookAppTheme
import com.nglauber.architecture_sample.core_android.ui.theme.custom.AppTheme
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.core_android.R as CoreR

@Composable
fun BookDetailsScreen(
    bookDetailsState: ResultState<Book?>,
    onEditClick: (Book) -> Unit,
    onBackPressed: () -> Unit,
) {
    AsyncData(resultState = bookDetailsState) { book ->
        if (book == null) {
            GenericError(
                onDismissAction = onBackPressed
            )
        } else {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = stringResource(id = CoreR.string.app_name))
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = onBackPressed
                            ) {
                                Icon(
                                    ImageVector.vectorResource(id = CoreR.drawable.ic_arrow_back),
                                    contentDescription = stringResource(id = CoreR.string.cd_back)
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = { onEditClick(book) }
                            ) {
                                Icon(
                                    ImageVector.vectorResource(id = CoreR.drawable.ic_edit),
                                    contentDescription = stringResource(id = CoreR.string.menu_action_edit)
                                )
                            }
                        }
                    )
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(it)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(book.coverUrl),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(bookCoverSize * 1.5f)
                            .background(AppTheme.colors.secondary),
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Text(
                        text = book.title,
                        style = AppTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = book.author,
                        style = AppTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_format_book_pages,
                            formatArgs = arrayOf(book.pages)
                        )
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_format_book_year,
                            formatArgs = arrayOf(book.year)
                        )
                    )
                    book.publisher?.let {
                        Text(
                            text = stringResource(
                                id = R.string.text_format_book_publisher,
                                formatArgs = arrayOf(it.name)
                            )
                        )
                    }
                    Text(text = stringResource(id = availableText(book.available)))
                    Text(text = stringResource(id = mediaTypeText(book.mediaType)))
                    RatingBar(rating = book.rating, onChange = {})
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBookDetailsContent() {
    BookAppTheme {
        BookDetailsScreen(
            bookDetailsState = ResultState.Success(bookForUiPreview()),
            onEditClick = {},
            onBackPressed = {},
        )
    }
}