package com.example.dietapp.ui.day.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.items
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietapp.R
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.common.MacroDetailsUnderIngredientXAmount
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.day.viewmodel.DishWithAmountDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayView(
    saveButtonOnClick: () -> Unit,
    deleteButtonOnClick: () -> Unit,
    deleteButtonVisible: Boolean,
    dayViewModel: DayViewModel,
    onAddIconClick: (dishWithAmountDetails: DishWithAmountDetails) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(Dp(10F)),
    ) {
        OutlinedTextField(
            value = dayViewModel.dayWithDishesUiState.dayWithDishesDetails.day.name,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
            onValueChange = { dayViewModel.updateDayName(it) },
            label = { Text("name") },
            enabled = true,
            singleLine = true
        )
        DishList(Modifier.weight(4F), dayViewModel, onAddIconClick)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = saveButtonOnClick,
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text(text = "Save")
                }
                if (deleteButtonVisible)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishList(
    modifier: Modifier,
    dayViewModel: DayViewModel,
    onAddIconClick: (dishWithAmountDetails: DishWithAmountDetails) -> Unit
) {
    val focusManager = LocalFocusManager.current
    LazyColumn(modifier = modifier) {
        items(dayViewModel.dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails) { dish ->
            var extendedIngredients by remember { mutableStateOf(true) }
            var extendedIngredientsDetails by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {

                Row(
                    modifier = Modifier.fillParentMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Text(
                        modifier = Modifier
                            .clickable {
                                extendedIngredients = !extendedIngredients
                            },
                        style = MaterialTheme.typography.titleMedium,
                        text = dish.dishWithIngredientsDetails.dishDetails.name
                    )
                    Row(
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "delete",
                            modifier = Modifier
                                .clickable {
                                    dayViewModel.deleteDishFromDay(dish)
                                }
                                .scale(0.7F)
                        )
                    }
                }
                if (extendedIngredients) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            extendedIngredientsDetails = !extendedIngredientsDetails
                        }) {
                        dish.dishWithIngredientsDetails.ingredientList.sortedBy { it.ingredientDetails.name }
                            .forEach { ingredientWithAmountDetails ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(10.dp, 0.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        modifier = Modifier.size(10.dp, 10.dp),
                                        painter = when (ingredientWithAmountDetails.ingredientDetails.foodCategory) {
                                            FoodCategory.Fruit -> painterResource(R.drawable.banana)
                                            FoodCategory.Vegetable -> painterResource(R.drawable.lettuce)
                                            FoodCategory.MilkAndReplacement -> painterResource(
                                                R.drawable.milk
                                            )

                                            FoodCategory.AddedFat -> painterResource(R.drawable.oliveoil)
                                            FoodCategory.Wheet -> painterResource(R.drawable.wheat)
                                            FoodCategory.ProteinSource -> painterResource(R.drawable.meat)
                                        },
                                        contentDescription = null,
                                        tint = when (ingredientWithAmountDetails.ingredientDetails.foodCategory) {
                                            FoodCategory.Fruit -> com.example.dietapp.ui.theme.lightFruit
                                            FoodCategory.Vegetable -> com.example.dietapp.ui.theme.lightVegetable
                                            FoodCategory.MilkAndReplacement -> com.example.dietapp.ui.theme.lightMilk
                                            FoodCategory.AddedFat -> com.example.dietapp.ui.theme.lightAddedFat
                                            FoodCategory.Wheet -> com.example.dietapp.ui.theme.lightGrain
                                            FoodCategory.ProteinSource -> com.example.dietapp.ui.theme.lightProteinSource
                                        }
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(10.dp, 0.dp)
                                            .weight(4F)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${ingredientWithAmountDetails.ingredientDetails.name}  ",
                                                fontStyle = FontStyle.Italic,
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.bodySmall
                                            )
                                        }
                                        if (extendedIngredientsDetails)
                                            MacroDetailsUnderIngredientXAmount(
                                                ingredientWithAmountDetails
                                            )
                                    }

                                    Row(
                                        verticalAlignment = Alignment.Bottom,
                                        horizontalArrangement = Arrangement.End,
                                        modifier = Modifier.weight(1F)
                                    ) {
                                        BasicTextField(
                                            textStyle = TextStyle(
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurface
                                            ),
                                            modifier = Modifier
                                                .width(IntrinsicSize.Min),
                                            value = ingredientWithAmountDetails.amount,
                                            onValueChange = {
                                                dayViewModel.updateDayUiState(
                                                    ingredientWithAmountDetails,
                                                    dish.dishWithIngredientsDetails.dishDetails.dishId,
                                                    it
                                                )
                                            },
                                            enabled = true,
                                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                                            keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                                            singleLine = true,
                                        )
                                        Text(
                                            text = " g",
                                            fontStyle = FontStyle.Italic,
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    }
                                    Icon(
                                        modifier = Modifier
                                            .scale(0.6F)
                                            .clickable {
                                                dayViewModel.removeIngredientFromDishInDay(
                                                    ingredientWithAmountDetails.ingredientDetails.id,
                                                    dish.dishWithIngredientsDetails.dishDetails.dishId.toInt()
                                                )
                                            },
                                        imageVector = Icons.Outlined.Clear,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                    }
                }

            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                IconButton(onClick = { onAddIconClick(dish) }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Back"
                    )
                }
            }
        }
    }
}