package com.example.dietapp.ui.ingredient

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dietapp.data.Ingredient
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.AppViewModelProvider
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientScreen(
    modifier: Modifier = Modifier,
    viewModel: IngredientViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val ingredientUiState by viewModel.ingredientUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveItem()
                    }
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(Dp(20F))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Save"
                )
            }
        },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FoodCategory.values().forEach { foodCategory ->
                if (ingredientUiState.ingredientList.any { it.foodCategory == foodCategory }) item {
                    Text(
                        text = "${foodCategory}"
                    )
                }
                items(ingredientUiState.ingredientList.filter { it.foodCategory == foodCategory }) { ingredient ->
                    IngredientItem(
                        ingredient = ingredient,
                        onItemClick = {
                            coroutineScope.launch {
                                viewModel.deleteItem(ingredient)
                            }
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun IngredientItem(ingredient: Ingredient, onItemClick: () -> Unit) {
    Card(modifier = Modifier
        .clickable { onItemClick() }) {
        Text(
            text = "${ingredient.name}     b :${ingredient.protein} / w: ${ingredient.carbohydrates} / t: ${ingredient.fats}"
        )
    }
}