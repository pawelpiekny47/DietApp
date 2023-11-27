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
import com.example.dietapp.ui.day.viewmodel.toDishDetails
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

    fun updateDishWithIngredientUiState(dishWithIngredientsDetails: DishWithIngredientsDetails) {
        dishWithIngredientsUiState =
            DishWithIngredientsDetailsUiState(dishWithIngredientsDetails = dishWithIngredientsDetails)

    }

    fun updateDishWithIngredientUiStateForVariantDish(dishDetails: DishWithIngredientsDetails) {
        dishWithIngredientsUiState =
            DishWithIngredientsDetailsUiState(dishWithIngredientsDetails = dishDetails)

    }

    fun updateDishWithIngredientUiState(ingredientId: Int, amount: String) {
        dishWithIngredientsUiState =
            DishWithIngredientsDetailsUiState(
                dishWithIngredientsDetails = DishWithIngredientsDetails(
                    dishDetails = dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails,
                    ingredientList = dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList.stream()
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
                dishWithIngredientsDetails = DishWithIngredientsDetails(
                    dishDetails = DishDetails(
                        dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails.dishId,
                        name,
                        dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails.description,
                        dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails.baseDish,
                    ),
                    ingredientList = dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList
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
                dishWithIngredientsDetails = DishWithIngredientsDetails(
                    dishDetails = dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails,
                    ingredientList = mutableListOf<IngredientWithAmountDetails>().also {
                        it.addAll(
                            dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList,
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
                dishWithIngredientsDetails = DishWithIngredientsDetails(
                    dishDetails = dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails,
                    ingredientList = dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList.stream()
                        .filter { (it != ingredientWithAmountDetails) }
                        .toList()),
                dishIngredientCrossRefToDelete = mutableListOf<DishIngredientCrossRef>().also {
                    it.addAll(
                        dishWithIngredientsUiState.dishIngredientCrossRefToDelete,
                    )
                }.also {
                    it.add(
                        DishIngredientCrossRef(
                            dishId = dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails.dishId.toInt(),
                            ingredientId = ingredientWithAmountDetails.ingredientDetails.id,
                            amount = 0.0
                        )
                    )
                }
            )
    }

    override fun returnCurrentKcal(): Double {
        return dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList.stream().map {
            (it.ingredientDetails.totalKcal.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnCurrentProtein(): Double {
        return dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList.stream().map {
            (it.ingredientDetails.protein.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnCurrentCarbs(): Double {
        return dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList.stream().map {
            (it.ingredientDetails.carbohydrates.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnCurrentFat(): Double {
        return dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList.stream().map {
            (it.ingredientDetails.fats.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnCurrentSoil(): Double {
        return dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList.stream().map {
            (it.ingredientDetails.soil.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnCurrentFiber(): Double {
        return dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList.stream().map {
            (it.ingredientDetails.fiber.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnCurrentPufa(): Double {
        return dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList.stream().map {
            (it.ingredientDetails.polyunsaturatedFats.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    }

    override fun returnCurrentKcalForFoodCategory(foodType: FoodCategory): Double {
        return dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList.stream().filter {
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
        dishRepository.upsertDish(dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails.toDish())
        dishRepository.saveAll(dishWithIngredientsUiState.toDishIngredientCrossRefList())
        dishRepository.deleteAll(dishWithIngredientsUiState.dishIngredientCrossRefToDelete)
    }

    suspend fun copyDishWithIngredientsAsVariant(dishWithIngredientsDetails: DishWithIngredientsDetails): Long {
        updateDishWithIngredientUiState(
            dishWithIngredientsDetails.copy(
                dishDetails = dishWithIngredientsDetails.dishDetails.copy(
                    baseDish = "0", dishId = "0"
                )
            )
        )
        val id = dishRepository.saveDish(dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails.toDish())
        updateDishWithIngredientUiState(
            dishWithIngredientsDetails.copy(
                dishDetails = dishWithIngredientsDetails.dishDetails.copy(
                    baseDish = "0", dishId = "$id"
                )
            )
        )
        dishRepository.saveAll(dishWithIngredientsUiState.toDishIngredientCrossRefList())
        return id
    }

    suspend fun deleteDish() {
        dishRepository.deleteDish(dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails.toDish())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class DishWithIngredientsDetailsUiState(
    val dishWithIngredientsDetails: DishWithIngredientsDetails = DishWithIngredientsDetails(),
    val dishIngredientCrossRefToDelete: List<DishIngredientCrossRef> = mutableListOf()
)

fun DishWithIngredientsDetailsUiState.toDishIngredientCrossRefList(): List<DishIngredientCrossRef> {
    return dishWithIngredientsDetails.ingredientList
        .stream()
        .map {
            DishIngredientCrossRef(
                dishWithIngredientsDetails.dishDetails.dishId.toInt(),
                it.ingredientDetails.id,
                if (it.amount == "") 0.0 else it.amount.toDouble()
            )
        }
        .toList()
}

data class DishWithIngredientsDetails(
    val dishDetails: DishDetails = DishDetails(name = "", description = "", baseDish = "1"),
    var ingredientList: List<IngredientWithAmountDetails> = mutableListOf()
)

data class DishListUiState(val dishList: List<DishWithIngredients> = listOf())

fun DishWithIngredients.toDishWithIngredientDetails(): DishWithIngredientsDetails {
    return DishWithIngredientsDetails(
        dish.toDishDetails(),
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
    val description: String = "",
    val baseDish: String = "1"
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

fun DishDetails.toDish(): Dish {
    return Dish(
        dishId = dishId.toInt(),
        name = name,
        description = description,
        baseDish = baseDish.toInt()
    )
}