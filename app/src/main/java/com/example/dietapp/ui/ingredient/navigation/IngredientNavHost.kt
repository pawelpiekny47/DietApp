package com.example.dietapp.ui.ingredient.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.ingredient.screen.IngredientListScreen
import com.example.dietapp.ui.ingredient.screen.IngredientScreen
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import com.example.dietapp.ui.mainscreen.DietAppScreen
import kotlinx.coroutines.launch

@Composable
fun IngredientNavHost(
    innerPadding: PaddingValues,
    visibleFloatButton: (Boolean) -> Unit,
    updateTopBarName: (DietAppScreen) -> Unit,
    updateCanNavigateBack: (Boolean) -> Unit,
    updateNavigateUp: (() -> Unit) -> Unit,
    updateFloatButtonOnClick: (() -> Unit) -> Unit,
    viewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = DietAppScreen.IngredientListScreen.name
    ) {
        composable(route = DietAppScreen.IngredientListScreen.name) {
            visibleFloatButton(true)
            updateTopBarName(DietAppScreen.IngredientListScreen)
            updateCanNavigateBack(false)
            updateNavigateUp { navController.navigateUp() }
            updateFloatButtonOnClick {
                viewModel.resetUiState()
                viewModel.deleteButtonVisible = false
                navController.navigate(DietAppScreen.NewIngredientScreen.name)
            }
            IngredientListScreen(
                onListItemClick = { ingredientDetails ->
                    viewModel.updateUiState(ingredientDetails)
                    viewModel.deleteButtonVisible = true
                    navController.navigate(DietAppScreen.NewIngredientScreen.name)
                },
                modifier = androidx.compose.ui.Modifier.padding(innerPadding),
                viewModel
            )
        }
        composable(route = DietAppScreen.NewIngredientScreen.name) {
            visibleFloatButton(false)
            updateTopBarName(DietAppScreen.NewIngredientScreen)
            updateCanNavigateBack(true)
            updateNavigateUp { navController.navigateUp() }
            updateFloatButtonOnClick { }
            IngredientScreen(
                modifier = androidx.compose.ui.Modifier.padding(innerPadding),
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