package com.nglauber.architecture_sample.data_local.files

import com.nglauber.architecture_sample.domain.entities.Book

interface FileHelper {

    fun deleteExistingCover(book: Book): Boolean
}
