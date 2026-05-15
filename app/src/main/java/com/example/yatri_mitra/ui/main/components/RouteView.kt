package com.example.yatri_mitra.ui.main.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.yatri_mitra.data.model.Route
import com.example.yatri_mitra.data.model.Vehicle
import kotlin.random.Random

@Composable
fun RouteView(vehicles: List<Vehicle>, route: Route) {
    val density = LocalDensity.current
    val marginPx = with(density) { 40.dp.toPx() }
    
    // Animation for ambient traffic
    val infiniteTransition = rememberInfiniteTransition(label = "ambient_traffic")
    val ambientOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "offset"
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color(0xFF8BC34A)), // Grass/Base Green
        contentAlignment = Alignment.Center
    ) {
        val widthPx = constraints.maxWidth.toFloat()
        val heightPx = constraints.maxHeight.toFloat()
        val trackWidthPx = widthPx - (2 * marginPx)
        val roadHeightPx = with(density) { 80.dp.toPx() }
        val centerY = heightPx / 2

        Canvas(modifier = Modifier.fillMaxSize()) {
            // 1. Draw Buildings & City Layout (Background)
            val buildingColor = Color(0xFFB0BEC5)
            val roofColor = Color(0xFF90A4AE)
            
            // Top Row Buildings
            for (i in 0..10) {
                val x = i * 150f - (ambientOffset % 150f)
                drawRect(
                    color = buildingColor,
                    topLeft = Offset(x, 20f),
                    size = Size(80f, 100f)
                )
                drawRect(
                    color = roofColor,
                    topLeft = Offset(x + 10f, 30f),
                    size = Size(60f, 80f)
                )
            }

            // Bottom Row Buildings
            for (i in 0..10) {
                val x = i * 200f + (ambientOffset % 200f) - 200f
                drawRect(
                    color = buildingColor,
                    topLeft = Offset(x, heightPx - 120f),
                    size = Size(100f, 100f)
                )
            }

            // 2. Draw Road Infrastructure
            // Road Bed
            drawRect(
                color = Color(0xFF455A64),
                topLeft = Offset(0f, centerY - roadHeightPx / 2),
                size = Size(widthPx, roadHeightPx)
            )
            
            // Footpaths (Sidewalks)
            val sidewalkHeight = 10.dp.toPx()
            drawRect(
                color = Color(0xFFCFD8DC),
                topLeft = Offset(0f, centerY - roadHeightPx / 2 - sidewalkHeight),
                size = Size(widthPx, sidewalkHeight)
            )
            drawRect(
                color = Color(0xFFCFD8DC),
                topLeft = Offset(0f, centerY + roadHeightPx / 2),
                size = Size(widthPx, sidewalkHeight)
            )

            // Center Lane Marking
            val dashWidth = 30.dp.toPx()
            val dashGap = 20.dp.toPx()
            drawLine(
                color = Color.White.copy(alpha = 0.8f),
                start = Offset(0f, centerY),
                end = Offset(widthPx, centerY),
                strokeWidth = 3.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap), 0f)
            )

            // 3. Ambient Traffic (Background cars)
            val ambientCarColor = Color.White.copy(alpha = 0.4f)
            for (i in 0..5) {
                val x = (i * 400f + ambientOffset) % widthPx
                drawRoundRect(
                    color = ambientCarColor,
                    topLeft = Offset(x, centerY + 15.dp.toPx()),
                    size = Size(25.dp.toPx(), 12.dp.toPx()),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                )
            }
        }

        // 4. User's Stop (Only ONE red marker)
        val userStopOffsetPx = marginPx + (route.userStop / route.length) * trackWidthPx
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(x = with(density) { (userStopOffsetPx - 40.dp.toPx()).toDp() }, y = (-40).dp)
        ) {
            StopMarker(name = "Pick-up Point", isUserStop = true)
        }

        // Start/End dots (Not prominent)
        Box(modifier = Modifier.align(Alignment.CenterStart).offset(x = with(density) { (marginPx/2).toDp() })) {
            StopMarker(name = "", isUserStop = false)
        }
        Box(modifier = Modifier.align(Alignment.CenterEnd).offset(x = with(density) { (-marginPx/2).toDp() })) {
            StopMarker(name = "", isUserStop = false)
        }

        // 5. Active Vehicles
        vehicles.forEach { vehicle ->
            val vehicleXPx = marginPx + (vehicle.position / route.length) * trackWidthPx
            val animatedXPx by animateFloatAsState(
                targetValue = vehicleXPx,
                animationSpec = spring(stiffness = Spring.StiffnessLow),
                label = "vehicle_pos"
            )

            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(
                        x = with(density) { (animatedXPx - 22.dp.toPx()).toDp() },
                        y = if (vehicle.id.hashCode() % 2 == 0) (-18).dp else 8.dp // Position in lanes
                    )
            ) {
                VehicleMarker(vehicle = vehicle)
            }
        }
    }
}
