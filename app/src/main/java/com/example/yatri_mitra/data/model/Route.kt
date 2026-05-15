package com.example.yatri_mitra.data.model

data class Route(
    val id: String,
    val name: String,
    val length: Float,
    val userStop: Float,
    val activeVehicles: Int,
    val startLat: Double,
    val startLon: Double,
    val endLat: Double,
    val endLon: Double
)
