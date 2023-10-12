package com.khoa.demotoeictest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Part1Model(var response: Part1Response)

data class Part1Response(@SerializedName("part1") var listPart1: ArrayList<Part1>? = null)
@Parcelize
data class Part1(
    @SerializedName("id") val id:String? = null,
    @SerializedName("title") val title:String? = null,
    @SerializedName("numQuestions") val numQuestions:String? = null,
    @SerializedName("des") val des:String? = null,
    @SerializedName("ets") val ets:String? = null,
    @SerializedName("test") val test:String? = null,
    @SerializedName("part") val part:String? = null,
): Parcelable
