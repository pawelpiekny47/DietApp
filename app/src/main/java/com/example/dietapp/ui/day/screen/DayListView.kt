package com.example.dietapp.ui.day.screen

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dietapp.data.DayWithDishes
import com.example.dietapp.ui.common.BasicMacrosStats
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.day.viewmodel.DayWithDishesDetails
import com.example.dietapp.ui.day.viewmodel.toDayDetails
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DayListView(
    onItemClick: (DayWithDishesDetails) -> Unit,
    viewModel: DayViewModel,
    dietSettingsViewModel: DietSettingsViewModel,
    filteredText: String
) {
    val dayUiState by viewModel.dayListUiState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(dayUiState.dayList.filter { day ->
            day.day.name.contains(filteredText, true) ||
                    day.dishWithAmountList.any { dish ->
                        dish.dishWithIngredients.dish.name.contains(filteredText, true)
                    }
                    || day.dishWithAmountList.any { dish ->
                dish.dishWithIngredients.ingredientList.any { ingredient ->
                    ingredient.ingredient.name.contains(
                        filteredText, true
                    )
                }
            }

        }) { day ->
            DayItem(
                onItemClick = onItemClick,
                day = day,
                dietSettingsViewModel = dietSettingsViewModel
            )
            Divider(modifier = Modifier.padding(30.dp, 0.dp))

        }
    }
}

@Composable
fun DayItem(
    onItemClick: (DayWithDishesDetails) -> Unit,
    day: DayWithDishes,
    dietSettingsViewModel: DietSettingsViewModel
) {
    var extended2 by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .animateContentSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(Dp(5F))
        .clickable { onItemClick(day.toDayDetails()) }) {

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    text = day.day.name
                )
            }
            DayDishList(day)
            Box(modifier = Modifier.clickable { extended2 = !extended2 }) {
                val kcalTextValue: String
                val proteinTextValue: String
                val carbsTextValue: String
                val fatsTextValue: String
                when (extended2) {
                    true -> {
                        kcalTextValue = "${
                            day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.totalKcal * it.amount / 100 } }
                                .toInt()
                        }g"
                        proteinTextValue = "${
                            day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.protein * it.amount / 100 } }
                                .toInt()
                        }g"
                        carbsTextValue = "${
                            day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.carbohydrates * it.amount / 100 } }
                                .toInt()
                        }g"
                        fatsTextValue = "${
                            day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.fats * it.amount / 100 } }
                                .toInt()
                        }g"
                        BasicMacrosStats(
                            kcalTextValue,
                            proteinTextValue,
                            carbsTextValue,
                            fatsTextValue
                        )
                    }

                    false -> {
                        kcalTextValue = "${
                            (day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.totalKcal * it.amount / 100 } }
                                    / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 100)
                                .toInt()
                        }%"
                        proteinTextValue = "${
                            (day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.protein * it.amount / 100 } }
                                    / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble() * 100)
                                .toInt()
                        }%"
                        carbsTextValue = "${
                            (day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.carbohydrates * it.amount / 100 } }
                                    / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble() * 100)
                                .toInt()
                        }%"
                        fatsTextValue = "${
                            (day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.fats * it.amount / 100 } }
                                    / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble() * 100)
                                .toInt()
                        }%"
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

    }
}


@Composable
fun DayDishList(day: DayWithDishes) {
    day.dishWithAmountList.forEach { dish ->
        Text(
            text = "- ${dish.dishWithIngredients.dish.name}",
            modifier = Modifier
                .padding(Dp(5F)),
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.bodySmall
        )
    }
}