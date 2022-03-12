package com.nglauber.architecture_sample.di

import android.content.Context
import androidx.activity.ComponentActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.nglauber.architecture_sample.R
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.login.auth.FirebaseSignIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class AuthModule {
    @Provides
    fun providesAuth(
        @ActivityContext context: Context
    ): Auth<Unit, GoogleSignInAccount?> {
        return FirebaseSignIn(
            context as ComponentActivity,
            context.getString(R.string.default_web_client_id)
        )
    }
}