package com.dk.hotels.data.repository

import com.dk.hotels.data.model.Hotel
import com.dk.hotels.data.retrofit.HotelsApi
import com.dk.hotels.domain.HotelsRepo
import retrofit2.await

class RemoteHotelsRepositoryImpl(private val api: HotelsApi) : HotelsRepo {

    override suspend fun getHotels(): Hotel {
        return api.getHotel().await()
    }
}