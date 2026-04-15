package com.task_compose.network.api.extension

import retrofit2.HttpException

fun Throwable.getErrorBody() = (this as? HttpException)?.response()?.errorBody()?.string()
