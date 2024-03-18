package com.example.treasurehunt.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.treasurehunt.R
import com.example.treasurehunt.TreasureHuntScreen

@Composable
fun TimerCard(
    viewModel: TreasureHuntViewModel,
    currentScreen: TreasureHuntScreen
) {
    val timerState = viewModel.getTimerState().value


    // Side effect to start or resume the timer when the screen is clue screen
    LaunchedEffect(currentScreen) {
        if (currentScreen == TreasureHuntScreen.Clue) {
            if (viewModel.isTimerPaused()) {
                viewModel.resumeTimer()
            } else {
                viewModel.startTimer()
            }
        }
    }

    // Side effect to pause the timer when transitioning to clue solved screen
    LaunchedEffect(currentScreen) {
        if (currentScreen == TreasureHuntScreen.Solved) {
            viewModel.pauseTimer()
        }
    }

    // Side effect to resume the timer when transitioning back to clue screen
    LaunchedEffect(currentScreen) {
        if (currentScreen == TreasureHuntScreen.Clue && viewModel.isTimerPaused()) {
            viewModel.resumeTimer()
        }
    }

    // Side effect to stop the timer when the screen is completed screen
    LaunchedEffect(currentScreen) {
        if (currentScreen == TreasureHuntScreen.Completed) {
            viewModel.pauseTimer()
        }
    }

    Card(
        modifier = Modifier,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .height(100.dp), // Customize the card height as needed
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val totalTimeString = stringResource(R.string.total_time, timerState.timerSeconds)
            Text(text = totalTimeString)
        }
    }
}
