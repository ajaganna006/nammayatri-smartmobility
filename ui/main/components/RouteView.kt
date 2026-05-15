package com.example.yatri_mitra.ui.main.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.yatri_mitra.data.model.Vehicle
import com.example.yatri_mitra.utils.Constants

/**
 * Draws route line and vehicles
 */
@Composable
fun RouteView(vehicles: List<Vehicle>) {

    Canvas(modifier = Modifier.fillMaxWidth()) {

        val canvasWidth = size.width
        val centerY = size.height / 2

        // Draw route line
        drawLine(
            color = Color.Gray,
            start = Offset(50f, centerY),
            end = Offset(canvasWidth - 50f, centerY),
            strokeWidth = 8f
        )

        // Draw user stop
        val stopX =
            50f + (Constants.USER_STOP_POSITION / Constants.ROUTE_LENGTH) * (canvasWidth - 100f)

        drawCircle(
            color = Color.Red,
            radius = 12f,
            center = Offset(stopX, centerY)
        )

        // Draw vehicles
        vehicles.forEach {
            val x =
                50f + (it.position / Constants.ROUTE_LENGTH) * (canvasWidth - 100f)

            drawCircle(
                color = Color.Blue,
                radius = 10f,
                center = Offset(x, centerY)
            )
        }
    }
}