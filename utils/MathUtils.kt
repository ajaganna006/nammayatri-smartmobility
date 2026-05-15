package com.example.yatri_mitra.utils

import com.example.yatri_mitra.data.model.Vehicle

/**
 * Utility functions for calculations
 */
object MathUtils {

    /**
     * Calculate ETA for a vehicle to reach user stop
     */
    fun calculateETA(vehicle: Vehicle): Float {
        val distance = Constants.USER_STOP_POSITION - vehicle.position
        return if (distance > 0) distance / vehicle.speed else 0f
    }

    /**
     * Normalize position if exceeds route length
     */
    fun normalizePosition(position: Float): Float {
        return if (position > Constants.ROUTE_LENGTH) 0f else position
    }
}