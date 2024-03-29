package com.example.dietapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.fire),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightKcal
            )
            Text(
                text = "$kcalTextValue ",
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall,
                color = com.example.dietapp.ui.theme.lightKcal
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.meat),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightProtein
            )
            Text(
                text = "$proteinTextValue ",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall,
                color = com.example.dietapp.ui.theme.lightProtein
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.wheat),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightCarbs
            )
            Text(
                text = "$carbsTextValue ",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall,
                color = com.example.dietapp.ui.theme.lightCarbs
            )
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.oilbottle),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightFats
            )
            Text(
                text = "$fatsTextValue ",
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall,
                color = com.example.dietapp.ui.theme.lightFats
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
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.meat),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightProtein
            )
            Text(
                text = (ingredient.ingredientDetails.protein.toDoubleEvenWhenEmpty() * ingredient.amount.toDoubleEvenWhenEmpty() / 100).roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = com.example.dietapp.ui.theme.lightProtein

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.wheat),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightCarbs
            )
            Text(
                text = (ingredient.ingredientDetails.carbohydrates.toDoubleEvenWhenEmpty() * ingredient.amount.toDoubleEvenWhenEmpty() / 100).roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = com.example.dietapp.ui.theme.lightCarbs

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.oilbottle),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightFats
            )
            Text(
                text = (ingredient.ingredientDetails.fats.toDoubleEvenWhenEmpty() * ingredient.amount.toDoubleEvenWhenEmpty() / 100).roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = com.example.dietapp.ui.theme.lightFats

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.fire),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightKcal
            )
            Text(
                text = (ingredient.ingredientDetails.totalKcal.toDoubleEvenWhenEmpty() * ingredient.amount.toDoubleEvenWhenEmpty() / 100).roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = com.example.dietapp.ui.theme.lightKcal

            )
        }
        Text(
            text = "       /${ingredient.amount.toDoubleEvenWhenEmpty().toInt()}g",
            fontSize = 8.sp,
            fontStyle = FontStyle.Italic,
            color = Color.LightGray
        )
    }
}

@Composable
fun MacroDetailsUnderIngredient(ingredient: IngredientWithAmountDetails) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.meat),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightProtein
            )
            Text(
                text = ingredient.ingredientDetails.protein.toDoubleEvenWhenEmpty().roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = com.example.dietapp.ui.theme.lightProtein
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.wheat),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightCarbs
            )
            Text(
                text = ingredient.ingredientDetails.carbohydrates.toDoubleEvenWhenEmpty()
                    .roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = com.example.dietapp.ui.theme.lightCarbs

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.oilbottle),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightFats
            )
            Text(
                text = ingredient.ingredientDetails.fats.toDoubleEvenWhenEmpty().roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = com.example.dietapp.ui.theme.lightFats

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(10.dp, 10.dp),
                painter = painterResource(R.drawable.fire),
                contentDescription = null,
                tint = com.example.dietapp.ui.theme.lightKcal
            )
            Text(
                text = ingredient.ingredientDetails.totalKcal.toDoubleEvenWhenEmpty().roundToInt()
                    .toString(),
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = com.example.dietapp.ui.theme.lightKcal

            )
        }
        Text(
            text = "       /100g",
            fontSize = 8.sp,
            fontStyle = FontStyle.Italic,
            color = Color.LightGray
            )
    }
}