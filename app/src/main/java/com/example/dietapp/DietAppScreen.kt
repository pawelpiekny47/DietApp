package com.example.dietapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dietapp.ui.ingredient.IngredientListScreen
import com.example.dietapp.ui.ingredient.IngredientScreen

enum class DietAppScreen(val title: String) {
    IngredientListScreen(title = " Ingredient list"),
    NewIngredientScreen(title = " Add ingredient")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietAppTopBar(
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
        }
    )
}

@Composable
fun FloatButton(
    visibleActionButton: Boolean,
    addButtonRoute: () -> Unit,
) {
    if (visibleActionButton) {
        FloatingActionButton(
            onClick = addButtonRoute,
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
    navController: NavHostController = rememberNavController(),
) {
    var visibleFloatButton = true
    var floatButtonRoute = { navController.navigate(DietAppScreen.NewIngredientScreen.name) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = DietAppScreen.valueOf(
        backStackEntry?.destination?.route ?: DietAppScreen.IngredientListScreen.name
    )

    Scaffold(
        topBar = {
            DietAppTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        floatingActionButton = {
            FloatButton(visibleFloatButton, floatButtonRoute)
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DietAppScreen.IngredientListScreen.name
        ) {
            composable(route = DietAppScreen.IngredientListScreen.name) {
                IngredientListScreen(modifier = Modifier.padding(innerPadding))
                floatButtonRoute =
                    { navController.navigate(DietAppScreen.NewIngredientScreen.name) }
                visibleFloatButton = true
            }
            composable(route = DietAppScreen.NewIngredientScreen.name) {
                IngredientScreen(
                    visibleCancelButton = true,
                    modifier = Modifier.padding(innerPadding)
                )
                visibleFloatButton = false
            }
        }
    }
}
