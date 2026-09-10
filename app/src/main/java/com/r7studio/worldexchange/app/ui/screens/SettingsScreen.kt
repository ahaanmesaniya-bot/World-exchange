package com.r7studio.worldexchange.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.r7studio.worldexchange.app.data.ThemeMode
import com.r7studio.worldexchange.app.ui.MainViewModel
import com.r7studio.worldexchange.app.ui.SubScreen

@Composable
fun SettingsScreen(viewModel: MainViewModel, onNavigate: (SubScreen) -> Unit) {
    val context = LocalContext.current
    val themeMode by viewModel.themeMode.collectAsStateWithLifecycle()
    val isDark = themeMode == ThemeMode.DARK

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("Settings", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.padding(top = 16.dp))

        SettingsGroup {
            SettingsSwitchItem(
                icon = Icons.Default.DarkMode,
                title = "Dark Mode",
                checked = isDark,
                onCheckedChange = { checked ->
                    viewModel.setThemeMode(if (checked) ThemeMode.DARK else ThemeMode.LIGHT)
                }
            )
            SettingsItem(
                icon = Icons.Default.History,
                title = "Conversion History",
                onClick = { onNavigate(SubScreen.HISTORY) }
            )
            SettingsItem(
                icon = Icons.Default.DeleteOutline,
                title = "Clear History",
                titleColor = Color(0xFFDC2626),
                onClick = { viewModel.clearHistory() }
            )
            SettingsItem(
                icon = Icons.Default.Shield,
                title = "Privacy Policy",
                onClick = { onNavigate(SubScreen.PRIVACY) }
            )
            SettingsItem(
                icon = Icons.Default.Description,
                title = "Terms & Conditions",
                onClick = { onNavigate(SubScreen.TERMS) }
            )
            SettingsItem(
                icon = Icons.Default.Info,
                title = "About",
                onClick = { onNavigate(SubScreen.ABOUT) }
            )
            SettingsItem(
                icon = Icons.Default.Comment,
                title = "Feedback / Support",
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:rstudioofficial3@gmail.com")
                        putExtra(Intent.EXTRA_SUBJECT, "World Exchange App Feedback")
                    }
                    context.startActivity(Intent.createChooser(emailIntent, "Send Email"))
                }
            )
        }
    }
}

@Composable
private fun SettingsGroup(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)),
        content = content
    )
}

@Composable
private fun SettingsItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    titleColor: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = if (titleColor != Color.Unspecified) titleColor else MaterialTheme.colorScheme.primary)
            Spacer(Modifier.padding(start = 14.dp))
            Text(title, fontWeight = FontWeight.SemiBold, color = if (titleColor != Color.Unspecified) titleColor else MaterialTheme.colorScheme.onSurface)
        }
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SettingsSwitchItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.padding(start = 14.dp))
            Text(title, fontWeight = FontWeight.SemiBold)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
