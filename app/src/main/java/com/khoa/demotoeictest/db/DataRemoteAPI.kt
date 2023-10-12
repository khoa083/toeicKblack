package com.khoa.demotoeictest.db

import javax.inject.Inject

class DataRemoteAPI @Inject constructor(private val apiServices: APIServices) {

    suspend fun getListPart1() = apiServices.getListPart1()

}