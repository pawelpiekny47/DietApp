package com.example.dietapp.ui.dish.screen

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
import com.example.dietapp.data.DishWithIngredients
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import com.example.dietapp.ui.dish.viewmodel.DishWithIngredientsDetails
import com.example.dietapp.ui.dish.viewmodel.toDishWithIngredientDetails


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DishListView(
    onItemClick: (DishWithIngredientsDetails) -> Unit,
    viewModel: DishViewModel
) {
    val dishUiState by viewModel.dishListUiState.collectAsState()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = " Dishes"
            )
        }
        items(dishUiState.dishList) { dish ->
            DishItem(
                onItemClick = onItemClick,
                dish = dish,
            )
        }
    }
}

@Composable
fun DishItem(
    onItemClick: (DishWithIngredientsDetails) -> Unit,
    dish: DishWithIngredients,
) {
    Card(modifier = Modifier
        .clickable { onItemClick(dish.toDishWithIngredientDetails()) }) {
        Text(
            text = "${dish.dish.name}  protein:${dish.ingredientList.sumOf { it.ingredient.protein }} carbs:${dish.ingredientList.sumOf { it.ingredient.carbohydrates }}"
        )
    }
}