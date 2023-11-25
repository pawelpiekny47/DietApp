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
import com.example.dietapp.data.DishWithAmount
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.repository.DayRepository
import com.example.dietapp.ui.dish.viewmodel.DishDetails
import com.example.dietapp.ui.dish.viewmodel.DishWithIngredientsDetails
import com.example.dietapp.ui.dish.viewmodel.toDishWithIngredientDetails
import com.example.dietapp.ui.common.DietStatistics
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.math.RoundingMode
import java.util.stream.Collectors
import kotlin.streams.toList

class DayViewModel(private val dayRepository: DayRepository) : ViewModel(), DietStatistics {
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
            DayWithDishesDetailsUiState(dayDetails = dayDetails)

    }

    fun updateDayWithDishUiState(dishId: Int, string: String) {
        dayWithDishesUiState =
            DayWithDishesDetailsUiState(
                dayDetails = DayWithDishesDetails(
                    day = dayWithDishesUiState.dayDetails.day,
                    dishList = dayWithDishesUiState.dayDetails.dishList.stream()
                        .map {
                            if (it.dishDetails.dish.dishId == dishId)
                                DishWithAmountDetails(it.dishDetails, string)
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

    fun updateDayName(name: String) {
        dayWithDishesUiState =
            DayWithDishesDetailsUiState(
                dayDetails = DayWithDishesDetails(
                    day = Day(
                        dayWithDishesUiState.dayDetails.day.dayId,
                        name
                    ),
                    dishList = dayWithDishesUiState.dayDetails.dishList
                ),
                dayDishCrossRefToDelete = mutableListOf<DayDishCrossRef>().also {
                    it.addAll(
                        dayWithDishesUiState.dayDishCrossRefToDelete,
                    )
                }
            )
    }

    fun addToDishWithAmountList(dishWithAmountDetails: DishWithAmountDetails) {
        dayWithDishesUiState =
            DayWithDishesDetailsUiState(
                dayDetails = DayWithDishesDetails(
                    day = dayWithDishesUiState.dayDetails.day,
                    dishList = mutableListOf<DishWithAmountDetails>().also {
                        it.addAll(
                            dayWithDishesUiState.dayDetails.dishList,
                        )
                        it.add(dishWithAmountDetails)
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
                dayDetails = DayWithDishesDetails(
                    day = dayWithDishesUiState.dayDetails.day,
                    dishList = dayWithDishesUiState.dayDetails.dishList.stream()
                        .filter { (it != dishWithAmountDetails) }
                        .toList()),
                dayDishCrossRefToDelete = mutableListOf<DayDishCrossRef>().also {
                    it.addAll(
                        dayWithDishesUiState.dayDishCrossRefToDelete,
                    )
                }.also {
                    it.add(
                        DayDishCrossRef(
                            dayId = dayWithDishesUiState.dayDetails.day.dayId,
                            dishId = dishWithAmountDetails.dishDetails.dish.dishId,
                            amount = 0
                        )
                    )
                }
            )
    }

    suspend fun saveDayWithDishes() {
        dayRepository.saveDay(dayWithDishesUiState.dayDetails.day)
        dayRepository.saveAll(dayWithDishesUiState.toDayDishCrossRefList())
        dayRepository.deleteAll(dayWithDishesUiState.dayDishCrossRefToDelete)
    }

    override fun returnCurrentKcal(): Double {
        return dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentProtein(): Double {
        return dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.protein.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentCarbs(): Double {
        return dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.carbohydrates.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentFat(): Double {
        return dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.fats.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentSoil(): Double {
        return dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.soil.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentFiber(): Double {
        return dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.fiber.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentPufa(): Double {
        return dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.polyunsaturatedFats.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    }

    override fun returnCurrentKcalForFoodCategory(foodType: FoodCategory): Double {
        return dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
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
    val dayDetails: DayWithDishesDetails = DayWithDishesDetails(),
    val dayDishCrossRefToDelete: List<DayDishCrossRef> = mutableListOf()
)

fun DayWithDishesDetailsUiState.toDayDishCrossRefList(): List<DayDishCrossRef> {
    return dayDetails.dishList
        .stream()
        .map {
            DayDishCrossRef(
                dayDetails.day.dayId,
                it.dishDetails.dish.dishId,
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

data class DishWithAmountDetails(
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