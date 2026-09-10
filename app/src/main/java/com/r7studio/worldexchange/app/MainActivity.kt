package com.r7studio.worldexchange.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.r7studio.worldexchange.app.data.ThemeMode
import com.r7studio.worldexchange.app.ui.MainTab
import com.r7studio.worldexchange.app.ui.MainViewModel
import com.r7studio.worldexchange.app.ui.Routes
import com.r7studio.worldexchange.app.ui.SubScreen
import com.r7studio.worldexchange.app.ui.components.BottomNavBar
import com.r7studio.worldexchange.app.ui.screens.AboutScreen
import com.r7studio.worldexchange.app.ui.screens.CalculatorScreen
import com.r7studio.worldexchange.app.ui.screens.CurrenciesScreen
import com.r7studio.worldexchange.app.ui.screens.HistoryScreen
import com.r7studio.worldexchange.app.ui.screens.HomeConverterScreen
import com.r7studio.worldexchange.app.ui.screens.PrivacyScreen
import com.r7studio.worldexchange.app.ui.screens.SettingsScreen
import com.r7studio.worldexchange.app.ui.screens.SplashScreen
import com.r7studio.worldexchange.app.ui.screens.TermsScreen
import com.r7studio.worldexchange.app.ui.theme.WorldExchangeTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
            val darkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
            }

            WorldExchangeTheme(darkTheme = darkTheme) {
                AppRoot(viewModel)
            }
        }
    }
}

@Composable
fun AppRoot(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {
        composable(Routes.SPLASH) {
            SplashScreen(onFinish = {
                navController.navigate(Routes.MAIN) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            })
        }
        composable(Routes.MAIN) {
            MainShell(viewModel)
        }
    }
}

@Composable
fun MainShell(viewModel: MainViewModel) {
    var activeTab by remember { mutableStateOf(MainTab.HOME) }
    var subScreen by remember { mutableStateOf(SubScreen.NONE) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                activeTab = activeTab,
                onTabSelected = {
                    activeTab = it
                    subScreen = SubScreen.NONE
                }
            )
        }
    ) { innerPadding ->
        androidx.compose.foundation.layout.Box(modifier = Modifier.padding(innerPadding)) {
            when (subScreen) {
                SubScreen.HISTORY -> HistoryScreen(viewModel, onBack = { subScreen = SubScreen.NONE })
                SubScreen.PRIVACY -> PrivacyScreen(onBack = { subScreen = SubScreen.NONE })
                SubScreen.TERMS -> TermsScreen(onBack = { subScreen = SubScreen.NONE })
                SubScreen.ABOUT -> AboutScreen(onBack = { subScreen = SubScreen.NONE })
                SubScreen.NONE -> when (activeTab) {
                    MainTab.HOME -> HomeConverterScreen(viewModel)
                    MainTab.CURRENCIES -> CurrenciesScreen(viewModel)
                    MainTab.CALCULATOR -> CalculatorScreen(viewModel)
                    MainTab.SETTINGS -> SettingsScreen(viewModel, onNavigate = { subScreen = it })
                }
            }
        }
    }
}
