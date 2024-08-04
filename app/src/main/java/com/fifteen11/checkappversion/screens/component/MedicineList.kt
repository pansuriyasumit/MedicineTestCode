package com.fifteen11.checkappversion.screens.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.fifteen11.checkappversion.data.model.ProblemsItem

@Composable
fun MedicineList(medicine: List<ProblemsItem?>?, onClick: (problemId: Int) -> Unit) {

    LazyColumn(content = {
        items(items = medicine ?: emptyList(), itemContent = { medicine ->
            MedicineCard(medicine = medicine, onClick)
        })
    })
}