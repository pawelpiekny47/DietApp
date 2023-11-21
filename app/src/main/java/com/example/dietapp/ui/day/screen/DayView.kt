package com.example.dietapp.ui.day.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietSettingsStatistic(
    dayViewModel: DayViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val totalKcal = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val kcalFromFruits = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .filter { it.ingredientDetails.foodCategory == FoodCategory.Fruit }
            .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val kcalFromVegetables =
        dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .filter { it.ingredientDetails.foodCategory == FoodCategory.Vegetable }
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    val kcalFromProteinSource =
        dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .filter { it.ingredientDetails.foodCategory == FoodCategory.ProteinSource }
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    val kcalFromMilkProducts =
        dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .filter { it.ingredientDetails.foodCategory == FoodCategory.MilkAndReplacement }
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    val kcalFromGrain = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .filter { it.ingredientDetails.foodCategory == FoodCategory.Wheet }
            .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val kcalFromAddedFat =
        dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .filter { it.ingredientDetails.foodCategory == FoodCategory.AddedFat }
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    val protein = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.protein.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val carbohydrates = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.carbohydrates.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val fats = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.fats.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val polyunsaturatedFats =
        dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.polyunsaturatedFats.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    val soil = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.soil.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val fiber = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.fiber.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull() ?: 0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()

    var expanded by remember { mutableStateOf(false) }
    Card(modifier = Modifier.animateContentSize(),
        onClick = { expanded = !expanded }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            DrawStatistics(
                totalKcal,
                dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble()
                    .toFloat(),
                "totalKcal"
            )
            DrawStatistics(
                protein,
                dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble()
                    .toFloat(),
                "protein"
            )
            DrawStatistics(
                carbohydrates,
                dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble()
                    .toFloat(),
                "carbs"
            )
            DrawStatistics(
                fats,
                dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble()
                    .toFloat(),
                "fats"
            )

            if (expanded) {

                DrawStatistics(
                    kcalFromFruits,
                    dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromFruits.toDouble()
                        .toFloat(),
                    "kcalFromFruits"
                )
                DrawStatistics(
                    kcalFromVegetables,
                    dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromVegetables.toDouble()
                        .toFloat(),
                    "kcalFromVegetables"
                )
                DrawStatistics(
                    kcalFromProteinSource,
                    dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromProteinSource.toDouble()
                        .toFloat(),
                    "kcalFromProteinSource"
                )
                DrawStatistics(
                    kcalFromMilkProducts,
                    dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromMilkProducts.toDouble()
                        .toFloat(),
                    "kcalFromMilkProducts"
                )
                DrawStatistics(
                    kcalFromGrain,
                    dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromGrain.toDouble()
                        .toFloat(),
                    "kcalFromGrain"
                )
                DrawStatistics(
                    kcalFromAddedFat,
                    dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromAddedFat.toDouble()
                        .toFloat(),
                    "kcalFromAddedFat"
                )

                DrawStatistics(
                    polyunsaturatedFats,
                    dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.polyunsaturatedFats.toDouble()
                        .toFloat(),
                    "pufa"
                )
                DrawStatistics(
                    soil,
                    dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.soil.toDouble()
                        .toFloat(),
                    "soil"
                )
                DrawStatistics(
                    fiber,
                    dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fiber.toDouble()
                        .toFloat(),
                    "fiber"
                )
            }
            Icon(
                imageVector = if (expanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                "details"
            )
        }
    }
}

@Composable
fun DrawStatistics(current: Double, total: Float, name: String) {
    Row(
        modifier = Modifier.padding(0.dp, 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(10.dp, 0.dp),
            text = "$name:",
            maxLines = 1,
            fontSize = 12.sp,
        )
        LinearProgressIndicator(
            modifier = Modifier
                .weight(1F)
                .padding(10.dp, 0.dp),
            progress = if (current < total) (current / total).toFloat() else 1f
        )
        Text(
            modifier = Modifier
                .weight(1F)
                .padding(10.dp, 0.dp),
            text = "$current / $total",
            maxLines = 1,
            fontSize = 12.sp,
            fontStyle = FontStyle.Italic
        )
    }
}