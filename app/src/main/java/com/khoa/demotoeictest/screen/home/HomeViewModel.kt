package com.khoa.demotoeictest.screen.home

import androidx.lifecycle.MutableLiveData
import com.kblack.base.BaseViewModel

class HomeViewModel: BaseViewModel() {
    val part1Title = MutableLiveData<String>()


    private fun toView() {
        part1Title.value = ""
    }

    fun setData() {
        toView()
    }
}