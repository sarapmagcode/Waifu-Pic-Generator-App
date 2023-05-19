package com.example.waifupicsapp.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waifupicsapp.network.WaifuApi
import com.example.waifupicsapp.network.WaifuPic
import kotlinx.coroutines.launch

enum class WaifuPicStatus { LOADING, DONE, ERROR }

class OverviewViewModel : ViewModel() {

    private val _status = MutableLiveData<WaifuPicStatus>()
    val status: LiveData<WaifuPicStatus> = _status

    private val _waifuPic = MutableLiveData<WaifuPic>()
    val waifuPic: LiveData<WaifuPic> = _waifuPic

    init {
        getWaifuPic()
    }

    private fun getWaifuPic(category: String = "waifu") {
        viewModelScope.launch {
            _status.value = WaifuPicStatus.LOADING
            try {
                _waifuPic.value = WaifuApi.retrofitService.getWaifuPic(category)
                _status.value = WaifuPicStatus.DONE
            } catch (e: Exception) {
                _status.value = WaifuPicStatus.ERROR
                Log.d("OverviewFragment", "Error: $e")
            }
        }
    }

    fun generateAgain(category: String) {
        getWaifuPic(category)
    }
}