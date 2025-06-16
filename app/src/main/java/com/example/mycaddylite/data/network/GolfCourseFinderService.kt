package com.example.mycaddylite.data.network

import com.example.mycaddylite.data.model.GolfCourse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.mycaddylite.data.model.GolfCourseDetailResponse

interface GolfCourseFinderService {
    @GET("api/golf-clubs")
    suspend fun getNearbyCourses(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("kilometers") miles: Int = 5,
    ): List<GolfCourse>

    @GET("golf-clubs/{id}")
    suspend fun getGolfCourseDetails(
        @Path("id") id: String
    ): GolfCourseDetailResponse
}