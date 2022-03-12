package com.nglauber.architecture_sample.books.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.nglauber.architecture_sample.core.navigation.AppRoutes

// Entry point for this feature
object BooksFeature : AppRoutes("BooksFeature")

// Screens
object BooksList : AppRoutes("BooksList")

object BookDetails : AppRoutes("BookDetails") {
    const val paramBookId = "bookId"
    override val route: String = "$baseRoute/{$paramBookId}"
    val navArguments = listOf(
        navArgument(paramBookId) {
            type = NavType.StringType
        }
    )

    fun buildBookDetailsRoute(id: String) = "$baseRoute/$id"
}

object BookForm : AppRoutes("BookForm") {
    const val paramBookId = "bookId"
    const val paramNewBook = "newBook"
    override val route: String = "$baseRoute/{$paramBookId}"
    val navArguments = listOf(
        navArgument(paramBookId) {
            type = NavType.StringType
        }
    )

    fun buildBookDetailsRoute(id: String?) = "$baseRoute/${id ?: paramNewBook}"
}