package com.example.dietapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "ingredient")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Int = 0,

    val name: String,
    val totalKcal: Double,
    val protein: Double,
    val carbohydrates: Double,
    val fats: Double,
    val polyunsaturatedFats: Double,
    val soil: Double,
    val fiber: Double,
    val foodCategory: FoodCategory
)
