package com.vikayarska.data.network

import com.vikayarska.domain.model.BaseResult
import retrofit2.Response


fun <T> Response<T>.getResponse(): BaseResult<T> {
    if (this.isSuccessful) {
        this.body()?.let { return BaseResult.Success(it) }
        return BaseResult.Empty()
    }
    return BaseResult.Error("${this.code()} -> ${this.message()}")
}

