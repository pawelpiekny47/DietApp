package com.example.dietapp.ui.ingredient.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.ingredient.viewmodel.IngredientDetails
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import java.util.Currency
import java.util.Locale

@Composable
fun IngredientView(
    modifier: Modifier,
    viewModel: IngredientViewModel,
    saveButton: () -> Unit,
    deleteButtonVisible: Boolean,
    deleteButtonOnClick: () -> Unit
) {

    Column(
        modifier = modifier
    ) {
        IngredientForm(
            viewModel.ingredientUiState.ingredientDetails,
            viewModel::updateUiState
        )
        FoodCategoryDropdownMenu(
            viewModel.ingredientUiState.ingredientDetails,
            viewModel::updateUiState
        )
        Row()
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
        value = ingredientDetailsState.name,
        onValueChange = { onValueChange(ingredientDetailsState.copy(name = it)) },
        label = { Text("name") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = ingredientDetailsState.protein,
        onValueChange = { onValueChange(ingredientDetailsState.copy(protein = it)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text("proteins") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = ingredientDetailsState.carbohydrates,
        onValueChange = { onValueChange(ingredientDetailsState.copy(carbohydrates = it)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text("carbohydrates") },
        leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = ingredientDetailsState.fats,
        onValueChange = { onValueChange(ingredientDetailsState.copy(fats = it)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text("fats") },
        leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
        enabled = true,
        singleLine = true
    )

    OutlinedTextField(
        value = ingredientDetailsState.polyunsaturatedFats,
        onValueChange = { onValueChange(ingredientDetailsState.copy(polyunsaturatedFats = it)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text("polyunsaturatedFats") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = ingredientDetailsState.soil,
        onValueChange = { onValueChange(ingredientDetailsState.copy(soil = it)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text("soil") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = ingredientDetailsState.fiber,
        onValueChange = { onValueChange(ingredientDetailsState.copy(fiber = it)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        label = { Text("fiber") },
        enabled = true,
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodCategoryDropdownMenu(
    ingredientDetailsState: IngredientDetails,
    onValueChange: (IngredientDetails) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
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
            colors = ExposedDropdownMenuDefaults.textFieldColors()
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