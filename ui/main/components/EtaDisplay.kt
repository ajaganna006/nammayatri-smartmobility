package com.example.yatri_mitra.ui.main.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

/**
 * Displays ETA
 */
@Composable
fun EtaDisplay(eta: Float) {
    Text(
        text = "Nearest ETA: ${eta.toInt()} sec",
        fontSize = 20.sp
    )
}