package com.example.dietapp.ui.ingredient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.data.Ingredient
import com.example.dietapp.repository.IngredientRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class IngredientListViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {
    val ingredientUiState: StateFlow<IngredientListUiState> =
        ingredientRepository.getAll().map { IngredientListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = IngredientListUiState()
            )

    suspend fun deleteItem(ingredient: Ingredient) {
        ingredientRepository.delete(ingredient)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class IngredientListUiState(val ingredientList: List<Ingredient> = listOf())
