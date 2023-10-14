package com.khoa.demotoeictest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PartsDataModel(var response: PartsDataResponse)

data class PartsDataResponse(@SerializedName("partEts") var listPartsData: ArrayList<PartsData>? = null)
@Parcelize
data class PartsData(
    @SerializedName("id") val id: String? = null,
    @SerializedName("img") val img: String? = null,
    @SerializedName("audio") val audio: String? = null,
    @SerializedName("question") val question: String? = null,
    @SerializedName("smallQues1") val smallQues1: String? = null,
    @SerializedName("a1") val a1: String? = null,
    @SerializedName("b1") val b1: String? = null,
    @SerializedName("c1") val c1: String? = null,
    @SerializedName("d1") val d1: String? = null,
    @SerializedName("correctAnswer1") val correctAnswer1: String? = null,
    @SerializedName("smallQues2") val smallQues2: String? = null,
    @SerializedName("a2") val a2: String? = null,
    @SerializedName("b2") val b2: String? = null,
    @SerializedName("c2") val c2: String? = null,
    @SerializedName("d2") val d2: String? = null,
    @SerializedName("correctAnswer2") val correctAnswer2: String? = null,
    @SerializedName("smallQues3") val smallQues3: String? = null,
    @SerializedName("a3") val a3: String? = null,
    @SerializedName("b3") val b3: String? = null,
    @SerializedName("c3") val c3: String? = null,
    @SerializedName("d3") val d3: String? = null,
    @SerializedName("correctAnswer3") val correctAnswer3: String? = null,
    @SerializedName("smallQues4") val smallQues4: String? = null,
    @SerializedName("a4") val a4: String? = null,
    @SerializedName("b4") val b4: String? = null,
    @SerializedName("c4") val c4: String? = null,
    @SerializedName("d4") val d4: String? = null,
    @SerializedName("correctAnswer4") val correctAnswer4: String? = null,
    @SerializedName("smallQues5") val smallQues5: String? = null,
    @SerializedName("a5") val a5: String? = null,
    @SerializedName("b5") val b5: String? = null,
    @SerializedName("c5") val c5: String? = null,
    @SerializedName("d5") val d5: String? = null,
    @SerializedName("correctAnswer5") val correctAnswer5: String? = null,
    @SerializedName("part") val part: String? = null
): Parcelable