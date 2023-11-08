package com.example.dietapp.ui.dish.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.data.Dish
import com.example.dietapp.data.DishWithIngredients
import com.example.dietapp.data.Ingredient
import com.example.dietapp.repository.DishRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DishViewModel(private val dishRepository: DishRepository) : ViewModel() {
    var deleteButtonVisible by mutableStateOf(true)
    var dishUiState by mutableStateOf(DishUiState())
        private set

    val dishListUiState: StateFlow<DishListUiState> =
        dishRepository.getAll().map { DishListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DishListUiState()
            )

    fun resetUiState() {
        dishUiState = DishUiState()
    }

    fun updateUiState(dishDetails: DishDetails) {
        dishUiState =
            DishUiState(dishDetails = dishDetails)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class DishUiState(val dishDetails: DishDetails = DishDetails())

data class DishDetails(
    val dish: Dish = Dish(name = "", description = ""),
    val ingredientList: List<Ingredient> = mutableListOf()
)

data class DishListUiState(val dishList: List<DishWithIngredients> = listOf())

fun DishWithIngredients.toDishDetails(): DishDetails {
    return DishDetails(
        this.dish,
        this.ingredientList
    )
}
