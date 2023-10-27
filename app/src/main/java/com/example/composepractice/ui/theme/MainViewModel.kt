package com.example.composepractice.ui.theme

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import java.util.Random

class MainViewModel: ViewModel() {

    // Color
    val color = mutableStateOf(Color.Yellow)
    private var job: Job? = null

    // Text Field
    val name by lazy { mutableStateOf("") }
    val snackbarHostState = SnackbarHostState()

    fun runChangeColor() {
        job = viewModelScope.launch {
            while (true) {
                color.value = Color(
                    Random().nextFloat(),
                    Random().nextFloat(),
                    Random().nextFloat(),
                    1f
                )
                delay(500L)
            }
        }
    }

    fun cancelChangeColor() {
        job?.cancel()
    }
}