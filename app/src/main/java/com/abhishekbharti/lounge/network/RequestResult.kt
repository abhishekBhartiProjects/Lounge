package com.abhishekbharti.lounge.network

sealed class RequestResult<out R> {

    data class Loading<out T>(val any: T) : RequestResult<T>()
    data class Success<out T>(val data: T) : RequestResult<T>()
    data class ApiError(val errorCode:Int, val message:String): RequestResult<Nothing>()
    data class OtherError(val exception: Exception) : RequestResult<Nothing>()
    object NetworkError: RequestResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading[any=$any]"
            is Success<*> -> "Success[data=$data]"
            is ApiError -> "ApiError[errorCode:$errorCode, message=$message]"
            is OtherError -> "OtherError[exception=$exception]"
            is NetworkError -> "Network Error"
        }
    }
}
