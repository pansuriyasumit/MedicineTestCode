package com.fifteen11.checkappversion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.fifteen11.checkappversion.navigation.SetupNavGraph
import com.fifteen11.checkappversion.ui.theme.CheckAppVersionTheme
import com.fifteen11.checkappversion.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSplashScreen()

        setContent {
            MyApp {
                SetupNavGraph()
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