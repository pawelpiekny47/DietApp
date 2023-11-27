package com.example.dietapp.ui.day.viewmodel

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
import com.example.dietapp.data.IngredientWithAmount
import com.example.dietapp.repository.DayRepository
import com.example.dietapp.repository.DishRepository
import com.example.dietapp.ui.dish.viewmodel.DishDetails
import com.example.dietapp.ui.dish.viewmodel.DishWithIngredientsDetails
import com.example.dietapp.ui.dish.viewmodel.toDishWithIngredientDetails
import com.example.dietapp.ui.common.DietStatistics
import com.example.dietapp.ui.dish.viewmodel.IngredientWithAmountDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.math.RoundingMode
import java.util.stream.Collectors
import kotlin.streams.toList

class DayViewModel(
    private val dayRepository: DayRepository,
    private val dishRepository: DishRepository
) : ViewModel(), DietStatistics {
    var deleteButtonVisible by mutableStateOf(true)
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
                dayWithDishesUiState.dayWithDishesDetails.dishList.stream().map { dish ->
                    DishWithAmountDetails(
                        DishWithIngredientsDetails(
                            dish.dishDetails.dishDetails,
                            dish.dishDetails.ingredientList.stream()
                                .map { current ->
                                    if (dish.dishDetails.dishDetails.dishId == dishId) {
                                        if (current.ingredientDetails.id == ingredientWithAmountDetails.ingredientDetails.id)
                                            IngredientWithAmountDetails(
                                                current.ingredientDetails,
                                                amount
                                            )
                                        else current
                                    } else current
                                }.toList()
                        ), dish.amount
                    )
                }.toList()
            ),
            dayWithDishesUiState.dayDishCrossRefToDelete
        )
    }


    fun updateDayWithDishUiState(dishId: String, amount: String) {
        dayWithDishesUiState =
            DayWithDishesDetailsUiState(
                dayWithDishesDetails = DayWithDishesDetails(
                    day = dayWithDishesUiState.dayWithDishesDetails.day,
                    dishList = dayWithDishesUiState.dayWithDishesDetails.dishList.stream()
                        .map {
                            if (it.dishDetails.dishDetails.dishId == dishId)
                                DishWithAmountDetails(it.dishDetails, amount)
                            else it
                        }
                        .toList()),
                dayDishCrossRefToDelete = mutableListOf<DayDishCrossRef>().also {
                    it.addAll(
                        dayWithDishesUiState.dayDishCrossRefToDelete,
                    )
                }
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
                    dishList = dayWithDishesUiState.dayWithDishesDetails.dishList
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
                    dishList = dayWithDishesUiState.dayWithDishesDetails.dishList
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
                    dishList = mutableListOf<DishWithAmountDetails>().also {
                        it.addAll(
                            dayWithDishesUiState.dayWithDishesDetails.dishList,
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
                    dishList = dayWithDishesUiState.dayWithDishesDetails.dishList.stream()
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
                            dishId = dishWithAmountDetails.dishDetails.dishDetails.dishId.toInt(),
                            amount = 0
                        )
                    )
                }
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
        dishRepository.saveAll(
            dayWithDishesUiState.dayWithDishesDetails.dishList.stream()
                .map { dishWithAmountDetails ->
                    dishWithAmountDetails.dishDetails.ingredientList.stream()
                        .map { ingredientWithAmountDetails ->
                            DishIngredientCrossRef(
                                dishWithAmountDetails.dishDetails.dishDetails.dishId.toInt(),
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
        return dayWithDishesUiState.dayWithDishesDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentProtein(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.protein.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentCarbs(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.carbohydrates.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentFat(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.fats.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentSoil(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.soil.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentFiber(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.fiber.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentPufa(): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.polyunsaturatedFats.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentKcalForFoodCategory(foodType: FoodCategory): Double {
        return dayWithDishesUiState.dayWithDishesDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .filter { it.ingredientDetails.foodCategory == foodType }
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
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
    return dayWithDishesDetails.dishList
        .stream()
        .map {
            DayDishCrossRef(
                dayWithDishesDetails.day.dayId,
                it.dishDetails.dishDetails.dishId.toInt(),
                it.amount.toInt()
            )
        }
        .toList()
}

data class DayWithDishesDetails(
    val day: Day = Day(name = ""),
    var dishList: List<DishWithAmountDetails> = mutableListOf()
)

data class DayListUiState(val dayList: List<DayWithDishes> = listOf())

fun DayWithDishes.toDayDetails(): DayWithDishesDetails {
    return DayWithDishesDetails(
        day,
        dishWithAmountList.map { it.toDishWithAmountDetails() },
    )
}

class DishWithAmountDetails(
    val dishDetails: DishWithIngredientsDetails,
    var amount: String = "0"
)

fun DishWithAmount.toDishWithAmountDetails(): DishWithAmountDetails {
    return DishWithAmountDetails(
        dishWithIngredients.toDishWithIngredientDetails(),
        this.amount.toString()
    )
}

fun DishWithIngredientsDetails.toDishWithAmountDetails(): DishWithAmountDetails {
    return DishWithAmountDetails(this, "0")
}

fun Dish.toDishDetails(): DishDetails {
    return DishDetails(
        this.dishId.toString(),
        this.name,
        this.description
    )
}