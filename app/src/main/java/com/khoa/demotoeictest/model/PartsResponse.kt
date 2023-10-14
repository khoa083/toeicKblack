package com.khoa.demotoeictest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PartsModel(var response: PartsResponse)

data class PartsResponse(@SerializedName("parts") var listParts: ArrayList<Parts>? = null)
@Parcelize
data class Parts(
    @SerializedName("id") val id:String? = null,
    @SerializedName("title") val title:String? = null,
    @SerializedName("numQuestions") val numQuestions:String? = null,
    @SerializedName("des") val des:String? = null,
    @SerializedName("ets") val ets:String? = null,
    @SerializedName("test") val test:String? = null,
    @SerializedName("part") val part:String? = null,
): Parcelable
