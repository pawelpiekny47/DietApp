package com.example.dietapp.ui.dish.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.data.Dish
import com.example.dietapp.data.DishIngredientCrossRef
import com.example.dietapp.data.DishWithIngredients
import com.example.dietapp.data.IngredientWithAmount
import com.example.dietapp.repository.DishRepository
import com.example.dietapp.ui.ingredient.viewmodel.IngredientDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.streams.toList

class DishViewModel(private val dishRepository: DishRepository) : ViewModel() {
    var deleteButtonVisible by mutableStateOf(true)
    var dishWithIngredientsUiState by mutableStateOf(DishWithIngredientsDetailsUiState())
        private set

    val dishListUiState: StateFlow<DishListUiState> =
        dishRepository.getAll()
            .map { DishListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DishListUiState()
            )

    fun resetUiState() {
        dishWithIngredientsUiState = DishWithIngredientsDetailsUiState()
    }

    fun updateDishWithIngredientUiState(dishDetails: DishWithIngredientsDetails) {
        dishWithIngredientsUiState =
            DishWithIngredientsDetailsUiState(dishDetails = dishDetails)

    }

    fun updateDishWithIngredientUiState(ingredientId: Int, string: String) {
        dishWithIngredientsUiState =
            DishWithIngredientsDetailsUiState(
                dishDetails = DishWithIngredientsDetails(
                    dish = dishWithIngredientsUiState.dishDetails.dish,
                    ingredientList = dishWithIngredientsUiState.dishDetails.ingredientList.stream()
                        .map {
                            if (it.ingredientDetails.id == ingredientId)
                                IngredientWithAmountDetails(it.ingredientDetails, string.toDouble())
                            else it
                        }
                        .toList()),
                dishIngredientCrossRefToDelete = mutableListOf<DishIngredientCrossRef>().also {
                    it.addAll(
                        dishWithIngredientsUiState.dishIngredientCrossRefToDelete,
                    )
                }
            )
    }

    fun updateDishName(name: String) {
        dishWithIngredientsUiState =
            DishWithIngredientsDetailsUiState(
                dishDetails = DishWithIngredientsDetails(
                    dish = Dish(
                        dishWithIngredientsUiState.dishDetails.dish.dishId,
                        name,
                        dishWithIngredientsUiState.dishDetails.dish.description
                    ),
                    ingredientList = dishWithIngredientsUiState.dishDetails.ingredientList
                ),
                dishIngredientCrossRefToDelete = mutableListOf<DishIngredientCrossRef>().also {
                    it.addAll(
                        dishWithIngredientsUiState.dishIngredientCrossRefToDelete,
                    )
                }
            )
    }

    fun addToIngredientWithAmountList(ingredientWithAmountDetails: IngredientWithAmountDetails) {
        dishWithIngredientsUiState =
            DishWithIngredientsDetailsUiState(
                dishDetails = DishWithIngredientsDetails(
                    dish = dishWithIngredientsUiState.dishDetails.dish,
                    ingredientList = mutableListOf<IngredientWithAmountDetails>().also {
                        it.addAll(
                            dishWithIngredientsUiState.dishDetails.ingredientList,
                        )
                        it.add(ingredientWithAmountDetails)
                    }.toList()
                ),
                dishIngredientCrossRefToDelete = mutableListOf<DishIngredientCrossRef>().also {
                    it.addAll(
                        dishWithIngredientsUiState.dishIngredientCrossRefToDelete,
                    )
                }
            )
    }

    fun deleteIngredientFromDish(
        ingredientWithAmountDetails: IngredientWithAmountDetails
    ) {
        dishWithIngredientsUiState.dishIngredientCrossRefToDelete
        dishWithIngredientsUiState =
            DishWithIngredientsDetailsUiState(
                dishDetails = DishWithIngredientsDetails(
                    dish = dishWithIngredientsUiState.dishDetails.dish,
                    ingredientList = dishWithIngredientsUiState.dishDetails.ingredientList.stream()
                        .filter { (it != ingredientWithAmountDetails) }
                        .toList()),
                dishIngredientCrossRefToDelete = mutableListOf<DishIngredientCrossRef>().also {
                    it.addAll(
                        dishWithIngredientsUiState.dishIngredientCrossRefToDelete,
                    )
                }.also {
                    it.add(
                        DishIngredientCrossRef(
                            dishId = dishWithIngredientsUiState.dishDetails.dish.dishId,
                            ingredientId = ingredientWithAmountDetails.ingredientDetails.id,
                            amount = 0.0
                        )
                    )
                }
            )
    }

    suspend fun saveDishWithIngredients() {
        dishRepository.saveDish(dishWithIngredientsUiState.dishDetails.dish)
        dishRepository.saveAll(dishWithIngredientsUiState.toDishIngredientCrossRefList())
        dishRepository.deleteAll(dishWithIngredientsUiState.dishIngredientCrossRefToDelete)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class DishWithIngredientsDetailsUiState(
    val dishDetails: DishWithIngredientsDetails = DishWithIngredientsDetails(),
    val dishIngredientCrossRefToDelete: List<DishIngredientCrossRef> = mutableListOf()
)

fun DishWithIngredientsDetailsUiState.toDishIngredientCrossRefList(): List<DishIngredientCrossRef> {
    return dishDetails.ingredientList
        .stream()
        .map {
            DishIngredientCrossRef(
                dishDetails.dish.dishId,
                it.ingredientDetails.id,
                it.amount
            )
        }
        .toList()
}

data class DishWithIngredientsDetails(
    val dish: Dish = Dish(name = "", description = ""),
    var ingredientList: List<IngredientWithAmountDetails> = mutableListOf()
)

data class DishListUiState(val dishList: List<DishWithIngredients> = listOf())

fun DishWithIngredients.toDishWithIngredientDetails(): DishWithIngredientsDetails {
    return DishWithIngredientsDetails(
        dish,
        ingredientList.map { it.toIngredientWithAmountDetails() },

        )
}

data class IngredientWithAmountDetails(
    val ingredientDetails: IngredientDetails,
    var amount: Double = 0.0
)

data class DishDetails(
    val dishId: Int = 0,
    val name: String = "",
    val description: String = ""
)

fun IngredientWithAmount.toIngredientWithAmountDetails(): IngredientWithAmountDetails {
    return IngredientWithAmountDetails(
        IngredientDetails(
            this.ingredient.ingredientId,
            this.ingredient.name,
            this.ingredient.totalKcal.toString(),
            this.ingredient.protein.toString(),
            this.ingredient.carbohydrates.toString(),
            this.ingredient.fats.toString(),
            this.ingredient.polyunsaturatedFats.toString(),
            this.ingredient.soil.toString(),
            this.ingredient.fiber.toString(),
            this.ingredient.foodCategory
        ),
        this.amount
    )
}
