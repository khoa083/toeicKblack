package com.khoa.demotoeictest.screen.parts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.khoa.demotoeictest.repository.Repository
import com.khoa.demotoeictest.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class PartsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun getListPart1() = liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getListPart1()))
        } catch (e: Exception) {
            Log.e("Okhttp", e.toString())
            emit(DataResult.error(e.message ?: "Error Data"))
        }
    }

}