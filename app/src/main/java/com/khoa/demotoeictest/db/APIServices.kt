package com.khoa.demotoeictest.db

import com.khoa.demotoeictest.model.Part1Response
import retrofit2.Response
import retrofit2.http.GET

interface APIServices {

    @GET("part1")
    suspend fun getListPart1(): Response<Part1Response>

//    @GET("ets{ets}test{test}/{part}")

}