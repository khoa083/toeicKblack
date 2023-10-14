package com.khoa.demotoeictest.db

import com.khoa.demotoeictest.model.PartsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIServices {

    @GET("part{part}")
    suspend fun getListParts(@Path("part") part: String): Response<PartsResponse>

//    @GET("ets{ets}test{test}/{part}")

}