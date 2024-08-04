package com.fifteen11.checkappversion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fifteen11.checkappversion.data.model.ProblemsItem
import com.fifteen11.checkappversion.screens.details.DetailScreen
import com.fifteen11.checkappversion.screens.history.UserLoginHistory
import com.fifteen11.checkappversion.screens.home.HomeScreen
import com.fifteen11.checkappversion.screens.login.LoginScreen
import com.google.gson.Gson

@Composable
fun SetupNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SPLASH_GRAPH_ROUTE
    ) {
        composable(route = Route.LoginScreen.routeName) {
            LoginScreen(
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
                navController = navController
            )
        }

        composable(
            route = "${Route.DetailsScreen.routeName}?problemItem={problemItem}",
            arguments = listOf(
                navArgument(name = "problemItem") {
                    type = NavType.StringType
                    defaultValue = ""
                },
            )
        ) {
            val problemItemJson = it.arguments?.getString("problemItem")
            val problemItem = Gson().fromJson(problemItemJson, ProblemsItem::class.java)
            DetailScreen(problemItem = problemItem)
        }

        composable(route = Route.UserLoginHistoryScreen.routeName) {
            UserLoginHistory()
        }
    }
}