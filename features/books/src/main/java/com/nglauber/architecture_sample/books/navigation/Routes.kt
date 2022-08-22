package com.nglauber.architecture_sample.books.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.nglauber.architecture_sample.core.navigation.AppRoutes

// Entry point for this feature
object BooksFeature : AppRoutes("BooksFeature")

// Screens
object BooksListRoute : AppRoutes("BooksList")

object BookDetailsRoute : AppRoutes("BookDetails") {
    private const val paramBookId = "bookId"
    override val route: String = "$baseRoute/{$paramBookId}"
    val navArguments = listOf(
        navArgument(paramBookId) {
            type = NavType.StringType
        }
    )

    fun getBookId(backStackEntry: NavBackStackEntry): String? =
        backStackEntry.arguments?.getString(paramBookId)

    fun buildBookDetailsRoute(id: String) = "$baseRoute/$id"
}

object BookFormRoute : AppRoutes("BookForm") {
    private const val paramBookId = "bookId"
    override val route: String = "$baseRoute?$paramBookId={$paramBookId}"
    val navArguments = listOf(
        navArgument(paramBookId) {
            type = NavType.StringType
            nullable = true
        }
    )

    fun getBookId(backStackEntry: NavBackStackEntry): String? =
        backStackEntry.arguments?.getString(paramBookId)

    fun buildBookDetailsRoute(id: String?) =
        id?.let { "$baseRoute?$paramBookId=$id" } ?: baseRoute
}