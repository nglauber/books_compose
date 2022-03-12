package com.nglauber.architecture_sample.domain.navigation

import com.nglauber.architecture_sample.domain.entities.Book

interface Router<T> {
    val navigationController: T
    fun showLogin()
    fun showBooksList()
    fun showBookForm(book: Book? = null)
    fun showBookDetails(book: Book)
    fun showSettings()
    fun back()
    fun navigationUp(): Boolean
}
