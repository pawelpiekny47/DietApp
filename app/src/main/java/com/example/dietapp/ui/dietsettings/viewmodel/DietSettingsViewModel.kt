package com.example.dietapp.ui.dietsettings.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.data.DietSettings
import com.example.dietapp.repository.DietSettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.Serializable
import kotlin.math.roundToInt

class DietSettingsViewModel(private var dietSettingsRepository: DietSettingsRepository) :
    ViewModel() {

    var dietSettingsUiState by mutableStateOf(DietSettingsUiState())

    init {
        viewModelScope.launch(Dispatchers.Main) {
            val dataFromDatabase = dietSettingsRepository.getDietSettings()?.first()?.toUiState()
            dietSettingsUiState = dataFromDatabase ?: DietSettingsUiState(DietSettingsDetails())
        }

    }

    fun updateDietSettingsUiState(dietSettingsDetails: DietSettingsDetails) {
        dietSettingsUiState = DietSettingsUiState(dietSettingsDetails = dietSettingsDetails)
    }

    fun generateValuesForGivenKcal() {
        dietSettingsUiState = DietSettingsUiState(
            dietSettingsDetails = DietSettingsDetails(
                totalKcal = dietSettingsUiState.dietSettingsDetails.totalKcal,
                kcalFromFruits = (dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 0.047).roundToInt()
                    .toString(),
                kcalFromVegetables = (dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 0.1).roundToInt()
                    .toString(),
                kcalFromProteinSource = (dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 0.13334).roundToInt()
                    .toString(),
                kcalFromMilkProducts = (dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 0.2).roundToInt()
                    .toString(),
                kcalFromGrain = (dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 0.33334).roundToInt()
                    .toString(),
                kcalFromAddedFat = (dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 0.18).roundToInt()
                    .toString(),
                protein = (dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 0.26 / 4).roundToInt()
                    .toString(),
                carbohydrates = (dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * (1 - ((0.26 + 0.3))) / 4).roundToInt()
                    .toString(),
                fats = (dietSettingsUiState.dietSettingsDetails.totalKcal.toDouble() * 0.3 / 9).roundToInt()
                    .toString(),
                polyunsaturatedFats = "10",
                soil = "5",
                fiber = "25"
            )
        )
    }

    suspend fun saveDietSettingsInDatabase() {
        dietSettingsRepository.saveDietSettings(dietSettingsUiState.toDietSettings())
    }
}

data class DietSettingsUiState(
    val dietSettingsDetails: DietSettingsDetails = DietSettingsDetails()
) : Serializable

data class DietSettingsDetails(
    var totalKcal: String = "0",
    var kcalFromFruits: String = "0",
    var kcalFromVegetables: String = "0",
    var kcalFromProteinSource: String = "0",
    var kcalFromMilkProducts: String = "0",
    var kcalFromGrain: String = "0",
    var kcalFromAddedFat: String = "0",
    val protein: String = "0",
    val carbohydrates: String = "0",
    val fats: String = "0",
    val polyunsaturatedFats: String = "0",
    val soil: String = "0",
    val fiber: String = "0"
)

fun DietSettings.toUiState() = DietSettingsUiState(
    DietSettingsDetails(
        totalKcal = totalKcal.toString(),
        kcalFromFruits = kcalFromFruits.toString(),
        kcalFromVegetables = kcalFromVegetables.toString(),
        kcalFromProteinSource = kcalFromProteinSource.toString(),
        kcalFromMilkProducts = kcalFromMilkProducts.toString(),
        kcalFromGrain = kcalFromGrain.toString(),
        kcalFromAddedFat = kcalFromAddedFat.toString(),
        protein = protein.toString(),
        carbohydrates = carbohydrates.toString(),
        fats = fats.toString(),
        polyunsaturatedFats = polyunsaturatedFats.toString(),
        soil = soil.toString(),
        fiber = fiber.toString(),
    )
)

fun DietSettingsUiState.toDietSettings() = DietSettings(
    dietSettingsId = 1,
    totalKcal = dietSettingsDetails.totalKcal.toInt(),
    kcalFromFruits = dietSettingsDetails.kcalFromFruits.toInt(),
    kcalFromVegetables = dietSettingsDetails.kcalFromVegetables.toInt(),
    kcalFromProteinSource = dietSettingsDetails.kcalFromProteinSource.toInt(),
    kcalFromMilkProducts = dietSettingsDetails.kcalFromMilkProducts.toInt(),
    kcalFromGrain = dietSettingsDetails.kcalFromGrain.toInt(),
    kcalFromAddedFat = dietSettingsDetails.kcalFromAddedFat.toInt(),
    protein = dietSettingsDetails.protein.toInt(),
    carbohydrates = dietSettingsDetails.carbohydrates.toInt(),
    fats = dietSettingsDetails.fats.toInt(),
    polyunsaturatedFats = dietSettingsDetails.polyunsaturatedFats.toInt(),
    soil = dietSettingsDetails.soil.toInt(),
    fiber = dietSettingsDetails.fiber.toInt()
)