package com.example.dietapp.ui.ingredient.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.dietapp.barcode.PreviewViewComposable
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.ingredient.viewmodel.IngredientDetails
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import java.util.Currency
import java.util.Locale

@Composable
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
fun IngredientView(
    viewModel: IngredientViewModel,
    saveButton: () -> Unit,
    deleteButtonVisible: Boolean,
    deleteButtonOnClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column() {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "delete",
            modifier = Modifier
                .clickable {
                    expanded = !expanded
                }
        )
        if (expanded) {
            Column {
                PreviewViewComposable(viewModel)
            }
        }

        IngredientForm(
            ingredientDetailsState = viewModel.ingredientUiState.ingredientDetails,
            onValueChange = viewModel::updateUiState
        )
        FoodCategoryDropdownMenu(
            ingredientDetailsState = viewModel.ingredientUiState.ingredientDetails,
            onValueChange = viewModel::updateUiState
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dp(40F)),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Button(
                onClick = saveButton,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientForm(
    ingredientDetailsState: IngredientDetails,
    onValueChange: (IngredientDetails) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dp(10F)),
        value = ingredientDetailsState.name,
        onValueChange = { onValueChange(ingredientDetailsState.copy(name = it)) },
        label = { Text("name") },
        enabled = true,
        singleLine = true
    )
    Row(modifier = Modifier.defaultMinSize(minHeight = 48.dp)) {

        TextField(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .weight(1F)
                .padding(Dp(10F)),
            value = ingredientDetailsState.protein,
            onValueChange = { onValueChange(ingredientDetailsState.copy(protein = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text("proteins") },
            enabled = true,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )
        TextField(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .weight(1F)
                .padding(Dp(10F)),
            value = ingredientDetailsState.carbohydrates,
            onValueChange = { onValueChange(ingredientDetailsState.copy(carbohydrates = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text("carbs") },
            enabled = true,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )
        TextField(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .weight(1F)
                .padding(Dp(10F)),
            value = ingredientDetailsState.fats,
            onValueChange = { onValueChange(ingredientDetailsState.copy(fats = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text("fats") },
            enabled = true,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )
    }
    Row() {
        TextField(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .weight(1F)
                .padding(Dp(10F)),
            value = ingredientDetailsState.totalKcal,
            onValueChange = { onValueChange(ingredientDetailsState.copy(totalKcal = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(text = "kcal") },
            enabled = true,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )
        Box(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .weight(2F)
        ) { }
    }
    Row() {
        TextField(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .weight(1F)
                .padding(Dp(10F)),
            value = ingredientDetailsState.polyunsaturatedFats,
            onValueChange = { onValueChange(ingredientDetailsState.copy(polyunsaturatedFats = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(text = "PUFA") },
            enabled = true,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )
        TextField(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .weight(1F)
                .padding(Dp(10F)),
            value = ingredientDetailsState.soil,
            onValueChange = { onValueChange(ingredientDetailsState.copy(soil = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text("soil") },
            enabled = true,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )
        TextField(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .weight(1F)
                .padding(Dp(10F)),
            value = ingredientDetailsState.fiber,
            onValueChange = { onValueChange(ingredientDetailsState.copy(fiber = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text("fiber") },
            enabled = true,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCategoryDropdownMenu(
    ingredientDetailsState: IngredientDetails,
    onValueChange: (IngredientDetails) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .defaultMinSize(minHeight = 48.dp)
            .fillMaxWidth()
            .padding(Dp(10F)),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .defaultMinSize(minHeight = 48.dp)
                .weight(1F)
        ) { }
        ExposedDropdownMenuBox(
            modifier = Modifier
                .weight(2F),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            TextField(
                readOnly = true,
                modifier = Modifier.menuAnchor(),
                value = ingredientDetailsState.foodCategory.name,
                onValueChange = { },
                label = { Text("Food Category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            )
            {
                FoodCategory.values().toList().forEach { label ->
                    DropdownMenuItem(
                        text = { Text(text = label.name) },
                        onClick = {
                            expanded = false
                            onValueChange(
                                ingredientDetailsState.copy(
                                    foodCategory = FoodCategory.valueOf(label.name)
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}