package com.fifteen11.checkappversion.screens.home

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.PowerSettingsNew
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fifteen11.checkappversion.R
import com.fifteen11.checkappversion.data.model.ProblemsItem
import com.fifteen11.checkappversion.navigation.Route
import com.fifteen11.checkappversion.screens.component.AppBar
import com.fifteen11.checkappversion.screens.component.LockScreenOrientation
import com.fifteen11.checkappversion.screens.component.MedicineList
import com.fifteen11.checkappversion.ui.theme.SecondaryColor
import com.fifteen11.checkappversion.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onMedicineClick: (ProblemsItem) -> Unit,
    onHistoryClick: () -> Unit,
    onLogout: () -> Unit
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val userName = remember { homeViewModel.getUserPreferenceData().toString() }
    val greetingMessage = remember { homeViewModel.getGreetingMessage() }
    val medicineResponse = homeViewModel.medicines.collectAsState().value

    Scaffold(
        topBar = {
            AppBar(Route.HomeScreen.title.toString(), isShowIcon = true, onClick = {
                onHistoryClick()
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onLogout()
                homeViewModel.clearUserData()
            }) {
                Icon(imageVector = Icons.TwoTone.PowerSettingsNew, contentDescription = "Logout")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GreetingText(
                "$greetingMessage, $userName!"
            )
            Spacer(modifier = Modifier.height(16.dp))

            MedicineList(medicine = medicineResponse.medicines.problems ?: listOf()) {
                onMedicineClick(it)
            }
        }
    }

    val context = LocalContext.current
    BackHandler { (context as? Activity)?.finish() }
}

@Composable
fun GreetingText(text: String) {
    Text(
        text = text,
        fontFamily = FontFamily(Font(R.font.lato_bold)),
        letterSpacing = 1.sp,
        fontSize = 18.sp,
        color = SecondaryColor,
        style = MaterialTheme.typography.labelLarge
    )
}
