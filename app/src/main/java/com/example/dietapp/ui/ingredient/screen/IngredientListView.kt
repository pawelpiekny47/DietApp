package com.example.dietapp.ui.ingredient.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dietapp.data.Ingredient
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.ingredient.viewmodel.IngredientDetails
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import com.example.dietapp.ui.ingredient.viewmodel.toIngredientDetails

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IngredientListScreen(
    onListItemClick: (IngredientDetails) -> Unit,
    viewModel: IngredientViewModel
) {
    val ingredientUiState by viewModel.ingredientListUiState.collectAsState()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FoodCategory.values().forEach { foodCategory ->
            if (ingredientUiState.ingredientList.any { it.foodCategory == foodCategory }) item {
                Text(
                    text = "${foodCategory}"
                )
            }
            items(ingredientUiState.ingredientList.filter { it.foodCategory == foodCategory }) { ingredient ->
                IngredientItem(
                    ingredient = ingredient,
                    onItemClick = onListItemClick,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient, onItemClick: (IngredientDetails) -> Unit, viewModel: IngredientViewModel) {
    Card(modifier = Modifier
        .clickable { onItemClick(ingredient.toIngredientDetails()) }) {
        Text(
            text = "${ingredient.name}     b :${ingredient.protein} / w: ${ingredient.carbohydrates} / t: ${ingredient.fats}"
        )
    }
}