package com.nglauber.architecture_sample.data_local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.nglauber.architecture_sample.data_local.converters.MediaTypeConverter
import com.nglauber.architecture_sample.domain.entities.MediaType
import com.nglauber.architecture_sample.domain.entities.Publisher

@Entity
@TypeConverters(MediaTypeConverter::class)
internal data class Book(
    @PrimaryKey
    var id: String,
    var title: String = "",
    var author: String = "",
    var coverUrl: String = "",
    var pages: Int = 0,
    var year: Int = 0,
    @Embedded(prefix = "publisher_")
    var publisher: Publisher,
    var available: Boolean = false,
    var mediaType: MediaType = MediaType.PAPER,
    var rating: Float = 0f
)
