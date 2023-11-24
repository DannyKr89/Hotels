package com.dk.hotels.data.retrofit

import com.dk.hotels.data.model.Hotel
import retrofit2.Call
import retrofit2.http.GET

interface HotelsApi {

    @GET("/v3/d144777c-a67f-4e35-867a-cacc3b827473")
    fun getHotel(): Call<Hotel>
}