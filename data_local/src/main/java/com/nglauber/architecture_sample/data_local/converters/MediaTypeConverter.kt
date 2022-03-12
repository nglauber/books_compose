package com.nglauber.architecture_sample.data_local.converters

import androidx.room.TypeConverter
import com.nglauber.architecture_sample.domain.entities.MediaType

internal class MediaTypeConverter {
    @TypeConverter
    fun fromMediaType(value: MediaType): Int {
        return value.let { value.ordinal }
    }

    @TypeConverter
    fun toMediaType(value: Int): MediaType {
        return MediaType.values()[value]
    }
}