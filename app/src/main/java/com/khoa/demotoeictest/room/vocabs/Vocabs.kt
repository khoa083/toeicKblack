package com.khoa.demotoeictest.room.vocabs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_title_vocab")
data class ListTitleVocab(
    @PrimaryKey val id: String,
    @ColumnInfo("img") val img: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("des") val des: String,
    @ColumnInfo("type") val type: String,
)

@Entity(tableName = "vocab_600")
data class Vocab600(
    @PrimaryKey val id: String,
    @ColumnInfo("img") val img: String,
    @ColumnInfo("audio") val audio: String,
    @ColumnInfo("meaning") val meaning: String,
    @ColumnInfo("vocabulary") val vocabulary: String,
    @ColumnInfo("pronunciation") val pronunciation: String,
    @ColumnInfo("translation") val translation: String,
    @ColumnInfo("exampleEng") val exampleEng: String,
    @ColumnInfo("exampleVn") val exampleVn: String,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("favorite") var favorite: String,
)
