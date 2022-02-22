package com.vikayarska.domain.viewstates

sealed class BaseScreenState<T> {
    class Loading<T> : BaseScreenState<T>()
    class Empty<T> : BaseScreenState<T>()
    data class Loaded<T>(val data: T) : BaseScreenState<T>()
    data class Error<T>(val message: String?) : BaseScreenState<T>()
}