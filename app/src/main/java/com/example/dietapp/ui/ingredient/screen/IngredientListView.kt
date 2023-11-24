package com.example.dietapp.ui.ingredient.screen

import android.annotation.SuppressLint
import android.graphics.Paint.Style
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dietapp.data.Ingredient
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
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
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FoodCategory.values().forEach { foodCategory ->
            if (ingredientUiState.ingredientList.any { it.foodCategory == foodCategory }) item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dp(20f), Dp(0f)),
                    text = "${foodCategory}",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Left,
                    fontStyle = FontStyle.Italic,
                )
            }
            items(ingredientUiState.ingredientList.sortedBy { it.name }
                .filter { it.foodCategory == foodCategory }) { ingredient ->
                IngredientItem(
                    ingredient = ingredient,
                    onItemClick = onListItemClick
                )
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient, onItemClick: (IngredientDetails) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(7.dp)
        .clickable { onItemClick(ingredient.toIngredientDetails()) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.weight(2f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                text = ingredient.name
            )
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "p: ${ingredient.protein}",
                        modifier = Modifier
                            .padding(Dp(1F)),
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "c: ${ingredient.carbohydrates}",
                        modifier = Modifier
                            .padding(Dp(1F)),
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "f: ${ingredient.fats}",
                        modifier = Modifier
                            .padding(Dp(1F)),
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Row {

                    Text(
                        text = "kcal: ${ingredient.totalKcal}",
                        modifier = Modifier
                            .padding(Dp(1F)),
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

        }
    }
}