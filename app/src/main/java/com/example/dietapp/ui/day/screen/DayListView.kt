package com.example.dietapp.ui.day.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dietapp.data.DayWithDishes
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.day.viewmodel.DayWithDishesDetails
import com.example.dietapp.ui.day.viewmodel.toDayDetails

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DayListView(
    onItemClick: (DayWithDishesDetails) -> Unit,
    viewModel: DayViewModel
) {
    val dayUiState by viewModel.dayListUiState.collectAsState()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Days"
            )
        }
        items(dayUiState.dayList) { day ->
            DayItem(
                onItemClick = onItemClick,
                day = day,
            )
        }
    }
}

@Composable
fun DayItem(
    onItemClick: (DayWithDishesDetails) -> Unit,
    day: DayWithDishes,
) {
    Card(modifier = Modifier
        .clickable { onItemClick(day.toDayDetails()) }) {
        Text(
            text = "${day.day.name}  protein:${day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.protein } * it.amount }} carbs:${day.dishWithAmountList.sumOf { it -> it.dishWithIngredients.ingredientList.sumOf { it.ingredient.carbohydrates }*it.amount }}"
        )
    }
}