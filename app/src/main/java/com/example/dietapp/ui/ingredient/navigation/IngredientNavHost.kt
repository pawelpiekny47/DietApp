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
import com.example.dietapp.ui.ingredient.screen.IngredientScreenList
import com.example.dietapp.ui.ingredient.screen.IngredientListScreen
import com.example.dietapp.ui.ingredient.screen.IngredientView
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import kotlinx.coroutines.launch

@Composable
fun IngredientNavHost(
    innerPadding: PaddingValues,
    visibleFloatButton: (Boolean) -> Unit,
    updateTopBarName: (IngredientScreenList) -> Unit,
    updateCanNavigateBack: (Boolean) -> Unit,
    updateNavigateUp: (() -> Unit) -> Unit,
    updateFloatButtonOnClick: (() -> Unit) -> Unit,
    viewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = IngredientScreenList.IngredientListScreen.name
    ) {
        composable(route = IngredientScreenList.IngredientListScreen.name) {
            visibleFloatButton(true)
            updateTopBarName(IngredientScreenList.IngredientListScreen)
            updateCanNavigateBack(false)
            updateNavigateUp { navController.navigateUp() }
            updateFloatButtonOnClick {
                viewModel.resetUiState()
                viewModel.deleteButtonVisible = false
                navController.navigate(IngredientScreenList.NewIngredientScreen.name)
            }
            IngredientListScreen(
                onListItemClick = { ingredientDetails ->
                    viewModel.updateUiState(ingredientDetails)
                    viewModel.deleteButtonVisible = true
                    navController.navigate(IngredientScreenList.NewIngredientScreen.name)
                },
                modifier = androidx.compose.ui.Modifier.padding(innerPadding),
                viewModel
            )
        }
        composable(route = IngredientScreenList.NewIngredientScreen.name) {
            visibleFloatButton(false)
            updateTopBarName(IngredientScreenList.NewIngredientScreen)
            updateCanNavigateBack(true)
            updateNavigateUp { navController.navigateUp() }
            updateFloatButtonOnClick { }
            IngredientView(
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