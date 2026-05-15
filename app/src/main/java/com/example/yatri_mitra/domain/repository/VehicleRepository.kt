package com.example.yatri_mitra.domain.repository

import com.example.yatri_mitra.data.model.Route
import com.example.yatri_mitra.data.model.Vehicle
import kotlinx.coroutines.flow.Flow

/**
 * Domain-level interface for Vehicle synchronization
 */
interface VehicleRepository {
    fun getActiveVehicles(routeId: String): Flow<List<Vehicle>>
    suspend fun updateVehiclePosition(vehicle: Vehicle)
}
