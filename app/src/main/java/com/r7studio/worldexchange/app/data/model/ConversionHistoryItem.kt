package com.r7studio.worldexchange.app.data.model

data class ConversionHistoryItem(
    val amount: String,
    val fromCode: String,
    val result: String,
    val toCode: String,
    val time: String
)
