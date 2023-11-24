package com.example.dietapp.ui.day.screen

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
import com.example.dietapp.data.DayWithDishes
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.day.viewmodel.DayWithDishesDetails
import com.example.dietapp.ui.day.viewmodel.toDayDetails
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DayListView(
    onItemClick: (DayWithDishesDetails) -> Unit,
    viewModel: DayViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val dayUiState by viewModel.dayListUiState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        items(dayUiState.dayList) { day ->
            DayItem(
                onItemClick = onItemClick,
                day = day,
                dietSettingsViewModel = dietSettingsViewModel
            )
        }
    }
}

@Composable
fun DayItem(
    onItemClick: (DayWithDishesDetails) -> Unit,
    day: DayWithDishes,
    dietSettingsViewModel: DietSettingsViewModel
) {
    var extended by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }




    Card(modifier = Modifier
        .fillMaxWidth()
        .animateContentSize()
        .padding(Dp(5F))
        .clickable { onItemClick(day.toDayDetails()) }) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(8F),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    text = day.day.name
                )
                Box(
                    Modifier
                        .weight(5f)
                        .clickable { extended2 = !extended2 }
                ) {
                    if (extended2) DayMacrosRow(day)
                    else DayMacrosRowWithPercent(day, dietSettingsViewModel)

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
            DayDishList(day)
        }
    }
}

@Composable
fun DayMacrosRow(day: DayWithDishes) {
    Column(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row{
            Text(
                text = "kcal:${
                    day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.totalKcal * it.amount / 100 } * it.amount }
                        .toInt()
                }",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "p:${
                    day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.protein * it.amount / 100 } * it.amount }
                        .toInt()
                }",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "c:${
                    day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.carbohydrates * it.amount / 100 } * it.amount }
                        .toInt()
                }",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "f:${
                    day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.fats * it.amount / 100 } * it.amount }
                        .toInt()
                }",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun DayMacrosRowWithPercent(day: DayWithDishes, dietSettingsViewModel: DietSettingsViewModel) {
    Column(
        Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row{
            Text(
                text = "kcal:${
                    (day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.totalKcal * it.amount / 100 } * it.amount }
                        / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 100)
                        .toInt()
                }%",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "p:${
                    (day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.protein * it.amount / 100 } * it.amount }
                            / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble() * 100)
                        .toInt()
                }%",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "c:${
                    ( day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.carbohydrates * it.amount / 100 } * it.amount }
                            / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble() * 100)
                        .toInt()
                }%",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "f:${
                    ( day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.fats * it.amount / 100 } * it.amount }
                            / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble() * 100)
                        .toInt()
                }%",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
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