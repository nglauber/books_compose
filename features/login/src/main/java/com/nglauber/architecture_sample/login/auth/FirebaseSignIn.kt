package com.nglauber.architecture_sample.login.auth

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.nglauber.architecture_sample.core.auth.Auth
import javax.inject.Inject

class FirebaseSignIn @Inject constructor(
    private val activity: ComponentActivity,
    private val webClientId: String
) : Auth<Unit, GoogleSignInAccount?>() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val authListener: FirebaseAuth.AuthStateListener =
        FirebaseAuth.AuthStateListener { fbAuth ->
            callbacks.forEach {
                it.onAuthChanged(fbAuth.currentUser != null)
            }
        }

    private val googleApiClient: GoogleSignInClient by lazy {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()
        GoogleSignIn.getClient(activity, options)
    }

    private var onLoginSuccess: (() -> Unit)? = null

    private var onLoginError: (() -> Unit)? = null

    init {
        firebaseAuth.addAuthStateListener(authListener)
    }

    private val signInLauncher = activity.registerForActivityResult(
        FirebaseSignInContract()
    ) { acct ->
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    onLoginSuccess?.invoke()
                    onLoginSuccess = null
                } else {
                    onLoginError?.invoke()
                    onLoginError = null
                }
            }
    }

    override fun isLoggedIn(): Boolean = firebaseAuth.currentUser != null

    override fun signIn(
        onSuccess: () -> Unit,
        onError: () -> Unit,
        input: Unit?,
    ) {
        onLoginSuccess = onSuccess
        onLoginError = onError
        signInLauncher.launch(googleApiClient.signInIntent)
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun clear() {
        super.clear()
        callbacks.clear()
        firebaseAuth.removeAuthStateListener(authListener)
    }

    private class FirebaseSignInContract : ActivityResultContract<Intent, GoogleSignInAccount?>() {
        override fun createIntent(context: Context, input: Intent): Intent {
            return input
        }

        override fun parseResult(resultCode: Int, intent: Intent?): GoogleSignInAccount? {
            val signInTask = GoogleSignIn.getSignedInAccountFromIntent(intent)
            return signInTask.getResult(ApiException::class.java)
        }
    }
}
