package com.example.dietapp.ui.dietsettings.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.OutlinedTextFieldDecorationBox
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dietapp.ui.dietsettings.viewmodel.DietSettingsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DietGenerator(viewModel: DietSettingsViewModel) {
    val coroutineScope = rememberCoroutineScope()
    Column {
        DietGeneratorFields(
        )
        Row()
        {
            Button(
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) { viewModel.saveDietSettingsInDatabase() }
                },
                shape = MaterialTheme.shapes.small,
            ) {
                Text(text = "Save")
            }
        }
    }
}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietGeneratorFields() {
    var sliderPosition by remember { mutableStateOf(0f) }
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.fillMaxWidth()) {
        Row {
            BasicTextField(
                value = "1500",
                onValueChange = { },
                enabled = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                modifier = Modifier.width(IntrinsicSize.Min).height(IntrinsicSize.Min)

            ){
                val interactionSource = remember { MutableInteractionSource() }

                OutlinedTextFieldDecorationBox(
                    value = "text",
                    visualTransformation = VisualTransformation.None,
                    innerTextField = it,
                    enabled = true,
                    interactionSource = interactionSource,
                    // keep vertical paddings but change the horizontal
                    contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                        start = 8.dp, end = 8.dp, bottom = 2.dp
                    ),
                    colors = outlinedTextFieldColors(),
                    singleLine = false,
                )
            }

            TextField(
                value = "80",
                onValueChange = { },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
                label = { Text("weight") },
                enabled = true,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
            )
        }
        TextField(
            value = "2",
            onValueChange = { },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus()}),
            label = { Text("protein g/kg") },
            enabled = true,
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent)
        )
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "fruit: ")
            Slider(
                value = sliderPosition,
                onValueChange = { sliderPosition = it }
            )
            Text(text = "fruit: ")
        }
    }

}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietGeneratorPreview() {
    DietGeneratorFields()
}