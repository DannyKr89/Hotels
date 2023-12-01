package com.dk.hotels.data.retrofit

import com.dk.hotels.data.model.hotel.Hotel
import com.dk.hotels.data.model.room.Rooms
import retrofit2.Call
import retrofit2.http.GET

interface HotelsApi {

    @GET("/v3/d144777c-a67f-4e35-867a-cacc3b827473")
    fun getHotel(): Call<Hotel>

    @GET("/v3/8b532701-709e-4194-a41c-1a903af00195")
    fun getRooms(): Call<Rooms>
}