package com.fifteen11.checkappversion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.fifteen11.checkappversion.navigation.SetupNavGraph
import com.fifteen11.checkappversion.ui.theme.CheckAppVersionTheme
import com.fifteen11.checkappversion.viewmodel.HomeViewModel
import com.fifteen11.checkappversion.viewmodel.LoginViewModel
import com.fifteen11.checkappversion.viewmodel.MainViewModel
import com.fifteen11.checkappversion.viewmodel.UserHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var navController: NavHostController
    private val loginViewModel by viewModels<LoginViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()
    private val historyViewModel by viewModels<UserHistoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSplashScreen()

        setContent {
            MyApp {
                navController = rememberNavController()
                SetupNavGraph(
                    navController,
                    loginViewModel,
                    homeViewModel = homeViewModel,
                    historyViewModel = historyViewModel
                )
            }
        }
    }

    private fun setupSplashScreen() {
        val splashScreen = installSplashScreen().apply {
            setKeepOnScreenCondition {
                !viewModel.isLoadingState.value
            }
        }

        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            splashScreenViewProvider.view.animate()
                .alpha(0f)
                .setDuration(500L)
                .withEndAction {
                    splashScreenViewProvider.remove()
                }
                .start()
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    CheckAppVersionTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}