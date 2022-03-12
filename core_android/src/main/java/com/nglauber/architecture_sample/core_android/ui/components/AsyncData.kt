package com.nglauber.architecture_sample.core_android.ui.components

import androidx.compose.runtime.Composable
import com.nglauber.architecture_sample.core.ResultState

@Composable
fun <T> AsyncData(
    resultState: ResultState<T?>?,
    loadingContent: @Composable () -> Unit = { GenericLoading() },
    errorContent: @Composable () -> Unit = { GenericError() },
    content: @Composable (data: T?) -> Unit
) {
    resultState.let { state ->
        when (state) {
            is ResultState.Loading -> {
                loadingContent()
            }
            is ResultState.Error -> {
                errorContent()
            }
            null -> {
                content(null)
            }
            is ResultState.Success -> {
                content(state.data)
            }
        }
    }
}