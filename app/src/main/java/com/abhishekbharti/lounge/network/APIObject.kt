package com.abhishekbharti.lounge.network

import com.abhishekbharti.lounge.BuildConfig
import com.abhishekbharti.lounge.common.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIObject {

    private const val CONTENT_TYPE = "content-type"
    private const val CONTENT_TYPE_VALUE = "application/json"
//    private const val CONTENT_TYPE_VALUE = "multipart/form-data"

    fun build(): APIInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient())
            .build()

        return retrofit.create(APIInterface::class.java)
    }

    private fun okHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.interceptors().add(interceptor(0))
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        if(BuildConfig.DEBUG)
            httpClient.interceptors().add(httpLoggingInterceptor())

        return httpClient.build()
    }

    private fun interceptor(cacheDuration: Long): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
            requestBuilder.addHeader(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            requestBuilder.addHeader("Authorization", "Bearer ${SharedPreferenceManager.getUserSessionToken()}")
//            requestBuilder.addHeader("Authorization", "Bearer ${BuildConfig.OPENAPI_KEY}")

            if (cacheDuration > 0) {
                requestBuilder.addHeader("Cache-Control", "public, max-age=$cacheDuration")
            }
            requestBuilder.addHeader("app-version", BuildConfig.VERSION_CODE.toString())

            val request = requestBuilder.build()
            val response = chain.proceed(request)
            response
        }
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}