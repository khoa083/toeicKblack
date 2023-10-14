package com.khoa.demotoeictest.db

import javax.inject.Inject

class DataRemoteAPI @Inject constructor(private val apiServices: APIServices) {

    suspend fun getListParts(part: String) = apiServices.getListParts(part)

}