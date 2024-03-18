/**
    Jessica Lee
    Assignment 6: Mobile Treasure Hunt App
    Oregon State University CS 492
    March 14, 2024 (happy pi day!)
 **/

package com.example.treasurehunt

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import com.example.treasurehunt.ui.theme.TreasurehuntTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Request location permission
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Request background location permission for devices running Android Q and above
                    requestBackgroundPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                } else {
                    // Launch the app if background location permission is not required
                    launchApp()
                }
            } else {
                // Permission denied, show error message
                showPermissionDeniedMessage()
            }
        }

    private val requestBackgroundPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Launch the app after background location permission is granted
                launchApp()
            } else {
                // Permission denied, show error message
                showPermissionDeniedMessage()
            }
        }

    private fun launchApp() {
        setTheme(R.style.Theme_Treasurehunt)
        setContent {
            TreasurehuntTheme {
                TreasureHuntApp(fusedLocationClient = fusedLocationClient)
            }
        }
    }

    private fun showPermissionDeniedMessage() {
        setContent {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Cannot run app without permissions.", fontSize = 24.sp)
            }
        }
    }
}
