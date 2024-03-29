package com.example.dietapp.ui.ingredient.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dietapp.data.FoodCategory
import com.example.dietapp.data.Ingredient
import com.example.dietapp.repository.IngredientRepository
import com.example.dietapp.rest.ExampleJson2KtKotlin
import com.example.dietapp.rest.NutritionNetwork
import com.example.dietapp.rest.Product
import com.example.dietapp.ui.dish.viewmodel.IngredientWithAmountDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException

class IngredientViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {
    //Retrofit response / query
    val myResponse: MutableLiveData<ExampleJson2KtKotlin> = MutableLiveData()
    var product by mutableStateOf(Product())
    var pictureUrl by mutableStateOf("")

    var deleteButtonVisible by mutableStateOf(true)
    var ingredientUiState by mutableStateOf(IngredientUiState())
        private set

    val ingredientListUiState: StateFlow<IngredientListUiState> =
        ingredientRepository.getAll().map { IngredientListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = IngredientListUiState()
            )

    fun updateUiState(ingredientDetails: IngredientDetails) {
        ingredientUiState =
            IngredientUiState(ingredientDetails = ingredientDetails)
        pictureUrl = ""
    }

    fun resetUiState() {
        ingredientUiState = IngredientUiState()
        pictureUrl = ""
    }

    suspend fun saveItem() {
        if (ingredientUiState.ingredientDetails.id == 0)
            ingredientRepository.insertItem(ingredientUiState.ingredientDetails.toIngredient())
        else ingredientRepository.updateItem(ingredientUiState.ingredientDetails.toIngredient())

    }

    suspend fun deleteItem() {
        ingredientRepository.delete(ingredientUiState.ingredientDetails.toIngredient())
    }

    fun updateUiWithBarcode(barcode: String?) {
        if (barcode != null) {
            viewModelScope.launch {
                try {
                    myResponse.value = NutritionNetwork.retrofit.getData(barcode)
                    if (myResponse.value!!.status != 0.0) {
                        product = myResponse.value!!.product!!
                        updateUiState(
                            IngredientDetails(
                                id = ingredientUiState.ingredientDetails.id,
                                name = product.productName ?: "",
                                totalKcal = product.nutriments?.energyKcal100g.toString(),
                                protein = product.nutriments?.proteins100g.toString(),
                                carbohydrates = product.nutriments?.carbohydrates100g.toString(),
                                fats = product.nutriments?.fat100g.toString(),
                                polyunsaturatedFats = product.nutriments?.saturatedFat100g.toString(),
                                soil = product.nutriments?.salt100g.toString(),
                                fiber = product.nutriments?.fiber100g.toString(),
                            )
                        )
                        pictureUrl = myResponse.value!!.product!!.imageUrl ?: ""
                    }
                } catch (e: HttpException) {
                    updateUiState(
                        IngredientDetails(
                            name = product.productName ?: "not found ;c",
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class IngredientUiState(val ingredientDetails: IngredientDetails = IngredientDetails())

data class IngredientListUiState(val ingredientList: List<Ingredient> = listOf())

data class IngredientDetails(
    val id: Int = 0,
    val name: String = "",
    val totalKcal: String = "",
    val protein: String = "",
    val carbohydrates: String = "",
    val fats: String = "",
    val polyunsaturatedFats: String = "",
    val soil: String = "",
    val fiber: String = "",
    val foodCategory: FoodCategory = FoodCategory.Vegetable
)

fun IngredientDetails.toIngredient(): Ingredient {
    return Ingredient(
        ingredientId = id,
        name,
        totalKcal.toDoubleOrNull() ?: 0.0,
        protein.toDoubleOrNull() ?: 0.0,
        carbohydrates.toDoubleOrNull() ?: 0.0,
        fats.toDoubleOrNull() ?: 0.0,
        polyunsaturatedFats.toDoubleOrNull() ?: 0.0,
        soil.toDoubleOrNull() ?: 0.0,
        fiber.toDoubleOrNull() ?: 0.0,
        foodCategory
    )
}

fun IngredientDetails.toIngredientWithAmountDetails(): IngredientWithAmountDetails {
    return IngredientWithAmountDetails(this, "")
}

fun Ingredient.toIngredientDetails(): IngredientDetails {
    return IngredientDetails(
        this.ingredientId,
        this.name,
        this.totalKcal.toString(),
        this.protein.toString(),
        this.carbohydrates.toString(),
        this.fats.toString(),
        this.polyunsaturatedFats.toString(),
        this.soil.toString(),
        this.fiber.toString(),
        this.foodCategory
    )
}