package com.nglauber.architecture_sample

import android.app.Application
import com.nglauber.architecture_sample.core.auth.Auth
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookApp : Application() {
    var auth: Auth<*, *>? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        var instance: BookApp? = null
    }
}