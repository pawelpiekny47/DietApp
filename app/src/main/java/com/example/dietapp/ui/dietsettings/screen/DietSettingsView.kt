package com.example.dietapp.ui.dietsettings.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.dietapp.R
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsDetails
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DietSettingsView(
    viewModel: DietSettingsViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier.padding(20.dp)) {
        DietSettingsForm(
            dietSettingsDetails = viewModel.dietSettingsUiState.dietSettingsDetails,
            onValueChange = viewModel::updateDietSettingsUiState
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
        {Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    viewModel.generateValuesForGivenKcal()
                }
            },
            shape = MaterialTheme.shapes.small,
        ) {
            Text(text = "generate for given kcal")
        }
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
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.fire),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.totalKcal,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(totalKcal = it)) },
                    label = { Text("kcal") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.meat),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.protein,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(protein = it)) },
                    label = { Text("protein") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.wheat),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.carbohydrates,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(carbohydrates = it)) },
                    label = { Text("carbs") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.oilbottle),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.fats,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(fats = it)) },
                    label = { Text("fats") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.banana),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.kcalFromFruits,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromFruits = it)) },
                    label = { Text("fruits") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.lettuce),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.kcalFromVegetables,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromVegetables = it)) },
                    label = { Text("vegetables") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.meat),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.kcalFromProteinSource,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromProteinSource = it)) },
                    label = { Text("protein source") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.milk),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.kcalFromMilkProducts,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromMilkProducts = it)) },
                    label = { Text("milk") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.wheat),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.kcalFromGrain,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromGrain = it)) },
                    label = { Text("grain") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.oliveoil),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.kcalFromAddedFat,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(kcalFromAddedFat = it)) },
                    label = { Text("added fat") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.fiber),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.fiber,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(fiber = it)) },
                    label = { Text("fiber") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.oilfree),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.polyunsaturatedFats,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(polyunsaturatedFats = it)) },
                    label = { Text("saturated fat") },
                    enabled = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal,imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(10.dp, 10.dp),
                    painter = painterResource(R.drawable.salt),
                    contentDescription = null
                )
                TextField(
                    value = dietSettingsDetails.soil,
                    onValueChange = { onValueChange(dietSettingsDetails.copy(soil = it)) },
                    label = { Text("soil") },
                    enabled = true,
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
                )
            }
        }
    }
}
