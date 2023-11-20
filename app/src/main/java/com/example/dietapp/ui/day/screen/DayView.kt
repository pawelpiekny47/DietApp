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
                value = dish.amount,
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
    val totalKcal = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val kcalFromFruits = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .filter { it.ingredientDetails.foodCategory == FoodCategory.Fruit }
            .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val kcalFromVegetables =
        dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .filter { it.ingredientDetails.foodCategory == FoodCategory.Vegetable }
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    val kcalFromProteinSource =
        dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .filter { it.ingredientDetails.foodCategory == FoodCategory.ProteinSource }
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    val kcalFromMilkProducts =
        dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .filter { it.ingredientDetails.foodCategory == FoodCategory.MilkAndReplacement }
                .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    val kcalFromGrain = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .filter { it.ingredientDetails.foodCategory == FoodCategory.Wheet }
            .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val kcalFromAddedFat = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .filter { it.ingredientDetails.foodCategory == FoodCategory.AddedFat }
            .map { ((it.ingredientDetails.totalKcal.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val protein = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.protein.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val carbohydrates = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.carbohydrates.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val fats = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.fats.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val polyunsaturatedFats =
        dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
            it.dishDetails.ingredientList.stream()
                .map { ((it.ingredientDetails.polyunsaturatedFats.toDouble() * it.amount.toDouble()) / (100)) }
                .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
        }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
            2,
            RoundingMode.HALF_DOWN
        ).toDouble()
    val soil = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.soil.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()
    val fiber = dayViewModel.dayWithDishesUiState.dayDetails.dishList.stream().map { it ->
        it.dishDetails.ingredientList.stream()
            .map { ((it.ingredientDetails.fiber.toDouble() * it.amount.toDouble()) / (100)) }
            .collect(Collectors.summingDouble { d -> d }) * (it.amount.toIntOrNull()?:0)
    }.collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
        2,
        RoundingMode.HALF_DOWN
    ).toDouble()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F),
            text = "totalKcal:")
        LinearProgressIndicator(
            modifier = Modifier.weight(1F),
            progress = (totalKcal / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble()).toFloat()
        )
        Text(
            modifier = Modifier.weight(1F),
            text = "$totalKcal / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal}"
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F), text = "kcalFromFruits:")
        LinearProgressIndicator(
            modifier = Modifier.weight(1F),
            progress = (kcalFromFruits / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromFruits.toDouble()).toFloat()
        )
        Text(
            modifier = Modifier.weight(1F),
            text = "$kcalFromFruits / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromFruits}"
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.weight(1F),
            text = "kcalFromVegetables:"
        )
        LinearProgressIndicator(
            modifier = Modifier.weight(1F),
            progress = (kcalFromVegetables / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromVegetables.toDouble()).toFloat()
        )
        Text(
            modifier = Modifier.weight(1F),
            text = "$kcalFromVegetables / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromVegetables}"
        )
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F),
            text = "kcalFromProteinSource:")
        LinearProgressIndicator(
            modifier = Modifier.weight(1F),

            progress = (kcalFromProteinSource / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromProteinSource.toDouble()).toFloat()
        )
        Text(modifier = Modifier.weight(1F),
            text = "$kcalFromProteinSource / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromProteinSource}")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F),
            text = "kcalFromMilkProducts:")
        LinearProgressIndicator(
            modifier = Modifier.weight(1F),

            progress = (kcalFromMilkProducts / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromMilkProducts.toDouble()).toFloat()
        )
        Text(modifier = Modifier.weight(1F),
            text = "$kcalFromMilkProducts / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromMilkProducts}")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.weight(1F),
            text = "kcalFromGrain:")
        LinearProgressIndicator(
            modifier = Modifier.weight(1F),
            progress = (kcalFromGrain / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromGrain.toDouble()).toFloat()
        )
        Text(
            modifier = Modifier.weight(1F),
            text = "$kcalFromGrain / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromGrain}")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.weight(1F),
            text = "kcalFromAddedFat:")
        LinearProgressIndicator(
            modifier = Modifier.weight(1F),
            progress = (kcalFromAddedFat / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromAddedFat.toDouble()).toFloat()
        )
        Text(
            modifier = Modifier.weight(1F),
            text = "$kcalFromAddedFat / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromAddedFat}")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F),
            text = "protein:")
        LinearProgressIndicator(modifier = Modifier.weight(1F),
            progress = (protein / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble()).toFloat()
        )
        Text(modifier = Modifier.weight(1F),
            text = "$protein / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein}")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F),
            text = "carbohydrates:")
        LinearProgressIndicator(modifier = Modifier.weight(1F),

            progress = (carbohydrates / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble()).toFloat()
        )
        Text(modifier = Modifier.weight(1F),
            text = "$carbohydrates / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates}")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F),
            text = "fats:")
        LinearProgressIndicator(modifier = Modifier.weight(1F),

            progress = (fats / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble()).toFloat()
        )
        Text(modifier = Modifier.weight(1F),
            text = "$fats / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats}")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F),
            text = "polyunsaturatedFats:")
        LinearProgressIndicator(modifier = Modifier.weight(1F),

            progress = (polyunsaturatedFats / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.polyunsaturatedFats.toDouble()).toFloat()
        )
        Text(modifier = Modifier.weight(1F),
            text = "$polyunsaturatedFats / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.polyunsaturatedFats}")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F),
            text = "soil:")
        LinearProgressIndicator(modifier = Modifier.weight(1F),

            progress = (soil / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.soil.toDouble()).toFloat()
        )
        Text(modifier = Modifier.weight(1F),
            text = "$soil / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.soil}")
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(modifier = Modifier.weight(1F),
            text = "fiber:")
        LinearProgressIndicator(modifier = Modifier.weight(1F),

            progress = (fiber / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fiber.toDouble()).toFloat()
        )
        Text(modifier = Modifier.weight(1F),
            text = "$fiber / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fiber}")
    }
}