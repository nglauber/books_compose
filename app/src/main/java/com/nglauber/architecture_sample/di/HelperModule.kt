package com.nglauber.architecture_sample.di

import android.content.Context
import com.nglauber.architecture_sample.core.theme.ThemeManager
import com.nglauber.architecture_sample.core_android.files.FilePicker
import com.nglauber.architecture_sample.core_android.ui.theme.DarkModeManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HelperModule {
    @Provides
    fun providesFilePicker(
        @ApplicationContext context: Context
    ): FilePicker {
        return FilePicker(context, "com.nglauber.architecture_sample.fileprovider")
    }
}

@Module
@InstallIn(SingletonComponent::class)
class HelperModuleSingleton {
    @Singleton
    @Provides
    fun providesDarkModeManager(
        @ApplicationContext context: Context,
    ): DarkModeManager {
        return DarkModeManager(context)
    }

    @Singleton
    @Provides
    fun providesThemeManager(
        @ApplicationContext context: Context
    ): ThemeManager {
        return DarkModeManager(context)
    }
}