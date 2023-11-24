package com.dk.hotels.domain

import com.dk.hotels.data.model.Hotel

interface HotelsRepo {

    suspend fun getHotels(): Hotel
}