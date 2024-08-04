package com.fifteen11.checkappversion.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fifteen11.checkappversion.data.model.ProblemsItem
import com.fifteen11.checkappversion.navigation.Route
import com.fifteen11.checkappversion.screens.component.AppBar
import com.fifteen11.checkappversion.screens.component.MedicineCard

@Composable
fun DetailScreen(
    problemItem: ProblemsItem?,
    onClick: (problemId: Int) -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppBar("${Route.DetailsScreen.title.toString()} - ${problemItem?.type}")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MedicineCard(medicine = problemItem, onClick)
        }
    }
}
