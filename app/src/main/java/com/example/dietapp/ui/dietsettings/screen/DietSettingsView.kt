package com.example.dietapp.ui.dietsettings.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
    OutlinedTextField(
        value = dietSettingsDetails.totalKcal,
        onValueChange = { onValueChange(dietSettingsDetails.copy(totalKcal = it)) },
        label = { Text("totalKcal") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = dietSettingsDetails.kcalFromFruits,
        onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromFruits = it)) },
        label = { Text("kcalFromFruits") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = dietSettingsDetails.kcalFromVegetables,
        onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromVegetables = it)) },
        label = { Text("kcalFromVegetables") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = dietSettingsDetails.kcalFromProteinSource,
        onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromProteinSource = it)) },
        label = { Text("kcalFromProteinSource") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = dietSettingsDetails.kcalFromMilkProducts,
        onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromMilkProducts = it)) },
        label = { Text("kcalFromMilkProducts") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = dietSettingsDetails.kcalFromGrain,
        onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromGrain = it)) },
        label = { Text("kcalFromGrain") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = dietSettingsDetails.kcalFromAddedFat,
        onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromAddedFat = it)) },
        label = { Text("kcalFromAddedFat") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = dietSettingsDetails.protein,
        onValueChange = { onValueChange(dietSettingsDetails.copy(protein = it)) },
        label = { Text("protein") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = dietSettingsDetails.carbohydrates,
        onValueChange = { onValueChange(dietSettingsDetails.copy(carbohydrates = it)) },
        label = { Text("carbohydrates") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = dietSettingsDetails.fats,
        onValueChange = { onValueChange(dietSettingsDetails.copy(fats = it)) },
        label = { Text("fats") },
        enabled = true,
        singleLine = true
    )
    OutlinedTextField(
        value = dietSettingsDetails.fiber,
        onValueChange = { onValueChange(dietSettingsDetails.copy(fiber = it)) },
        label = { Text("fiber") },
        enabled = true,
        singleLine = true
    )
//    OutlinedTextField(
//        value = dietSettingsDetails.polyunsaturatedFats,
//        onValueChange = { onValueChange(dietSettingsDetails.copy(polyunsaturatedFats = it)) },
//        label = { Text("polyunsaturatedFats") },
//        enabled = true,
//        singleLine = true
//    )
//    OutlinedTextField(
//        value = dietSettingsDetails.soil,
//        onValueChange = { onValueChange(dietSettingsDetails.copy(soil = it)) },
//        label = { Text("soil") },
//        enabled = true,
//        singleLine = true
//    )
//    OutlinedTextField(
//        value = dietSettingsDetails.fiber,
//        onValueChange = { onValueChange(dietSettingsDetails.copy(fiber = it)) },
//        label = { Text("fiber") },
//        enabled = true,
//        singleLine = true
//    )
}