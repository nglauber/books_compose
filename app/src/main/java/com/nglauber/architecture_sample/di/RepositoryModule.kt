package com.nglauber.architecture_sample.di

import android.content.Context
import com.nglauber.architecture_sample.datafb.FirebaseBookRepository
import com.nglauber.architecture_sample.domain.repositories.BooksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun providesBookRepository(
        @ApplicationContext context: Context
    ): BooksRepository {
        //return RoomLocalRepository(context, LocalFileHelper())
        return FirebaseBookRepository()
    }
}