package com.example.dietapp.ui.day.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dietapp.R
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.ingredient.screen.IngredientItem
import com.example.dietapp.ui.ingredient.viewmodel.IngredientDetails
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import com.example.dietapp.ui.theme.lightAddedFat
import com.example.dietapp.ui.theme.lightFruit
import com.example.dietapp.ui.theme.lightGrain
import com.example.dietapp.ui.theme.lightMilk
import com.example.dietapp.ui.theme.lightProteinSource
import com.example.dietapp.ui.theme.lightVegetable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddIngredientForDayView(
    onListItemClick: (IngredientDetails) -> Unit,
    ingredientViewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory),
    filteredText: String
) {
    val ingredientUiState by ingredientViewModel.ingredientListUiState.collectAsState()
    Column {
        Box(modifier = Modifier.weight(5F)) {
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
                                        FoodCategory.Fruit -> lightFruit
                                        FoodCategory.Vegetable -> lightVegetable
                                        FoodCategory.MilkAndReplacement -> lightMilk
                                        FoodCategory.AddedFat -> lightAddedFat
                                        FoodCategory.Wheet -> lightGrain
                                        FoodCategory.ProteinSource -> lightProteinSource
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
    }
}