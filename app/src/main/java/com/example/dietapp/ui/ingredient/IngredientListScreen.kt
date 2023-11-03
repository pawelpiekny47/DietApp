package com.example.dietapp.ui.ingredient

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dietapp.data.Ingredient
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IngredientListScreen(
    modifier: Modifier,
    viewModel: IngredientListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val ingredientUiState by viewModel.ingredientUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(
        modifier = modifier,
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
                    onItemClick = {
                        coroutineScope.launch {
                            viewModel.deleteItem(ingredient)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient, onItemClick: () -> Unit) {
    Card(modifier = Modifier
        .clickable { onItemClick() }) {
        Text(
            text = "${ingredient.name}     b :${ingredient.protein} / w: ${ingredient.carbohydrates} / t: ${ingredient.fats}"
        )
    }
}