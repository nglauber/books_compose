package com.nglauber.architecture_sample.data_local

import com.nglauber.architecture_sample.domain.entities.Book
import java.util.*
import com.nglauber.architecture_sample.data_local.entity.Book as BookEntity

internal object BookConverter {

    fun fromData(book: Book) = BookEntity(
        book.id.ifBlank { UUID.randomUUID().toString() },
        book.title,
        book.author,
        book.coverUrl,
        book.pages,
        book.year,
        book.publisher!!,
        book.available,
        book.mediaType,
        book.rating
    )

    fun toData(entity: BookEntity) = Book().apply {
        id = entity.id
        title = entity.title
        author = entity.author
        coverUrl = entity.coverUrl
        pages = entity.pages
        year = entity.year
        publisher = entity.publisher
        available = entity.available
        mediaType = entity.mediaType
        rating = entity.rating
    }
}
