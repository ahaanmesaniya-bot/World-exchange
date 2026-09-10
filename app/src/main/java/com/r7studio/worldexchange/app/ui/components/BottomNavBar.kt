package com.r7studio.worldexchange.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.r7studio.worldexchange.app.ui.MainTab

@Composable
fun BottomNavBar(
    activeTab: MainTab,
    onTabSelected: (MainTab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = activeTab == MainTab.HOME,
            onClick = { onTabSelected(MainTab.HOME) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = activeTab == MainTab.CURRENCIES,
            onClick = { onTabSelected(MainTab.CURRENCIES) },
            icon = { Icon(Icons.Default.Public, contentDescription = "Currencies") },
            label = { Text("Currencies") }
        )
        NavigationBarItem(
            selected = activeTab == MainTab.CALCULATOR,
            onClick = { onTabSelected(MainTab.CALCULATOR) },
            icon = { Icon(Icons.Default.Calculate, contentDescription = "Calculator") },
            label = { Text("Calculator") }
        )
        NavigationBarItem(
            selected = activeTab == MainTab.SETTINGS,
            onClick = { onTabSelected(MainTab.SETTINGS) },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") }
        )
    }
}
