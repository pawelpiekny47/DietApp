package com.example.dietapp.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dietapp.DietAppApplication
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import com.example.dietapp.ui.mainscreen.viewmodel.MainScreenViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            IngredientViewModel(
                dietAppApplication().container.ingredientRepository
            )
        }
        initializer {
            DishViewModel(
                dietAppApplication().container.dishRepository
            )
        }
        initializer {
            DayViewModel(
                dietAppApplication().container.dayRepository
            )
        }
        initializer {
            MainScreenViewModel()
        }
        initializer {
            DietSettingsViewModel(
                dietAppApplication().container.dietSettingsRepository
            )
        }
    }
}

fun CreationExtras.dietAppApplication(): DietAppApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DietAppApplication)
