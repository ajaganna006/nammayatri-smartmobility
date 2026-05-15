package com.example.yatri_mitra.data.repository

import com.example.yatri_mitra.data.model.Vehicle
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

    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    val vehicles: StateFlow<List<Vehicle>> = _vehicles

    init {
        initializeVehicles()
    }

    private fun initializeVehicles() {
        val list = List(Constants.VEHICLE_COUNT) { index ->
            Vehicle(
                id = index,
                position = Random.nextFloat() * Constants.ROUTE_LENGTH,
                speed = Random.nextFloat()
                    .coerceIn(Constants.MIN_SPEED, Constants.MAX_SPEED)
            )
        }
        _vehicles.value = list
    }

    /**
     * Starts continuous simulation loop
     */
    suspend fun startSimulation() {
        while (true) {
            delay(Constants.UPDATE_INTERVAL)

            val updated = _vehicles.value.map {
                val newPos = it.position + it.speed
                it.copy(position = MathUtils.normalizePosition(newPos))
            }

            _vehicles.value = updated
        }
    }
}