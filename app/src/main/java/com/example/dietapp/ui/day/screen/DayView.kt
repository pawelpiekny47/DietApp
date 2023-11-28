package com.example.dietapp.ui.day.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import com.example.dietapp.ui.common.DietSettingsStatistic
import com.example.dietapp.ui.common.MacroDetailsUnderIngredientXAmount
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.day.viewmodel.DishWithAmountDetails
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayView(
    saveButtonOnClick: () -> Unit,
    dayViewModel: DayViewModel,
    dietSettingsViewModel: DietSettingsViewModel,
    onAddIconClick: (dishWithAmountDetails: DishWithAmountDetails) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(Dp(10F)),
    ) {
        Box(modifier = Modifier.weight(1f)) {
            DietSettingsStatistic(
                viewModel = dayViewModel,
                dietSettingsViewModel = dietSettingsViewModel
            )
        }
        OutlinedTextField(
            value = dayViewModel.dayWithDishesUiState.dayWithDishesDetails.day.name,
            onValueChange = { dayViewModel.updateDayName(it) },
            label = { Text("name") },
            enabled = true,
            singleLine = true
        )
        DishList(Modifier.weight(4F), dayViewModel, onAddIconClick)
        Box(modifier = Modifier.weight(1f)) {
            Button(
                onClick = saveButtonOnClick,
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = "Save")
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


    LazyColumn(modifier = modifier) {
        items(dayViewModel.dayWithDishesUiState.dayWithDishesDetails.dishWithAmountDetails) { dish ->
            var extendedIngredients by remember { mutableStateOf(true) }
            var extendedIngredientsDetails by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier
                    .animateContentSize(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
            ) {

                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    Text(
                        modifier = Modifier
                            .weight(2F)
                            .clickable {
                                extendedIngredients = !extendedIngredients
                            },
                        style = MaterialTheme.typography.titleMedium,
                        text = dish.dishWithIngredientsDetails.dishDetails.name
                    )
                    Row(
                        modifier = Modifier
                            .weight(1F),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "delete",
                            modifier = Modifier
                                .weight(1F)
                                .clickable {
                                    dayViewModel.deleteDishFromDay(dish)
                                }
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
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.Bottom
                                    ) {


                                        Text(
                                            text = "- ${ingredientWithAmountDetails.ingredientDetails.name}  ",
                                            fontStyle = FontStyle.Italic,
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        BasicTextField(
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
                                            singleLine = true,
                                        )
                                        Text(
                                            text = " g   ",
                                            fontStyle = FontStyle.Italic,
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        Box(modifier = Modifier
                                            .scale(0.6F)
                                            .clickable {
                                                dayViewModel.removeIngredientFromDishInDay(
                                                    ingredientWithAmountDetails.ingredientDetails.id,
                                                    dish.dishWithIngredientsDetails.dishDetails.dishId.toInt()
                                                )
                                            }
                                            .width(IntrinsicSize.Min),
                                            contentAlignment = Alignment.BottomStart
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.Clear,
                                                contentDescription = "Back"
                                            )
                                        }
                                    }
                                    if (extendedIngredientsDetails)
                                        MacroDetailsUnderIngredientXAmount(
                                            ingredientWithAmountDetails
                                        )
                                }
                            }
                    }
                }
            }
            IconButton(onClick = { onAddIconClick(dish) }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Back"
                )
            }
        }
    }
}