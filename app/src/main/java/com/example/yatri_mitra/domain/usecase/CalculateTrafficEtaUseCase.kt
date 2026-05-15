package com.example.yatri_mitra.domain.usecase

import com.example.yatri_mitra.data.model.Route
import com.example.yatri_mitra.data.model.Vehicle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CalculateTrafficEtaUseCase(
    // private val mapsRepository: MapsRepository
) {
    operator fun invoke(vehicle: Vehicle, route: Route): Flow<Float> {
        // Placeholder for advanced Maps Distance Matrix ETA calculation
        return kotlinx.coroutines.flow.flow { emit(0f) }
    }
}
