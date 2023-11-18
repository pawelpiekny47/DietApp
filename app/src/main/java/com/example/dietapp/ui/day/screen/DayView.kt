package com.example.dietapp.ui.day.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dietapp.ui.day.viewmodel.DayViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayView(
    saveButtonOnClick: () -> Unit,
    viewModel: DayViewModel
) {
    Column(
    ) {
        OutlinedTextField(
            value = viewModel.dayWithDishesUiState.dayDetails.day.name,
            onValueChange = { viewModel.updateDayName(it) },
            label = { Text("name") },
            enabled = true,
            singleLine = true
        )
        DishList(
            viewModel
        )
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
                text = "name: ${dish.dishDetails.name}"
            )
            OutlinedTextField(
                value = dish.amount.toString(),
                onValueChange = {
                    viewModel.updateDayWithDishUiState(
                        dish.dishDetails.dishId,
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