package com.example.dietapp.ui.dish.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.ingredient.screen.IngredientItem
import com.example.dietapp.ui.ingredient.viewmodel.IngredientDetails
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel

@Composable
fun AddIngredient(
    onListItemClick: (IngredientDetails) -> Unit,
    ingredientViewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val ingredientUiState by ingredientViewModel.ingredientListUiState.collectAsState()
    Column {
        Box(modifier = Modifier.weight(5F)) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FoodCategory.values().forEach { foodCategory ->
                    if (ingredientUiState.ingredientList.any { it.foodCategory == foodCategory }) item {
                        Text(
                            text = "${foodCategory}",
                        )
                    }
                    items(ingredientUiState.ingredientList.filter { it.foodCategory == foodCategory }) { ingredient ->
                        IngredientItem(
                            ingredient = ingredient,
                            onItemClick = onListItemClick
                        )
                    }
                }
            }
        }
    }
}