package com.example.dietapp.ui.dietsettings.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.dietsettings.screen.DietSettingsScreenList
import com.example.dietapp.ui.dietsettings.screen.DietSettingsView
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel

@Composable
fun DietSettingsNavHost(
    setMainScreen: ((isFloatButtonVisible: Boolean, floatButtonAction: () -> Unit, isNavigateBackVisible: Boolean, navigateBackAction: () -> Unit, topBarName: String) -> Unit),
    viewModel: DietSettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = DietSettingsScreenList.CustomDietSettings.name
    ) {
        composable(route = DietSettingsScreenList.CustomDietSettings.name) {
            setMainScreen(
                true,
                {},
                false,
                {},
                DietSettingsScreenList.CustomDietSettings.title
            )
            DietSettingsView(viewModel)
        }
    }
}