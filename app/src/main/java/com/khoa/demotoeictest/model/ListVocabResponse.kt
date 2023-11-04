package com.khoa.demotoeictest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ListVocabModel(var response: ListVocabResponse)
data class ListVocabResponse(@SerializedName("allListVocab") var listVocab: ArrayList<ListVocab>? = null)

@Parcelize
data class ListVocab(
    @SerializedName("id") val id: String? = null,
    @SerializedName("img") val img: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("des") val des: String? = null,
    @SerializedName("type") val type: String? = null,
): Parcelable