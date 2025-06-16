package com.example.mycaddylite.data.repository

import com.example.mycaddylite.data.model.GolfCourse
import com.example.mycaddylite.data.network.RetrofitInstance

class GolfCourseRepository {
    private val service = RetrofitInstance.api

    suspend fun getNearbyCourses(latitude: String, longitude: String): List<GolfCourse> {
        val response = service.getNearbyCourses(latitude, longitude)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("API Error: ${response.code()}")
        }
    }
}