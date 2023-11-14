package com.example.dietapp.ui.dish.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.dish.screen.AddIngredient
import com.example.dietapp.ui.dish.screen.DishListView
import com.example.dietapp.ui.dish.screen.DishScreenList
import com.example.dietapp.ui.dish.screen.DishView
import com.example.dietapp.ui.dish.viewmodel.DishViewModel
import com.example.dietapp.ui.ingredient.viewmodel.toIngredientWithAmountDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DishNavHost(
    setMainScreen: ((isFloatButtonVisible: Boolean, floatButtonAction: () -> Unit, isNavigateBackVisible: Boolean, navigateBackAction: () -> Unit, topBarName: String) -> Unit),
    viewModel: DishViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = DishScreenList.DishList.name,
    ) {
        composable(route = DishScreenList.DishList.name) {
            setMainScreen(
                true,
                {
                    viewModel.resetUiState()
                    viewModel.deleteButtonVisible = false
                    navController.navigate(DishScreenList.Dish.name)
                },
                false,
                {},
                DishScreenList.DishList.title
            )
            DishListView(
                onItemClick = { dishDetails ->
                    viewModel.updateDishWithIngredientUiState(dishDetails)
                    viewModel.deleteButtonVisible = true
                    navController.navigate(DishScreenList.Dish.name)
                },
                viewModel
            )
        }
        composable(route = DishScreenList.Dish.name) {
            setMainScreen(
                true,
                { navController.navigate(DishScreenList.AddIngredientToDish.name) },
                true,
                { navController.navigateUp() },
                DishScreenList.Dish.title
            )
            DishView(
                saveButtonOnClick = {
                    coroutineScope.launch(Dispatchers.IO) { viewModel.saveDishWithIngredients() }
                },
                viewModel = viewModel
            )
        }
        composable(route = DishScreenList.AddIngredientToDish.name) {
            setMainScreen(
                false,
                {},
                true,
                { navController.navigateUp() },
                DishScreenList.Dish.title
            )
            AddIngredient(
                {
                    viewModel.addToIngredientWithAmountList(it.toIngredientWithAmountDetails())
                    navController.navigateUp()
                }
            )
        }
    }
}