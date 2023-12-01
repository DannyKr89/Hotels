package com.dk.hotels.di

import com.dk.hotels.data.repository.RemoteHotelsRepositoryImpl
import com.dk.hotels.data.retrofit.HotelsApi
import com.dk.hotels.ui.adapters.ImageAdapter
import com.dk.hotels.ui.adapters.RoomsAdapter
import com.dk.hotels.ui.hotel.HotelsViewModel
import com.dk.hotels.ui.rooms.RoomsViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(HotelsApi::class.java) }

    single { RemoteHotelsRepositoryImpl(get()) }

    viewModel<HotelsViewModel> { HotelsViewModel(get()) }

    single<ImageAdapter> { ImageAdapter() }
    single<RoomsAdapter> { RoomsAdapter(get()) }

    viewModel<RoomsViewModel> { RoomsViewModel(get()) }
}
