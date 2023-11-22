package com.example.dietapp.ui.dish.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietapp.ui.common.DietSettingsStatistic
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import com.example.dietapp.ui.dish.viewmodel.DishViewModel

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
        IngredientList(Modifier.weight(4F), dishViewModel)
        Box(modifier = Modifier.weight(1f)) {
            DietSettingsStatistic(
                viewModel = dishViewModel,
                dietSettingsViewModel = dietSettingsViewModel
            )
        }

        Box(modifier = Modifier.weight(1f))
        {
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
fun IngredientList(
    modifier: Modifier,
    dishViewModel: DishViewModel
) {
    LazyColumn(modifier = modifier) {
        items(dishViewModel.dishWithIngredientsUiState.dishDetails.ingredientList) { ingredient ->
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
}