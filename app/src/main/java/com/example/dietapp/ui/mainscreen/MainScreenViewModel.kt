package com.example.dietapp.ui.mainscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainScreenViewModel : ViewModel() {
    var visibleFloatButton by mutableStateOf(true)
    var canNavigateBack by mutableStateOf(false)
    var topBarName by mutableStateOf(DietAppScreen.IngredientListScreen)
    var navigateUp by mutableStateOf({ })
    var floatButtonOnClick by mutableStateOf({ })

    fun updateVisibleFloatButton(isVisible: Boolean) {
        visibleFloatButton = isVisible
    }

    fun updateCanNavigateBack(canNavigateBack: Boolean) {
        this.canNavigateBack = canNavigateBack
    }

    fun updateTopBarName(topBarName: DietAppScreen) {
        this.topBarName = topBarName
    }

    fun updateNavigateUp(navigateUp: () -> Unit) {
        this.navigateUp = navigateUp
    }

    fun updateFloatButtonOnClick(floatButtonOnClick: () -> Unit) {
        this.floatButtonOnClick = floatButtonOnClick
    }
}