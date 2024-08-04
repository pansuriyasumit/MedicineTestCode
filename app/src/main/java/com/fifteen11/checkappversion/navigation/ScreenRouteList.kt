package com.fifteen11.checkappversion.navigation

const val SPLASH_GRAPH_ROUTE = "login_screen"

sealed class Route(
    val routeName: String,
    val title: String? = null
) {
    data object LoginScreen : Route(routeName = "login_screen", title = "Login")
    data object HomeScreen : Route(routeName = "Home_Screen", title = "Home")

    data object DetailsScreen :
        Route(routeName = "Details_Screen/{problemsItem}", title = "Details")

    data object UserLoginHistoryScreen :
        Route(routeName = "Login_History_Screen", title = "Login History")
}