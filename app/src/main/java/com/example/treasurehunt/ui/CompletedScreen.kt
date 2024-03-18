package com.example.treasurehunt.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.treasurehunt.R
import com.example.treasurehunt.TreasureHuntScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CompletedScreen(navController: NavController, viewModel: TreasureHuntViewModel) {
    val currentScreen = TreasureHuntScreen.Completed

    Scaffold(
        content = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    TimerCard(viewModel = viewModel, currentScreen = currentScreen)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(id = R.string.congrats_msg),
                        fontSize = 22.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.congrats_msg2),
                        fontSize = 22.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.info_2),
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.dogpatch),
                        contentDescription = "Example indoor climbing gym!",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = {
                            navController.navigate(route = TreasureHuntScreen.Home.name)
                            viewModel.resetLastTimerValue()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(bottom = 16.dp)
                    ) {
                        Text(text = "Home")
                    }
                }
            }
        }
    )
}