package com.example.dietapp.ui.ingredient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.ingredient.screen.IngredientScreenList
import com.example.dietapp.ui.ingredient.screen.IngredientListScreen
import com.example.dietapp.ui.ingredient.screen.IngredientView
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import kotlinx.coroutines.launch

@Composable
fun IngredientNavHost(
    setMainScreen: ((isFloatButtonVisible: Boolean, floatButtonAction: () -> Unit, isNavigateBackVisible: Boolean, navigateBackAction: () -> Unit, topBarName: String) -> Unit),
    viewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = IngredientScreenList.IngredientListScreen.name
    ) {
        composable(route = IngredientScreenList.IngredientListScreen.name) {
            setMainScreen(
                true,
                {
                    viewModel.resetUiState()
                    viewModel.deleteButtonVisible = false
                    navController.navigate(IngredientScreenList.NewIngredientScreen.name)
                },
                false,
                {},
                IngredientScreenList.IngredientListScreen.title
            )

            IngredientListScreen(
                onListItemClick = { ingredientDetails ->
                    viewModel.updateUiState(ingredientDetails)
                    viewModel.deleteButtonVisible = true
                    navController.navigate(IngredientScreenList.NewIngredientScreen.name)
                },
                viewModel
            )
        }
        composable(route = IngredientScreenList.NewIngredientScreen.name) {
            setMainScreen(
                false,
                { },
                true,
                { navController.navigateUp() },
                IngredientScreenList.NewIngredientScreen.title
            )
            IngredientView(
                viewModel,
                saveButton = {
                    coroutineScope.launch { viewModel.saveItem() }
                    navController.navigateUp()
                },
                deleteButtonVisible = viewModel.deleteButtonVisible,
                deleteButtonOnClick = {
                    coroutineScope.launch { viewModel.deleteItem() }
                    navController.navigateUp()
                }
            )
        }
    }
}