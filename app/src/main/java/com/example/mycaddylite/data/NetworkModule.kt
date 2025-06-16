package com.example.mycaddylite.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.mycaddylite.BuildConfig

object NetworkModule {

    private const val BASE_URL = "https://golf-course-finder.p.rapidapi.com/"
    private const val API_KEY = BuildConfig.XRAPID_API_KEY

    private val authInterceptor = Interceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", "golf-course-finder.p.rapidapi.com")
            .build()
        chain.proceed(request)
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: GolfCourseFinderService = retrofit.create(GolfCourseFinderService::class.java)
}
