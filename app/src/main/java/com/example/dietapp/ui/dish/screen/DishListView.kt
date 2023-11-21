package com.example.dietapp.ui.dish.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.dietapp.data.DishWithIngredients
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import com.example.dietapp.ui.dish.viewmodel.DishWithIngredientsDetails
import com.example.dietapp.ui.dish.viewmodel.toDishWithIngredientDetails
import java.math.RoundingMode


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DishListView(
    onItemClick: (DishWithIngredientsDetails) -> Unit,
    viewModel: DishViewModel
) {
    val dishUiState by viewModel.dishListUiState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        .fillMaxWidth()
        .padding(Dp(5F))
        .clickable { onItemClick(dish.toDishWithIngredientDetails()) }) {

        Row {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                text = dish.dish.name
            )
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "p: ${
                    dish.ingredientList.sumOf { it.ingredient.protein * it.amount / 100 }
                        .toBigDecimal().setScale(
                        2,
                        RoundingMode.HALF_DOWN
                    ).toDouble()
                }",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "c: ${dish.ingredientList.sumOf { it.ingredient.carbohydrates * it.amount / 100 }.toBigDecimal().setScale(
                    2,
                    RoundingMode.HALF_DOWN
                ).toDouble()}",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "f: ${dish.ingredientList.sumOf { it.ingredient.fats * it.amount / 100 }.toBigDecimal().setScale(
                    2,
                    RoundingMode.HALF_DOWN
                ).toDouble()}",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "kcal: ${dish.ingredientList.sumOf { it.ingredient.totalKcal * it.amount / 100 }}",
                modifier = Modifier
                    .padding(Dp(5F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}