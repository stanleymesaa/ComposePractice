package com.example.composepractice.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    val color = mutableStateOf(Color.Yellow)
    val isBreak = mutableStateOf(false)
    val changeColor: () -> Unit = {
        while (!isBreak.value) {
            Log.e("TAG", "LOOP: ${isBreak.value}", )
        }
    }

    private var job: Job? = null

    fun runChangeColor() {
        job = viewModelScope.launch {
            while (true) {
//                Log.e("TAG", "LOOP", )
            }
        }
    }

    fun cancelChangeColor() {
        Log.e("TAG", "CANCEL", )
        job?.cancel()
    }
}