package com.example.dietapp.ui.mainscreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietapp.ui.ingredient.screen.IngredientScreenList

class MainScreenViewModel : ViewModel() {
    var visibleFloatButton by mutableStateOf(true)
    var canNavigateBack by mutableStateOf(false)
    var topBarName by mutableStateOf(com.example.dietapp.ui.ingredient.screen.IngredientScreenList.IngredientListScreen)
    var navigateUp by mutableStateOf({ })
    var floatButtonOnClick by mutableStateOf({ })

    fun updateVisibleFloatButton(isVisible: Boolean) {
        visibleFloatButton = isVisible
    }

    fun updateCanNavigateBack(canNavigateBack: Boolean) {
        this.canNavigateBack = canNavigateBack
    }

    fun updateTopBarName(topBarName: IngredientScreenList) {
        this.topBarName = topBarName
    }

    fun updateNavigateUp(navigateUp: () -> Unit) {
        this.navigateUp = navigateUp
    }

    fun updateFloatButtonOnClick(floatButtonOnClick: () -> Unit) {
        this.floatButtonOnClick = floatButtonOnClick
    }
}