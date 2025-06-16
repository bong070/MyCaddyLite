package com.example.mycaddylite.data.model

import com.google.gson.annotations.SerializedName

// 전체 응답
data class GolfCourseResponse(
    @SerializedName("courses") val courses: List<GolfCourse>
)

// 코스 하나
data class GolfCourse(
    @SerializedName("club_name") val courseName: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("club_id") val clubId: String
)
