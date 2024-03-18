package com.example.treasurehunt.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TreasureHuntViewModel : ViewModel() {
    // Counter variable to track the number of solved clues
    private var solvedCluesCount: Int = 0

    // Functions to manage solved clues count
    fun incrementSolvedCluesCount() {
        solvedCluesCount++
    }

    fun resetSolvedCluesCount() {
        solvedCluesCount = 0
    }

    fun getSolvedCluesCount(): Int {
        return solvedCluesCount
    }

    // Timer variables
    private var timerJob: Job? = null
    private val _timerState = mutableStateOf(TimerState(0))
    private var lastTimerValue: Int = 0 // Variable to store the last recorded timer value

    // Functions to manage the timer
    fun startTimer() {
        timerJob?.cancel() // Cancel any existing timer job
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000) // Delay for 1 second
                lastTimerValue++ // Increment last recorded timer value
                _timerState.value = TimerState(lastTimerValue) // Update timer state
            }
        }
    }

    // Function to pause the timer
    fun pauseTimer() {
        timerJob?.cancel() // Cancel the timer job
    }

    // Function to resume the timer
    fun resumeTimer() {
        startTimer() // Resume the timer by starting it again
    }

    // Function to get the timer state
    fun getTimerState(): MutableState<TimerState> {
        return _timerState
    }

    fun resetLastTimerValue() {
        lastTimerValue = 0 // Reset last timer value
    }

    // State variable to track whether the timer is paused
    private var isTimerPaused = mutableStateOf(false)

    // Function to check if the timer is paused
    fun isTimerPaused(): Boolean {
        return isTimerPaused.value
    }
}

data class TimerState(val timerSeconds: Int)
