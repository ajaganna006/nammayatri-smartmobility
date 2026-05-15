package com.example.yatri_mitra.utils

import com.example.yatri_mitra.data.model.Route
import com.example.yatri_mitra.data.model.Vehicle

/**
 * Utility functions for calculations
 */
object MathUtils {

    /**
     * Calculate Distance for a vehicle to reach user stop.
     * Handles circular route distance.
     */
    fun calculateDistance(vehicle: Vehicle, route: Route): Float {
        return if (vehicle.position <= route.userStop) {
            route.userStop - vehicle.position
        } else {
            (route.length - vehicle.position) + route.userStop
        }
    }

    /**
     * Calculate ETA for a vehicle to reach user stop.
     * Formula: ETA = Distance / Speed.
     */
    fun calculateETA(vehicle: Vehicle, route: Route): Float {
        val distance = calculateDistance(vehicle, route)
        return if (vehicle.speed > 0) distance / vehicle.speed else 0f
    }

    /**
     * Normalize position if exceeds route length to simulate a continuous loop.
     */
    fun normalizePosition(position: Float, route: Route): Float {
        return position % route.length
    }
}
