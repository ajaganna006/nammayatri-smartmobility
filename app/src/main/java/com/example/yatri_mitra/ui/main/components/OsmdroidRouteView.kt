package com.example.yatri_mitra.ui.main.components

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.yatri_mitra.data.model.Route
import com.example.yatri_mitra.data.model.Vehicle
import com.example.yatri_mitra.data.model.VehicleType
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

/**
 * Enhanced Osmdroid Map View with professional styling and vehicle differentiation.
 */
@Composable
fun OsmdroidRouteView(
    vehicles: List<Vehicle>,
    route: Route,
    modifier: Modifier = Modifier
) {
    val vehicleMarkers = remember { mutableMapOf<String, Marker>() }

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
                setTileSource(TileSourceFactory.MAPNIK)
                setMultiTouchControls(true)
                
                // Initial camera setup
                val startPoint = GeoPoint(route.startLat, route.startLon)
                controller.setZoom(15.5)
                controller.setCenter(startPoint)

                // 1. Modern Polyline Styling (Indigo)
                val polyline = Polyline(this).apply {
                    addPoint(GeoPoint(route.startLat, route.startLon))
                    addPoint(GeoPoint(route.endLat, route.endLon))
                    color = Color.parseColor("#3F51B5")
                    width = 14f
                    outlinePaint.strokeCap = Paint.Cap.ROUND
                    outlinePaint.isAntiAlias = true
                }
                overlays.add(polyline)

                // 2. User Stop Marker (Red) - Only ONE marker as per Requirement 1
                val stopPoint = calculateInterpolatedPoint(route, route.userStop)
                val stopMarker = Marker(this).apply {
                    position = stopPoint
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    title = "Your Stop"
                    icon = ContextCompat.getDrawable(context, org.osmdroid.library.R.drawable.marker_default)?.mutate()?.apply {
                        setTint(Color.RED)
                    }
                }
                overlays.add(stopMarker)
            }
        },
        update = { map ->
            // Update or create markers for active vehicles
            val activeIds = vehicles.map { it.id }.toSet()
            
            // Remove markers for vehicles no longer in the list
            val iterator = vehicleMarkers.iterator()
            while (iterator.hasNext()) {
                val entry = iterator.next()
                if (entry.key !in activeIds) {
                    map.overlays.remove(entry.value)
                    iterator.remove()
                }
            }

            vehicles.forEach { vehicle ->
                val vehiclePoint = calculateInterpolatedPoint(route, vehicle.position)
                val marker = vehicleMarkers.getOrPut(vehicle.id) {
                    Marker(map).apply {
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        title = "${vehicle.type} ${vehicle.id}"
                        
                        // Requirement 4: Color-coded vehicles
                        icon = ContextCompat.getDrawable(map.context, org.osmdroid.library.R.drawable.marker_default)?.mutate()?.apply {
                            val tintColor = when (vehicle.type) {
                                VehicleType.BUS -> Color.parseColor("#1976D2") // Blue
                                VehicleType.AUTO -> Color.parseColor("#4CAF50") // Green/Yellowish Green
                                VehicleType.CAR -> Color.parseColor("#FBC02D") // Yellow
                            }
                            setTint(tintColor)
                        }
                        map.overlays.add(this)
                    }
                }
                marker.position = vehiclePoint
            }
            map.invalidate()
        }
    )
}

fun calculateInterpolatedPoint(route: Route, position: Float): GeoPoint {
    val fraction = (position / route.length).coerceIn(0f, 1f)
    val lat = route.startLat + (route.endLat - route.startLat) * fraction
    val lon = route.startLon + (route.endLon - route.startLon) * fraction
    return GeoPoint(lat, lon)
}
