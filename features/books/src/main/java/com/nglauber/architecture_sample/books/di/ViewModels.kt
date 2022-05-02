package com.nglauber.architecture_sample.books.di

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nglauber.architecture_sample.books.viewmodel.BookDetailsViewModel
import com.nglauber.architecture_sample.books.viewmodel.BookFormViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun bookDetailsViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    bookId: String,
): BookDetailsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).bookDetailsViewModelFactory()

    return viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = BookDetailsViewModel.provideFactory(factory, bookId)
    )
}

@Composable
fun bookFormViewModel(
    viewModelStoreOwner: ViewModelStoreOwner,
    bookId: String?
): BookFormViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).bookFormViewModelFactory()

    return viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = BookFormViewModel.provideFactory(factory, bookId)
    )
}