package com.example.dietapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dietapp.DietAppApplication
import com.example.dietapp.ui.ingredient.IngredientListViewModel
import com.example.dietapp.ui.ingredient.IngredientViewModel
import com.example.dietapp.ui.mainscreen.MainScreenViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            IngredientListViewModel(
                dietAppApplication().container.ingredientRepository
            )
        }
        initializer {
            IngredientViewModel(
                dietAppApplication().container.ingredientRepository
            )
        }
        initializer {
            MainScreenViewModel()
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.dietAppApplication(): DietAppApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DietAppApplication)
