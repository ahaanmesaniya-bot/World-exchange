package com.r7studio.worldexchange.app.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrivacyScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("Privacy Policy", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.padding(top = 8.dp))

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            LegalTitle("World Exchange Privacy Policy")
            LegalMuted("Effective date: September 9, 2026")
            Spacer(Modifier.padding(top = 12.dp))

            LegalBody("This Privacy Policy explains how World Exchange (\"the App\", \"we\", \"us\"), developed and published by R7 Studio, handles information when you use the App. We built World Exchange to work with as little data as possible, and this document explains exactly what that means in practice.")

            LegalSection("1. Who we are")
            LegalBody("World Exchange is developed and maintained by R7 Studio. For any privacy-related question, you can reach us at rstudioofficial3@gmail.com.")

            LegalSection("2. Information we do not collect")
            LegalBody("World Exchange does not require you to create an account, sign in, or provide any personal details to use the App. We do not collect, and have no access to, your name, email address, phone number, physical address, precise or approximate location, photos, contacts, files on your device, payment information, advertising identifiers, or biometric/health data.")

            LegalSection("3. Information stored on your device")
            LegalBody("To make the App useful across sessions, a small amount of data is saved locally on your device: your selected base and target currencies, currencies marked as favorites, your conversion history, and app preferences such as dark mode on/off. This data stays on your device only \u2014 it is not transmitted to R7 Studio or any server we control. You can delete it anytime using \"Clear History\" in Settings, or by uninstalling the App.")

            LegalSection("4. Third-party services we use")
            LegalBody("To show live currency conversion rates, the App sends a request over the internet to a third-party exchange rate API. This request only asks for public exchange rate data and does not include your name, device ID, or any identifying information. We do not embed advertising SDKs, analytics SDKs, crash-reporting SDKs, or any other tracking library in the App.")

            LegalSection("5. Permissions the App requests")
            LegalBody("World Exchange only requests internet access, required to fetch live exchange rates. The App does not request camera, microphone, contacts, SMS, location, storage, or any other sensitive Android permission.")

            LegalSection("6. Data security")
            LegalBody("Because your data never leaves your device, it is protected by your device's own security measures. We do not operate a server that stores your data, so there is no central database of user data that could be exposed in a breach.")

            LegalSection("7. Children's privacy")
            LegalBody("World Exchange does not knowingly collect personal information from anyone, including children under 13. If you believe a child has provided us personal information outside the App, please contact us and we will address it.")

            LegalSection("8. Your choices and control")
            LegalBody("You can clear your conversion history at any time from Settings, reset all locally stored preferences by clearing the App's data in your device settings, and stop all data storage entirely by uninstalling the App.")

            LegalSection("9. Changes to this policy")
            LegalBody("If we ever change what data the App accesses or how it's used, we will update this page and change the effective date above.")

            LegalSection("10. Contact us")
            LegalBody("If you have any questions, concerns, or requests regarding this Privacy Policy, please contact R7 Studio at: rstudioofficial3@gmail.com")
            Spacer(Modifier.padding(top = 24.dp))
        }
    }
}

@Composable
fun LegalTitle(text: String) {
    Text(text, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
}

@Composable
fun LegalMuted(text: String) {
    Text(text, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun LegalSection(text: String) {
    Spacer(Modifier.padding(top = 14.dp))
    Text(text, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    Spacer(Modifier.padding(top = 4.dp))
}

@Composable
fun LegalBody(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineHeight = 21.sp
    )
}
