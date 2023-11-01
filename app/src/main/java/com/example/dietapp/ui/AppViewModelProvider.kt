package com.example.dietapp.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.dietapp.DietAppApplication
import com.example.dietapp.ui.ingredient.IngredientViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ItemEditViewModel
        initializer {
            IngredientViewModel(
                dietAppApplication().container.ingredientRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.dietAppApplication(): DietAppApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DietAppApplication)
