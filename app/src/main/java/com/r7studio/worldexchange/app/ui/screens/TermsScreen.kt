package com.r7studio.worldexchange.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

@Composable
fun TermsScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("Terms & Conditions", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.padding(top = 8.dp))

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            LegalTitle("World Exchange Terms & Conditions")
            LegalMuted("Effective date: September 9, 2026")
            Spacer(Modifier.padding(top = 12.dp))

            LegalBody("These Terms & Conditions (\"Terms\") govern your use of World Exchange (\"the App\"), developed and published by R7 Studio. By downloading, installing, or using the App, you agree to be bound by these Terms. If you do not agree, please uninstall and do not use the App.")

            LegalSection("1. Use of the App")
            LegalBody("World Exchange is provided for personal, non-commercial, informational use to convert between currencies and perform basic calculations. You agree to use the App only for lawful purposes.")

            LegalSection("2. Accuracy of exchange rates")
            LegalBody("Exchange rates displayed in the App are fetched from a third-party rate provider and are intended for general informational and reference purposes only. Rates may be delayed, approximate, or occasionally unavailable, and do not include transaction fees, spreads, or margins that a bank or money-transfer service may apply. Do not rely on the App's rates for real financial transactions \u2014 always confirm current rates with your bank or financial institution.")

            LegalSection("3. No professional or financial advice")
            LegalBody("Nothing in the App constitutes financial, investment, tax, or legal advice. The App is a utility tool, not a financial advisory service.")

            LegalSection("4. \"As is\" \u2014 no warranty")
            LegalBody("The App is provided \"as is\" and \"as available,\" without warranties of any kind, express or implied, including that the App will be uninterrupted, error-free, or that exchange-rate data will always be accurate or current.")

            LegalSection("5. Limitation of liability")
            LegalBody("To the maximum extent permitted by applicable law, R7 Studio shall not be liable for any direct, indirect, incidental, or consequential damages arising out of or in connection with your use of, or reliance on, the App.")

            LegalSection("6. Intellectual property")
            LegalBody("The App, including its design, source code, user interface, logos, and branding, is the property of R7 Studio. You may not copy, modify, reverse-engineer, distribute, or create derivative works based on the App without prior written permission.")

            LegalSection("7. Third-party services")
            LegalBody("The App relies on a third-party exchange-rate data provider to function. R7 Studio does not control this third-party service and is not responsible for its availability or accuracy.")

            LegalSection("8. Termination")
            LegalBody("You may stop using the App at any time by uninstalling it. We reserve the right to modify, suspend, or discontinue the App at any time.")

            LegalSection("9. Changes to these Terms")
            LegalBody("We may update these Terms from time to time. Continued use of the App after changes take effect means you accept the revised Terms.")

            LegalSection("10. Contact us")
            LegalBody("If you have any questions about these Terms, please contact R7 Studio at: rstudioofficial3@gmail.com")
            Spacer(Modifier.padding(top = 24.dp))
        }
    }
}
