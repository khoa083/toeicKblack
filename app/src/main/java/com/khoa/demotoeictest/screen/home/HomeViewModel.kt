package com.khoa.demotoeictest.screen.home

import androidx.lifecycle.MutableLiveData
import com.kblack.base.BaseViewModel

class HomeViewModel: BaseViewModel() {
    val part1Title = MutableLiveData<String?>()
    val part1Icon = MutableLiveData<String?>()


    init {
        part1Title.value = "part_1"
        part1Icon.value = "part_1"
    }

//    private fun toView() {
//        part1Title.value = "part_1"
//    }
//
//    fun setData() {
//        toView()
//    }
}