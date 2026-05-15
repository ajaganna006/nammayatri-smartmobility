package com.example.yatri_mitra.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yatri_mitra.presentation.driver.DriverDashboard
import com.example.yatri_mitra.presentation.passenger.PassengerDashboard
import com.example.yatri_mitra.viewmodel.SimulationViewModel

enum class AppScreen {
    Passenger,
    Driver,
    Routes,
    Settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: SimulationViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf(AppScreen.Passenger) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yatri-Mitra", color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Passenger") },
                    label = { Text("Passenger") },
                    selected = currentScreen == AppScreen.Passenger,
                    onClick = { currentScreen = AppScreen.Passenger }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Driver") },
                    label = { Text("Driver") },
                    selected = currentScreen == AppScreen.Driver,
                    onClick = { currentScreen = AppScreen.Driver }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.List, contentDescription = "Routes") },
                    label = { Text("Routes") },
                    selected = currentScreen == AppScreen.Routes,
                    onClick = { currentScreen = AppScreen.Routes }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = currentScreen == AppScreen.Settings,
                    onClick = { currentScreen = AppScreen.Settings }
                )
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                AppScreen.Passenger -> PassengerDashboard(viewModel) // Currently rendering our Simulation
                AppScreen.Driver -> DriverDashboard() // Future AI Driver View
                AppScreen.Routes -> RoutesScreen(viewModel) {
                    currentScreen = AppScreen.Passenger
                }
                AppScreen.Settings -> SettingsScreen()
            }
        }
    }
}