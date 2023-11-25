package com.example.dietapp.ui.dish.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.data.Dish
import com.example.dietapp.data.DishIngredientCrossRef
import com.example.dietapp.data.DishWithIngredients
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.data.IngredientWithAmount
import com.example.dietapp.repository.DishRepository
import com.example.dietapp.ui.ingredient.viewmodel.IngredientDetails
import com.example.dietapp.ui.common.DietStatistics
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.math.RoundingMode
import java.util.stream.Collectors
import kotlin.streams.toList

class DishViewModel(private val dishRepository: DishRepository) : ViewModel(), DietStatistics {
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

    fun updateDishWithIngredientUiState(ingredientId: Int, amount: String) {
        dishWithIngredientsUiState =
            DishWithIngredientsDetailsUiState(
                dishDetails = DishWithIngredientsDetails(
                    dish = dishWithIngredientsUiState.dishDetails.dish,
                    ingredientList = dishWithIngredientsUiState.dishDetails.ingredientList.stream()
                        .map {
                            if (it.ingredientDetails.id == ingredientId)
                                IngredientWithAmountDetails(it.ingredientDetails, amount)
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

    override fun returnTotalKcal(): Double {
        return dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.totalKcal.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnTotalProtein(): Double {
        return dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.protein.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnTotalCarbs(): Double {
        return dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.carbohydrates.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnTotalFat(): Double {
        return dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.fats.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnTotalSoil(): Double {
        return dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.soil.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnTotalFiber(): Double {
        return dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.fiber.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnTotalPufa(): Double {
        return dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.polyunsaturatedFats.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnTotalKcalForFoodCategory(foodType: FoodCategory): Double {
        return dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == foodType)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }


    suspend fun saveDishWithIngredients() {
        dishRepository.saveDish(dishWithIngredientsUiState.dishDetails.dish)
        dishRepository.saveAll(dishWithIngredientsUiState.toDishIngredientCrossRefList())
        dishRepository.deleteAll(dishWithIngredientsUiState.dishIngredientCrossRefToDelete)
    }

    suspend fun deleteDish() {
        dishRepository.deleteDish(dishWithIngredientsUiState.dishDetails.dish)
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
                if (it.amount == "") 0.0 else it.amount.toDouble()
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
    var amount: String = ""
)

data class DishDetails(
    val dishId: String = "0",
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
        this.amount.toString()
    )
}
