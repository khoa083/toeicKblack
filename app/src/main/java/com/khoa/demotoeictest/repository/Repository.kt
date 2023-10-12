package com.khoa.demotoeictest.repository

import com.khoa.demotoeictest.db.DataRemoteAPI
import javax.inject.Inject

class Repository @Inject constructor(private val dataRemoteAPI: DataRemoteAPI) {

    suspend fun getListPart1() = dataRemoteAPI.getListPart1()

}