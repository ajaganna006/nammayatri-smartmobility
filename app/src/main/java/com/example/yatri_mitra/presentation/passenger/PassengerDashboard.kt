package com.example.yatri_mitra.presentation.passenger

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yatri_mitra.ui.main.components.EtaDisplay
import com.example.yatri_mitra.ui.main.components.RouteView
import com.example.yatri_mitra.utils.MathUtils
import com.example.yatri_mitra.viewmodel.SimulationViewModel

@Composable
fun PassengerDashboard(viewModel: SimulationViewModel = viewModel()) {
    val vehicles by viewModel.vehicles.collectAsState()
    val route by viewModel.currentRoute.collectAsState()
    
    val nearestVehicle = viewModel.getNearestVehicle()
    val distance = nearestVehicle?.let { MathUtils.calculateDistance(it, route) } ?: 0f
    val eta = nearestVehicle?.let { MathUtils.calculateETA(it, route) } ?: 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        // Route Information Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = route.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Real-time tracking active",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
                Surface(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "${vehicles.size} Vehicles",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Realistic Route Visualization
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                RouteView(vehicles = vehicles, route = route)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ETA and Distance Display
        AnimatedVisibility(
            visible = vehicles.isNotEmpty(),
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            EtaDisplay(distance = distance, eta = eta)
        }
    }
}
