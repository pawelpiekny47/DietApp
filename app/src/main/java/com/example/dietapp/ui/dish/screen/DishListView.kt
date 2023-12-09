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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dietapp.R
import com.example.dietapp.data.DishWithIngredients
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.common.BasicMacrosStats
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
            Divider(modifier = Modifier.padding(30.dp, 0.dp, 30.dp, 20.dp))

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
    var extended2 by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dp(5F))
            .animateContentSize()
            .clickable { onItemClick(dish.toDishWithIngredientDetails()) },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    text = dish.dish.name
                )
            }
            DishIngredientList(dish)
            Box(modifier = Modifier.clickable { extended2 = !extended2 }) {

                val kcalTextValue: String
                val proteinTextValue: String
                val carbsTextValue: String
                val fatsTextValue: String
                when (extended2) {
                    true -> {
                        kcalTextValue = "${
                            (dish.ingredientList.sumOf { it.ingredient.totalKcal * it.amount / 100 } / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 100)
                                .toInt()

                        }%"
                        proteinTextValue = "${
                            (dish.ingredientList.sumOf { it.ingredient.protein * it.amount / 100 } / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble() * 100)
                                .toInt()

                        }%"
                        carbsTextValue = "${
                            (dish.ingredientList.sumOf { it.ingredient.carbohydrates * it.amount / 100 } / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble() * 100)
                                .toInt()

                        }%"
                        fatsTextValue = "${
                            (dish.ingredientList.sumOf { it.ingredient.fats * it.amount / 100 } / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble() * 100)
                                .toInt()

                        }%"
                    }

                    false -> {
                        kcalTextValue = "${
                            dish.ingredientList.sumOf { it.ingredient.totalKcal * it.amount / 100 }
                                .toInt()
                        }"
                        proteinTextValue = "${
                            dish.ingredientList.sumOf { it.ingredient.protein * it.amount / 100 }
                                .toInt()
                        }g"
                        carbsTextValue = "${
                            dish.ingredientList.sumOf { it.ingredient.carbohydrates * it.amount / 100 }
                                .toInt()
                        }g"
                        fatsTextValue = "${
                            dish.ingredientList.sumOf { it.ingredient.fats * it.amount / 100 }
                                .toInt()
                        }g"

                    }
                }
                BasicMacrosStats(
                    kcalTextValue,
                    proteinTextValue,
                    carbsTextValue,
                    fatsTextValue
                )
            }
        }
    }
}

@Composable
fun DishIngredientList(dish: DishWithIngredients) {
    dish.ingredientList.sortedByDescending { it.amount }.forEach { ingredient ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = when (ingredient.ingredient.foodCategory) {
                    FoodCategory.Fruit -> painterResource(R.drawable.banana)
                    FoodCategory.Vegetable -> painterResource(R.drawable.lettuce)
                    FoodCategory.MilkAndReplacement -> painterResource(R.drawable.milk)
                    FoodCategory.AddedFat -> painterResource(R.drawable.oliveoil)
                    FoodCategory.Wheet -> painterResource(R.drawable.wheat)
                    FoodCategory.ProteinSource -> painterResource(R.drawable.meat)
                },
                contentDescription = null,
                tint = when (ingredient.ingredient.foodCategory) {
                    FoodCategory.Fruit -> com.example.dietapp.ui.theme.lightFruit
                    FoodCategory.Vegetable -> com.example.dietapp.ui.theme.lightVegetable
                    FoodCategory.MilkAndReplacement -> com.example.dietapp.ui.theme.lightMilk
                    FoodCategory.AddedFat -> com.example.dietapp.ui.theme.lightAddedFat
                    FoodCategory.Wheet -> com.example.dietapp.ui.theme.lightGrain
                    FoodCategory.ProteinSource -> com.example.dietapp.ui.theme.lightProteinSource
                }
            )
            Text(
                text = " ${ingredient.ingredient.name}    ${ingredient.amount}g",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}