package com.example.yatri_mitra.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.ElectricRickshaw
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yatri_mitra.data.model.Vehicle
import com.example.yatri_mitra.data.model.VehicleType

@Composable
fun VehicleMarker(vehicle: Vehicle, modifier: Modifier = Modifier) {
    val (icon, color) = when (vehicle.type) {
        VehicleType.AUTO -> Icons.Default.ElectricRickshaw to Color(0xFF4CAF50) // Green/Yellowish Green
        VehicleType.BUS -> Icons.Default.DirectionsBus to Color(0xFF1976D2)  // Blue
        VehicleType.CAR -> Icons.Default.DirectionsCar to Color(0xFFFBC02D)   // Yellow
    }

    Box(
        modifier = modifier
            .size(44.dp)
            .shadow(6.dp, CircleShape)
            .background(Color.White, CircleShape)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
