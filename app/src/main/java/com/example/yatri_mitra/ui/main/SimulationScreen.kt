package com.example.yatri_mitra.ui.main

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yatri_mitra.viewmodel.SimulationViewModel
import com.example.yatri_mitra.ui.main.components.RouteView
import com.example.yatri_mitra.ui.main.components.EtaDisplay
import com.example.yatri_mitra.utils.MathUtils

@Composable
fun PauseIcon() {
    Canvas(modifier = Modifier.size(24.dp)) {
        val barWidth = size.width * 0.25f
        val barHeight = size.height * 0.7f
        val startX = size.width * 0.15f
        val startY = (size.height - barHeight) / 2
        
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(startX, startY),
            size = Size(barWidth, barHeight),
            cornerRadius = CornerRadius(2.dp.toPx())
        )
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(size.width - startX - barWidth, startY),
            size = Size(barWidth, barHeight),
            cornerRadius = CornerRadius(2.dp.toPx())
        )
    }
}

@Composable
fun SimulationScreen(viewModel: SimulationViewModel) {

    val vehicles by viewModel.vehicles.collectAsState()
    val route by viewModel.currentRoute.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    
    val nearestVehicle = viewModel.getNearestVehicle()
    val distance = nearestVehicle?.let { MathUtils.calculateDistance(it, route) } ?: 0f
    val eta = nearestVehicle?.let { MathUtils.calculateETA(it, route) } ?: 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Route Header
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
                        text = "Next Stop: Your Stop",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
                Badge(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("${vehicles.size} Live", modifier = Modifier.padding(4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Route Visualization Container
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
                RouteView(vehicles, route)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ETA and Distance Info
        AnimatedVisibility(
            visible = vehicles.isNotEmpty(),
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            EtaDisplay(distance = distance, eta = eta)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Playback Controls
        Surface(
            tonalElevation = 2.dp,
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.resetSimulation() }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Reset", tint = MaterialTheme.colorScheme.primary)
                }

                FloatingActionButton(
                    onClick = { viewModel.togglePlayPause() },
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp),
                    elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp)
                ) {
                    if (isPlaying) PauseIcon() else Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = Color.White)
                }
            }
        }
    }
}
