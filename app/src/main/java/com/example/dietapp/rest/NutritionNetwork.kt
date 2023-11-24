package com.example.dietapp.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NutritionNetwork {
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/api/v2/product/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NutritionApi::class.java)
    }
}