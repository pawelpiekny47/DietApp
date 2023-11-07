package com.example.dietapp.ui.mainscreen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.ingredient.navigation.IngredientNavHost
import com.example.dietapp.ui.mainscreen.viewmodel.MainScreenViewModel

enum class MenuCategories(val title: String) {
    Ingredient(title = " Ingredient"),
    Dish(title = " Dish"),
    DietSettings(title = " Diet settings")
}

@Composable
fun MainHost(
    innerPadding: PaddingValues,
    navController: NavHostController,
    viewModel: MainScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    NavHost(
        navController = navController,
        startDestination = MenuCategories.Ingredient.name
    )
    {
        composable(route = MenuCategories.Ingredient.name) {
            IngredientNavHost(
                innerPadding,
                viewModel::updateVisibleFloatButton,
                viewModel::updateTopBarName,
                viewModel::updateCanNavigateBack,
                viewModel::updateNavigateUp,
                viewModel::updateFloatButtonOnClick
            )
        }
    }
}