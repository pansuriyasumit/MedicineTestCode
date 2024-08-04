package com.fifteen11.checkappversion.screens.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.fifteen11.checkappversion.ui.theme.AppColor
import com.fifteen11.checkappversion.ui.theme.SecondaryColor

@Composable
fun OutlinedTextFieldComponent(
    labelText: String,
    placeholderText: String,
    leadingIcon: ImageVector,
    trailingIcon: @Composable (() -> Unit)?,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    keyboardActions: KeyboardActions,
    fontFamily: FontFamily,
    singleLine: Boolean,
    visualTransformation: VisualTransformation,
    modifier: Modifier,
    onValueChange: (value: String) -> Unit = {}

) {
    var textValue by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null
            )
        },
        trailingIcon = trailingIcon,
        value = textValue,
        onValueChange = {
            textValue = it
            onValueChange(it)
        },
        shape = RoundedCornerShape(12.dp),
        label = {
            Text(
                labelText,
                color = SecondaryColor,
                style = MaterialTheme.typography.labelMedium,
                fontFamily = fontFamily
            )
        },
        visualTransformation = visualTransformation,
        placeholder = {
            Text(
                text = placeholderText,
                fontFamily = fontFamily
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppColor,
            unfocusedBorderColor = AppColor
        ),
        singleLine = singleLine,
        modifier = modifier,
        keyboardActions = keyboardActions
    )

}