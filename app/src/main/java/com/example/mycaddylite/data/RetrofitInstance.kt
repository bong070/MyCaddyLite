package com.example.mycaddylite.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://golf-course-finder.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: GolfCourseFinderService by lazy {
        retrofit.create(GolfCourseFinderService::class.java)
    }
}
