package com.example.dietapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BasicMacrosStats(
    kcalTextValue: String,
    proteinTextValue: String,
    carbsTextValue: String,
    fatsTextValue: String
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "kcal",
                modifier = Modifier.scale(0.6F),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = kcalTextValue,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "p",
                modifier = Modifier.scale(0.6F),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = proteinTextValue,
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "c",
                modifier = Modifier.scale(0.6F),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = carbsTextValue,
                modifier = Modifier
                    .padding(Dp(2F)),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "f",
                modifier = Modifier.scale(0.6F),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = fatsTextValue,
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

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        list.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = it.shortName,
                    modifier = Modifier.scale(0.6F),
                    fontStyle = FontStyle.Italic,
                    color = it.statisticColor,
                    style = MaterialTheme.typography.bodySmall
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
fun FoodCategoryMacrosStats(
    fruitTextValue: String,
    vegetableTextValue: String,
    proteinSourceTextValue: String,
    milkTextValue: String,
    grainsTextValue: String,
    addedFatTextValue: String
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "fruit",
                modifier = Modifier.scale(0.6F),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = fruitTextValue,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "vegetable",
                modifier = Modifier.scale(0.6F),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = vegetableTextValue,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "protein source",
                modifier = Modifier.scale(0.6F),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = proteinSourceTextValue,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "milk",
                modifier = Modifier.scale(0.6F),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = milkTextValue,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "grains",
                modifier = Modifier.scale(0.6F),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = grainsTextValue,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "added fats",
                modifier = Modifier.scale(0.6F),
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = addedFatTextValue,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}