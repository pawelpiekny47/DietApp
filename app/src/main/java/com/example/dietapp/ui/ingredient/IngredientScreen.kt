package com.example.dietapp.ui.ingredient

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dietapp.data.Ingredient
import com.example.dietapp.ui.AppViewModelProvider
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

@Composable
fun IngredientScreen(
    ingredient: Ingredient?,
    visibleCancelButton: Boolean,
    modifier: Modifier,
    viewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    if (ingredient != null) {
        viewModel.updateUiState(ingredient.toIngredientDetails())
    }
    val ingredientUiState = viewModel.ingredientUiState
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
    ) {
        IngredientForm(
            ingredientUiState.ingredientDetails,
            viewModel::updateUiState
        )

        Row()
        {
            if (visibleCancelButton) {
                Button(
                    onClick = { coroutineScope.launch { viewModel.saveItem() } },
                    shape = MaterialTheme.shapes.small,
                ) {
                    Text(text = "Cancel")
                }
            }
            Button(
                onClick = { coroutineScope.launch { viewModel.saveItem() } },
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = "Save")
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