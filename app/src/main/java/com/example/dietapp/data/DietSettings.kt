package com.example.dietapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diet_settings")
data class DietSettings(

    @PrimaryKey(autoGenerate = true)
    val dietSettingsId: Int = 0,
    var totalKcal: Int = 0,
    var kcalFromFruits: Int = 0,
    var kcalFromVegetables: Int = 0,
    var kcalFromProteinSource: Int = 0,
    var kcalFromMilkProducts: Int = 0,
    var kcalFromGrain: Int = 0,
    var kcalFromAddedFat: Int = 0,
    val protein: Int = 0,
    val carbohydrates: Int = 0,
    val fats: Int = 0,
    val polyunsaturatedFats: Int = 0,
    val soil: Int = 0,
    val fiber: Int = 0)