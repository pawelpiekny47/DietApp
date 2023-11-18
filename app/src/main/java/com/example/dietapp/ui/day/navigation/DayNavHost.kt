package com.example.dietapp.ui.day.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.day.screen.DayListView
import com.example.dietapp.ui.day.screen.DayScreenList
import com.example.dietapp.ui.day.screen.DayView
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.day.screen.AddDish
import com.example.dietapp.ui.day.viewmodel.toDishWithAmountDetails
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@Composable
fun DayNavHost(
    setMainScreen: ((isFloatButtonVisible: Boolean, floatButtonAction: () -> Unit, isNavigateBackVisible: Boolean, navigateBackAction: () -> Unit, topBarName: String) -> Unit),
    dayViewModel: DayViewModel = viewModel(factory = AppViewModelProvider.Factory),
    dietSettingsViewModel: DietSettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
) {
    val coroutineScope = rememberCoroutineScope()

    NavHost(
    navController = navController,
    startDestination = DayScreenList.DayList.name,
    )
    {
        composable(route = DayScreenList.DayList.name) {
            setMainScreen(
                true,
                {
                    dayViewModel.resetUiState()
                    dayViewModel.deleteButtonVisible = false
                    navController.navigate(DayScreenList.Day.name)
                },
                false,
                {},
                DayScreenList.DayList.title
            )
            DayListView(
                onItemClick = { dayDetails ->
                    dayViewModel.updateDayWithDishesUiState(dayDetails)
                    dayViewModel.deleteButtonVisible = true
                    navController.navigate(DayScreenList.Day.name)
                },
                dayViewModel
            )
        }
        composable(route = DayScreenList.Day.name) {
            setMainScreen(
                true,
                { navController.navigate(DayScreenList.AddDishToDay.name) },
                true,
                { navController.navigateUp() },
                DayScreenList.Day.title
            )
            DayView(
                saveButtonOnClick = {
                    coroutineScope.launch(Dispatchers.IO) { dayViewModel.saveDayWithDishes() }
                },
                dayViewModel = dayViewModel,
                dietSettingsViewModel = dietSettingsViewModel
            )
        }
        composable(route = DayScreenList.AddDishToDay.name) {
            setMainScreen(
                false,
                {},
                true,
                { navController.navigateUp() },
                DayScreenList.AddDishToDay.title
            )
            AddDish(
                {
                    dayViewModel.addToDishWithAmountList(it.toDishWithAmountDetails())
                    navController.navigateUp()
                }
            )
        }
    }
}