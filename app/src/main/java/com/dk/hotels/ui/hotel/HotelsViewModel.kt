package com.dk.hotels.ui.hotel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.hotels.data.model.Hotel
import com.dk.hotels.data.repository.RemoteHotelsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelsViewModel(private val repository: RemoteHotelsRepositoryImpl) : ViewModel() {

    private var _liveData = MutableLiveData<Hotel>()
    val liveData: LiveData<Hotel> = _liveData


    fun getHotels() {
        viewModelScope.launch(Dispatchers.IO) {
            _liveData.postValue(repository.getHotels())
        }
    }
}