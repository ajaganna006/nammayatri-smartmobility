package com.example.yatri_mitra.ui.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Modern Stop Marker - Shows only the user's stop in Red as per requirements.
 */
@Composable
fun StopMarker(name: String, isUserStop: Boolean = false) {
    if (!isUserStop) return // Remove non-user stops to avoid duplicates

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .shadow(8.dp, CircleShape)
                .background(Color.Red, CircleShape)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Black,
            color = Color.Red,
            maxLines = 1,
            fontSize = 12.sp
        )
        
        Surface(
            color = Color.Red.copy(alpha = 0.1f),
            shape = CircleShape
        ) {
            Text(
                text = "YOUR STOP",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
