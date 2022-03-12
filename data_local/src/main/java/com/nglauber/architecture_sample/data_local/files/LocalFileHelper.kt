package com.nglauber.architecture_sample.data_local.files

import com.nglauber.architecture_sample.domain.entities.Book
import java.io.File

class LocalFileHelper : FileHelper {

    override fun deleteExistingCover(book: Book): Boolean {
        return if (book.coverUrl.startsWith("file:")) {
            val coverFile = File(book.coverUrl.replace("file://", ""))
            return if (coverFile.exists()) {
                coverFile.delete()
            } else {
                true
            }
        } else {
            true
        }
    }
}
