package com.khoa.demotoeictest.db

import javax.inject.Inject

class DataRemoteAPI @Inject constructor(private val apiServices: APIServices) {

    suspend fun getListParts(part: String) = apiServices.getListParts(part)

    suspend fun getListTypesData(ets: String, test: String, part: String) = apiServices.getListTypesData(ets,test,part)

    suspend fun getListPartsData(ets: String, test: String, part: String) = apiServices.getListPartsData(ets,test,part)

    suspend fun getListVocab() = apiServices.getListVocab()

}