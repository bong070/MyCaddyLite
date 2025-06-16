package com.example.mycaddylite.data.model

import com.google.gson.annotations.SerializedName

// 코스 하나
data class GolfCourse(
    @SerializedName("club_name") val courseName: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("club_id") val clubId: String
)
