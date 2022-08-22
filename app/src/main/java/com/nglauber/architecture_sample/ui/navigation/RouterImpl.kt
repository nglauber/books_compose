package com.nglauber.architecture_sample.ui.navigation

import androidx.navigation.NavHostController
import com.nglauber.architecture_sample.books.navigation.BookDetailsRoute
import com.nglauber.architecture_sample.books.navigation.BookFormRoute
import com.nglauber.architecture_sample.books.navigation.BooksListRoute
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.login.navigation.LoginFeature
import com.nglauber.architecture_sample.login.navigation.LoginScreenRoute
import com.nglauber.architecture_sample.settings.navigation.SettingsFeature

class RouterImpl(
    private val navigationController: NavHostController
) : Router {

    override fun showLogin() {
        navigationController.navigate(LoginScreenRoute.route) {
            popUpTo(0) // reset stack
        }
    }

    override fun showBooksList() {
        navigationController.navigate(BooksListRoute.route) {
            popUpTo(LoginFeature.route) {
                inclusive = true
                saveState = false
            }
        }
    }

    override fun showBookForm(book: Book?) {
        navigationController.navigate(
            BookFormRoute.buildBookDetailsRoute(book?.id)
        )
    }

    override fun showBookDetails(book: Book) {
        navigationController.navigate(BookDetailsRoute.buildBookDetailsRoute(book.id))
    }

    override fun back() {
        navigationController.popBackStack()
    }

    override fun navigationUp(): Boolean {
        return navigationController.navigateUp()
    }

    override fun showSettings() {
        navigationController.navigate(SettingsFeature.route)
    }
}