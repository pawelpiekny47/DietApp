package com.example.dietapp.ui.day.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dietapp.ui.common.DietSettingsStatistic
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayView(
    saveButtonOnClick: () -> Unit,
    dayViewModel: DayViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(Dp(10F)),
    ) {
        OutlinedTextField(
            value = dayViewModel.dayWithDishesUiState.dayDetails.day.name,
            onValueChange = { dayViewModel.updateDayName(it) },
            label = { Text("name") },
            enabled = true,
            singleLine = true
        )
        DishList(Modifier.weight(4F), dayViewModel)
        Box(modifier = Modifier.weight(1f)) {
            DietSettingsStatistic(
                viewModel = dayViewModel,
                dietSettingsViewModel = dietSettingsViewModel
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            Button(
                onClick = saveButtonOnClick,
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = "Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishList(
    modifier: Modifier,
    viewModel: DayViewModel
) {
    LazyColumn(modifier = modifier) {
        items(viewModel.dayWithDishesUiState.dayDetails.dishList) { dish ->

            Row(verticalAlignment = Alignment.CenterVertically)
            {
                Text(
                    modifier = Modifier.weight(2F),
                    text = dish.dishDetails.dish.name
                )
                Row(
                    modifier = Modifier
                        .weight(1F),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextField(
                        modifier = Modifier
                            .weight(1F)
                            .defaultMinSize(minHeight = 20.dp),
                        value = dish.amount,
                        onValueChange = {
                            viewModel.updateDayWithDishUiState(
                                dish.dishDetails.dish.dishId,
                                it
                            )
                        },
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                    )

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "delete",
                        modifier = Modifier
                            .weight(1F)
                            .clickable {
                                viewModel.deleteDishFromDay(dish)
                            }
                    )
                }

            }

        }
    }
}