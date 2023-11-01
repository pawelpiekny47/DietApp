package com.example.dietapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val protein: Double,
    val carbohydrates: Double,
    val fats: Double,
    val polyunsaturatedFats: Double,
    val soil: Double,
    val fiber: Double,
    val foodCategory: FoodCategory
)
