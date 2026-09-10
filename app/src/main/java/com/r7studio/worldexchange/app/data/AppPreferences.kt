package com.r7studio.worldexchange.app.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.r7studio.worldexchange.app.data.model.ConversionHistoryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject

private val Context.dataStore by preferencesDataStore(name = "world_exchange_prefs")

enum class ThemeMode { LIGHT, DARK, SYSTEM }

class AppPreferences(private val context: Context) {

    private object Keys {
        val THEME = stringPreferencesKey("theme_mode")
        val FAVORITES = stringSetPreferencesKey("favorite_currencies")
        val HISTORY = stringPreferencesKey("history_json")
        val FROM_CURR = stringPreferencesKey("from_currency")
        val TO_CURR = stringPreferencesKey("to_currency")
        val ONBOARDED = booleanPreferencesKey("onboarded")
    }

    val themeMode: Flow<ThemeMode> = context.dataStore.data.map { prefs ->
        when (prefs[Keys.THEME]) {
            "LIGHT" -> ThemeMode.LIGHT
            "DARK" -> ThemeMode.DARK
            else -> ThemeMode.SYSTEM
        }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { it[Keys.THEME] = mode.name }
    }

    val favorites: Flow<Set<String>> = context.dataStore.data.map {
        it[Keys.FAVORITES] ?: setOf("USD", "EUR", "GBP", "AED")
    }

    suspend fun toggleFavorite(code: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.FAVORITES] ?: setOf("USD", "EUR", "GBP", "AED")
            prefs[Keys.FAVORITES] = if (current.contains(code)) current - code else current + code
        }
    }

    val fromCurrency: Flow<String> = context.dataStore.data.map { it[Keys.FROM_CURR] ?: "INR" }
    val toCurrency: Flow<String> = context.dataStore.data.map { it[Keys.TO_CURR] ?: "USD" }

    suspend fun setFromCurrency(code: String) {
        context.dataStore.edit { it[Keys.FROM_CURR] = code }
    }

    suspend fun setToCurrency(code: String) {
        context.dataStore.edit { it[Keys.TO_CURR] = code }
    }

    val history: Flow<List<ConversionHistoryItem>> = context.dataStore.data.map { prefs ->
        val raw = prefs[Keys.HISTORY] ?: "[]"
        parseHistory(raw)
    }

    suspend fun addHistoryItem(item: ConversionHistoryItem) {
        context.dataStore.edit { prefs ->
            val current = parseHistory(prefs[Keys.HISTORY] ?: "[]").toMutableList()
            current.add(0, item)
            while (current.size > 15) current.removeAt(current.size - 1)
            prefs[Keys.HISTORY] = serializeHistory(current)
        }
    }

    suspend fun clearHistory() {
        context.dataStore.edit { it[Keys.HISTORY] = "[]" }
    }

    private fun parseHistory(json: String): List<ConversionHistoryItem> {
        return try {
            val arr = JSONArray(json)
            (0 until arr.length()).map { i ->
                val o = arr.getJSONObject(i)
                ConversionHistoryItem(
                    amount = o.getString("amount"),
                    fromCode = o.getString("from"),
                    result = o.getString("result"),
                    toCode = o.getString("to"),
                    time = o.getString("time")
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun serializeHistory(list: List<ConversionHistoryItem>): String {
        val arr = JSONArray()
        list.forEach { item ->
            val o = JSONObject()
            o.put("amount", item.amount)
            o.put("from", item.fromCode)
            o.put("result", item.result)
            o.put("to", item.toCode)
            o.put("time", item.time)
            arr.put(o)
        }
        return arr.toString()
    }
}
