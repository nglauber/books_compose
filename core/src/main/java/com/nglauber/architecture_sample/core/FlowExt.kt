package com.nglauber.architecture_sample.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList

suspend fun <T> Flow<T>.takeTwo() = this.take(2).toList()