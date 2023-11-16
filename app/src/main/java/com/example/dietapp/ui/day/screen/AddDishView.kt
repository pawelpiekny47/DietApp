package com.example.dietapp.ui.day.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.dish.screen.DishItem
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import com.example.dietapp.ui.dish.viewmodel.DishWithIngredientsDetails


@Composable
fun AddDish(
    onListItemClick: (DishWithIngredientsDetails) -> Unit,
    viewModel: DishViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val dishUiState by viewModel.dishListUiState.collectAsState()
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            items(dishUiState.dishList) { dish ->
                DishItem(
                    dish = dish,
                    onItemClick =  onListItemClick
                )
            }

    }
}