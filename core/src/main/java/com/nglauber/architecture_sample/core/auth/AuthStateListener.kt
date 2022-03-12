package com.nglauber.architecture_sample.core.auth

interface AuthStateListener {
    fun onAuthChanged(isLoggedIn: Boolean)
}
