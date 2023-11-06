package com.example.dietapp.ui.mainscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietapp.data.Ingredient

class MainScreenViewModel : ViewModel() {
    var ingredient: Ingredient? by mutableStateOf(null)
    var visibleFloatButton by mutableStateOf(true)
}