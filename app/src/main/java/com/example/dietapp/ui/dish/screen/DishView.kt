package com.example.dietapp.ui.dish.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        DietSettingsStatistic(
            dishViewModel = dishViewModel,
            dietSettingsViewModel = dietSettingsViewModel
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
fun IngredientList(
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.forEach { ingredient ->
        Row(modifier = Modifier.clickable { dishViewModel.deleteIngredientFromDish(ingredient) }) {
            Text(
                modifier = Modifier.clickable { dishViewModel.deleteIngredientFromDish(ingredient) },
                text = "name: ${ingredient.ingredientDetails.name}"
            )
            OutlinedTextField(
                value = ingredient.amount.toString(),
                onValueChange = {
                    dishViewModel.updateDishWithIngredientUiState(
                        ingredient.ingredientDetails.id,
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
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val totalKcal =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.totalKcal.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val kcalFromFruits =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.Fruit)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val kcalFromVegetables =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.Vegetable)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val kcalFromProteinSource =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.ProteinSource)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val kcalFromMilkProducts =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.MilkAndReplacement)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val kcalFromGrain =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.Wheet)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()
    val kcalFromAddedFat =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().filter {
            (it.ingredientDetails.foodCategory == FoodCategory.AddedFat)
        }.map {
            (it.ingredientDetails.totalKcal.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val protein =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.protein.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val carbohydrates =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.carbohydrates.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val fats =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.fats.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val polyunsaturatedFats =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.polyunsaturatedFats.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val soil =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.soil.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()

    val fiber =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map {
            (it.ingredientDetails.fiber.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
                2,
                RoundingMode.HALF_DOWN
            ).toDouble()


    Card {
        Row(verticalAlignment = Alignment.CenterVertically) {
//            Text(text = "protein:")
//            LinearProgressIndicator(
//                progress = (protein / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble()).toFloat()
//            )
//            Text(text = "$protein / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein}")
            Text(text = "totalKcal: $totalKcal")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "fruits: $kcalFromFruits vegetables: $kcalFromVegetables proteins: $kcalFromProteinSource milk: $kcalFromMilkProducts grain: $kcalFromGrain")
        }
    }
}