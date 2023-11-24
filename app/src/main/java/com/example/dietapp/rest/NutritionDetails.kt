package com.example.dietapp.rest

import com.google.gson.annotations.SerializedName


data class ExampleJson2KtKotlin(
    @SerializedName("code") var code: String? = null,
    @SerializedName("product") var product: Product? = Product(),
    @SerializedName("status") var status: Double? = null,
    @SerializedName("status_verbose") var statusVerbose: String? = null
)


data class Nutriments(

    @SerializedName("calcium") var calcium: Double? = null,
    @SerializedName("calcium_100g") var calcium100g: Double? = null,
    @SerializedName("calcium_serving") var calciumServing: Double? = null,
    @SerializedName("calcium_unit") var calciumUnit: String? = null,
    @SerializedName("calcium_value") var calciumValue: Double? = null,
    @SerializedName("carbohydrates") var carbohydrates: Double? = null,
    @SerializedName("carbohydrates_100g") var carbohydrates100g: Double? = null,
    @SerializedName("carbohydrates_serving") var carbohydratesServing: Double? = null,
    @SerializedName("carbohydrates_unit") var carbohydratesUnit: String? = null,
    @SerializedName("carbohydrates_value") var carbohydratesValue: Double? = null,
    @SerializedName("cholesterol") var cholesterol: Double? = null,
    @SerializedName("cholesterol_100g") var cholesterol100g: Double? = null,
    @SerializedName("cholesterol_serving") var cholesterolServing: Double? = null,
    @SerializedName("cholesterol_unit") var cholesterolUnit: String? = null,
    @SerializedName("cholesterol_value") var cholesterolValue: Double? = null,
    @SerializedName("energy") var energy: Double? = null,
    @SerializedName("energy-kcal") var energyKcal: Double? = null,
    @SerializedName("energy-kcal_100g") var energyKcal100g: Double? = null,
    @SerializedName("energy-kcal_serving") var energyKcalServing: Double? = null,
    @SerializedName("energy-kcal_unit") var energyKcalUnit: String? = null,
    @SerializedName("energy-kcal_value") var energyKcalValue: Double? = null,
    @SerializedName("energy-kcal_value_computed") var energyKcalValueComputed: Double? = null,
    @SerializedName("energy_100g") var energy100g: Double? = null,
    @SerializedName("energy_serving") var energyServing: Double? = null,
    @SerializedName("energy_unit") var energyUnit: String? = null,
    @SerializedName("energy_value") var energyValue: Double? = null,
    @SerializedName("fat") var fat: Double? = null,
    @SerializedName("fat_100g") var fat100g: Double? = null,
    @SerializedName("fat_serving") var fatServing: Double? = null,
    @SerializedName("fat_unit") var fatUnit: String? = null,
    @SerializedName("fat_value") var fatValue: Double? = null,
    @SerializedName("fiber") var fiber: Double? = null,
    @SerializedName("fiber_100g") var fiber100g: Double? = null,
    @SerializedName("fiber_serving") var fiberServing: Double? = null,
    @SerializedName("fiber_unit") var fiberUnit: String? = null,
    @SerializedName("fiber_value") var fiberValue: Double? = null,
    @SerializedName("fruits-vegetables-legumes-estimate-from-ingredients_100g") var fruitsVegetablesLegumesEstimateFromIngredients100g: Double? = null,
    @SerializedName("fruits-vegetables-legumes-estimate-from-ingredients_serving") var fruitsVegetablesLegumesEstimateFromIngredientsServing: Double? = null,
    @SerializedName("fruits-vegetables-nuts-estimate-from-ingredients_100g") var fruitsVegetablesNutsEstimateFromIngredients100g: Double? = null,
    @SerializedName("fruits-vegetables-nuts-estimate-from-ingredients_serving") var fruitsVegetablesNutsEstimateFromIngredientsServing: Double? = null,
    @SerializedName("iron") var iron: Double? = null,
    @SerializedName("iron_100g") var iron100g: Double? = null,
    @SerializedName("iron_serving") var ironServing: Double? = null,
    @SerializedName("iron_unit") var ironUnit: String? = null,
    @SerializedName("iron_value") var ironValue: Double? = null,
    @SerializedName("nova-group") var novaGroup: Double? = null,
    @SerializedName("nova-group_100g") var novaGroup100g: Double? = null,
    @SerializedName("nova-group_serving") var novaGroupServing: Double? = null,
    @SerializedName("nutrition-score-fr") var nutritionScoreFr: Double? = null,
    @SerializedName("nutrition-score-fr_100g") var nutritionScoreFr100g: Double? = null,
    @SerializedName("proteins") var proteins: Double? = null,
    @SerializedName("proteins_100g") var proteins100g: Double? = null,
    @SerializedName("proteins_serving") var proteinsServing: Double? = null,
    @SerializedName("proteins_unit") var proteinsUnit: String? = null,
    @SerializedName("proteins_value") var proteinsValue: Double? = null,
    @SerializedName("salt") var salt: Double? = null,
    @SerializedName("salt_100g") var salt100g: Double? = null,
    @SerializedName("salt_serving") var saltServing: Double? = null,
    @SerializedName("salt_unit") var saltUnit: String? = null,
    @SerializedName("salt_value") var saltValue: Double? = null,
    @SerializedName("saturated-fat") var saturatedFat: Double? = null,
    @SerializedName("saturated-fat_100g") var saturatedFat100g: Double? = null,
    @SerializedName("saturated-fat_serving") var saturatedFatServing: Double? = null,
    @SerializedName("saturated-fat_unit") var saturatedFatUnit: String? = null,
    @SerializedName("saturated-fat_value") var saturatedFatValue: Double? = null,
    @SerializedName("sodium") var sodium: Double? = null,
    @SerializedName("sodium_100g") var sodium100g: Double? = null,
    @SerializedName("sodium_serving") var sodiumServing: Double? = null,
    @SerializedName("sodium_unit") var sodiumUnit: String? = null,
    @SerializedName("sodium_value") var sodiumValue: Double? = null,
    @SerializedName("sugars") var sugars: Double? = null,
    @SerializedName("sugars_100g") var sugars100g: Double? = null,
    @SerializedName("sugars_serving") var sugarsServing: Double? = null,
    @SerializedName("sugars_unit") var sugarsUnit: String? = null,
    @SerializedName("sugars_value") var sugarsValue: Double? = null,
    @SerializedName("trans-fat") var transFat: Double? = null,
    @SerializedName("trans-fat_100g") var transFat100g: Double? = null,
    @SerializedName("trans-fat_serving") var transFatServing: Double? = null,
    @SerializedName("trans-fat_unit") var transFatUnit: String? = null,
    @SerializedName("trans-fat_value") var transFatValue: Double? = null,
    @SerializedName("vitamin-a") var vitaminA: Double? = null,
    @SerializedName("vitamin-a_100g") var vitaminA100g: Double? = null,
    @SerializedName("vitamin-a_serving") var vitaminAServing: Double? = null,
    @SerializedName("vitamin-a_unit") var vitaminAUnit: String? = null,
    @SerializedName("vitamin-a_value") var vitaminAValue: Double? = null,
    @SerializedName("vitamin-c") var vitaminC: Double? = null,
    @SerializedName("vitamin-c_100g") var vitaminC100g: Double? = null,
    @SerializedName("vitamin-c_serving") var vitaminCServing: Double? = null,
    @SerializedName("vitamin-c_unit") var vitaminCUnit: String? = null,
    @SerializedName("vitamin-c_value") var vitaminCValue: Double? = null
)


data class Product(
    @SerializedName("nutriments") var nutriments: Nutriments? = Nutriments(),
    @SerializedName("product_name") var productName: String? = null,
)