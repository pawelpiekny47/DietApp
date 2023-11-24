package com.example.dietapp.ui.dish.screen

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.dietapp.data.DishWithIngredients
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import com.example.dietapp.ui.dish.viewmodel.DishWithIngredientsDetails
import com.example.dietapp.ui.dish.viewmodel.toDishWithIngredientDetails


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DishListView(
    onItemClick: (DishWithIngredientsDetails) -> Unit,
    viewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val dishUiState by viewModel.dishListUiState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(dishUiState.dishList.sortedBy { it.dish.name }) { dish ->
            DishItem(
                onItemClick = onItemClick,
                dish = dish,
                dietSettingsViewModel = dietSettingsViewModel
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun DishItem(
    onItemClick: (DishWithIngredientsDetails) -> Unit,
    dish: DishWithIngredients,
    dietSettingsViewModel: DietSettingsViewModel
) {
    var extended by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }

    Card(modifier = Modifier
        .padding(Dp(5F))
        .animateContentSize()
        .clickable { onItemClick(dish.toDishWithIngredientDetails()) }) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(8F),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    text = dish.dish.name
                )
                Box(
                    Modifier
                        .weight(4f)
                        .clickable { extended2 = !extended2 }
                ) {
                    if (extended2) DishMacrosRow(dish)
                    else DishMacrosRowWithPercent(dish, dietSettingsViewModel)
                }
                Icon(
                    imageVector = if (extended) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "delete",
                    modifier = Modifier
                        .weight(1f)
                        .scale(0.7F)
                        .clickable {
                            extended = !extended
                        }
                )
            }
            DishIngredientList(dish, extended)
        }
    }
}

@Composable
fun DishMacrosRow(dish: DishWithIngredients) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row {

            Text(
                text = "kcal: ${
                    dish.ingredientList.sumOf { it.ingredient.totalKcal * it.amount / 100 }
                        .toInt()
                }",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "p: ${
                    dish.ingredientList.sumOf { it.ingredient.protein * it.amount / 100 }
                        .toInt()
                }",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "c: ${
                    dish.ingredientList.sumOf { it.ingredient.carbohydrates * it.amount / 100 }
                        .toInt()
                }",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "f: ${
                    dish.ingredientList.sumOf { it.ingredient.fats * it.amount / 100 }
                        .toInt()
                }",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun DishMacrosRowWithPercent(
    dish: DishWithIngredients,
    dietSettingsViewModel: DietSettingsViewModel
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row {

            Text(
                text = "kcal: ${
                    (dish.ingredientList.sumOf { it.ingredient.totalKcal * it.amount / 100 } / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 100)
                        .toInt()

                }%",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "p: ${
                    (dish.ingredientList.sumOf { it.ingredient.protein * it.amount / 100 } / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble() * 100)
                        .toInt()
                }%",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "c: ${
                    (dish.ingredientList.sumOf { it.ingredient.carbohydrates * it.amount / 100 } / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble() * 100)
                        .toInt()

                }%",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "f: ${
                    (dish.ingredientList.sumOf { it.ingredient.fats * it.amount / 100 } / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble() * 100)
                        .toInt()
                }%",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }


    }
}

@Composable
fun DishIngredientList(dish: DishWithIngredients, extended: Boolean) {
    if (extended) {
        dish.ingredientList.sortedByDescending { it.amount }.forEach { ingredient ->
            Text(
                text = "- ${ingredient.ingredient.name}    ${ingredient.amount}g",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }
    } else {
        val ingredientAmount = dish.ingredientList.count()
        dish.ingredientList.sortedByDescending { it.amount }.subList(0, minOf(ingredientAmount, 2))
            .forEach { ingredient ->
                Text(
                    text = "- ${ingredient.ingredient.name}    ${ingredient.amount}g",
                    modifier = Modifier
                        .padding(Dp(2F)),
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
    }
}