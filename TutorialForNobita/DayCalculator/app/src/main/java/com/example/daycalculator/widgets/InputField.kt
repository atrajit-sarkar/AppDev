package com.example.daycalculator.widgets

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.daycalculator.ui.theme.DayCalculatorTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayCalcInputField(
    modifier: Modifier = Modifier,
    text: String="",
    label: String="",
    maxLine: Int = 1,
    onTextChange: (String) -> Unit={},
    onImeAction: () -> Unit = {},
    placeholder: @Composable ()->Unit={},
    borderColor:Long
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(borderColor)
        ),
        maxLines = maxLine,
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        placeholder = placeholder
    )

}

@Composable
fun DayCalcButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabeld: Boolean = true
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        enabled = enabeld,
        modifier = modifier
    ) {
        Text(text=text)

    }
}

@Preview
@Composable
fun PreviewInputField(){
    DayCalculatorTheme {
//        DayCalcInputField()
    }
}
