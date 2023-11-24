package com.example.dietapp.ui.dietsettings.screen

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsDetails
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DietSettingsView(
    viewModel: DietSettingsViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    Column {
        DietSettingsForm(
            dietSettingsDetails = viewModel.dietSettingsUiState.dietSettingsDetails,
            onValueChange = viewModel::updateDietSettingsUiState
        )
        Row()
        {
            Button(
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) { viewModel.saveDietSettingsInDatabase() }
                },
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = "Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietSettingsForm(
    dietSettingsDetails: DietSettingsDetails,
    onValueChange: (DietSettingsDetails) -> Unit,
) {
    LazyColumn {

        item {
            TextField(
                value = dietSettingsDetails.totalKcal,
                onValueChange = { onValueChange(dietSettingsDetails.copy(totalKcal = it)) },
                label = { Text("totalKcal") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.kcalFromFruits,
                onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromFruits = it)) },
                label = { Text("kcalFromFruits") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)

            )
        }
        item {
            TextField(
                value = dietSettingsDetails.kcalFromVegetables,
                onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromVegetables = it)) },
                label = { Text("kcalFromVegetables") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.kcalFromProteinSource,
                onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromProteinSource = it)) },
                label = { Text("kcalFromProteinSource") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.kcalFromMilkProducts,
                onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromMilkProducts = it)) },
                label = { Text("kcalFromMilkProducts") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.kcalFromGrain,
                onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromGrain = it)) },
                label = { Text("kcalFromGrain") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.kcalFromAddedFat,
                onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromAddedFat = it)) },
                label = { Text("kcalFromAddedFat") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.protein,
                onValueChange = { onValueChange(dietSettingsDetails.copy(protein = it)) },
                label = { Text("protein") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.carbohydrates,
                onValueChange = { onValueChange(dietSettingsDetails.copy(carbohydrates = it)) },
                label = { Text("carbohydrates") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.fats,
                onValueChange = { onValueChange(dietSettingsDetails.copy(fats = it)) },
                label = { Text("fats") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.fiber,
                onValueChange = { onValueChange(dietSettingsDetails.copy(fiber = it)) },
                label = { Text("fiber") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.polyunsaturatedFats,
                onValueChange = { onValueChange(dietSettingsDetails.copy(polyunsaturatedFats = it)) },
                label = { Text("polyunsaturatedFats") },
                enabled = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.soil,
                onValueChange = { onValueChange(dietSettingsDetails.copy(soil = it)) },
                label = { Text("soil") },
                enabled = true,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        item {
            TextField(
                value = dietSettingsDetails.fiber,
                onValueChange = { onValueChange(dietSettingsDetails.copy(fiber = it)) },
                label = { Text("fiber") },
                enabled = true,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
    }

}