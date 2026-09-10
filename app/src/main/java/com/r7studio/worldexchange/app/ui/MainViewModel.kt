package com.r7studio.worldexchange.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.r7studio.worldexchange.app.data.AppPreferences
import com.r7studio.worldexchange.app.data.CURRENCY_DB
import com.r7studio.worldexchange.app.data.Currency
import com.r7studio.worldexchange.app.data.ExchangeRateRepository
import com.r7studio.worldexchange.app.data.RatesResult
import com.r7studio.worldexchange.app.data.ThemeMode
import com.r7studio.worldexchange.app.data.model.ConversionHistoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class RatesStatus { LOADING, LIVE, OFFLINE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = AppPreferences(application)
    private val repository = ExchangeRateRepository()

    // ---- Converter state ----
    private val _amount = MutableStateFlow("10000")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _fromCurrency = MutableStateFlow("INR")
    val fromCurrency: StateFlow<String> = _fromCurrency.asStateFlow()

    private val _toCurrency = MutableStateFlow("USD")
    val toCurrency: StateFlow<String> = _toCurrency.asStateFlow()

    private val _rates = MutableStateFlow<Map<String, Double>>(emptyMap())
    private val _ratesStatus = MutableStateFlow(RatesStatus.LOADING)
    val ratesStatus: StateFlow<RatesStatus> = _ratesStatus.asStateFlow()

    private val _resultAmount = MutableStateFlow("--")
    val resultAmount: StateFlow<String> = _resultAmount.asStateFlow()

    private val _unitRate = MutableStateFlow("--")
    val unitRate: StateFlow<String> = _unitRate.asStateFlow()

    // ---- Favorites / theme / history (persisted) ----
    private val _favorites = MutableStateFlow<Set<String>>(setOf("USD", "EUR", "GBP", "AED"))
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _history = MutableStateFlow<List<ConversionHistoryItem>>(emptyList())
    val history: StateFlow<List<ConversionHistoryItem>> = _history.asStateFlow()

    // ---- Calculator state ----
    private val _calcValue = MutableStateFlow("0")
    val calcValue: StateFlow<String> = _calcValue.asStateFlow()

    init {
        viewModelScope.launch { prefs.favorites.collect { _favorites.value = it } }
        viewModelScope.launch { prefs.themeMode.collect { _themeMode.value = it } }
        viewModelScope.launch { prefs.history.collect { _history.value = it } }
        viewModelScope.launch { prefs.fromCurrency.collect { _fromCurrency.value = it } }
        viewModelScope.launch { prefs.toCurrency.collect { _toCurrency.value = it } }
        loadRates()
    }

    fun loadRates() {
        viewModelScope.launch {
            _ratesStatus.value = RatesStatus.LOADING
            when (val result = repository.fetchRates()) {
                is RatesResult.Success -> {
                    _rates.value = result.rates
                    _ratesStatus.value = RatesStatus.LIVE
                    performConversion()
                }
                is RatesResult.Failure -> {
                    _ratesStatus.value = RatesStatus.OFFLINE
                }
            }
        }
    }

    fun onAmountChange(value: String) {
        _amount.value = value
        performConversion()
    }

    fun setFromCurrency(code: String) {
        _fromCurrency.value = code
        viewModelScope.launch { prefs.setFromCurrency(code) }
        performConversion()
    }

    fun setToCurrency(code: String) {
        _toCurrency.value = code
        viewModelScope.launch { prefs.setToCurrency(code) }
        performConversion()
    }

    fun swapCurrencies() {
        val temp = _fromCurrency.value
        _fromCurrency.value = _toCurrency.value
        _toCurrency.value = temp
        viewModelScope.launch {
            prefs.setFromCurrency(_fromCurrency.value)
            prefs.setToCurrency(_toCurrency.value)
        }
        performConversion()
    }

    fun performConversion() {
        val rates = _rates.value
        val from = _fromCurrency.value
        val to = _toCurrency.value
        val amountValue = _amount.value.toDoubleOrNull() ?: 0.0
        val fromRate = rates[from]
        val toRate = rates[to]
        if (fromRate == null || toRate == null || fromRate == 0.0) {
            return
        }
        val fromInUsd = amountValue / fromRate
        val result = fromInUsd * toRate
        val unit = toRate / fromRate
        val symbol = CURRENCY_DB.find { it.code == to }?.symbol ?: ""

        _resultAmount.value = "$symbol${"%.2f".format(result)}"
        _unitRate.value = "1 $from = ${"%.4f".format(unit)} $to"

        if (amountValue > 0) {
            saveHistory(amountValue, from, "%.2f".format(result), to)
        }
    }

    private fun saveHistory(amount: Double, from: String, result: String, to: String) {
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val item = ConversionHistoryItem(
            amount = if (amount == amount.toLong().toDouble()) amount.toLong().toString() else amount.toString(),
            fromCode = from,
            result = result,
            toCode = to,
            time = timeFormat.format(Date())
        )
        viewModelScope.launch { prefs.addHistoryItem(item) }
    }

    fun clearHistory() {
        viewModelScope.launch { prefs.clearHistory() }
    }

    fun toggleFavorite(code: String) {
        viewModelScope.launch { prefs.toggleFavorite(code) }
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch { prefs.setThemeMode(mode) }
    }

    fun quickChips(): List<Currency> {
        val favs = CURRENCY_DB.filter { _favorites.value.contains(it.code) }
        val rest = CURRENCY_DB.filter { !_favorites.value.contains(it.code) }
        return (favs + rest).take(8)
    }

    fun setQuickTo(code: String) {
        setToCurrency(code)
    }

    // ---- Calculator ----
    fun calcInput(char: String) {
        _calcValue.value = if (char == "C") {
            "0"
        } else if (_calcValue.value == "0" && char != ".") {
            char
        } else {
            _calcValue.value + char
        }
    }

    fun calcCompute() {
        val expr = _calcValue.value
        if (!Regex("^[0-9+\\-*/.\\s]+$").matches(expr)) {
            _calcValue.value = "Error"
            return
        }
        try {
            val result = evaluateExpression(expr)
            _calcValue.value = if (result == result.toLong().toDouble()) {
                result.toLong().toString()
            } else {
                result.toString()
            }
        } catch (e: Exception) {
            _calcValue.value = "Error"
        }
    }

    // Minimal left-to-right expression evaluator supporting + - * /
    // (mirrors the simple calculator behavior from the original design)
    private fun evaluateExpression(expr: String): Double {
        val tokens = Regex("(\\d+\\.?\\d*)|[+\\-*/]").findAll(expr).map { it.value }.toList()
        if (tokens.isEmpty()) return 0.0
        var result = tokens[0].toDouble()
        var i = 1
        while (i < tokens.size - 1) {
            val op = tokens[i]
            val next = tokens[i + 1].toDouble()
            result = when (op) {
                "+" -> result + next
                "-" -> result - next
                "*" -> result * next
                "/" -> if (next != 0.0) result / next else throw ArithmeticException("Divide by zero")
                else -> result
            }
            i += 2
        }
        return result
    }
}
