package com.r7studio.worldexchange.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text("About", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
        }
        Spacer(Modifier.padding(top = 8.dp))

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier.size(56.dp).background(MaterialTheme.colorScheme.primary, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Public, contentDescription = null, tint = Color.White)
                    }
                    Spacer(Modifier.padding(top = 12.dp))
                    Text("World Exchange", fontWeight = FontWeight.Black, fontSize = 18.sp)
                    Text("Version 1.0.0", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.padding(top = 16.dp))
                    Text(
                        "World Exchange is a fast, lightweight currency converter built for travelers, students, freelancers, online shoppers, and anyone who deals with more than one currency. It gives you live exchange rates, a searchable list of world currencies, a built-in calculator, and a private, on-device history of your conversions \u2014 all in one clean interface.",
                        fontSize = 13.sp,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.padding(top = 12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("FEATURES", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.padding(top = 10.dp))
                    listOf(
                        "Live rates" to "up-to-date exchange rates for all major world currencies.",
                        "Full currency list" to "browse and search every supported country and currency.",
                        "Favorites / quick chips" to "pin the currencies you use most for one-tap conversions.",
                        "Standalone calculator" to "a simple calculator for quick maths.",
                        "Conversion history" to "automatically saved on your device \u2014 clearable anytime.",
                        "Dark mode" to "switch between light and dark themes.",
                        "Privacy-first" to "no account, no ads, no analytics, no tracking."
                    ).forEach { (title, desc) ->
                        Text(buildString { append(title); append(": ") }, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(desc, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.padding(top = 10.dp))
                    }
                }
            }

            Spacer(Modifier.padding(top = 12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("DEVELOPED BY", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.padding(top = 6.dp))
                    Text("R7 Studio", fontWeight = FontWeight.Black, fontSize = 15.sp)
                    Text("rstudioofficial3@gmail.com", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Spacer(Modifier.padding(top = 24.dp))
        }
    }
}
