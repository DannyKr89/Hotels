package com.dk.hotels.domain

import com.dk.hotels.data.model.hotel.Hotel
import com.dk.hotels.data.model.room.Rooms

interface HotelsRepo {

    suspend fun getHotels(): Hotel
    suspend fun getRooms(): Rooms
}