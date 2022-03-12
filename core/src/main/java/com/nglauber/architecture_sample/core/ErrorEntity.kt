package com.nglauber.architecture_sample.core

data class ErrorEntity(
    val throwable: Throwable? = null,
    val id: String? = null,
    val message: String? = null,
)
