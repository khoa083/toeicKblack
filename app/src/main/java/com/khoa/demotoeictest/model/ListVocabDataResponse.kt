package com.khoa.demotoeictest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ListVocabDataModel(var response: ListVocabDataResponse)

data class ListVocabDataResponse(@SerializedName("vocab") var listVocabData:  ArrayList<ListVocabData>? = null)
@Parcelize
data class ListVocabData(
    @SerializedName("id") val id: String? = null,
    @SerializedName("img") val img: String? = null,
    @SerializedName("audio") val audio: String? = null,
    @SerializedName("meaning") val meaning: String? = null,
    @SerializedName("vocabulary") val vocabulary: String? = null,
    @SerializedName("pronunciation") val pronunciation: String? = null,
    @SerializedName("translation") val translation: String? = null,
    @SerializedName("exampleEng") val exampleEng: String? = null,
    @SerializedName("exampleVn") val exampleVn: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("favorite") var favorite: String? = null,
): Parcelable