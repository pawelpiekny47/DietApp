package com.example.dietapp.ui.dish.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.dish.screen.DishListView
import com.example.dietapp.ui.dish.screen.DishScreenList
import com.example.dietapp.ui.dish.screen.DishView
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import com.example.dietapp.ui.ingredient.screen.IngredientScreenList

@Composable
fun DishNavHost(
    viewModel: DishViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = DishScreenList.DishList.name,
    ) {
        composable(route = DishScreenList.DishList.name) {
            DishListView(
                onItemClick = { dishDetails ->
                    viewModel.updateUiState(dishDetails)
                    viewModel.deleteButtonVisible = true
                    navController.navigate(DishScreenList.Dish.name)
                },
                viewModel
            )
        }
        composable(route = DishScreenList.Dish.name) {
            DishView(viewModel = viewModel)
        }
    }
}