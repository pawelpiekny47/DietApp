package com.example.dietapp.ui.day.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.common.DietSettingsStatistic
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
        DishList(
            dayViewModel
        )
        Box(modifier = Modifier.weight(2f))
        DietSettingsStatistic(
            viewModel = dayViewModel,
            dietSettingsViewModel = dietSettingsViewModel
        )
        Button(
            onClick = saveButtonOnClick,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = "Save")
        }
        Box(modifier = Modifier.weight(2f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishList(
    viewModel: DayViewModel
) {
    viewModel.dayWithDishesUiState.dayDetails.dishList.forEach { dish ->
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