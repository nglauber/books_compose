package com.nglauber.architecture_sample.books.di

import com.nglauber.architecture_sample.books.viewmodel.BookDetailsViewModel
import com.nglauber.architecture_sample.books.viewmodel.BookFormViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider {

    fun bookDetailsViewModelFactory(): BookDetailsViewModel.Factory

    fun bookFormViewModelFactory(): BookFormViewModel.Factory
}