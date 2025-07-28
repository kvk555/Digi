package com.example.appplus.data.network

import com.example.appplus.data.database.ItemDb
import com.example.appplus.domain.entities.ItemEntity
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Item(
    val text: String,
    val confidence: Float,
    val image: String,
    val _id: String
)

fun Item.toEntity() = ItemEntity(
    text = text,
    confidence = confidence,
    image = addPngExtension(image),
    id = _id
)

fun Item.toDbEntity() = ItemDb(
    text = text,
    confidence = confidence,
    image = addPngExtension(image),
    id = _id
)

private fun addPngExtension(url: String): String {
    return if (url.contains(".png")) {
        url
    } else {
        val parts = url.split("?")
        if (parts.size > 1) {
            "${parts[0]}.png?${parts[1]}"
        } else {
            "$url.png"
        }
    }
}