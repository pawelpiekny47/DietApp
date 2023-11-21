package com.example.dietapp.ui.dish.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import java.math.RoundingMode
import java.util.stream.Collectors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishView(
    saveButtonOnClick: () -> Unit,
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(Dp(5F)),
    ) {
        OutlinedTextField(
            value = dishViewModel.dishWithIngredientsUiState.dishDetails.dish.name,
            onValueChange = { dishViewModel.updateDishName(it) },
            label = { Text("name") },
            enabled = true,
            singleLine = true
        )
        IngredientList(
            dishViewModel,
            dietSettingsViewModel
        )
        Box(modifier = Modifier.weight(2f))
        DietSettingsStatistic(
            dishViewModel = dishViewModel,
            dietSettingsViewModel = dietSettingsViewModel
        )
        Box(modifier = Modifier.weight(1f))
        Button(
            onClick = saveButtonOnClick,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = "Save")
        }
        Box(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientList(
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.forEach { ingredient ->
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier.weight(2F),
                text = ingredient.ingredientDetails.name,
                fontSize = 15.sp,
            )
            Row(
                modifier = Modifier
                    .weight(1F),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.weight(3F)
                ) {
                    TextField(
                        textStyle = TextStyle(fontSize = 15.sp),
                        modifier = Modifier
                            .weight(5F)
                            .defaultMinSize(minHeight = 10.dp),
                        value = ingredient.amount,
                        onValueChange = {
                            dishViewModel.updateDishWithIngredientUiState(
                                ingredient.ingredientDetails.id,
                                it
                            )
                        },
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                    )
                    Text(
                        modifier = Modifier
                            .defaultMinSize(minWidth = 1.dp)
                            .weight(1F),
                        text = " g",
                        fontSize = 12.sp,
                    )
                }
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "delete",
                    modifier = Modifier
                        .weight(1F)
                        .clickable {
                            dishViewModel.deleteIngredientFromDish(
                                ingredient
                            )
                        }
                )
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietSettingsStatistic(
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val totalKcal =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.totalKcal.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val kcalFromFruits =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.Fruit)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val kcalFromVegetables =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.Vegetable)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val kcalFromProteinSource =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.ProteinSource)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val kcalFromMilkProducts =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.MilkAndReplacement)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val kcalFromGrain =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.Wheet)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    val kcalFromAddedFat =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.AddedFat)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val protein =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.protein.toDouble() * (it.amount.toDoubleOrNull() ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val carbohydrates =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.carbohydrates.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val fats =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.fats.toDouble() * (it.amount.toDoubleOrNull() ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val polyunsaturatedFats =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.polyunsaturatedFats.toDouble() * (it.amount.toDoubleOrNull()
                ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val soil =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.soil.toDouble() * (it.amount.toDoubleOrNull() ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val fiber =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.fiber.toDouble() * (it.amount.toDoubleOrNull() ?: 0.0)) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
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