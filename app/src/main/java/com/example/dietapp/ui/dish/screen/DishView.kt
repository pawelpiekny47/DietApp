package com.example.dietapp.ui.dish.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietapp.R
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.common.DietSettingsStatistic
import com.example.dietapp.ui.common.MacroDetailsUnderIngredient
import com.example.dietapp.ui.common.MacroDetailsUnderIngredientXAmount
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import com.example.dietapp.ui.dish.viewmodel.DishViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishView(
    saveButtonOnClick: () -> Unit,
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel,
    deleteButtonVisible: Boolean,
    deleteButtonOnClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(Dp(2F)),
    ) {
        Box(modifier = Modifier.weight(1f)) {
            DietSettingsStatistic(
                viewModel = dishViewModel,
                dietSettingsViewModel = dietSettingsViewModel
            )
        }
        Box(contentAlignment = Alignment.Center) {
            TextField(
                modifier = Modifier.width(IntrinsicSize.Min),
                value = dishViewModel.dishWithIngredientsUiState.dishWithIngredientsDetails.dishDetails.name,
                onValueChange = { dishViewModel.updateDishName(it) },
                label = { Text("name") },
                enabled = true,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)

            )
        }
        IngredientList(
            Modifier
                .weight(4F)
                .fillMaxSize(), dishViewModel
        )
        Box(modifier = Modifier.weight(1.5f), contentAlignment = Alignment.TopCenter)
        {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                Button(
                    onClick = saveButtonOnClick,
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text(text = "Save")
                }
                if (deleteButtonVisible) {
                    Button(
                        onClick = deleteButtonOnClick,
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientList(
    modifier: Modifier,
    dishViewModel: DishViewModel
) {
    var extended by remember { mutableStateOf(0) }
    LazyColumn(modifier = modifier) {
        items(dishViewModel.dishWithIngredientsUiState.dishWithIngredientsDetails.ingredientList) { ingredient ->
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(3F)
                        .padding(10.dp, 0.dp)
                        .animateContentSize()
                        .clickable {
                            extended = ++extended
                            if (extended == 3) extended = 0
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(10.dp, 10.dp),
                            painter = when (ingredient.ingredientDetails.foodCategory) {
                                FoodCategory.Fruit -> painterResource(R.drawable.banana)
                                FoodCategory.Vegetable -> painterResource(R.drawable.lettuce)
                                FoodCategory.MilkAndReplacement -> painterResource(R.drawable.milk)
                                FoodCategory.AddedFat -> painterResource(R.drawable.oliveoil)
                                FoodCategory.Wheet -> painterResource(R.drawable.wheat)
                                FoodCategory.ProteinSource -> painterResource(R.drawable.meat)
                            },
                            contentDescription = null,
                            tint = when (ingredient.ingredientDetails.foodCategory) {
                                FoodCategory.Fruit -> com.example.dietapp.ui.theme.lightFruit
                                FoodCategory.Vegetable -> com.example.dietapp.ui.theme.lightVegetable
                                FoodCategory.MilkAndReplacement -> com.example.dietapp.ui.theme.lightMilk
                                FoodCategory.AddedFat -> com.example.dietapp.ui.theme.lightAddedFat
                                FoodCategory.Wheet -> com.example.dietapp.ui.theme.lightGrain
                                FoodCategory.ProteinSource -> com.example.dietapp.ui.theme.lightProteinSource
                            }
                        )
                        Text(
                            text = "   ${ingredient.ingredientDetails.name}",
                            fontSize = 14.sp,
                        )
                    }
                    if (extended == 1) MacroDetailsUnderIngredient(ingredient)
                    if (extended == 2) MacroDetailsUnderIngredientXAmount(ingredient)
                }
                Row(
                    modifier = Modifier
                        .weight(1F),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.weight(3F)
                    ) {
                        BasicTextField(
                            textStyle = TextStyle(fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface),
                            modifier = Modifier
                                .width(IntrinsicSize.Min),
                            value = ingredient.amount,
                            onValueChange = {
                                dishViewModel.updateDishWithIngredientUiState(
                                    ingredient.ingredientDetails.id,
                                    it
                                )
                            },
                            enabled = true,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true
                        )
                        Text(
                            text = " g",
                            fontSize = 12.sp,
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "delete",
                        modifier = Modifier
                            .scale(0.6F)
                            .clickable {
                                dishViewModel.deleteIngredientFromDish(
                                    ingredient
                                )
                            }
                    )
                }
            }
        }
    }
}



