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
    statisticType: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val totalKcal = statisticType.returnTotalKcal()
    val totalProteins = statisticType.returnTotalProtein()
    val totalCarbs = statisticType.returnTotalCarbs()
    val totalFat = statisticType.returnTotalFat()

    var extended by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }


    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clickable { extended = !extended }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {

            val kcal: String
            val proteins: String
            val carbs: String
            val fats: String

            if (extended) {
                kcal =
                    "${totalKcal.toInt()}"
                proteins =
                    "${totalProteins.toInt()}"
                carbs =
                    "${totalCarbs.toInt()}"
                fats =
                    "${totalFat.toInt()}"

            } else {
                kcal =
                    "${((totalKcal / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble()) * 100).toInt()}%"
                proteins =
                    "${((totalProteins / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble()) * 100).toInt()}%"
                carbs =
                    "${((totalCarbs / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble()) * 100).toInt()}%"
                fats =
                    "${((totalFat / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble()) * 100).toInt()}%"
            }
            BasicMacrosStatsV2(kcal, proteins, carbs, fats)
        }

        Box(
            modifier = Modifier
                .clickable { extended2 = !extended2 }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            if (extended2) LinearBasicStatistics(statisticType, dietSettingsViewModel)
            else CircularBasicStatisticsV2(statisticType, dietSettingsViewModel)
        }
    }
}


@Composable
fun FoodTypeStatistics(
    viewModel: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val totalKcalFruit = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Fruit)
    val totalKcalVegetable = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Vegetable)
    val totalKcalWheet = viewModel.returnTotalKcalForFoodCategory(FoodCategory.Wheet)
    val totalKcalMilk = viewModel.returnTotalKcalForFoodCategory(FoodCategory.MilkAndReplacement)
    val totalKcalProteinSource =
        viewModel.returnTotalKcalForFoodCategory(FoodCategory.ProteinSource)
    val totalKcalAddFat = viewModel.returnTotalKcalForFoodCategory(FoodCategory.AddedFat)

    var extended by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clickable { extended = !extended }
                .weight(1F)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            val fruit: String
            val vegetable: String
            val proteinSource: String
            val milk: String
            val grains: String
            val addedFats: String

            when (extended) {
                true -> {
                    fruit = "${totalKcalFruit.toInt()}"
                    vegetable = "${totalKcalVegetable.toInt()}"
                    proteinSource = "${totalKcalProteinSource.toInt()}"
                    milk = "${totalKcalMilk.toInt()}"
                    grains = "${totalKcalWheet.toInt()}"
                    addedFats = "${totalKcalAddFat.toInt()}"
                }

                false -> {
                    fruit =
                        "${(totalKcalFruit / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromFruits.toDouble() * 100).toInt()}%"
                    vegetable =
                        "${(totalKcalVegetable / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromVegetables.toDouble() * 100).toInt()}%"
                    proteinSource =
                        "${(totalKcalProteinSource / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromProteinSource.toDouble() * 100).toInt()}%"
                    milk =
                        "${(totalKcalMilk / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromMilkProducts.toDouble() * 100).toInt()}%"
                    grains =
                        "${(totalKcalWheet / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromGrain.toDouble() * 100).toInt()}%"
                    addedFats =
                        "${(totalKcalAddFat / dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromAddedFat.toDouble() * 100).toInt()}%"
                }
            }
            FoodCategoryMacrosStats(fruit, vegetable, proteinSource, milk, grains, addedFats)
        }

        Box(
            modifier = Modifier
                .clickable { extended2 = !extended2 }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            if (extended2) CircularFoodTypeStatistics(viewModel)
            else CircularFoodTypeStatisticsV2(viewModel, dietSettingsViewModel)
        }

    }
}

@Composable
fun AdditionalInfo(
    viewModel: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val totalPufa = viewModel.returnTotalPufa()
    val totalSoil = viewModel.returnTotalSoil()
    val totalFiber = viewModel.returnTotalFiber()

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
    viewModel: DietStatistics,
) {
    var totalKcal = viewModel.returnTotalKcal()
    var proteinProgress = 0.0
    var carbsProgress = 0.0
    var fatProgress = 0.0
    val totalProgress =
        (viewModel.returnTotalProtein() * 4 + viewModel.returnTotalFat() * 9 + viewModel.returnTotalCarbs() * 4)

    when (totalProgress) {
        0.0 -> {
            proteinProgress = 0.0
            carbsProgress = 0.0
            fatProgress = 0.0
        }

        else -> {
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

    kcal = viewModel.returnTotalKcal()
    when (kcalCalculatedTarget) {
        0.0 -> {
            proteinProgress = 0.0
            carbsProgress = 0.0
            fatProgress = 0.0
            kcalCalculatedTarget = 1.0

        }

        else -> {
            proteinProgress = viewModel.returnTotalProtein() * 4 / kcalCalculatedTarget
            carbsProgress = viewModel.returnTotalCarbs() * 4 / kcalCalculatedTarget
            fatProgress = viewModel.returnTotalFat() * 9 / kcalCalculatedTarget
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
fun LinearBasicStatistics(
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

    kcal = viewModel.returnTotalKcal()
    when (kcalCalculatedTarget) {
        0.0 -> {
            proteinProgress = 0.0
            carbsProgress = 0.0
            fatProgress = 0.0
            kcalCalculatedTarget = 1.0

        }

        else -> {
            proteinProgress = viewModel.returnTotalProtein() * 4 / kcalCalculatedTarget
            carbsProgress = viewModel.returnTotalCarbs() * 4 / kcalCalculatedTarget
            fatProgress = viewModel.returnTotalFat() * 9 / kcalCalculatedTarget
        }
    }




    Column(verticalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier.padding(3.dp, 3.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = proteinProgress.toFloat(),
                color = Color.Black
            )
            LinearProgressIndicator(
                progress = ((proteinTarget / kcalCalculatedTarget) - proteinProgress).toFloat(),
                color = Color.LightGray
            )
            LinearProgressIndicator(
                progress = carbsProgress.toFloat(),
                color = Color.Red
            )
            LinearProgressIndicator(
                progress = ((carbsTarget / kcalCalculatedTarget) - carbsProgress).toFloat(),
                color = Color.LightGray
            )
            LinearProgressIndicator(
                progress = fatProgress.toFloat(),
                color = Color.Green
            )
            LinearProgressIndicator(
                progress = (fatTarget / kcalCalculatedTarget - fatProgress).toFloat(),
                color = Color.LightGray
            )
        }
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

    total = FoodCategory.values().sumOf { viewModel.returnTotalKcalForFoodCategory(it) }
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
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.Fruit) / total
            vegetableProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.Vegetable) / total
            grainProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.Wheet) / total
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
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.Fruit) / kcalTarget
            vegetableProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.Vegetable) / kcalTarget
            grainProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.Wheet) / kcalTarget
            milkProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.MilkAndReplacement) / kcalTarget
            proteinSourceProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.ProteinSource) / kcalTarget
            addedFatProgress =
                viewModel.returnTotalKcalForFoodCategory(FoodCategory.AddedFat) / kcalTarget
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
    fun returnTotalKcal(): Double
    fun returnTotalProtein(): Double
    fun returnTotalCarbs(): Double
    fun returnTotalFat(): Double
    fun returnTotalSoil(): Double
    fun returnTotalFiber(): Double
    fun returnTotalPufa(): Double
    fun returnTotalKcalForFoodCategory(foodType: FoodCategory): Double

}