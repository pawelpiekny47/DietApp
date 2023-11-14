package com.example.dietapp.ui.dish.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.dietapp.ui.dish.viewmodel.DishViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishView(
    saveButtonOnClick: () -> Unit,
    viewModel: DishViewModel
) {
    Column(
    ) {
        OutlinedTextField(
            value = viewModel.dishWithIngredientsUiState.dishDetails.dish.name,
            onValueChange = { viewModel.updateDishName(it) },
            label = { Text("name") },
            enabled = true,
            singleLine = true
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientList(
    viewModel: DishViewModel
) {
    viewModel.dishWithIngredientsUiState.dishDetails.ingredientList.forEach { ingredient ->
        Row(modifier = Modifier.clickable { viewModel.deleteIngredientFromDish(ingredient) }) {
            Text(
                modifier = Modifier.clickable { viewModel.deleteIngredientFromDish(ingredient) },
                text = "name: ${ingredient.ingredientDetails.name}"
            )
            OutlinedTextField(
                value = ingredient.amount.toString(),
                onValueChange = {
                    viewModel.updateDishWithIngredientUiState(
                        ingredient.ingredientDetails.id,
                        it
                    )
                },
                label = { Text("amount") },
                enabled = true,
                singleLine = true
            )
        }

    }
}