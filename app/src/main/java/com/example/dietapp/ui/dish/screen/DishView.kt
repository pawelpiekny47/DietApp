package com.example.dietapp.ui.dish.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dietapp.ui.dish.viewmodel.DishViewModel

@Composable
fun DishView(
    saveButtonOnClick: () -> Unit,
    viewModel: DishViewModel
) {
    Column(
    ) {
        Text(text = viewModel.dishWithIngredientsUiState.dishDetails.dish.name)
        IngredientList(
            viewModel
        )
        Button(
            onClick = saveButtonOnClick,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun IngredientList(
    viewModel: DishViewModel
) {
    viewModel.dishWithIngredientsUiState.dishDetails.ingredientList.forEach {
        Text(
            modifier = Modifier.clickable { viewModel.deleteIngredientFromDish(it) },
            text = it.name
        )
    }
}