package com.nglauber.architecture_sample.books.di

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nglauber.architecture_sample.books.viewmodel.BookDetailsViewModel
import com.nglauber.architecture_sample.books.viewmodel.BookFormViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun bookDetailsViewModel(bookId: String): BookDetailsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).bookDetailsViewModelFactory()

    return viewModel(factory = BookDetailsViewModel.provideFactory(factory, bookId))
}

@Composable
fun bookFormViewModel(bookId: String?): BookFormViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).bookFormViewModelFactory()

    return viewModel(factory = BookFormViewModel.provideFactory(factory, bookId))
}