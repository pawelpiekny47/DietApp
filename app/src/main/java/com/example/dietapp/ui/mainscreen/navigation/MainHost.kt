package com.example.dietapp.ui.mainscreen.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.common.DietSettingsStatistic
import com.example.dietapp.ui.day.navigation.DayNavHost
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.dietsettings.navigation.DietSettingsNavHost
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import com.example.dietapp.ui.dish.navigation.DishNavHost
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
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
    mainScreenViewModel: MainScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    dietSettingsViewModel: DietSettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    dishViewModel: DishViewModel = viewModel(factory = AppViewModelProvider.Factory),
    dayViewModel: DayViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {

    NavHost(
        navController = navController,
        startDestination = MenuCategories.Ingredient.name,
        modifier = Modifier.padding(innerPadding)
    )
    {
        composable(route = MenuCategories.Ingredient.name) {
            IngredientNavHost(mainScreenViewModel::setMainScreen)
        }
        composable(route = MenuCategories.Dish.name) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box {
                    DietSettingsStatistic(
                        dietStatisticsViewModel = dishViewModel,
                        dietSettingsViewModel = dietSettingsViewModel
                    )
                }
                Box {
                    DishNavHost(
                        mainScreenViewModel::setMainScreen,
                        mainScreenViewModel = mainScreenViewModel,
                        dishViewModel = dishViewModel
                    )
                }
            }
        }
        composable(route = MenuCategories.Day.name) {
            Column(modifier = Modifier.fillMaxSize()) {
                DietSettingsStatistic(
                    dietStatisticsViewModel = dayViewModel,
                    dietSettingsViewModel = dietSettingsViewModel
                )
                DayNavHost(
                    mainScreenViewModel::setMainScreen,
                    dayViewModel = dayViewModel
                )
            }
        }
        composable(route = MenuCategories.DietSettings.name) {
            DietSettingsNavHost(mainScreenViewModel::setMainScreen)
        }
    }
}