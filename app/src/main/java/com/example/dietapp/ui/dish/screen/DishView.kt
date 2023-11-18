package com.example.dietapp.ui.dish.screen

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
    DietSettingsStatistic(dishViewModel, dietSettingsViewModel)
}

@Composable
fun DietSettingsStatistic(
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val protein =
        dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.stream().map { it ->
            (it.ingredientDetails.protein.toDouble() * it.amount) / (100)
        }
            .collect(Collectors.summingDouble { d -> d }).toBigDecimal().setScale(
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