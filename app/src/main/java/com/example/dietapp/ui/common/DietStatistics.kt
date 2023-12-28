package com.example.dietapp.ui.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.dietapp.R
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DietSettingsStatistic(
    dietStatisticsViewModel: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel,
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
                AllStatistics(dietStatisticsViewModel, dietSettingsViewModel)
            }
        }
        item {
            Column(modifier = Modifier.fillParentMaxWidth()) {
                BasicStatistics(dietStatisticsViewModel, dietSettingsViewModel)
            }
        }
        item {
            Column(modifier = Modifier.fillParentMaxWidth()) {
                FoodTypeStatistics(dietStatisticsViewModel, dietSettingsViewModel)
            }
        }
        item {
            Column(modifier = Modifier.fillParentMaxWidth()) {
                AdditionalInfo(dietStatisticsViewModel, dietSettingsViewModel)
            }
        }
    }
}

@Composable
fun AllStatistics(
    dietStatistics: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val additionalStatistics = mutableListOf(
        DietStatisticItem(
            "pufa",
            dietStatistics.returnCurrentPufa(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.polyunsaturatedFats.toDouble(),
            com.example.dietapp.ui.theme.lightSaturatedFats,
            painterResource(R.drawable.oilfree)
        ),
        DietStatisticItem(
            "soil",
            dietStatistics.returnCurrentSoil(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.soil.toDouble(),
            com.example.dietapp.ui.theme.lightSoil,
            painterResource(R.drawable.salt)
        ),
        DietStatisticItem(
            "fiber",
            dietStatistics.returnCurrentFiber(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fiber.toDouble(),
            com.example.dietapp.ui.theme.lightFiber,
            painterResource(R.drawable.fiber)
        )
    )
    val foodStatistics = mutableListOf(
        DietStatisticItem(
            "fruit",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.Fruit),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromFruits.toDouble(),
            com.example.dietapp.ui.theme.lightFruit,
            painterResource(R.drawable.banana)
        ),
        DietStatisticItem(
            "vegetable",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.Vegetable),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromVegetables.toDouble(),
            com.example.dietapp.ui.theme.lightVegetable,
            painterResource(R.drawable.lettuce)
        ),
        DietStatisticItem(
            "grain",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.Wheet),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromGrain.toDouble(),
            com.example.dietapp.ui.theme.lightGrain,
            painterResource(R.drawable.wheat)
        ),
        DietStatisticItem(
            "milk",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.MilkAndReplacement),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromMilkProducts.toDouble(),
            com.example.dietapp.ui.theme.lightMilk,
            painterResource(R.drawable.milk)
        ),
        DietStatisticItem(
            "protein source",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.ProteinSource),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromProteinSource.toDouble(),
            com.example.dietapp.ui.theme.lightProteinSource,
            painterResource(R.drawable.meat)
        ),
        DietStatisticItem(
            "added fat",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.AddedFat),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromAddedFat.toDouble(),
            com.example.dietapp.ui.theme.lightAddedFat,
            painterResource(R.drawable.oliveoil)
        ),
    )

    val basicStatistics = mutableListOf(
        DietStatisticItem(
            "kcal",
            dietStatistics.returnCurrentKcal(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble(),
            com.example.dietapp.ui.theme.lightKcal,
            painterResource(R.drawable.fire)
        ),
        DietStatisticItem(
            "p",
            dietStatistics.returnCurrentProtein(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble(),
            com.example.dietapp.ui.theme.lightProtein,
            painterResource(R.drawable.meat)
        ),
        DietStatisticItem(
            "c",
            dietStatistics.returnCurrentCarbs(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble(),
            com.example.dietapp.ui.theme.lightCarbs,
            painterResource(R.drawable.wheat)
        ),
        DietStatisticItem(
            "f",
            dietStatistics.returnCurrentFat(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble(),
            com.example.dietapp.ui.theme.lightFats,
            painterResource(R.drawable.oilbottle)
        ),
    )

    var inPercent by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clickable { inPercent = !inPercent }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            BasicMacrosStatsV2(basicStatistics, inPercent)
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clickable { inPercent = !inPercent }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            BasicMacrosStatsV2(foodStatistics, inPercent)
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clickable { inPercent = !inPercent }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            BasicMacrosStatsV2(additionalStatistics, inPercent)
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
            com.example.dietapp.ui.theme.lightKcal,
            painterResource(R.drawable.fire)
        ),
        DietStatisticItem(
            "p",
            dietStatistics.returnCurrentProtein(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.protein.toDouble(),
            com.example.dietapp.ui.theme.lightProtein,
            painterResource(R.drawable.meat)
        ),
        DietStatisticItem(
            "c",
            dietStatistics.returnCurrentCarbs(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.carbohydrates.toDouble(),
            com.example.dietapp.ui.theme.lightCarbs,
            painterResource(R.drawable.wheat)
        ),
        DietStatisticItem(
            "f",
            dietStatistics.returnCurrentFat(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fats.toDouble(),
            com.example.dietapp.ui.theme.lightFats,
            painterResource(R.drawable.oilbottle)
        ),
    )

    var inPercent by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clickable { inPercent = !inPercent }
                .weight(1F),
            contentAlignment = Alignment.Center
        ) {
            BasicMacrosStatsV2(listOfStatisticItems, inPercent)
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
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
            com.example.dietapp.ui.theme.lightFruit,
            painterResource(R.drawable.banana)
        ),
        DietStatisticItem(
            "vegetable",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.Vegetable),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromVegetables.toDouble(),
            com.example.dietapp.ui.theme.lightVegetable,
            painterResource(R.drawable.lettuce)
        ),
        DietStatisticItem(
            "grain",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.Wheet),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromGrain.toDouble(),
            com.example.dietapp.ui.theme.lightGrain,
            painterResource(R.drawable.wheat)
        ),
        DietStatisticItem(
            "milk",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.MilkAndReplacement),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromMilkProducts.toDouble(),
            com.example.dietapp.ui.theme.lightMilk,
            painterResource(R.drawable.milk)
        ),
        DietStatisticItem(
            "protein source",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.ProteinSource),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromProteinSource.toDouble(),
            com.example.dietapp.ui.theme.lightProteinSource,
            painterResource(R.drawable.meat)
        ),
        DietStatisticItem(
            "added fat",
            dietStatistics.returnCurrentKcalForFoodCategory(FoodCategory.AddedFat),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.kcalFromAddedFat.toDouble(),
            com.example.dietapp.ui.theme.lightAddedFat,
            painterResource(R.drawable.oliveoil)
        ),
    )
    var inPercent by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
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
fun AdditionalInfo(
    dietStatistics: DietStatistics,
    dietSettingsViewModel: DietSettingsViewModel
) {
    val listOfStatisticItems = mutableListOf(
        DietStatisticItem(
            "pufa",
            dietStatistics.returnCurrentPufa(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.polyunsaturatedFats.toDouble(),
            com.example.dietapp.ui.theme.lightSaturatedFats,
            painterResource(R.drawable.oilfree)
        ),
        DietStatisticItem(
            "soil",
            dietStatistics.returnCurrentSoil(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.soil.toDouble(),
            com.example.dietapp.ui.theme.lightSoil,
            painterResource(R.drawable.salt)
        ),
        DietStatisticItem(
            "fiber",
            dietStatistics.returnCurrentFiber(),
            dietSettingsViewModel.dietSettingsUiState.dietSettingsDetails.fiber.toDouble(),
            com.example.dietapp.ui.theme.lightFiber,
            painterResource(R.drawable.fiber)
        )
    )
    var inPercent by remember { mutableStateOf(false) }
    var extended2 by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
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
fun LinearBasicStatistics(
    list: MutableList<DietStatisticItem>,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        list.forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(4F),
                    contentAlignment = Alignment.Center
                ) {
                    LinearProgressIndicator(
                        progress = kotlin.math.min((it.current / it.target), 1.0).toFloat(),
                        color = it.statisticColor
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1F)
                        .scale(0.3F),
                    contentAlignment = Alignment.Center
                ) {
                }
            }

        }
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
    val statisticColor: Color,
    val icon: Painter,
)