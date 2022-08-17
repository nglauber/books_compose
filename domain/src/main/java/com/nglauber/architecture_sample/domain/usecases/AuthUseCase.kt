package com.nglauber.architecture_sample.domain.usecases

import com.nglauber.architecture_sample.core.ErrorEntity
import com.nglauber.architecture_sample.core.ResultState
import com.nglauber.architecture_sample.core.auth.Auth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val auth: Auth<*, *>
) {
    fun login(): Flow<ResultState<Unit>> {
        return callbackFlow {
            trySend(ResultState.Loading)
            auth.signIn(
                onSuccess = {
                    trySend(ResultState.Success(Unit))
                    close()
                },
                onError = {
                    trySend(ResultState.Error(ErrorEntity(it)))
                    close()
                }
            )
            awaitClose()
        }
    }

    fun logout() {
        auth.signOut()
    }
}