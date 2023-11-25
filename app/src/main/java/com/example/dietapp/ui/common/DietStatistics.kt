package com.example.dietapp.ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DietSettingsStatistic(
    viewModel: DietStatistics,
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
    dietStatistics: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val listOfStatisticItems = mutableListOf(
        DietStatisticItem(
            "kcal",
            dietStatistics.returnCurrentKcal(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble(),
            Color.Red
        ),
        DietStatisticItem(
            "p",
            dietStatistics.returnCurrentProtein(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble(),
            Color.Blue
        ),
        DietStatisticItem(
            "c",
            dietStatistics.returnCurrentCarbs(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble(),
            Color.Green
        ),
        DietStatisticItem(
            "f",
            dietStatistics.returnCurrentFat(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble(),
            Color.Yellow
        ),
    )

    var inPercent by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clickable { inPercent = !inPercent }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            BasicMacrosStatsV2(listOfStatisticItems, inPercent)
        }
        Box(
            modifier = Modifier
                .clickable { extended2 = !extended2 }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            LinearBasicStatistics(listOfStatisticItems)

        }
    }
}


@Composable
fun FoodTypeStatistics(
    dietStatistics: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val listOfStatisticItems = mutableListOf(
        DietStatisticItem(
            "fruit",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.Fruit),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromFruits.toDouble(),
            Color.Red
        ),
        DietStatisticItem(
            "vegetable",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.Vegetable),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromVegetables.toDouble(),
            Color.Green
        ),
        DietStatisticItem(
            "grain",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.Wheet),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromGrain.toDouble(),
            Color.Yellow
        ),
        DietStatisticItem(
            "milk",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.MilkAndReplacement),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromMilkProducts.toDouble(),
            Color.Blue
        ),
        DietStatisticItem(
            "protein source",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.ProteinSource),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromProteinSource.toDouble(),
            Color.Black
        ),
        DietStatisticItem(
            "added fat",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.AddedFat),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromAddedFat.toDouble(),
            Color.LightGray
        ),
    )
    var inPercent by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clickable { inPercent = !inPercent }
                .weight(1F)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BasicMacrosStatsV2(listOfStatisticItems, inPercent)
        }

        Box(
            modifier = Modifier
                .clickable { extended2 = !extended2 }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            LinearBasicStatistics(listOfStatisticItems)
        }

    }
}

@Composable
fun AdditionalInfo(
    dietStatistics: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val listOfStatisticItems = mutableListOf(
        DietStatisticItem(
            "pufa",
            dietStatistics.returnCurrentPufa(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.polyunsaturatedFats.toDouble(),
            Color.Red
        ),
        DietStatisticItem(
            "soil",
            dietStatistics.returnCurrentSoil(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.soil.toDouble(),
            Color.Green
        ),
        DietStatisticItem(
            "fiber",
            dietStatistics.returnCurrentFiber(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fiber.toDouble(),
            Color.Yellow
        )
    )
    var inPercent by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clickable { inPercent = !inPercent }
                .weight(1F)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BasicMacrosStatsV2(listOfStatisticItems, inPercent)
        }

        Box(
            modifier = Modifier
                .clickable { extended2 = !extended2 }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            LinearBasicStatistics(listOfStatisticItems)
        }

    }
}

@Composable
fun LinearBasicStatistics(
    list: MutableList<DietStatisticItem>,
) {
    Column(
        modifier = Modifier
            .padding(3.dp, 3.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        list.forEach {
            LinearProgressIndicator(
                progress = kotlin.math.min((it.current / it.target), 1.0).toFloat(),
                color = it.statisticColor
            )
        }
    }
}

@Composable
fun CircularBasicStatistics(
    viewModel: DietStatistics,
) {
    var totalKcal = viewModel.returnCurrentKcal()
    var proteinProgress = 0.0
    var carbsProgress = 0.0
    var fatProgress = 0.0
    val totalProgress =
        (viewModel.returnCurrentProtein() * 4 + viewModel.returnCurrentFat() * 9 + viewModel.returnCurrentCarbs() * 4)

    when (totalProgress) {
        0.0 -> {
            proteinProgress = 0.0
            carbsProgress = 0.0
            fatProgress = 0.0
        }

        else -> {
            proteinProgress = viewModel.returnCurrentProtein() * 4 / totalProgress
            carbsProgress = viewModel.returnCurrentCarbs() * 4 / totalProgress
            fatProgress = viewModel.returnCurrentFat() * 9 / totalProgress
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
fun CircularBasicStatisticsV2(
    viewModel: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel
) {
    var kcal = 0.0
    var proteinProgress = 0.0
    var carbsProgress = 0.0
    var fatProgress = 0.0

    val proteinTarget =
        dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble() * 4
    val carbsTarget =
        dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble() * 4
    val fatTarget =
        dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble() * 9
    var kcalCalculatedTarget = proteinTarget + carbsTarget + fatTarget

    kcal = viewModel.returnCurrentKcal()
    when (kcalCalculatedTarget) {
        0.0 -> {
            proteinProgress = 0.0
            carbsProgress = 0.0
            fatProgress = 0.0
            kcalCalculatedTarget = 1.0

        }

        else -> {
            proteinProgress = viewModel.returnCurrentProtein() * 4 / kcalCalculatedTarget
            carbsProgress = viewModel.returnCurrentCarbs() * 4 / kcalCalculatedTarget
            fatProgress = viewModel.returnCurrentFat() * 9 / kcalCalculatedTarget
        }
    }
    Box(contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(3.dp, 3.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "$kcal", fontSize = 10.sp)
            Text(text = "kcal", fontSize = 8.sp)
        }
        CircularProgressIndicator(
            progress = proteinProgress.toFloat(),
            color = Color.Black
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((proteinProgress * 360).toFloat()),
            progress = ((proteinTarget / kcalCalculatedTarget) - proteinProgress).toFloat(),
            color = Color.LightGray
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(((proteinTarget / kcalCalculatedTarget) * 360).toFloat()),
            progress = carbsProgress.toFloat(),
            color = Color.Red
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((((proteinTarget / kcalCalculatedTarget) + carbsProgress) * 360).toFloat()),
            progress = ((carbsTarget / kcalCalculatedTarget) - carbsProgress).toFloat(),
            color = Color.LightGray
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(((proteinTarget + carbsTarget) / kcalCalculatedTarget * 360).toFloat()),
            progress = fatProgress.toFloat(),
            color = Color.Green
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(
                (((proteinTarget + carbsTarget) / kcalCalculatedTarget + fatProgress) * 360)
                    .toFloat()
            ),
            progress = (fatTarget / kcalCalculatedTarget - fatProgress).toFloat(),
            color = Color.LightGray
        )
    }
}

@Composable
fun CircularFoodTypeStatistics(
    viewModel: DietStatistics,
) {
    var fruitProgress = 0.0
    var vegetableProgress = 0.0
    var grainProgress = 0.0
    var milkProgress = 0.0
    var proteinSourceProgress = 0.0
    var addedFatProgress = 0.0
    val total: Double

    total = FoodCategory.values().sumOf { viewModel.returnCurrentKcalForFoodCategory(it) }
    when (total) {
        0.0 -> {
            fruitProgress = 0.0
            vegetableProgress = 0.0
            grainProgress = 0.0
            milkProgress = 0.0
            proteinSourceProgress = 0.0
            addedFatProgress = 0.0
        }

        else -> {
            fruitProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.Fruit) / total
            vegetableProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.Vegetable) / total
            grainProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.Wheet) / total
            milkProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.MilkAndReplacement) / total
            proteinSourceProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.ProteinSource) / total
            addedFatProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.AddedFat) / total
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

@Composable
fun CircularFoodTypeStatisticsV2(
    viewModel: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel
) {
    var fruitProgress = 0.0
    var vegetableProgress = 0.0
    var grainProgress = 0.0
    var milkProgress = 0.0
    var proteinSourceProgress = 0.0
    var addedFatProgress = 0.0

    val fruitTarget =
        dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromFruits.toDouble()
    val vegetableTarget =
        dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromVegetables.toDouble()
    val grainTarget =
        dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromGrain.toDouble()
    val milkTarget =
        dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromMilkProducts.toDouble()
    val proteinSourceTarget =
        dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromProteinSource.toDouble()
    val addedFatTarget =
        dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromAddedFat.toDouble()
    val kcalTarget =
        dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble()

    when (kcalTarget) {
        0.0 -> {
            fruitProgress = 0.0
            vegetableProgress = 0.0
            grainProgress = 0.0
            milkProgress = 0.0
            proteinSourceProgress = 0.0
            addedFatProgress = 0.0
        }

        else -> {
            fruitProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.Fruit) / kcalTarget
            vegetableProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.Vegetable) / kcalTarget
            grainProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.Wheet) / kcalTarget
            milkProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.MilkAndReplacement) / kcalTarget
            proteinSourceProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.ProteinSource) / kcalTarget
            addedFatProgress =
                viewModel.returnCurrentKcalForFoodCategory(FoodCategory.AddedFat) / kcalTarget
        }
    }
    Box(contentAlignment = Alignment.Center) {

        CircularProgressIndicator(
            modifier = Modifier,
            progress = (fruitProgress.toFloat()),
            color = Color.Black
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((fruitProgress * 360).toFloat()),
            progress = ((fruitTarget / kcalTarget) - fruitProgress).toFloat(),
            color = Color.LightGray
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((fruitTarget / kcalTarget).toFloat() * 360),
            progress = (vegetableProgress.toFloat()),
            color = Color.Red
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((((fruitTarget / kcalTarget) + vegetableProgress) * 360).toFloat()),
            progress = ((vegetableTarget / kcalTarget) - vegetableProgress).toFloat(),
            color = Color.LightGray
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((((fruitTarget + vegetableTarget) / kcalTarget)).toFloat() * 360),
            progress = (proteinSourceProgress.toFloat()),
            color = Color.Green
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(((((fruitTarget + vegetableTarget) / kcalTarget) + proteinSourceProgress) * 360).toFloat()),
            progress = ((proteinSourceTarget / kcalTarget) - proteinSourceProgress).toFloat(),
            color = Color.LightGray
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((((fruitTarget + vegetableTarget + proteinSourceTarget) / kcalTarget)).toFloat() * 360),
            progress = (grainProgress.toFloat()),
            color = Color.DarkGray
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(((((fruitTarget + vegetableTarget + proteinSourceTarget) / kcalTarget) + grainProgress) * 360).toFloat()),
            progress = ((grainTarget / kcalTarget) - grainProgress).toFloat(),
            color = Color.LightGray
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((((fruitTarget + vegetableTarget + proteinSourceTarget + grainTarget) / kcalTarget)).toFloat() * 360),
            progress = (milkProgress.toFloat()),
            color = Color.Cyan
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(((((fruitTarget + vegetableTarget + proteinSourceTarget + grainTarget) / kcalTarget) + milkProgress) * 360).toFloat()),
            progress = ((milkTarget / kcalTarget) - milkProgress).toFloat(),
            color = Color.LightGray
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate((((fruitTarget + vegetableTarget + proteinSourceTarget + grainTarget + milkTarget) / kcalTarget)).toFloat() * 360),
            progress = (addedFatProgress.toFloat()),
            color = Color.Blue
        )
        CircularProgressIndicator(
            modifier = Modifier.rotate(((((fruitTarget + vegetableTarget + proteinSourceTarget + grainTarget + milkTarget) / kcalTarget) + addedFatProgress) * 360).toFloat()),
            progress = ((addedFatTarget / kcalTarget) - addedFatProgress).toFloat(),
            color = Color.LightGray
        )
    }
}

interface DietStatistics {
    fun returnCurrentKcal(): Double
    fun returnCurrentProtein(): Double
    fun returnCurrentCarbs(): Double
    fun returnCurrentFat(): Double
    fun returnCurrentSoil(): Double
    fun returnCurrentFiber(): Double
    fun returnCurrentPufa(): Double
    fun returnCurrentKcalForFoodCategory(foodType: FoodCategory): Double
}

class DietStatisticItem(
    val shortName: String,
    val current: Double,
    val target: Double,
    val statisticColor: Color
)