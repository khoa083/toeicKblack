package com.khoa.demotoeictest.screen.vocab

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.khoa.demotoeictest.repository.Repository
import com.khoa.demotoeictest.common.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


@HiltViewModel
class VocabViewModel @Inject constructor(private val repository: Repository, private val savedStateHandle: SavedStateHandle): ViewModel(){
    fun getListVocab() = liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.getListVocab()))
        } catch (e: Exception) {
            Log.e("Okhttp", "$e lỗi data")
            emit(DataResult.error(e.message ?: "Error Data"))
        }
    }

    fun getListVocabData() = liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            val type = savedStateHandle.get<String>("type")
            emit(DataResult.success(repository.getListVocabData(type?:"")))
        } catch (e: Exception) {
            Log.e("Okhttp", "$e lỗi data")
            emit(DataResult.error(e.message ?: "Error Data"))
        }
    }

    fun putFavoriteVocabData(id:String, favorite:String) = liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            emit(DataResult.success(repository.putFavoriteVocabData(id,favorite)))
        } catch (e: Exception) {
            Log.e("Okhttp", "$e lỗi data")
            emit(DataResult.error(e.message ?: "Error Data"))
        }
    }

}