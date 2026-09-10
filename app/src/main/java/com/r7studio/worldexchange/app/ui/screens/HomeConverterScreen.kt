package com.r7studio.worldexchange.app.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.r7studio.worldexchange.app.data.CURRENCY_DB
import com.r7studio.worldexchange.app.ui.MainViewModel
import com.r7studio.worldexchange.app.ui.RatesStatus
import com.r7studio.worldexchange.app.ui.components.CurrencyPickerSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeConverterScreen(viewModel: MainViewModel) {
    val amount by viewModel.amount.collectAsStateWithLifecycle()
    val fromCode by viewModel.fromCurrency.collectAsStateWithLifecycle()
    val toCode by viewModel.toCurrency.collectAsStateWithLifecycle()
    val resultAmount by viewModel.resultAmount.collectAsStateWithLifecycle()
    val unitRate by viewModel.unitRate.collectAsStateWithLifecycle()
    val ratesStatus by viewModel.ratesStatus.collectAsStateWithLifecycle()
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()

    var pickerTarget by remember { mutableStateOf<String?>(null) }
    var swapRotation by remember { mutableStateOf(0f) }

    val fromCurrency = CURRENCY_DB.find { it.code == fromCode }
    val toCurrency = CURRENCY_DB.find { it.code == toCode }

    val quickChips = remember(favorites) {
        val favs = CURRENCY_DB.filter { favorites.contains(it.code) }
        val rest = CURRENCY_DB.filter { !favorites.contains(it.code) }
        (favs + rest).take(8)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Public, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                }
                Spacer(Modifier.padding(start = 10.dp))
                Text("World Exchange", fontWeight = FontWeight.Black, fontSize = 18.sp)
            }
            LiveBadge(status = ratesStatus)
        }

        Spacer(Modifier.padding(top = 12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    "Enter amount",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.padding(top = 4.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { viewModel.onAmountChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleLarge,
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                Spacer(Modifier.padding(top = 16.dp))
                Text("From", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.padding(top = 4.dp))
                CurrencySelectBox(
                    flag = fromCurrency?.flag ?: "",
                    code = fromCurrency?.code ?: "",
                    name = fromCurrency?.name ?: "",
                    onClick = { pickerTarget = "from" }
                )

                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary,
                        shadowElevation = 4.dp
                    ) {
                        Icon(
                            Icons.Default.SwapVert,
                            contentDescription = "Swap",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(10.dp)
                                .size(20.dp)
                                .rotate(swapRotation)
                                .clickable {
                                    swapRotation += 180f
                                    viewModel.swapCurrencies()
                                }
                        )
                    }
                }

                Text("To", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.padding(top = 4.dp))
                CurrencySelectBox(
                    flag = toCurrency?.flag ?: "",
                    code = toCurrency?.code ?: "",
                    name = toCurrency?.name ?: "",
                    onClick = { pickerTarget = "to" }
                )

                Spacer(Modifier.padding(top = 14.dp))
                Button(
                    onClick = { viewModel.performConversion() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Convert", fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(Modifier.padding(top = 14.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "CONVERTED AMOUNT",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.padding(top = 4.dp))
                Text(
                    resultAmount,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.padding(top = 4.dp))
                Text(unitRate, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(Modifier.padding(top = 16.dp))
        Text("Quick Currencies", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.padding(top = 8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(((quickChips.size / 4 + 1) * 70).dp)
        ) {
            items(quickChips, key = { it.code }) { currency ->
                val isFav = favorites.contains(currency.code)
                Column(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(
                            if (isFav) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { viewModel.setQuickTo(currency.code) }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(currency.flag, fontSize = 20.sp)
                    Text(currency.code, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    pickerTarget?.let { target ->
        CurrencyPickerSheet(
            onDismiss = { pickerTarget = null },
            onSelect = { code ->
                if (target == "from") viewModel.setFromCurrency(code) else viewModel.setToCurrency(code)
                pickerTarget = null
            }
        )
    }
}

@Composable
private fun CurrencySelectBox(flag: String, code: String, name: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(flag, fontSize = 22.sp)
            Spacer(Modifier.padding(start = 8.dp))
            Column {
                Text(code, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(name, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Icon(
            Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun LiveBadge(status: RatesStatus) {
    val infiniteTransition = rememberInfiniteTransition(label = "livePulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Reverse),
        label = "pulseAlpha"
    )
    val (label, color) = when (status) {
        RatesStatus.LOADING -> "Loading..." to MaterialTheme.colorScheme.onSurfaceVariant
        RatesStatus.LIVE -> "Live rates" to MaterialTheme.colorScheme.primary
        RatesStatus.OFFLINE -> "Offline" to Color(0xFFDC2626)
    }
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, CircleShape)
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .alpha(if (status == RatesStatus.LIVE) pulseAlpha else 1f)
                .background(color, CircleShape)
        )
        Spacer(Modifier.padding(start = 5.dp))
        Text(label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
