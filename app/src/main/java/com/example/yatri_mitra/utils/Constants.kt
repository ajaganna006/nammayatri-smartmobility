package com.example.yatri_mitra.utils

/**
 * App-wide constants for realistic mobility simulation
 */
object Constants {

    // Simulation settings
    const val UPDATE_INTERVAL = 16L // milliseconds (~60 fps)


    const val MIN_SPEED = 5f

    const val MAX_SPEED = 25f
    // Realistic Speed (Converted to units/sec where 1 unit = 1 meter)
    // 1 km/h = 0.277778 m/s
    const val SPEED_VILLAGE_MIN = 20 * 0.2778f
    const val SPEED_VILLAGE_MAX = 30 * 0.2778f
    const val SPEED_TOWN_MIN = 30 * 0.2778f
    const val SPEED_TOWN_MAX = 45 * 0.2778f

    // Delays
    const val STOP_DURATION_MS = 3000L // 3 seconds stop
    const val ACCELERATION = 2.0f // m/s^2
    const val DECELERATION = 3.0f // m/s^2
    
    // Firebase Paths
    const val PATH_VEHICLES = "vehicles"
    const val PATH_ROUTES = "routes"
    const val PATH_DRIVERS = "drivers"
}
