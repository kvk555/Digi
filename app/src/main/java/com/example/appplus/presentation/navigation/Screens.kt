package com.example.appplus.presentation.navigation

import kotlinx.serialization.Serializable


@Serializable
object Catalog

@Serializable
data class Details(
    val text: String,
    val image: String,
    val id: String
)
