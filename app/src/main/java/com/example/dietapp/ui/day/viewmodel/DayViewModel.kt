package com.example.dietapp.ui.day.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.data.Day
import com.example.dietapp.data.DayDishCrossRef
import com.example.dietapp.data.DayWithDishes
import com.example.dietapp.data.Dish
import com.example.dietapp.data.DishIngredientCrossRef
import com.example.dietapp.data.DishWithAmount
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.repository.DayRepository
import com.example.dietapp.repository.DishRepository
import com.example.dietapp.ui.dish.viewmodel.DishDetails
import com.example.dietapp.ui.dish.viewmodel.DishWithIngredientsDetails
import com.example.dietapp.ui.dish.viewmodel.toDishWithIngredientDetails
import com.example.dietapp.ui.common.DietStatistics
import com.example.dietapp.ui.dish.viewmodel.IngredientWithAmountDetails
import com.example.dietapp.ui.ingredient.viewmodel.IngredientDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.math.RoundingMode
import java.util.stream.Collectors
import kotlin.streams.toList

@SuppressLint("MutableCollectionMutableState")
class DayViewModel(
    private val dayRepository: DayRepository,
    private val dishRepository: DishRepository
) : ViewModel(), DietStatistics {
    var deleteButtonVisible by mutableStateOf(true)
    var editedDishId by mutableStateOf(0)
    var crossRefListToDelete by mutableStateOf((mutableListOf<DishIngredientCrossRef>()))
    var dayWithDishesUiState by mutableStateOf(DayWithDishesDetailsUiState())
        private set

    val dayListUiState: StateFlow<DayListUiState> =
        dayRepository.getAll().map { DayListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DayListUiState()
            )

    fun resetUiState() {
        dayWithDishesUiState = DayWithDishesDetailsUiState()
    }

    fun updateDayWithDishesUiState(dayDetails: DayWithDishesDetails) {
        dayWithDishesUiState =
            DayWithDishesDetailsUiState(dayWithDishesDetails = dayDetails)

    }


    fun updateDayUiState(
        ingredientWithAmountDetails: IngredientWithAmountDetails,
        dishId: String,
        amount: String
    ) {
        dayWithDishesUiState = DayWithDishesDetailsUiState(
            dayWithDishesDetails = DayWithDishesDetails(
                dayWithDishesUiState.dayWithDishesDetails.day,
                dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream()
                    .map { dish ->
                        DishWithAmountDetails(
                            DishWithIngredientsDetails(
                                dish.dishWithIngredientsDetails.dishDetails,
                                dish.dishWithIngredientsDetails.ingredientList.stream()
                                    .map { current ->
                                        if (dish.dishWithIngredientsDetails.dishDetails.dishId == dishId) {
                                            if (current.ingredientDetails.id == ingredientWithAmountDetails.ingredientDetails.id) {
                                                IngredientWithAmountDetails(
                                                    current.ingredientDetails,
                                                    amount
                                                )
                                            } else current
                                        } else current
                                    }.toList()
                            )
                        )
                    }.toList()
            ),
            dayWithDishesUiState.dayDishCrossRefToDelete
        )
    }

    fun updateDayIdAfterSave(dayId: Int) {
        dayWithDishesUiState =
            DayWithDishesDetailsUiState(
                dayWithDishesDetails = DayWithDishesDetails(
                    day = Day(
                        dayId,
                        dayWithDishesUiState.dayWithDishesDetails.day.name,
                    ),
                    dishWithAmountDetails = dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails
                ),
                dayDishCrossRefToDelete = mutableListOf<DayDishCrossRef>().also {
                    it.addAll(
                        dayWithDishesUiState.dayDishCrossRefToDelete,
                    )
                }
            )
    }

    fun updateDayName(name: String) {
        dayWithDishesUiState =
            DayWithDishesDetailsUiState(
                dayWithDishesDetails = DayWithDishesDetails(
                    day = Day(
                        dayWithDishesUiState.dayWithDishesDetails.day.dayId,
                        name
                    ),
                    dishWithAmountDetails = dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails
                ),
                dayDishCrossRefToDelete = mutableListOf<DayDishCrossRef>().also {
                    it.addAll(
                        dayWithDishesUiState.dayDishCrossRefToDelete,
                    )
                }
            )
    }

    suspend fun addToDishWithAmountList(dishId: Long) {
        val newDay = dishRepository.getDishWithAmount(dishId.toInt()).first()
        dayWithDishesUiState =
            DayWithDishesDetailsUiState(
                dayWithDishesDetails = DayWithDishesDetails(
                    day = dayWithDishesUiState.dayWithDishesDetails.day,
                    dishWithAmountDetails = mutableListOf<DishWithAmountDetails>().also {
                        it.addAll(
                            dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails,
                        )
                        it.add(DishWithAmountDetails(newDay.toDishWithIngredientDetails()))
                    }.toList()
                ),
                dayDishCrossRefToDelete = mutableListOf<DayDishCrossRef>().also {
                    it.addAll(
                        dayWithDishesUiState.dayDishCrossRefToDelete,
                    )
                }
            )
    }

    fun deleteDishFromDay(
        dishWithAmountDetails: DishWithAmountDetails
    ) {
        dayWithDishesUiState.dayDishCrossRefToDelete
        dayWithDishesUiState =
            DayWithDishesDetailsUiState(
                dayWithDishesDetails = DayWithDishesDetails(
                    day = dayWithDishesUiState.dayWithDishesDetails.day,
                    dishWithAmountDetails = dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream()
                        .filter { (it != dishWithAmountDetails) }
                        .toList()),
                dayDishCrossRefToDelete = mutableListOf<DayDishCrossRef>().also {
                    it.addAll(
                        dayWithDishesUiState.dayDishCrossRefToDelete,
                    )
                }.also {
                    it.add(
                        DayDishCrossRef(
                            dayId = dayWithDishesUiState.dayWithDishesDetails.day.dayId,
                            dishId = dishWithAmountDetails.dishWithIngredientsDetails.dishDetails.dishId.toInt()
                        )
                    )
                }
            )
    }

    fun addIngredientToDishInDay(ingredientDetails: IngredientDetails) {
        dayWithDishesUiState = dayWithDishesUiState.copy(
            dayWithDishesDetails = DayWithDishesDetails(
                day = dayWithDishesUiState.dayWithDishesDetails.day,
                dishWithAmountDetails = dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.map { dishWithAmountDetails ->
                    if (dishWithAmountDetails.dishWithIngredientsDetails.dishDetails.dishId.toInt() == editedDishId) {
                        DishWithAmountDetails(
                            dishWithIngredientsDetails = DishWithIngredientsDetails(
                                dishDetails = dishWithAmountDetails.dishWithIngredientsDetails.dishDetails,
                                ingredientList = mutableListOf<IngredientWithAmountDetails>().also {
                                    it.addAll(dishWithAmountDetails.dishWithIngredientsDetails.ingredientList)
                                    it.add(IngredientWithAmountDetails(ingredientDetails, "0"))
                                }
                            )
                        )
                    } else dishWithAmountDetails
                }

            ),
        )
    }

    fun removeIngredientFromDishInDay(ingredientId: Int, dishId: Int) {
        dayWithDishesUiState = dayWithDishesUiState.copy(
            dayWithDishesDetails = DayWithDishesDetails(
                day = dayWithDishesUiState.dayWithDishesDetails.day,
                dishWithAmountDetails = dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.map { dishWithAmountDetails ->
                    if (dishWithAmountDetails.dishWithIngredientsDetails.dishDetails.dishId.toInt() == dishId) {
                        DishWithAmountDetails(
                            dishWithIngredientsDetails = DishWithIngredientsDetails(
                                dishDetails = dishWithAmountDetails.dishWithIngredientsDetails.dishDetails,
                                ingredientList = mutableListOf<IngredientWithAmountDetails>().let { ingredientWithAmount ->
                                    ingredientWithAmount.addAll(dishWithAmountDetails.dishWithIngredientsDetails.ingredientList)
                                    crossRefListToDelete.add(
                                        DishIngredientCrossRef(
                                            dishId,
                                            ingredientId,
                                            0.0
                                        )
                                    )
                                    ingredientWithAmount.filter { it.ingredientDetails.id != ingredientId }
                                }
                            )
                        )
                    } else dishWithAmountDetails
                }
            ),
        )
    }

    suspend fun saveDayWithDishes() {
        val dayId = dayRepository.saveDay(dayWithDishesUiState.dayWithDishesDetails.day)
        dayRepository.saveAll(dayWithDishesUiState.toDayDishCrossRefList())
        dayRepository.deleteAll(dayWithDishesUiState.dayDishCrossRefToDelete)
        dayWithDishesUiState.dayDishCrossRefToDelete.forEach {
            dishRepository.deleteDish(it.dishId)
            dishRepository.deleteAllCrossRefForDishId(it.dishId)
        }
        dishRepository.deleteAll(crossRefListToDelete)
        crossRefListToDelete = mutableListOf()

        dishRepository.saveAll(
            dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream()
                .map { dishWithAmountDetails ->
                    dishWithAmountDetails.dishWithIngredientsDetails.ingredientList.stream()
                        .map { ingredientWithAmountDetails ->
                            DishIngredientCrossRef(
                                dishWithAmountDetails.dishWithIngredientsDetails.dishDetails.dishId.toInt(),
                                ingredientWithAmountDetails.ingredientDetails.id,
                                ingredientWithAmountDetails.amount.toDouble()
                            )
                        }.toList()
                }.toList().flatten()
        )
        if (dayId != -1L)
            updateDayIdAfterSave(dayId.toInt())
    }

    override fun returnCurrentKcal(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream().map { it ->
            it.dishWithIngredientsDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d })
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentProtein(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream().map { it ->
            it.dishWithIngredientsDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.protein.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d })
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentCarbs(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream().map { it ->
            it.dishWithIngredientsDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.carbohydrates.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d })
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentFat(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream().map { it ->
            it.dishWithIngredientsDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.fats.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d })
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentSoil(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream().map { it ->
            it.dishWithIngredientsDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.soil.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d })
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentFiber(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream().map { it ->
            it.dishWithIngredientsDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.fiber.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d })
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentPufa(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream().map { it ->
            it.dishWithIngredientsDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.polyunsaturatedFats.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d })
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentKcalForFoodCategory(foodType: FoodCategory): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails.stream().map { it ->
            it.dishWithIngredientsDetails.ingredientList.stream()
                .filter { it.ingredientDetails.foodCategory == foodType }
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d })
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class DayWithDishesDetailsUiState(
    val dayWithDishesDetails: DayWithDishesDetails = DayWithDishesDetails(),
    val dayDishCrossRefToDelete: List<DayDishCrossRef> = mutableListOf()
)

fun DayWithDishesDetailsUiState.toDayDishCrossRefList(): List<DayDishCrossRef> {
    return dayWithDishesDetails.dishWithAmountDetails
        .stream()
        .map {
            DayDishCrossRef(
                dayWithDishesDetails.day.dayId,
                it.dishWithIngredientsDetails.dishDetails.dishId.toInt()
            )
        }
        .toList()
}

data class DayWithDishesDetails(
    val day: Day = Day(name = ""),
    var dishWithAmountDetails: List<DishWithAmountDetails> = mutableListOf()
)

data class DayListUiState(val dayList: List<DayWithDishes> = listOf())

fun DayWithDishes.toDayDetails(): DayWithDishesDetails {
    return DayWithDishesDetails(
        day,
        dishWithAmountList.map { it.toDishWithAmountDetails() },
    )
}

class DishWithAmountDetails(
    val dishWithIngredientsDetails: DishWithIngredientsDetails
)

fun DishWithAmount.toDishWithAmountDetails(): DishWithAmountDetails {
    return DishWithAmountDetails(
        dishWithIngredients.toDishWithIngredientDetails()
    )
}

fun Dish.toDishDetails(): DishDetails {
    return DishDetails(
        this.dishId.toString(),
        this.name,
        this.description
    )
}