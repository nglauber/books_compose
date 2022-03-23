package com.nglauber.architecture_sample.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.domain.usecases.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val useCase: AuthUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow<ResultState<Unit>?>(null)
    val loginState: StateFlow<ResultState<Unit>?> = _loginState

    private var loginJob: Job? = null

    fun login() {
        loginJob?.cancel()
        loginJob = viewModelScope.launch {
            useCase.login().collect {
                _loginState.value = it
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = null
    }
}