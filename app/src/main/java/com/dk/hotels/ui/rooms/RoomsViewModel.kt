package com.dk.hotels.ui.rooms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dk.hotels.data.model.room.Rooms
import com.dk.hotels.data.repository.RemoteHotelsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomsViewModel(private val repository: RemoteHotelsRepositoryImpl) : ViewModel() {

    private var _liveData = MutableLiveData<Rooms>()
    val liveData: LiveData<Rooms> = _liveData


    fun getRooms() {
        viewModelScope.launch(Dispatchers.IO) {
            _liveData.postValue(repository.getRooms())
        }
    }
}