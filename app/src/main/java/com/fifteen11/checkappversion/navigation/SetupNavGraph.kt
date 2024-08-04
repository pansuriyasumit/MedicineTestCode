package com.fifteen11.checkappversion.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fifteen11.checkappversion.screens.details.DetailScreen
import com.fifteen11.checkappversion.screens.history.UserLoginHistory
import com.fifteen11.checkappversion.screens.home.HomeScreen
import com.fifteen11.checkappversion.screens.login.LoginScreen
import com.fifteen11.checkappversion.viewmodel.HomeViewModel
import com.fifteen11.checkappversion.viewmodel.LoginViewModel
import com.fifteen11.checkappversion.viewmodel.UserHistoryViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    homeViewModel: HomeViewModel,
    historyViewModel: UserHistoryViewModel
) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_GRAPH_ROUTE
    ) {
        composable(route = Route.LoginScreen.routeName) {
            LoginScreen(
                loginViewModel = loginViewModel,
                onLogin = {
                    navController.navigate(Route.HomeScreen.routeName) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                })
        }

        composable(route = Route.HomeScreen.routeName) {
            HomeScreen(
                homeViewModel = homeViewModel,
                onMedicineClick = { problemItems ->
                    navController.navigate("detail/${problemItems.id}")
                },
                onHistoryClick = {
                    navController.navigate(Route.UserLoginHistoryScreen.routeName)
                },
                onLogout = {
                    navController.navigate(route = Route.LoginScreen.routeName) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable("detail/{medicineId}") { backStackEntry ->
            val medicineId = backStackEntry.arguments?.getString("medicineId")
            val medicine =
                homeViewModel.medicines.collectAsState().value.medicines.problems?.first {
                    (it?.id) == medicineId?.toInt()
                }
            DetailScreen(medicine)
        }

        composable(route = Route.UserLoginHistoryScreen.routeName) {
            UserLoginHistory(historyViewModel = historyViewModel)
        }
    }
}