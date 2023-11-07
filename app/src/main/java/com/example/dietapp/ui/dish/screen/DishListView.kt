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
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import com.example.dietapp.ui.ingredient.viewmodel.IngredientDetails
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import com.example.dietapp.ui.ingredient.viewmodel.toIngredientDetails


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DishListView(
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
                dish = dish,
            )
        }
    }
}

@Composable
fun DishItem(
    dish: DishWithIngredients,
) {
    Card {
        Text(
            text = "${dish.dish.name}  ingredients:${dish.ingredientList.count()} "
        )
    }
}