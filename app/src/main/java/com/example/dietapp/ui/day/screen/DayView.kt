package com.example.dietapp.ui.day.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import java.math.RoundingMode
import java.util.stream.Collectors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayView(
    saveButtonOnClick: () -> Unit,
    dayViewModel: DayViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    Column(
    ) {
        OutlinedTextField(
            value = dayViewModel.dayWithDishesUiState.dayDetails.day.name,
            onValueChange = { dayViewModel.updateDayName(it) },
            label = { Text("name") },
            enabled = true,
            singleLine = true
        )
        DishList(
            dayViewModel
        )
        DietSettingsStatistic(dayViewModel, dietSettingsViewModel)
        Button(
            onClick = saveButtonOnClick,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = "Save")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishList(
    viewModel: DayViewModel
) {
    viewModel.dayWithDishesUiState.dayDetails.dishList.forEach { dish ->
        Row(modifier = Modifier.clickable { viewModel.deleteDishFromDay(dish) }) {
            Text(
                modifier = Modifier.clickable { viewModel.deleteDishFromDay(dish) },
                text = "name: ${dish.dishDetails.dish.name}"
            )
            OutlinedTextField(
                value = dish.amount.toString(),
                onValueChange = {
                    viewModel.updateDayWithDishUiState(
                        dish.dishDetails.dish.dishId,
                        it
                    )
                },
                label = { Text("amount") },
                enabled = true,
                singleLine = true
            )
        }
    }
}

@Composable
fun DietSettingsStatistic(
    dayViewModel: DayViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val protein = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.protein.toDouble() * it.amount) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * it.amount
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "protein:")
        LinearProgressIndicator(
            progress = (protein / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble()).toFloat()
        )
        Text(text = "$protein / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein}")
    }
}