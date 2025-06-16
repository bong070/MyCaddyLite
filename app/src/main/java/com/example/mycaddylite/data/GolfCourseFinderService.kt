package com.example.mycaddylite.data

import com.example.mycaddylite.data.GolfCourseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GolfCourseFinderService {
    @GET("api/golf-clubs")
    suspend fun getNearbyCourses(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("kilometers") miles: Int = 5,
    ): Response<List<GolfCourse>>
}
