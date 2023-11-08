package com.example.dietapp.ui.dish.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.dietapp.ui.dish.viewmodel.DishViewModel

@Composable
fun DishView(
    viewModel: DishViewModel
) {
    Column(
    ) {
        Text(text = viewModel.dishUiState.dishDetails.dish.name)
        IngredientList(
            viewModel
        )
    }
}

@Composable
fun IngredientList(
    viewModel: DishViewModel
) {
    viewModel.dishUiState.dishDetails.ingredientList.forEach {
        Text(text = it.name)
    }
}