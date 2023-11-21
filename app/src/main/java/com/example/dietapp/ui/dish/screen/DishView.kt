package com.example.dietapp.ui.dish.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import java.math.RoundingMode
import java.util.stream.Collectors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishView(
    saveButtonOnClick: () -> Unit,
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(Dp(5F)),
    ) {
        OutlinedTextField(
            value = dishViewModel.dishWithIngredientsUiState.dishDetails.dish.name,
            onValueChange = { dishViewModel.updateDishName(it) },
            label = { Text("name") },
            enabled = true,
            singleLine = true
        )
        IngredientList(
            dishViewModel,
            dietSettingsViewModel
        )
        Box(modifier = Modifier.weight(2f))
        DietSettingsStatistic(
            dishViewModel = dishViewModel,
            dietSettingsViewModel = dietSettingsViewModel
        )
        Box(modifier = Modifier.weight(1f))
        Button(
            onClick = saveButtonOnClick,
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = "Save")
        }
        Box(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientList(
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList.forEach { ingredient ->
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier.weight(2F),
                text = ingredient.ingredientDetails.name,
                fontSize = 15.sp,
            )
            Row(
                modifier = Modifier
                    .weight(1F),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.weight(3F)
                ) {
                    TextField(
                        textStyle = TextStyle(fontSize = 15.sp),
                        modifier = Modifier
                            .weight(5F)
                            .defaultMinSize(minHeight = 10.dp),
                        value = ingredient.amount,
                        onValueChange = {
                            dishViewModel.updateDishWithIngredientUiState(
                                ingredient.ingredientDetails.id,
                                it
                            )
                        },
                        enabled = true,
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                    )
                    Text(
                        modifier = Modifier
                            .defaultMinSize(minWidth = 1.dp)
                            .weight(1F),
                        text = " g",
                        fontSize = 12.sp,
                    )
                }
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "delete",
                    modifier = Modifier
                        .weight(1F)
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


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DietSettingsStatistic(
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = lazyListState,
        flingBehavior = flingBehavior
    ) {
        item {
            Column(modifier = Modifier.fillParentMaxWidth()) {
                BasicStatistics(dishViewModel, dietSettingsViewModel)
            }
        }
        item {
            Column(modifier = Modifier.fillParentMaxWidth()) {
                FoodTypeStatistics(dishViewModel, dietSettingsViewModel)
            }
        }
        item {
            Column(modifier = Modifier.fillParentMaxWidth()) {
                AdditionalInfo(dishViewModel, dietSettingsViewModel)
            }
        }
    }
}


@Composable
fun BasicStatistics(
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "kcal: ${dishViewModel.returnTotalKcal()} / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal}",
                    fontSize = 10.sp
                )
                Text(
                    text = "protein: ${dishViewModel.returnTotalProtein()} / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein}",
                    fontSize = 10.sp
                )
                Text(
                    text = "carbs: ${dishViewModel.returnTotalCarbs()} / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates}",
                    fontSize = 10.sp
                )
                Text(
                    text = "fat: ${dishViewModel.returnTotalFat()} / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats}",
                    fontSize = 10.sp
                )
            }
        }
        CircularBasicStatistics(dishViewModel)
    }

}

@Composable
fun FoodTypeStatistics(
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "fruit: ${dishViewModel.returnTotalKcalForFoodCategory(FoodCategory.Fruit)} )/ ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromFruits}",
                    fontSize = 10.sp
                )
                Text(
                    text = "vegetable: ${
                        dishViewModel.returnTotalKcalForFoodCategory(
                            FoodCategory.Vegetable
                        )
                    } / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromVegetables}",
                    fontSize = 10.sp
                )
                Text(
                    text = "protein source: ${
                        dishViewModel.returnTotalKcalForFoodCategory(
                            FoodCategory.ProteinSource
                        )
                    } / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromProteinSource}",
                    fontSize = 10.sp
                )
                Text(
                    text = "milk: ${dishViewModel.returnTotalKcalForFoodCategory(FoodCategory.MilkAndReplacement)} / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromMilkProducts}",
                    fontSize = 10.sp
                )
                Text(
                    text = "grains: ${dishViewModel.returnTotalKcalForFoodCategory(FoodCategory.Wheet)} / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromGrain}",
                    fontSize = 10.sp
                )
                Text(
                    text = "added fats: ${
                        dishViewModel.returnTotalKcalForFoodCategory(
                            FoodCategory.AddedFat
                        )
                    } / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromAddedFat}",
                    fontSize = 10.sp
                )
            }
        }
        CircularFoodTypeStatistics(dishViewModel)
    }

}

@Composable
fun AdditionalInfo(
    dishViewModel: DishViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    Text(
        text = "pufa: ${dishViewModel.returnTotalPufa()} / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.polyunsaturatedFats}",
        fontSize = 10.sp
    )
    Text(
        text = "salt: ${dishViewModel.returnTotalSoil()} / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.soil}",
        fontSize = 10.sp
    )
    Text(
        text = "fiber: ${dishViewModel.returnTotalFiber()} / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fiber}",
        fontSize = 10.sp
    )
}

@Composable
fun CircularBasicStatistics(
    dishViewModel: DishViewModel
) {
    val total =
        dishViewModel.returnTotalProtein() * 4 + dishViewModel.returnTotalFat() * 9 + dishViewModel.returnTotalCarbs() * 4
    val fatProgress = dishViewModel.returnTotalFat() * 9 / total
    val carbsProgress = dishViewModel.returnTotalCarbs() * 4 / total
    val proteinProgress = dishViewModel.returnTotalProtein() * 4 / total
    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(3.dp, 3.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = dishViewModel.returnTotalKcal().toString(), fontSize = 10.sp)
            Text(text = "kcal", fontSize = 8.sp)
        }
        CircularProgressIndicator(
            modifier = Modifier,
            progress = (proteinProgress.toFloat()),
            color = Color.Black
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(proteinProgress.toFloat() * 360),
            progress = (carbsProgress.toFloat()),
            color = Color.Red
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((carbsProgress + proteinProgress).toFloat() * 360),
            progress = (fatProgress.toFloat()),
            color = Color.Green
        )
    }
}

@Composable
fun CircularFoodTypeStatistics(
    dishViewModel: DishViewModel
) {
    val total = FoodCategory.values().sumOf { dishViewModel.returnTotalKcalForFoodCategory(it) }
    val fruitProgress = dishViewModel.returnTotalKcalForFoodCategory(FoodCategory.Fruit) / total
    val vegetableProgress =
        dishViewModel.returnTotalKcalForFoodCategory(FoodCategory.Vegetable) / total
    val proteinSourceProgress =
        dishViewModel.returnTotalKcalForFoodCategory(FoodCategory.ProteinSource) / total
    val grainProgress = dishViewModel.returnTotalKcalForFoodCategory(FoodCategory.Wheet) / total
    val milkProgress =
        dishViewModel.returnTotalKcalForFoodCategory(FoodCategory.MilkAndReplacement) / total
    val addedFatProgress =
        dishViewModel.returnTotalKcalForFoodCategory(FoodCategory.AddedFat) / total

    Box(contentAlignment = Alignment.Center) {

        CircularProgressIndicator(
            modifier = Modifier,
            progress = (fruitProgress.toFloat()),
            color = Color.Black
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(fruitProgress.toFloat() * 360),
            progress = (vegetableProgress.toFloat()),
            color = Color.Red
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((fruitProgress + vegetableProgress).toFloat() * 360),
            progress = (proteinSourceProgress.toFloat()),
            color = Color.Green
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((fruitProgress + vegetableProgress + proteinSourceProgress).toFloat() * 360),
            progress = (grainProgress.toFloat()),
            color = Color.DarkGray
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((fruitProgress + vegetableProgress + proteinSourceProgress + grainProgress).toFloat() * 360),
            progress = (milkProgress.toFloat()),
            color = Color.Cyan
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((fruitProgress + vegetableProgress + proteinSourceProgress + grainProgress + milkProgress).toFloat() * 360),
            progress = (addedFatProgress.toFloat()),
            color = Color.Blue
        )
    }
}