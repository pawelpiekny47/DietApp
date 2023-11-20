package com.example.dietapp.ui.ingredient.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.data.Ingredient
import com.example.dietapp.repository.IngredientRepository
import com.example.dietapp.ui.dish.viewmodel.IngredientWithAmountDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class IngredientViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {
    var deleteButtonVisible by mutableStateOf(true)
    var ingredientUiState by mutableStateOf(IngredientUiState())
        private set

    val ingredientListUiState: StateFlow<IngredientListUiState> =
        ingredientRepository.getAll().map { IngredientListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = IngredientListUiState()
            )

    fun updateUiState(ingredientDetails: IngredientDetails) {
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientDetails)
    }

    fun resetUiState() {
        ingredientUiState = IngredientUiState()
    }

    suspend fun saveItem() {
        if (ingredientUiState.ingredientDetails.id == 0)
            ingredientRepository.insertItem(ingredientUiState.ingredientDetails.toIngredient())
        else ingredientRepository.updateItem(ingredientUiState.ingredientDetails.toIngredient())

    }

    suspend fun deleteItem() {
        ingredientRepository.delete(ingredientUiState.ingredientDetails.toIngredient())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class IngredientUiState(val ingredientDetails: IngredientDetails = IngredientDetails())

data class IngredientListUiState(val ingredientList: List<Ingredient> = listOf())

data class IngredientDetails(
    val id: Int = 0,
    val name: String = "",
    val totalKcal: String = "",
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
        ingredientId = id,
        name,
        totalKcal.toDoubleOrNull() ?:0.0,
        protein.toDoubleOrNull() ?: 0.0,
        carbohydrates.toDoubleOrNull() ?: 0.0,
        fats.toDoubleOrNull() ?: 0.0,
        polyunsaturatedFats.toDoubleOrNull() ?: 0.0,
        soil.toDoubleOrNull() ?: 0.0,
        fiber.toDoubleOrNull() ?: 0.0,
        foodCategory
    )
}

fun IngredientDetails.toIngredientWithAmountDetails(): IngredientWithAmountDetails {
    return IngredientWithAmountDetails(this, "0.0")
}

fun Ingredient.toIngredientDetails(): IngredientDetails {
    return IngredientDetails(
        this.ingredientId,
        this.name,
        this.totalKcal.toString(),
        this.protein.toString(),
        this.carbohydrates.toString(),
        this.fats.toString(),
        this.polyunsaturatedFats.toString(),
        this.soil.toString(),
        this.fiber.toString(),
        this.foodCategory
    )
}