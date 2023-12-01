package com.dk.hotels.data.model.room

import com.google.gson.annotations.SerializedName

data class Room(
    val id: Int,
    @SerializedName("image_urls")
    val imageUrls: List<String>,
    val name: String,
    val peculiarities: List<String>,
    val price: Int,
    @SerializedName("price_per")
    val pricePer: String
)