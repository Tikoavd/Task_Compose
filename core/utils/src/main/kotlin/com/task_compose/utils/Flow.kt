package com.task_compose.utils

import kotlinx.coroutines.flow.flow

fun <T> emitFlow(action: suspend () -> T) = flow { emit(action.invoke()) }
