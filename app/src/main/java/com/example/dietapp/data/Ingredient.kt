package com.example.dietapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(tableName = "ingredients")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val protein: BigDecimal,
    val carbohydrates: BigDecimal,
    val fats: BigDecimal,
    val polyunsaturatedFats: BigDecimal,
    val soil: BigDecimal,
    val fiber: BigDecimal,
    val foodCategory: FoodCategory
)
