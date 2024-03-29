package com.example.dietapp.ui.mainscreen.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.mainscreen.navigation.MainHost
import com.example.dietapp.ui.mainscreen.navigation.MenuCategories
import com.example.dietapp.ui.mainscreen.viewmodel.MainScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    viewModel: MainScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = MenuCategories.Ingredient.title) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(MenuCategories.Ingredient.name)
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = MenuCategories.Dish.title) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(MenuCategories.Dish.name)
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = MenuCategories.Day.title) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(MenuCategories.Day.name)
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = MenuCategories.DietSettings.title) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate(MenuCategories.DietSettings.name)
                    }
                )
            }
        },
    ) {
        Scaffold(
            topBar = {
                DietAppTopBar(
                    menuButtonAction = { scope.launch { drawerState.open() } },
                    viewModel = viewModel
                )
            },
            floatingActionButton = {
                FloatButton(viewModel)
            },
        ) { innerPadding ->
            MainHost(innerPadding, navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietAppTopBar(
    menuButtonAction: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel
) {
    TopAppBar(
        title = { Text(viewModel.topBarName) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (viewModel.isNavigateBackVisible) {
                IconButton(onClick = viewModel.navigateBackAction) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (viewModel.isDietStatButtonVisible)
                IconButton(onClick = { viewModel.changeVisibleDietStatistics() }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Diet statistics"
                    )
                }
            if (viewModel.isSearchButtonVisible)
                IconButton(onClick = {
                    viewModel.changeVisibleFilterBar()
                    if (!viewModel.isFilterBarVisible)
                        viewModel.filterText = ""
                }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search"
                    )
                }
            IconButton(onClick = menuButtonAction) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = "Menu"
                )
            }
        }
    )
}

@Composable
fun FloatButton(
    viewModel: MainScreenViewModel
) {
    if (viewModel.isFloatButtonVisible) {
        FloatingActionButton(
            onClick = viewModel.floatButtonAction,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.padding(Dp(20F))
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Save"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBar(mainScreenViewModel: MainScreenViewModel) {
    val focusManager = LocalFocusManager.current
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        TextField(
            value = mainScreenViewModel.filterText,
            onValueChange = { mainScreenViewModel.filterText = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
            label = { Text("filter") },
            enabled = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainViewPreview() {
    MainView()
}

