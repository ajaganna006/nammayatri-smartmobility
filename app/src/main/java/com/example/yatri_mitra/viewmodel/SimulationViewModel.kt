package com.example.yatri_mitra.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yatri_mitra.data.model.Route
import com.example.yatri_mitra.data.model.Vehicle
import com.example.yatri_mitra.data.repository.SimulationRepository
import com.example.yatri_mitra.utils.MathUtils
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Connects UI with simulation logic
 */
class SimulationViewModel : ViewModel() {

    private val repository = SimulationRepository()

    val currentRoute: StateFlow<Route> = repository.currentRoute
    val availableRoutes = repository.availableRoutes
    val vehicles: StateFlow<List<Vehicle>> = repository.vehicles
    val isPlaying: StateFlow<Boolean> = repository.isPlaying

    init {
        start()
    }

    private fun start() {
        viewModelScope.launch {
            repository.startSimulation()
        }
    }

    fun setRoute(route: Route) = repository.setRoute(route)
    fun togglePlayPause() = repository.togglePlayPause()
    fun resetSimulation() = repository.resetSimulation()

    /**
     * Get the nearest vehicle to the user stop
     */
    fun getNearestVehicle(): Vehicle? {
        val list = vehicles.value
        val route = currentRoute.value
        if (list.isEmpty()) return null

        return list.minByOrNull {
            MathUtils.calculateDistance(it, route)
        }
    }
}
