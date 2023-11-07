package com.example.dietapp.ui.mainscreen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietapp.ui.ingredient.screen.IngredientScreenList

class MainScreenViewModel : ViewModel() {
    var isFloatButtonVisible by mutableStateOf(true)
    var floatButtonAction by mutableStateOf({ })
    var isNavigateBackVisible by mutableStateOf(false)
    var navigateBackAction by mutableStateOf({ })
    var topBarName by mutableStateOf(IngredientScreenList.IngredientListScreen.title)

    fun setMainScreen(
        isFloatButtonVisible: Boolean,
        floatButtonAction: () -> Unit,
        isNavigateBackVisible: Boolean,
        navigateBackAction: () -> Unit,
        topBarName: String
    ) {
        this.isFloatButtonVisible = isFloatButtonVisible
        this.floatButtonAction = floatButtonAction
        this.isNavigateBackVisible = isNavigateBackVisible
        this.navigateBackAction = navigateBackAction
        this.topBarName = topBarName
    }

    fun updateVisibleFloatButton(isVisible: Boolean) {
        isFloatButtonVisible = isVisible
    }

    fun updateCanNavigateBack(canNavigateBack: Boolean) {
        this.isNavigateBackVisible = canNavigateBack
    }

    fun updateTopBarName(topBarName: String) {
        this.topBarName = topBarName
    }

    fun updateNavigateUp(navigateUp: () -> Unit) {
        this.navigateBackAction = navigateUp
    }

    fun updateFloatButtonOnClick(floatButtonOnClick: () -> Unit) {
        this.floatButtonAction = floatButtonOnClick
    }
}