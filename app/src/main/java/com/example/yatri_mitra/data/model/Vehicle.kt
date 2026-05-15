package com.example.yatri_mitra.data.model

import com.google.firebase.database.IgnoreExtraProperties

enum class VehicleType {
    AUTO, BUS, CAR
}

enum class VehicleStatus {
    MOVING, STOPPED, DELAYED
}

@IgnoreExtraProperties
data class Vehicle(
    val id: String = "",
    val position: Float = 0f,
    val speed: Float = 0f,
    val type: VehicleType = VehicleType.AUTO,
    val status: VehicleStatus = VehicleStatus.MOVING,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val routeId: String = "",
    val passengerCount: Int = 0,
    val lastUpdated: Long = System.currentTimeMillis()
)
