package com.abhishekbharti.lounge.base

import com.abhishekbharti.lounge.LoungeApplication
import com.abhishekbharti.lounge.network.RequestResult
import retrofit2.Response

open class BaseRepo {
    val mApplication = LoungeApplication.getInstance()
    val apiInterface = mApplication.getAPIInterface()
    val apiInterfaceWithoutToken = mApplication.getWithoutTokenIAPIService()

    suspend fun <T, R> handleCommonResponse(
        requestFunc: suspend () -> Response<T>,
        successCallback: (Response<T>) -> RequestResult.Success<R>
    ): RequestResult<R> {

        try {
            val response = requestFunc.invoke()
            return if (response.isSuccessful) {
                successCallback(response)
            } else {
                RequestResult.ApiError(response.code(), response.message())
            }
        } catch (e: Exception) {
            return RequestResult.OtherError(e)
        }
    }
}