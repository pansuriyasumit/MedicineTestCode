package com.fifteen11.checkappversion.screens.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.fifteen11.checkappversion.R
import com.fifteen11.checkappversion.ui.theme.SecondaryColor

@Composable
fun SecondaryTextButton(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            letterSpacing = 1.sp,
            fontSize = 18.sp,
            color = SecondaryColor,
            fontFamily = FontFamily(Font(R.font.lato_bold)),
            style = MaterialTheme.typography.labelLarge
        )
    }
}