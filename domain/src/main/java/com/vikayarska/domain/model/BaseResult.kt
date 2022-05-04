package com.vikayarska.domain.model

import java.lang.Exception

sealed class BaseResult<T> {
    class Success<T>(val data: T) : BaseResult<T>()
    class Empty<T> : BaseResult<T>()
    class Error<T>(val message: String? , val exception: Exception? = null) : BaseResult<T>()
}

sealed class UpdateResult {
    object Success : UpdateResult()
    object Empty : UpdateResult()
    class Error(val message: String?, val exception: Exception? = null) : UpdateResult()
}