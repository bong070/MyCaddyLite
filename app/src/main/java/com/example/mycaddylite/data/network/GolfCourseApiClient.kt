package com.example.mycaddylite.data.network

import com.example.mycaddylite.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object GolfCourseApiClient {
    private const val BASE_URL = "https://golf-course-finder.p.rapidapi.com/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", BuildConfig.XRAPID_API_KEY)
            .addHeader("X-RapidAPI-Host", "golf-course-finder.p.rapidapi.com")
            .build()
        chain.proceed(request)
    }
    .build()

