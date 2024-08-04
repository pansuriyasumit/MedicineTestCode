package com.fifteen11.checkappversion.screens.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fifteen11.checkappversion.data.model.ProblemsItem
import com.fifteen11.checkappversion.ui.theme.DisabledColor
import com.fifteen11.checkappversion.ui.theme.ProblemCardTitle

@Composable
fun MedicineCard(medicine: ProblemsItem?, onClick: (problem: ProblemsItem) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(medicine ?: ProblemsItem()) },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${medicine?.id ?: -1}", style = ProblemCardTitle
            )
            Text(
                text = "Disease: ${medicine?.type ?: "Unknown"}", style = ProblemCardTitle
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = DisabledColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
            )
            ProblemCard(medicine)
        }
    }
}