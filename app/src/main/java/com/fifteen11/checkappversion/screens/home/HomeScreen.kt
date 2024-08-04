package com.fifteen11.checkappversion.screens.home

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.PowerSettingsNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.fifteen11.checkappversion.R
import com.fifteen11.checkappversion.navigation.Route
import com.fifteen11.checkappversion.screens.component.AppBar
import com.fifteen11.checkappversion.screens.component.LockScreenOrientation
import com.fifteen11.checkappversion.screens.component.MedicineList
import com.fifteen11.checkappversion.ui.theme.SecondaryColor
import com.fifteen11.checkappversion.viewmodel.HomeViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if (homeViewModel.medicines.value.medicines?.problems == null) {
            homeViewModel.fetchMedicines()
        }
     }

    val userName = remember { homeViewModel.getUserPreferenceData().toString() }
    val greetingMessage = remember { homeViewModel.getGreetingMessage() }
    val medicineResponse = homeViewModel.medicines.collectAsState().value

    Scaffold(
        topBar = {
            AppBar(Route.HomeScreen.title.toString(), isShowIcon = true, onClick = {
                navController.navigate(Route.UserLoginHistoryScreen.routeName)
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                homeViewModel.clearUserData()
                navController.navigate(route = Route.LoginScreen.routeName) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
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

            if (medicineResponse.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(1.0f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(44.dp),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )

                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Loading...",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.lato_bold))
                        )
                    }
                }
            } else if (medicineResponse.error?.isNotEmpty() == true) {
                ErrorText(medicineResponse.error)
            } else {
                val medicineData = medicineResponse.medicines?.problems
                MedicineList(medicine = medicineData ?: listOf()) {
                    coroutineScope.launch {
                        val problemItem = medicineData?.first { problemId -> problemId.id == it }
                        val problemItemJson = Gson().toJson(problemItem)
                        navController.navigate(
                            "${Route.DetailsScreen.routeName}?problemItem=${problemItemJson}"
                        )
                    }
                }
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

@Composable
fun ErrorText(text: String) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.lato_bold)),
            letterSpacing = 1.sp,
            fontSize = 18.sp,
            color = SecondaryColor,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
