package com.r7studio.worldexchange.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

sealed interface RatesResult {
    data class Success(val rates: Map<String, Double>) : RatesResult
    data class Failure(val message: String) : RatesResult
}

class ExchangeRateRepository {

    private val client = OkHttpClient()
    private val apiUrl = "https://open.er-api.com/v6/latest/USD"

    suspend fun fetchRates(): RatesResult = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder().url(apiUrl).build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    return@withContext RatesResult.Failure("HTTP ${response.code}")
                }
                val body = response.body?.string() ?: return@withContext RatesResult.Failure("Empty response")
                val json = JSONObject(body)
                val ratesObj = json.optJSONObject("rates")
                    ?: return@withContext RatesResult.Failure("No rates in response")
                val map = mutableMapOf<String, Double>()
                val keyNames = ratesObj.names()
                if (keyNames != null) {
                    for (i in 0 until keyNames.length()) {
                        val key = keyNames.getString(i)
                        map[key] = ratesObj.getDouble(key)
                    }
                }
                RatesResult.Success(map)
            }
        } catch (e: IOException) {
            RatesResult.Failure(e.message ?: "Network error")
        } catch (e: Exception) {
            RatesResult.Failure(e.message ?: "Unknown error")
        }
    }
}
