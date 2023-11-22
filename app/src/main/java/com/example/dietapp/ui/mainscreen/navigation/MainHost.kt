package com.example.dietapp.ui.mainscreen.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.day.navigation.DayNavHost
import com.example.dietapp.ui.dietsettings.navigation.DietSettingsNavHost
import com.example.dietapp.ui.dish.navigation.DishNavHost
import com.example.dietapp.ui.ingredient.navigation.IngredientNavHost
import com.example.dietapp.ui.mainscreen.viewmodel.MainScreenViewModel

enum class MenuCategories(val title: String) {
    Ingredient(title = " Ingredient"),
    Dish(title = " Dish"),
    Day(title = "Day"),
    DietSettings(title = "Diet Settings")
}
@Composable
fun MainHost(
    innerPadding: PaddingValues,
    navController: NavHostController,
    viewModel: MainScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    NavHost(
        navController = navController,
        startDestination = MenuCategories.Ingredient.name,
        modifier = Modifier.padding(innerPadding)
    )
    {
        composable(route = MenuCategories.Ingredient.name) {
            IngredientNavHost(viewModel::setMainScreen)
        }
        composable(route = MenuCategories.Dish.name) {
            DishNavHost(viewModel::setMainScreen)
        }
        composable(route = MenuCategories.Day.name) {
            DayNavHost(viewModel::setMainScreen)
        }
        composable(route = MenuCategories.DietSettings.name) {
            DietSettingsNavHost(viewModel::setMainScreen)
        }
    }
}