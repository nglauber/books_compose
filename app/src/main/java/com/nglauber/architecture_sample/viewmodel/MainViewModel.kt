package com.nglauber.architecture_sample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.core.auth.AuthStateListener
import com.nglauber.architecture_sample.domain.navigation.Router
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)

    var router: Router<NavHostController>? = null

    var auth: Auth<*, *>? = null
        set(value) {
            field = value?.apply {
                removeAllListeners()
                addAuthChangeListener(object : AuthStateListener {
                    override fun onAuthChanged(isLoggedIn: Boolean) {
                        _isLoggedIn.value = isLoggedIn
                    }
                })
            }
        }

    init {
        viewModelScope.launch {
            _isLoggedIn.collect { isLoggedInState ->
                if (isLoggedInState == false) {
                    router?.showLogin()
                }
            }
        }
    }
}