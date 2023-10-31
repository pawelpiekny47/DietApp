package com.example.dietapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dietapp.data.Ingredient
import androidx.compose.foundation.lazy.items
import com.example.dietapp.data.FoodCategory

@Composable
fun IngredientScreen(ingredients: List<Ingredient>) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FoodCategory.values().forEach { foodCategory ->
            if (ingredients.any { it.foodCategory == foodCategory }) item {
                Text(
                    text = "${foodCategory}"
                )
            }
            items(ingredients.filter { it.foodCategory == foodCategory }) { ingredient ->
                IngredientItem(
                    ingredient = ingredient
                )
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient) {
    Card {
        Text(text = "${ingredient.name}     b :${ingredient.protein} / w: ${ingredient.carbohydrates} / t: ${ingredient.fats}")
    }
}
