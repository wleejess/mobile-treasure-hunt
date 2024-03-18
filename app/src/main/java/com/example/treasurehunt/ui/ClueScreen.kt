package com.example.treasurehunt.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.treasurehunt.R
import com.example.treasurehunt.TreasureHuntScreen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "MissingPermission")
@Composable
fun ClueScreen(
    navController: NavController,
    viewModel: TreasureHuntViewModel,
    fusedLocationClient: FusedLocationProviderClient
) {
    val (isTextVisible, setIsTextVisible) = remember { mutableStateOf(false) }
    val (alertDialogVisible, setAlertDialogVisible) = remember { mutableStateOf(false) }

    // Determine which clue and hint to display based on the number of solved clues
    val stringClue = if (viewModel.getSolvedCluesCount() < 1) R.string.clue_1 else R.string.clue_2
    val stringHint = if (viewModel.getSolvedCluesCount() < 1) R.string.hint_1 else R.string.hint_2
    val currentScreen = TreasureHuntScreen.Clue

    val scope = rememberCoroutineScope()

    var locationInfo by remember {
        mutableStateOf("")
    }

    Scaffold(
        content = {
            TimerCard(viewModel = viewModel, currentScreen = currentScreen)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display the clue text
                Text(
                    text = stringResource(id = stringClue),
                    fontSize = 30.sp,
                    modifier = Modifier.padding(bottom = 32.dp),
                )
                // Display the hint text if visible
                if (isTextVisible) {
                    Text(
                        text = stringResource(id = stringHint),
                        modifier = Modifier.padding(bottom = 32.dp),
                    )
                }
                // Button to toggle hint visibility
                Button(
                    onClick = { setIsTextVisible(!isTextVisible) },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Show Hint")
                }
                // Button to indicate clue found
                Button(
                    onClick = {
                        /*
                        if (viewModel.getSolvedCluesCount() < 1) {
                            // Navigate to the next clue screen based on the number of solved clues
                            viewModel.incrementSolvedCluesCount()
                            navController.navigate(route = TreasureHuntScreen.Solved.name) }
                        else if (viewModel.getSolvedCluesCount() == 1) {
                        navController.navigate(route = TreasureHuntScreen.Completed.name)
                        }
                        */
                        fusedLocationClient.getCurrentLocation(
                            LocationRequest.PRIORITY_HIGH_ACCURACY,
                            null
                            ).addOnCompleteListener { task ->
                                if (task.isSuccessful && task.result != null) {
                                    val location = task.result
                                    val currentLatitude = location.latitude
                                    val currentLongitude = location.longitude
                                    val userLocation = Geo(location.latitude, location.longitude)

                                    Log.d("LocationDebug", "Current Latitude: $currentLatitude, Current Longitude: $currentLongitude")

                                    if (viewModel.getSolvedCluesCount() < 1) {
                                        // Target is Monterey Bay Aquarium
                                        val targetLatitude = 36.61863223431459
                                        val targetLongitude = -121.9019049881607
                                        val targetLocation = Geo(targetLatitude, targetLongitude)
                                        val distance = userLocation.haversine(targetLocation)

                                        Log.d("LocationDebug", "Target Latitude: $targetLatitude, Target Longitude: $targetLongitude, Distance: $distance")

                                        if (distance <= 150) {
                                            Log.d("LocationDebug", "Distance within threshold, navigating to next clue")
                                            // Navigate to the next clue screen based on the number of solved clues
                                            viewModel.incrementSolvedCluesCount()
                                            navController.navigate(route = TreasureHuntScreen.Solved.name)
                                        } else {
                                            Log.d("LocationDebug", "Distance outside threshold, showing alert dialog")
                                            if (!alertDialogVisible) {
                                                scope.launch {
                                                    setAlertDialogVisible(true)
                                                }
                                            }                                        }
                                    } else if (viewModel.getSolvedCluesCount() == 1) {
                                        // Target is Dogpatch Boulders
                                        val targetLatitude = 37.75707439710457
                                        val targetLongitude = -122.38765857276907
                                        val targetLocation = Geo(targetLatitude, targetLongitude)
                                        val distance = userLocation.haversine(targetLocation)

                                        Log.d("LocationDebug", "Target Latitude: $targetLatitude, Target Longitude: $targetLongitude, Distance: $distance")

                                        if (distance <= 0.1) {
                                            Log.d("LocationDebug", "Distance within threshold, navigating to completed screen")
                                            navController.navigate(route = TreasureHuntScreen.Completed.name)
                                        } else {
                                            Log.d("LocationDebug", "Distance outside threshold, showing alert dialog")
                                            if (!alertDialogVisible) {
                                                scope.launch {
                                                    setAlertDialogVisible(true)
                                                }
                                            }
                                        }
                                        println("Calculated distance is:")
                                        println(distance)
                                    }
                                } else {
                                    locationInfo = "No last known location. Try fetching the current location first"
                                }
                            }
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Found it!")
                }
                // Button to quit and reset solved clues count
                Button(
                    onClick = {
                        navController.navigate(route = TreasureHuntScreen.Home.name)
                        viewModel.resetSolvedCluesCount()
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Quit")
                }

                if (alertDialogVisible) {
                    AlertDialog(
                        onDismissRequest = {
                            setAlertDialogVisible(false)
                        },
                        title = {
                            Text("Not quite...")
                        },
                        text = {
                            Text("You are not close enough to the target location. Try again!")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    setAlertDialogVisible(false)
                                }
                            ) {
                                Text("Yes ma'am.")
                            }
                        }
                    )
                }
            }
        }
    )
}
