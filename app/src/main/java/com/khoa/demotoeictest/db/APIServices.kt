package com.khoa.demotoeictest.db

import com.khoa.demotoeictest.model.ListVocabDataResponse
import com.khoa.demotoeictest.model.ListVocabResponse
import com.khoa.demotoeictest.model.PartsDataResponse
import com.khoa.demotoeictest.model.PartsResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIServices {

    @GET("part/{part}")
    suspend fun getListParts(
        @Path("part") part: String
    ): Response<PartsResponse>

    @GET("ets{ets}/test{test}/{part}")
    suspend fun getListPartsData(
        @Path("ets") ets: String,
        @Path("test") test: String,
        @Path("part") part: String
    ): Response<PartsDataResponse>

    @GET("ets{ets}/test{test}/type/{type}")
    suspend fun getListTypesData(
        @Path("ets") ets: String,
        @Path("test") test: String,
        @Path("type") part: String
    ): Response<PartsDataResponse>

    @GET("listvocab")
    suspend fun getListVocab(): Response<ListVocabResponse>

    @GET("vocab/{type}")
    suspend fun getListVocabData(
        @Path("type") type: String
    ): Response<ListVocabDataResponse>

    @FormUrlEncoded
    @PUT("vocab/{id}")
    suspend fun putFavoriteVocabData(
        @Path("id") id: String,
        @Field("favorite") favorite: String,
    ): Response<ListVocabDataResponse>

}