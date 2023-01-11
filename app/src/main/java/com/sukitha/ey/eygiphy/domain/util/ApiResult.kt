package com.sukitha.ey.eygiphy.domain.util

sealed class ApiResult<T>(val data: T? = null, val errorMessage: String? = null) {
    class Success<T>(data: T?) : ApiResult<T>(data)
    class Error<T>(data: T? = null, message: String? = null) : ApiResult<T>(data, message)
}
