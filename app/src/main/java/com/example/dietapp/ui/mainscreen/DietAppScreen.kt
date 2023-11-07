package com.example.dietapp.ui.mainscreen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dietapp.ui.AppViewModelProvider
import com.example.dietapp.ui.ingredient.navigation.IngredientNavHost

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
    Scaffold(
        topBar = {
            DietAppTopBar(
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
