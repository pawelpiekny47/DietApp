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
import com.example.dietapp.repository.DayRepository
import com.example.dietapp.ui.dish.viewmodel.DishDetails
import com.example.dietapp.ui.dish.viewmodel.DishWithIngredientsDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.streams.toList

class DayViewModel(private val dayRepository: DayRepository) : ViewModel() {
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
                            if (it.dishDetails.dishId == dishId)
                                DishWithAmountDetails(it.dishDetails, string.toInt())
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
                            dishId = dishWithAmountDetails.dishDetails.dishId,
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
                it.dishDetails.dishId,
                it.amount
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
    val dishDetails: DishDetails,
    var amount: Int = 0
)

fun DishWithAmount.toDishWithAmountDetails(): DishWithAmountDetails {
    return DishWithAmountDetails(
        DishDetails(
            this.dishWithIngredients.dish.dishId,
            this.dishWithIngredients.dish.name,
            this.dishWithIngredients.dish.description
        ),
        this.amount
    )
}

fun DishWithIngredientsDetails.toDishWithAmountDetails(): DishWithAmountDetails {
    return DishWithAmountDetails(this.dish.toDishDetails(), 0)
}
fun Dish.toDishDetails(): DishDetails{
    return DishDetails(
        this.dishId,
    this.name,
        this.description
    )
}