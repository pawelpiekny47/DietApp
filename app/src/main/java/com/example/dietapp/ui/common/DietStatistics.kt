package com.example.dietapp.ui.common

import android.graphics.Paint.Align
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        modifier = Modifier.fillMaxSize(),
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
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.weight(1F))
        Column(
            modifier = Modifier
                .weight(3F)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            list.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1F),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier.weight(5F),
                        progress = kotlin.math.min((it.current / it.target), 1.0).toFloat(),
                        color = it.statisticColor
                    )
                    Box(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxSize(), contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = "info",
                            tint = when {
                                (it.current / it.target) < 0.7 -> Color.LightGray
                                (it.current / it.target) < 0.9 -> Color.Green
                                (it.current / it.target) < 1.05 -> Color.Yellow
                                (it.current / it.target) < 1.15 -> Color.Red

                                else -> Color.Red
                            },
                            modifier = Modifier
                                .scale(0.5F),
                        )
                    }
                }

            }
        }
        Box(modifier = Modifier.weight(1F))
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