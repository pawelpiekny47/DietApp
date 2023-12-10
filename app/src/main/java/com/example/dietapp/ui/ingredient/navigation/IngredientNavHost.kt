package com.example.dietapp.ui.ingredient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietapp.barcode.BarcodeScanner
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.ingredient.screen.IngredientScreenList
import com.example.dietapp.ui.ingredient.screen.IngredientListScreen
import com.example.dietapp.ui.ingredient.screen.IngredientView
import com.example.dietapp.ui.ingredient.viewmodel.IngredientViewModel
import kotlinx.coroutines.launch

@Composable
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
fun IngredientNavHost(
    setMainScreen: ((isSearchButtonVisible: Boolean, isDietStatisticsButtonVisible: Boolean, isFloatButtonVisible: Boolean, floatButtonAction: () -> Unit, isNavigateBackVisible: Boolean, navigateBackAction: () -> Unit, topBarName: String) -> Unit),
    viewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
    filteredText:String
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = IngredientScreenList.IngredientListScreen.name
    ) {
        composable(route = IngredientScreenList.IngredientListScreen.name) {
            setMainScreen(
                true,
                false,
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
                viewModel,
                filteredText
            )
        }
        composable(route = IngredientScreenList.NewIngredientScreen.name) {
            setMainScreen(
                false,
                false,
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
                    navController.navigate(IngredientScreenList.IngredientListScreen.name)
                },
                deleteButtonVisible = viewModel.deleteButtonVisible,
                deleteButtonOnClick = {
                    coroutineScope.launch { viewModel.deleteItem() }
                    navController.navigateUp()
                },
                barcodeScannerButtonOnClick = { navController.navigate(IngredientScreenList.BarcodeScanner.name) }
            )
        }
        composable(route = IngredientScreenList.BarcodeScanner.name) {
            setMainScreen(
                false,
                false,
                false,
                { },
                true,
                { navController.navigateUp() },
                IngredientScreenList.NewIngredientScreen.title
            )
            BarcodeScanner(
                barcodeScannerEffect = { viewModel.updateUiWithBarcode(it) },
                returnToIngredientScreen = { navController.navigate(IngredientScreenList.NewIngredientScreen.name) })
        }
    }
}