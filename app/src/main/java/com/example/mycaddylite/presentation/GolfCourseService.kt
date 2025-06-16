package com.example.mycaddylite.presentation

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GolfCourseService {
    @GET("courses")
    suspend fun getCoursesNearby(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radius: Int = 20,
    ): CourseResponse
}

data class CourseResponse(
    val courses: List<GolfCourse>
)

data class GolfCourse(
    val id: String,
    val name: String,
    val location: String
)