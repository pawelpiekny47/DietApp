package com.example.dietapp.ui.day.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import com.example.dietapp.ui.dish.screen.DishItem
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import com.example.dietapp.ui.dish.viewmodel.DishWithIngredientsDetails


@Composable
fun AddDish(
    onListItemClick: (DishWithIngredientsDetails) -> Unit,
    viewModel: DishViewModel = viewModel(factory = AppViewModelProvider.Factory),
    dietSettingsViewModel: DietSettingsViewModel,
) {
    val dishUiState by viewModel.dishListUiState.collectAsState()
    Column {
        Box(modifier = Modifier.weight(5F)) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(dishUiState.dishList.filter { it.dish.baseDish == 1 }) { dish ->
                    DishItem(
                        dish = dish,
                        dietSettingsViewModel = dietSettingsViewModel,
                        onItemClick = onListItemClick
                    )
                }
            }
        }
    }
}