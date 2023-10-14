package com.khoa.demotoeictest.repository

import com.khoa.demotoeictest.db.DataRemoteAPI
import javax.inject.Inject

class Repository @Inject constructor(private val dataRemoteAPI: DataRemoteAPI) {

    suspend fun getListParts(part: String) = dataRemoteAPI.getListParts(part)

    suspend fun getListPartsData(ets: String, test: String, part: String) = dataRemoteAPI.getListPartsData(ets,test,part)

}