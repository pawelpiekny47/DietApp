package com.example.dietapp.rest

import retrofit2.http.GET
import retrofit2.http.Path


interface NutritionApi {
    @GET("{barcode}?fields=nutriments,product_name,image_url,quantity")
    suspend fun getData(@Path("barcode") barcode: String): ExampleJson2KtKotlin
}