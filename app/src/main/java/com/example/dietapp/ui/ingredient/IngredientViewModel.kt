package com.example.dietapp.ui.ingredient

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.data.Ingredient
import com.example.dietapp.repository.IngredientRepository

class IngredientViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {
    var ingredientUiState by mutableStateOf(IngredientUiState())
        private set

    fun updateUiState(itemDetails: IngredientDetails) {
        ingredientUiState =
            IngredientUiState(ingredientDetails = itemDetails)
    }

    suspend fun saveItem() {
        if (ingredientUiState.ingredientDetails.id != 0 || ingredientUiState.ingredientDetails.id != 0)
            ingredientRepository.insertItem(ingredientUiState.ingredientDetails.toIngredient())
        else ingredientRepository.updateItem(ingredientUiState.ingredientDetails.toIngredient())

    }

    suspend fun updateItem() {
        ingredientRepository.insertItem(ingredientUiState.ingredientDetails.toIngredient())
    }
}

data class IngredientUiState(
    val ingredientDetails: IngredientDetails = IngredientDetails()
)

data class IngredientDetails(
    val id: Int = 0,
    val name: String = "",
    val protein: String = "",
    val carbohydrates: String = "",
    val fats: String = "",
    val polyunsaturatedFats: String = "",
    val soil: String = "",
    val fiber: String = "",
    val foodCategory: FoodCategory = FoodCategory.Vegetable
)

fun IngredientDetails.toIngredient(): Ingredient {
    return Ingredient(
        id = id,
        name,
        protein.toDoubleOrNull() ?: 0.0,
        carbohydrates.toDoubleOrNull() ?: 0.0,
        fats.toDoubleOrNull() ?: 0.0,
        polyunsaturatedFats.toDoubleOrNull() ?: 0.0,
        soil.toDoubleOrNull() ?: 0.0,
        fiber.toDoubleOrNull() ?: 0.0,
        foodCategory
    )
}

fun Ingredient.toIngredientUiState(): IngredientUiState {
    return IngredientUiState(IngredientDetails())
}

fun Ingredient.toIngredientDetails(): IngredientDetails {
    return IngredientDetails(
        this.id,
        this.name,
        this.protein.toString(),
        this.carbohydrates.toString(),
        this.fats.toString(),
        this.polyunsaturatedFats.toString(),
        this.soil.toString(),
        this.fiber.toString(),
        this.foodCategory
    )
}