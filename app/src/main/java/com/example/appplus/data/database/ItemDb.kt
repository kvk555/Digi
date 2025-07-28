package com.example.appplus.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appplus.domain.entities.ItemEntity

@Entity
data class ItemDb(
    @PrimaryKey val id: String,
    val text: String,
    val confidence: Float,
    val image: String,
)

fun ItemDb.toEntity() = ItemEntity(
    text = text,
    confidence = confidence,
    image = image,
    id = id
)
