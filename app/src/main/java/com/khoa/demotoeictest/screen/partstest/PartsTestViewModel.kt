package com.khoa.demotoeictest.screen.partstest

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.khoa.demotoeictest.repository.Repository
import com.khoa.demotoeictest.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PartsTestViewModel @Inject constructor(private val repository: Repository, private val savedStateHandle: SavedStateHandle): ViewModel() {
    fun getListDataParts() = liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            val ets = savedStateHandle.get<String>("ets")
            val test = savedStateHandle.get<String>("test")
            val part = savedStateHandle.get<String>("part")
            emit(DataResult.success(repository.getListPartsData(ets?:"",test?:"",part?:"")))
        } catch (e: Exception) {
            Log.e("Okhttp", "${e.toString()} lỗi data")
            emit(DataResult.error(e.message ?: "Error Data"))
        }
    }
}