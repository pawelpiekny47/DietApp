package com.example.dietapp.ui.dish.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.data.DishWithIngredients
import com.example.dietapp.repository.DishRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DishViewModel(private val dishRepository: DishRepository): ViewModel() {


    val dishListUiState: StateFlow<DishListUiState> =
        dishRepository.getAll().map { DishListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DishListUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class DishListUiState(val dishList: List<DishWithIngredients> = listOf())
