package com.example.dietapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dish")
data class Dish(
    @PrimaryKey(autoGenerate = true)
    val dishId: Int = 0,

    val name: String,
    val description: String,
)
