package com.example.yatri_mitra.data.repository

import com.example.yatri_mitra.data.model.Route
import com.example.yatri_mitra.data.model.Vehicle
import com.example.yatri_mitra.data.model.VehicleType
import com.example.yatri_mitra.utils.Constants
import com.example.yatri_mitra.utils.MathUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

/**
 * Handles vehicle simulation logic
 */
class SimulationRepository {

    val availableRoutes = listOf(
        Route("1", "City Center - University", 100f, 80f, 3, 12.9716, 77.5946, 12.9352, 77.6245), // Bengaluru
        Route("2", "Railway Station - Bus Stand", 150f, 120f, 5, 12.9780, 77.5693, 12.9650, 77.5850),
        Route("3", "North Market - South Plaza", 80f, 40f, 2, 13.0068, 77.5810, 12.9250, 77.5930)
    )

    private val _currentRoute = MutableStateFlow(availableRoutes[0])
    val currentRoute: StateFlow<Route> = _currentRoute

    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicles: StateFlow<List<Vehicle>> = _vehicles

    private val _isPlaying = MutableStateFlow(true)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    init {
        initializeVehicles(_currentRoute.value)
    }

    fun setRoute(route: Route) {
        _currentRoute.value = route
        initializeVehicles(route)
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value
    }

    fun resetSimulation() {
        initializeVehicles(_currentRoute.value)
    }

    private fun initializeVehicles(route: Route) {

        val list = List(route.activeVehicles) { index ->

            Vehicle(
                id = index.toString(),

                position = Random.nextFloat() * route.length,

                speed = Random.nextFloat()
                    .coerceIn(
                        Constants.MIN_SPEED,
                        Constants.MAX_SPEED
                    ),

                type = VehicleType.entries
                    .toTypedArray()
                    .random()
            )
        }

        _vehicles.value = list
    }

    suspend fun startSimulation() {
        while (true) {
            delay(Constants.UPDATE_INTERVAL)

            if (_isPlaying.value) {
                val route = _currentRoute.value
                val updated = _vehicles.value.map {
                    val deltaSec = Constants.UPDATE_INTERVAL / 1000f
                    val newPos = it.position + (it.speed * deltaSec)
                    it.copy(position = MathUtils.normalizePosition(newPos, route))
                }

                _vehicles.value = updated
            }
        }
    }
}
