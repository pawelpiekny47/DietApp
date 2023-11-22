package com.example.dietapp.ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.day.viewmodel.DayViewModel
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import com.example.dietapp.ui.dish.viewmodel.DishViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DietSettingsStatistic(
    viewModel: ViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val lazyListState = rememberLazyListState()
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = lazyListState,
        flingBehavior = flingBehavior
    ) {
        item {
            Column(modifier = Modifier.fillParentMaxWidth()) {
                BasicStatistics(viewModel, dietSettingsViewModel)
            }
        }
        item {
            Column(modifier = Modifier.fillParentMaxWidth()) {
                FoodTypeStatistics(viewModel, dietSettingsViewModel)
            }
        }
        item {
            Column(modifier = Modifier.fillParentMaxWidth()) {
                AdditionalInfo(viewModel, dietSettingsViewModel)
            }
        }
    }
}


@Composable
fun BasicStatistics(
    viewModel: ViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    var totalKcal = 0.0
    var totalProteins = 0.0
    var totalCarbs = 0.0
    var totalFat = 0.0
    when (viewModel) {
        is DishViewModel -> {
            totalKcal = viewModel.returnTotalKcal()
            totalProteins = viewModel.returnTotalProtein()
            totalCarbs = viewModel.returnTotalCarbs()
            totalFat = viewModel.returnTotalFat()
        }

        is DayViewModel -> {
            totalKcal = viewModel.returnTotalKcal()
            totalProteins = viewModel.returnTotalProtein()
            totalCarbs = viewModel.returnTotalCarbs()
            totalFat = viewModel.returnTotalFat()
        }
    }


    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "kcal: $totalKcal / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal}",
                    fontSize = 10.sp
                )
                Text(
                    text = "protein: $totalProteins / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein}",
                    fontSize = 10.sp
                )
                Text(
                    text = "carbs: $totalCarbs / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates}",
                    fontSize = 10.sp
                )
                Text(
                    text = "fat: $totalFat / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats}",
                    fontSize = 10.sp
                )
            }
        }
        CircularBasicStatistics(viewModel)
    }

}

@Composable
fun FoodTypeStatistics(
    viewModel: ViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    var totalKcalFruit = 0.0
    var totalKcalVegetable = 0.0
    var totalKcalWheet = 0.0
    var totalKcalMilk = 0.0
    var totalKcalProteinSource = 0.0
    var totalKcalAddFat = 0.0

    when (viewModel) {
        is DishViewModel -> {
            totalKcalFruit = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Fruit)
            totalKcalVegetable = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Vegetable)
            totalKcalWheet = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Wheet)
            totalKcalMilk =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.MilkAndReplacement)
            totalKcalProteinSource =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.ProteinSource)
            totalKcalAddFat = viewModel.returnTotalKcalForFoodCategory(FoodCategory.AddedFat)
        }

        is DayViewModel -> {
            totalKcalFruit = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Fruit)
            totalKcalVegetable = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Vegetable)
            totalKcalWheet = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Wheet)
            totalKcalMilk =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.MilkAndReplacement)
            totalKcalProteinSource =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.ProteinSource)
            totalKcalAddFat = viewModel.returnTotalKcalForFoodCategory(FoodCategory.AddedFat)
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "fruit: $totalKcalFruit / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromFruits}",
                    fontSize = 10.sp
                )
                Text(
                    text = "vegetable: $totalKcalVegetable/ ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromVegetables}",
                    fontSize = 10.sp
                )
                Text(
                    text = "protein source: $totalKcalProteinSource / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromProteinSource}",
                    fontSize = 10.sp
                )
                Text(
                    text = "milk: $totalKcalMilk / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromMilkProducts}",
                    fontSize = 10.sp
                )
                Text(
                    text = "grains: $totalKcalWheet / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromGrain}",
                    fontSize = 10.sp
                )
                Text(
                    text = "added fats: $totalKcalAddFat / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromAddedFat}",
                    fontSize = 10.sp
                )
            }
        }
        CircularFoodTypeStatistics(viewModel)
    }

}

@Composable
fun AdditionalInfo(
    viewModel: ViewModel,
    dietSettingsViewModel: DietSettingsViewModel
) {
    var totalPufa = 0.0
    var totalSoil = 0.0
    var totalFiber = 0.0

    when (viewModel) {
        is DishViewModel -> {
            totalPufa = viewModel.returnTotalPufa()
            totalSoil = viewModel.returnTotalSoil()
            totalFiber = viewModel.returnTotalFiber()
        }

        is DayViewModel -> {
            totalPufa = viewModel.returnTotalPufa()
            totalSoil = viewModel.returnTotalSoil()
            totalFiber = viewModel.returnTotalFiber()
        }
    }
    Text(
        text = "pufa: $totalPufa / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.polyunsaturatedFats}",
        fontSize = 10.sp
    )
    Text(
        text = "salt: $totalSoil / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.soil}",
        fontSize = 10.sp
    )
    Text(
        text = "fiber: $totalFiber / ${dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fiber}",
        fontSize = 10.sp
    )
}

@Composable
fun CircularBasicStatistics(
    viewModel: ViewModel,
) {
    var totalKcal = 0.0
    var proteinProgress = 0.0
    var carbsProgress = 0.0
    var fatProgress = 0.0
    var totalProgress = 0.0
    when (viewModel) {
        is DishViewModel -> {
            totalKcal = viewModel.returnTotalKcal()
            totalProgress =
                viewModel.returnTotalProtein() * 4 + viewModel.returnTotalFat() * 9 + viewModel.returnTotalCarbs() * 4
            proteinProgress = viewModel.returnTotalProtein() * 4 / totalProgress
            carbsProgress = viewModel.returnTotalCarbs() * 4 / totalProgress
            fatProgress = viewModel.returnTotalFat() * 9 / totalProgress
        }

        is DayViewModel -> {
            totalKcal = viewModel.returnTotalKcal()
            totalProgress =
                viewModel.returnTotalProtein() * 4 + viewModel.returnTotalFat() * 9 + viewModel.returnTotalCarbs() * 4
            proteinProgress = viewModel.returnTotalProtein() * 4 / totalProgress
            carbsProgress = viewModel.returnTotalCarbs() * 4 / totalProgress
            fatProgress = viewModel.returnTotalFat() * 9 / totalProgress
        }
    }
    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(3.dp, 3.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "$totalKcal", fontSize = 10.sp)
            Text(text = "kcal", fontSize = 8.sp)
        }
        CircularProgressIndicator(
            progress = proteinProgress.toFloat(),
            color = Color.Black
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(proteinProgress.toFloat() * 360),
            progress = carbsProgress.toFloat(),
            color = Color.Red
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((carbsProgress + proteinProgress).toFloat() * 360),
            progress = fatProgress.toFloat(),
            color = Color.Green
        )
    }
}

@Composable
fun CircularFoodTypeStatistics(
    viewModel: ViewModel,
) {
    var fruitProgress = 0.0
    var vegetableProgress = 0.0
    var grainProgress = 0.0
    var milkProgress = 0.0
    var proteinSourceProgress = 0.0
    var addedFatProgress = 0.0
    val total: Double

    when (viewModel) {
        is DishViewModel -> {
            total = FoodCategory.values().sumOf { viewModel.returnTotalKcalForFoodCategory(it) }
            fruitProgress = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Fruit) / total
            vegetableProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.Vegetable) / total
            grainProgress = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Wheet) / total
            milkProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.MilkAndReplacement) / total
            proteinSourceProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.ProteinSource) / total
            addedFatProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.AddedFat) / total
        }

        is DayViewModel -> {
            total = FoodCategory.values().sumOf { viewModel.returnTotalKcalForFoodCategory(it) }
            fruitProgress = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Fruit) / total
            vegetableProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.Vegetable) / total
            grainProgress = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Wheet) / total
            milkProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.MilkAndReplacement) / total
            proteinSourceProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.ProteinSource) / total
            addedFatProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.AddedFat) / total
        }
    }

    Box(contentAlignment = Alignment.Center) {

        CircularProgressIndicator(
            modifier = Modifier,
            progress = (fruitProgress.toFloat()),
            color = Color.Black
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(fruitProgress.toFloat() * 360),
            progress = (vegetableProgress.toFloat()),
            color = Color.Red
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((fruitProgress + vegetableProgress).toFloat() * 360),
            progress = (proteinSourceProgress.toFloat()),
            color = Color.Green
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((fruitProgress + vegetableProgress + proteinSourceProgress).toFloat() * 360),
            progress = (grainProgress.toFloat()),
            color = Color.DarkGray
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((fruitProgress + vegetableProgress + proteinSourceProgress + grainProgress).toFloat() * 360),
            progress = (milkProgress.toFloat()),
            color = Color.Cyan
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((fruitProgress + vegetableProgress + proteinSourceProgress + grainProgress + milkProgress).toFloat() * 360),
            progress = (addedFatProgress.toFloat()),
            color = Color.Blue
        )
    }
}