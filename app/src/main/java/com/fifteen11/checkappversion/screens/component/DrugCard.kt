package com.fifteen11.checkappversion.screens.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fifteen11.checkappversion.R
import com.fifteen11.checkappversion.ui.theme.BlackColor
import com.fifteen11.checkappversion.ui.theme.SecondaryColor
import com.fifteen11.checkappversion.data.model.DrugsNameItem

@Composable
fun DrugCard(drugs: DrugsNameItem?) {
    Column {
        drugs?.associatedDrugType1?.forEach { drug ->
            DrugsDetails(drug?.name.toString(), drug?.dose, drug?.strength)
        }

        drugs?.associatedDrugType2?.forEach { drug ->
            DrugsDetails(drug?.name.toString(), drug?.dose, drug?.strength)
        }
    }
}

@Composable
fun DrugsDetails(name: String, dose: String?, strength: String?) {
    Text(
        text = "• Drugs: $name",
        fontFamily = FontFamily(Font(R.font.lato_bold)),
        letterSpacing = 1.sp,
        fontSize = 16.sp,
        color = SecondaryColor,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
    Text(
        text = "Dose: $dose",
        fontFamily = FontFamily(Font(R.font.lato_regular)),
        letterSpacing = 1.sp,
        fontSize = 14.sp,
        modifier = Modifier.padding(top = 1.dp, bottom = 1.dp),
        color = BlackColor,
        style = MaterialTheme.typography.labelLarge
    )
    Text(
        text = "Strength: $strength",
        fontFamily = FontFamily(Font(R.font.lato_regular)),
        modifier = Modifier.padding(top = 1.dp, bottom = 1.dp),
        letterSpacing = 1.sp,
        fontSize = 14.sp,
        color = BlackColor,
        style = MaterialTheme.typography.labelLarge
    )
}