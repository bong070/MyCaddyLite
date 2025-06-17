package com.example.mycaddylite.data.model

data class GolfCourseDetailResponse(
    val id: Int,
    val name: String,
    val holes: List<Hole>
)

data class Hole(
    val hole_number: Int,
    val tee_boxes: List<TeeBox>,
    val green_center: LatLng
)

data class TeeBox(
    val tee_type: String,
    val location: LatLng
)

data class LatLng(
    val latitude: Double,
    val longitude: Double
)