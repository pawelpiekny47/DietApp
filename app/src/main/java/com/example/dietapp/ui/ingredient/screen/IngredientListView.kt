package com.example.dietapp.ui.ingredient.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dietapp.data.Ingredient
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietapp.R
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.ingredient.viewmodel.IngredientDetails
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import com.example.dietapp.ui.ingredient.viewmodel.toIngredientDetails

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun IngredientListScreen(
    onListItemClick: (IngredientDetails) -> Unit,
    viewModel: IngredientViewModel,
    filteredText: String
) {
    val ingredientUiState by viewModel.ingredientListUiState.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FoodCategory.values().forEach { foodCategory ->
            if (ingredientUiState.ingredientList.filter { it.name.contains(filteredText, true) }
                    .any { it.foodCategory == foodCategory })
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(50.dp, 10.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(17.dp, 17.dp),
                            painter = when (foodCategory) {
                                FoodCategory.Fruit -> painterResource(R.drawable.banana)
                                FoodCategory.Vegetable -> painterResource(R.drawable.lettuce)
                                FoodCategory.MilkAndReplacement -> painterResource(R.drawable.milk)
                                FoodCategory.AddedFat -> painterResource(R.drawable.oliveoil)
                                FoodCategory.Wheet -> painterResource(R.drawable.wheat)
                                FoodCategory.ProteinSource -> painterResource(R.drawable.meat)
                            },
                            contentDescription = null,
                            tint = when (foodCategory) {
                                FoodCategory.Fruit -> com.example.dietapp.ui.theme.lightFruit
                                FoodCategory.Vegetable -> com.example.dietapp.ui.theme.lightVegetable
                                FoodCategory.MilkAndReplacement -> com.example.dietapp.ui.theme.lightMilk
                                FoodCategory.AddedFat -> com.example.dietapp.ui.theme.lightAddedFat
                                FoodCategory.Wheet -> com.example.dietapp.ui.theme.lightGrain
                                FoodCategory.ProteinSource -> com.example.dietapp.ui.theme.lightProteinSource
                            }
                        )
                    }
                }

            items(ingredientUiState.ingredientList.filter { it.name.contains(filteredText, true) }.sortedBy { it.name }
                .filter { it.foodCategory == foodCategory }) { ingredient ->
                IngredientItem(
                    ingredient = ingredient,
                    onItemClick = onListItemClick
                )
                Divider(modifier = Modifier.padding(30.dp, 0.dp))
            }
        }
    }
}

@Composable
fun IngredientItem(ingredient: Ingredient, onItemClick: (IngredientDetails) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(7.dp)
        .clickable { onItemClick(ingredient.toIngredientDetails()) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 10.dp, 10.dp, 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 5.dp), verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    text = ingredient.name
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(10.dp, 10.dp),
                        painter = painterResource(R.drawable.fire),
                        contentDescription = null,
                        tint = com.example.dietapp.ui.theme.lightKcal
                    )
                    Text(
                        text = "${ingredient.totalKcal}   ",
                        modifier = Modifier
                            .padding(Dp(1F)),
                        fontStyle = FontStyle.Italic,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodySmall,
                        color = com.example.dietapp.ui.theme.lightKcal
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(10.dp, 10.dp),
                        painter = painterResource(R.drawable.meat),
                        contentDescription = null,
                        tint = com.example.dietapp.ui.theme.lightProtein
                    )
                    Text(
                        text = "${ingredient.protein}  ",
                        modifier = Modifier
                            .padding(Dp(1F)),
                        fontStyle = FontStyle.Italic,
                        fontSize = 12.sp,

                        style = MaterialTheme.typography.bodySmall,
                        color = com.example.dietapp.ui.theme.lightProtein
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(10.dp, 10.dp),
                        painter = painterResource(R.drawable.wheat),
                        contentDescription = null,
                        tint = com.example.dietapp.ui.theme.lightCarbs
                    )
                    Text(
                        text = "${ingredient.carbohydrates}  ",
                        modifier = Modifier
                            .padding(Dp(1F)),
                        fontStyle = FontStyle.Italic,
                        fontSize = 12.sp,

                        style = MaterialTheme.typography.bodySmall,
                        color = com.example.dietapp.ui.theme.lightCarbs
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(10.dp, 10.dp),
                        painter = painterResource(R.drawable.oilbottle),
                        contentDescription = null,
                        tint = com.example.dietapp.ui.theme.lightFats
                    )
                    Text(
                        text = "${ingredient.fats}  ",
                        modifier = Modifier
                            .padding(Dp(1F)),
                        fontStyle = FontStyle.Italic,
                        fontSize = 12.sp,

                        style = MaterialTheme.typography.bodySmall,
                        color = com.example.dietapp.ui.theme.lightFats
                    )

                }
            }

        }
    }
}