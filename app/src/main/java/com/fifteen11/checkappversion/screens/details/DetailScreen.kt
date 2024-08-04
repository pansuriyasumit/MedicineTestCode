package com.fifteen11.checkappversion.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fifteen11.checkappversion.navigation.Route
import com.fifteen11.checkappversion.screens.component.AppBar
import com.fifteen11.checkappversion.screens.component.MedicineCard
import com.fifteen11.checkappversion.data.model.ProblemsItem

@Composable
fun DetailScreen(medicine: ProblemsItem?, onClick: (quote: ProblemsItem) -> Unit = {}) {
    Scaffold(
        topBar = {
            AppBar("${Route.DetailsScreen.title.toString()} - ${medicine?.type}")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MedicineCard(medicine = medicine, onClick)
        }
    }
}
