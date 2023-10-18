package com.khoa.demotoeictest.screen.partsdata

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
            emit(DataResult.success(repository.getListPartsData("2021","1","1")))
        } catch (e: Exception) {
            Log.e("Okhttp", "${e.toString()} lỗi data")
            emit(DataResult.error(e.message ?: "Error Data"))
        }
    }
}