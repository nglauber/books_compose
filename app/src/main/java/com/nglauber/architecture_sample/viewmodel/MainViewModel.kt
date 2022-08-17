package com.nglauber.architecture_sample.viewmodel

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.nglauber.architecture_sample.BookApp
import com.nglauber.architecture_sample.core.auth.Auth
import com.nglauber.architecture_sample.core.auth.AuthStateListener
import com.nglauber.architecture_sample.core.theme.ThemeMode
import com.nglauber.architecture_sample.domain.usecases.ThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val themeUseCase: ThemeUseCase
) : ViewModel(), DefaultLifecycleObserver {

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn.asStateFlow()

    val currentTheme = themeUseCase.themeMode

    var auth: Auth<*, *>? = null
        set(value) {
            field?.removeAllListeners()
            field = value?.apply {
                addAuthChangeListener(object : AuthStateListener {
                    override fun onAuthChanged(isLoggedIn: Boolean) {
                        _isLoggedIn.value = isLoggedIn
                    }
                })
            }
        }

    fun isDarkMode(themeMode: ThemeMode): Boolean? {
        return themeUseCase.isDark(themeMode)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        auth = BookApp.instance?.auth
    }
}