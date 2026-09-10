package com.r7studio.worldexchange.app.data

data class Currency(
    val code: String,
    val name: String,
    val flag: String,
    val symbol: String
)

val CURRENCY_DB = listOf(
    Currency("USD", "United States Dollar", "\uD83C\uDDFA\uD83C\uDDF8", "$"),
    Currency("EUR", "Euro", "\uD83C\uDDEA\uD83C\uDDFA", "\u20AC"),
    Currency("GBP", "British Pound", "\uD83C\uDDEC\uD83C\uDDE7", "\u00A3"),
    Currency("INR", "Indian Rupee", "\uD83C\uDDEE\uD83C\uDDF3", "\u20B9"),
    Currency("AED", "UAE Dirham", "\uD83C\uDDE6\uD83C\uDDEA", "\u062F.\u0625"),
    Currency("SAR", "Saudi Riyal", "\uD83C\uDDF8\uD83C\uDDE6", "\uFDFC"),
    Currency("CAD", "Canadian Dollar", "\uD83C\uDDE8\uD83C\uDDE6", "$"),
    Currency("AUD", "Australian Dollar", "\uD83C\uDDE6\uD83C\uDDFA", "$"),
    Currency("JPY", "Japanese Yen", "\uD83C\uDDEF\uD83C\uDDF5", "\u00A5"),
    Currency("CHF", "Swiss Franc", "\uD83C\uDDE8\uD83C\uDDED", "Fr"),
    Currency("CNY", "Chinese Yuan", "\uD83C\uDDE8\uD83C\uDDF3", "\u00A5"),
    Currency("SGD", "Singapore Dollar", "\uD83C\uDDF8\uD83C\uDDEC", "$"),
    Currency("NZD", "New Zealand Dollar", "\uD83C\uDDF3\uD83C\uDDFF", "$"),
    Currency("BDT", "Bangladeshi Taka", "\uD83C\uDDE7\uD83C\uDDE9", "\u09F3"),
    Currency("PKR", "Pakistani Rupee", "\uD83C\uDDF5\uD83C\uDDF0", "\u20A8"),
    Currency("LKR", "Sri Lankan Rupee", "\uD83C\uDDF1\uD83C\uDDF0", "Rs"),
    Currency("MYR", "Malaysian Ringgit", "\uD83C\uDDF2\uD83C\uDDFE", "RM"),
    Currency("THB", "Thai Baht", "\uD83C\uDDF9\uD83C\uDDED", "\u0E3F"),
    Currency("ZAR", "South African Rand", "\uD83C\uDDFF\uD83C\uDDE6", "R"),
    Currency("RUB", "Russian Ruble", "\uD83C\uDDF7\uD83C\uDDFA", "\u20BD"),
    Currency("KRW", "South Korean Won", "\uD83C\uDDF0\uD83C\uDDF7", "\u20A9"),
    Currency("BRL", "Brazilian Real", "\uD83C\uDDE7\uD83C\uDDF7", "R$"),
    Currency("MXN", "Mexican Peso", "\uD83C\uDDF2\uD83C\uDDFD", "$"),
    Currency("TRY", "Turkish Lira", "\uD83C\uDDF9\uD83C\uDDF7", "\u20BA"),
    Currency("EGP", "Egyptian Pound", "\uD83C\uDDEA\uD83C\uDDEC", "E\u00A3")
)
