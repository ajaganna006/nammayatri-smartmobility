package com.example.yatri_mitra.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    val vehicles: StateFlow<List<Vehicle>> = repository.vehicles

    init {
        start()
    }

    private fun start() {
        viewModelScope.launch {
            repository.startSimulation()
        }
    }

    /**
     * Get ETA of nearest vehicle
     */
    fun getNearestETA(): Float {
        val list = vehicles.value
        if (list.isEmpty()) return 0f

        val nearest = list.minByOrNull {
            MathUtils.calculateETA(it)
        }

        return nearest?.let { MathUtils.calculateETA(it) } ?: 0f
    }
}