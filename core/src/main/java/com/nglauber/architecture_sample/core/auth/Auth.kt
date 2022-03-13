package com.nglauber.architecture_sample.core.auth

abstract class Auth<Input, Result> {
    protected val callbacks = mutableSetOf<AuthStateListener>()

    abstract fun isLoggedIn(): Boolean

    abstract fun signIn(
        onSuccess: () -> Unit,
        onError: (t: Throwable?) -> Unit,
        input: Input? = null,
    )

    abstract fun signOut()

    fun addAuthChangeListener(listener: AuthStateListener) {
        callbacks.add(listener)
    }

    fun removeAuthChangeListener(listener: AuthStateListener) {
        callbacks.remove(listener)
    }

    fun removeAllListeners() {
        callbacks.clear()
    }

    open fun clear() {
        callbacks.clear()
    }
}
