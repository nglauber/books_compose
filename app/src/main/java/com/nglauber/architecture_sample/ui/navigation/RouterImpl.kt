package com.nglauber.architecture_sample.ui.navigation

import androidx.navigation.NavHostController
import com.nglauber.architecture_sample.books.navigation.BookDetails
import com.nglauber.architecture_sample.books.navigation.BookForm
import com.nglauber.architecture_sample.books.navigation.BooksList
import com.nglauber.architecture_sample.domain.entities.Book
import com.nglauber.architecture_sample.domain.navigation.Router
import com.nglauber.architecture_sample.login.navigation.LoginFeature
import com.nglauber.architecture_sample.login.navigation.LoginScreen
import com.nglauber.architecture_sample.settings.navigation.SettingsFeature

class RouterImpl(
    private val navigationController: NavHostController
) : Router {

    override fun showLogin() {
        navigationController.navigate(LoginScreen.route) {
            popUpTo(0) // reset stack
        }
    }

    override fun showBooksList() {
        navigationController.navigate(BooksList.route) {
            popUpTo(LoginFeature.route) {
                inclusive = true
                saveState = false
            }
        }
    }

    override fun showBookForm(book: Book?) {
        navigationController.navigate(
            BookForm.buildBookDetailsRoute(book?.id)
        )
    }

    override fun showBookDetails(book: Book) {
        navigationController.navigate(BookDetails.buildBookDetailsRoute(book.id))
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