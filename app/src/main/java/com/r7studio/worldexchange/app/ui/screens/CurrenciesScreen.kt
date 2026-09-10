package com.r7studio.worldexchange.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.r7studio.worldexchange.app.data.CURRENCY_DB
import com.r7studio.worldexchange.app.ui.MainViewModel
import com.r7studio.worldexchange.app.ui.components.CurrencyRow

@Composable
fun CurrenciesScreen(viewModel: MainViewModel) {
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }
    val filtered = CURRENCY_DB.filter {
        it.code.contains(query, ignoreCase = true) || it.name.contains(query, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("All Global Currencies", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
        androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 16.dp))
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search country or currency...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = MaterialTheme.colorScheme.outline)
        )
        androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(filtered, key = { it.code }) { currency ->
                val isFav = favorites.contains(currency.code)
                CurrencyRow(
                    currency = currency,
                    onClick = { viewModel.toggleFavorite(currency.code) },
                    trailing = {
                        IconButton(onClick = { viewModel.toggleFavorite(currency.code) }) {
                            Icon(
                                if (isFav) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "Favorite",
                                tint = if (isFav) Color(0xFFF59E0B) else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                )
            }
        }
    }
}
