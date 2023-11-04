package com.khoa.demotoeictest.db

import com.khoa.demotoeictest.model.PartsDataResponse
import com.khoa.demotoeictest.model.PartsResponse
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path

interface APIServices {

    @GET("part{part}")
    suspend fun getListParts(
        @Path("part") part: String
    ): Response<PartsResponse>

//    @FormUrlEncoded
    @GET("ets{ets}test{test}/{part}")
    suspend fun getListPartsData(
        @Path("ets") ets: String,
        @Path("test") test: String,
        @Path("part") part: String
    ): Response<PartsDataResponse>

    @GET("ets{ets}test{test}/type/{type}")
    suspend fun getListTypesData(
        @Path("ets") ets: String,
        @Path("test") test: String,
        @Path("type") part: String
    ): Response<PartsDataResponse>

//    @GET("ets{ets}test{test}")
//    suspend fun getListFullData(
//        @Path("ets") ets: String,
//        @Path("test") test: String
//    ): Response<PartsDataResponse>

    @GET("vocab/{type}")
    suspend fun getVocab(
        @Path("type") type: String
    )



}