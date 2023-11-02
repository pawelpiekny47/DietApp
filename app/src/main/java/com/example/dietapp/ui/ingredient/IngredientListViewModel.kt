package com.example.dietapp.ui.ingredient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.data.Ingredient
import com.example.dietapp.repository.IngredientRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.random.Random

class IngredientViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {
    val ingredientUiState: StateFlow<IngredientUiState> =
        ingredientRepository.getAll().map { IngredientUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = IngredientUiState()
            )

    suspend fun saveItem() {
        ingredientRepository.insertItem(
            Ingredient(
                Random.Default.nextInt(0, 9000000),
                Random.Default.nextInt(0, 40).toString(),
                Random.Default.nextDouble(0.0, 40.0).format(2),
                Random.Default.nextDouble(0.0, 40.0).format(2),
                Random.Default.nextDouble(0.0, 40.0).format(2),
                Random.Default.nextDouble(0.0, 40.0).format(2),
                Random.Default.nextDouble(0.0, 40.0).format(2),
                Random.Default.nextDouble(0.0, 40.0).format(2),
                FoodCategory.values().get(Random.nextInt(0, 4))
            )
        )
    }

    suspend fun deleteItem(ingredient: Ingredient) {
        ingredientRepository.delete(ingredient)
    }

    fun Double.format(scale: Int) = "%.${scale}f".format(this).toDouble()

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class IngredientUiState(val ingredientList: List<Ingredient> = listOf())
