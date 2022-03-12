package com.nglauber.architecture_sample.books.screens

import androidx.annotation.StringRes
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.nglauber.architecture_sample.books.R
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.entities.MediaType
import com.nglauber.architecture_sample.domain.entities.Publisher

val bookCoverSize = DpSize(96.dp, 128.dp)

@StringRes
fun availableText(available: Boolean): Int =
    if (available) R.string.text_book_available else R.string.text_book_unavailable

@StringRes
fun mediaTypeText(mediaType: MediaType): Int =
    when (mediaType) {
        MediaType.PAPER -> R.string.text_book_media_paper
        MediaType.EBOOK -> R.string.text_book_media_ebook
        else -> throw IllegalArgumentException("Invalid Media type")
    }

internal fun bookForUiPreview() =
    Book(
        id = "1",
        title = "Title",
        author = "Author",
        pages = 123,
        year = 2022,
        publisher = Publisher("1", "Publisher"),
        available = true,
        mediaType = MediaType.PAPER,
        rating = 2.5f
    )