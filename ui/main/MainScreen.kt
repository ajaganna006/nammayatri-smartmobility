package com.example.yatri_mitra.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.yatri_mitra.viewmodel.SimulationViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yatri_mitra.ui.main.components.RouteView
import com.example.yatri_mitra.ui.main.components.EtaDisplay

@Composable
fun MainScreen(viewModel: SimulationViewModel = viewModel()) {

    val vehicles by viewModel.vehicles.collectAsState()

    val eta = viewModel.getNearestETA()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Yatri-Mitra",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(30.dp))

        RouteView(vehicles)

        Spacer(modifier = Modifier.height(30.dp))

        EtaDisplay(eta)
    }
}