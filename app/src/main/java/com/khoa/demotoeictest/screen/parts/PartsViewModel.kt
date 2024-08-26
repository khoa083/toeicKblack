package com.khoa.demotoeictest.screen.parts

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.khoa.demotoeictest.model.Parts
import com.khoa.demotoeictest.repository.Repository
import com.khoa.demotoeictest.room.listparts.ListPartsDao
import com.khoa.demotoeictest.room.listparts.Part1
import com.khoa.demotoeictest.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartsViewModel @Inject constructor(private val repository: Repository, private val savedStateHandle: SavedStateHandle,
    private val listTitleParts: ListPartsDao): ViewModel() {

    fun getListParts() = liveData(Dispatchers.IO) {
        emit(DataResult.loading())
        try {
            val part = savedStateHandle.get<String>("part")
            emit(DataResult.success(repository.getListParts(part?:"")))
        } catch (e: Exception) {
            Log.e("Okhttp", "${e.toString()} lỗi data")
            emit(DataResult.error(e.message ?: "Error Data"))
        }
    }
    fun getAllTitle() {
        listTitleParts.getAllTitlePart1()
    }

    fun insertData(id: String,
    listPartsTitle: String,
    listPartsNumQues: String,
    listPartsDes: String,
    listPartsEts: String,
    listPartsTest: String,
    listPartsPart: String,) {
        listTitleParts.insertAllTitlePart1(
            Part1(id= id,
                listPartsTitle=listPartsTitle,
                listPartsNumQues=listPartsNumQues,
                listPartsDes=listPartsDes,
                listPartsEts=listPartsEts,
                listPartsTest=listPartsTest,
                listPartsPart=listPartsPart,)
        )
    }




}