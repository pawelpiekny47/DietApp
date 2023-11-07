package com.example.dietapp.ui.mainscreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Divider
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.ingredient.navigation.IngredientNavHost
import kotlinx.coroutines.launch

enum class DietAppScreen(val title: String) {
    IngredientListScreen(title = " Ingredient list"),
    NewIngredientScreen(title = " Add ingredient")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietAppTopBar(
    menuButtonAction: () -> Unit,
    currentScreen: DietAppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen.title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
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
    visibleActionButton: Boolean,
    onClick: () -> Unit,
) {
    if (visibleActionButton) {
        FloatingActionButton(
            onClick = onClick,
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietAppScreen(
    viewModel: MainScreenViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp))
                Divider()
                NavigationDrawerItem(
                    label = { Text(text = "Ingredients") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Dish") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Diet settings") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )
                // ...other drawer items
            }
        },
    ) {
        Scaffold(
            topBar = {
                DietAppTopBar(
                    menuButtonAction = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    currentScreen = viewModel.topBarName,
                    canNavigateBack = viewModel.canNavigateBack,
                    navigateUp = viewModel.navigateUp
                )
            },
            floatingActionButton = {
                FloatButton(viewModel.visibleFloatButton, viewModel.floatButtonOnClick)
            },
        ) { innerPadding ->
            IngredientNavHost(
                innerPadding,
                viewModel::updateVisibleFloatButton,
                viewModel::updateTopBarName,
                viewModel::updateCanNavigateBack,
                viewModel::updateNavigateUp,
                viewModel::updateFloatButtonOnClick
            )
        }
    }
}