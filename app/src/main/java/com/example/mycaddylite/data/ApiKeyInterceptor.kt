package com.example.mycaddylite.data

import okhttp3.Interceptor
import okhttp3.Response
import com.example.mycaddylite.BuildConfig

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", BuildConfig.XRAPID_API_KEY)
            .addHeader("X-RapidAPI-Host", "golf-course-finder.p.rapidapi.com")
            .build()
        return chain.proceed(request)
    }
}
