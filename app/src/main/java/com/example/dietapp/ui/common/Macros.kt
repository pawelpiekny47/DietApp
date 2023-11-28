package com.example.dietapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dietapp.R
import com.example.dietapp.ui.day.viewmodel.toDoubleEvenWhenEmpty
import com.example.dietapp.ui.dish.viewmodel.IngredientWithAmountDetails
import kotlin.math.roundToInt

@Composable
fun BasicMacrosStats(
    kcalTextValue: String,
    proteinTextValue: String,
    carbsTextValue: String,
    fatsTextValue: String
) {

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.fire),
                contentDescription = null
            )
            Text(
                text = "$kcalTextValue ",
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.meat),
                contentDescription = null
            )
            Text(
                text = "$proteinTextValue ",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.wheat),
                contentDescription = null
            )
            Text(
                text = "$carbsTextValue ",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.oilbottle),
                contentDescription = null
            )
            Text(
                text = "$fatsTextValue ",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun BasicMacrosStatsV2(
    list: MutableList<DietStatisticItem>,
    percent: Boolean
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        list.forEach {
            Row(
                modifier = Modifier
                    .weight(1F),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp, 10.dp)
                        .padding(5.dp, 0.dp),
                    tint = it.statisticColor,
                    painter = it.icon,
                    contentDescription = null
                )
                Text(
                    text = if (percent) {
                        ((it.current / it.target) * 100).toInt().toString()
                    } else "${it.current.toInt()}",
                    fontStyle = FontStyle.Italic,
                    color = it.statisticColor,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if (percent) "%" else "${it.target.toInt()}",
                    modifier = Modifier.scale(0.6F),
                    fontStyle = FontStyle.Italic,
                    color = it.statisticColor,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun MacroDetailsUnderIngredientXAmount(ingredient: IngredientWithAmountDetails) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp, 0.dp),
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.meat),
                contentDescription = null
            )
            Text(
                text = (ingredient.ingredientDetails.protein.toDoubleEvenWhenEmpty() * ingredient.amount.toDoubleEvenWhenEmpty() / 100).roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp, 0.dp),
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.wheat),
                contentDescription = null
            )
            Text(
                text = (ingredient.ingredientDetails.carbohydrates.toDoubleEvenWhenEmpty() * ingredient.amount.toDoubleEvenWhenEmpty() / 100).roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp, 0.dp),
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.oilbottle),
                contentDescription = null
            )
            Text(
                text = (ingredient.ingredientDetails.fats.toDoubleEvenWhenEmpty() * ingredient.amount.toDoubleEvenWhenEmpty() / 100).roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp, 0.dp),
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.fire),
                contentDescription = null
            )
            Text(
                text = (ingredient.ingredientDetails.totalKcal.toDoubleEvenWhenEmpty() * ingredient.amount.toDoubleEvenWhenEmpty() / 100).roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
            )
        }
    }
}

@Composable
fun MacroDetailsUnderIngredient(ingredient: IngredientWithAmountDetails) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp, 0.dp),
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.meat),
                contentDescription = null
            )
            Text(
                text = ingredient.ingredientDetails.protein.toDoubleEvenWhenEmpty().roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp, 0.dp),
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.wheat),
                contentDescription = null
            )
            Text(
                text = ingredient.ingredientDetails.carbohydrates.toDoubleEvenWhenEmpty()
                    .roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp, 0.dp),
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.oilbottle),
                contentDescription = null
            )
            Text(
                text = ingredient.ingredientDetails.fats.toDoubleEvenWhenEmpty().roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp, 0.dp),
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.fire),
                contentDescription = null
            )
            Text(
                text = ingredient.ingredientDetails.totalKcal.toDoubleEvenWhenEmpty().roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
            )
        }
    }
}