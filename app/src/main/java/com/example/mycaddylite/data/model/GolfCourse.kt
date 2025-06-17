package com.example.mycaddylite.data.model

import com.google.gson.annotations.SerializedName

data class GolfCourse(
    @SerializedName("club_name") val courseName: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)
