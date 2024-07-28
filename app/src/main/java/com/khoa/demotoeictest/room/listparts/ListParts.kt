package com.khoa.demotoeictest.room.listparts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "part_1")
data class Part1(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val listPartsTitle: String,
    @ColumnInfo(name = "numQuestions") val listPartsNumQues: String,
    @ColumnInfo(name = "des") val listPartsDes: String,
    @ColumnInfo(name = "ets") var listPartsEts: String,
    @ColumnInfo(name = "test") val listPartsTest: String,
    @ColumnInfo(name = "part") val listPartsPart: String,
)

@Entity(tableName = "part_2")
data class Part2(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val listPartsTitle: String,
    @ColumnInfo(name = "numQuestions") val listPartsNumQues: String,
    @ColumnInfo(name = "des") val listPartsDes: String,
    @ColumnInfo(name = "ets") var listPartsEts: String,
    @ColumnInfo(name = "test") val listPartsTest: String,
    @ColumnInfo(name = "part") val listPartsPart: String,
)

@Entity(tableName = "part_3")
data class Part3(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val listPartsTitle: String,
    @ColumnInfo(name = "numQuestions") val listPartsNumQues: String,
    @ColumnInfo(name = "des") val listPartsDes: String,
    @ColumnInfo(name = "ets") var listPartsEts: String,
    @ColumnInfo(name = "test") val listPartsTest: String,
    @ColumnInfo(name = "part") val listPartsPart: String,
)

@Entity(tableName = "part_4")
data class Part4(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val listPartsTitle: String,
    @ColumnInfo(name = "numQuestions") val listPartsNumQues: String,
    @ColumnInfo(name = "des") val listPartsDes: String,
    @ColumnInfo(name = "ets") var listPartsEts: String,
    @ColumnInfo(name = "test") val listPartsTest: String,
    @ColumnInfo(name = "part") val listPartsPart: String,
)

@Entity(tableName = "part_5")
data class Part5(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val listPartsTitle: String,
    @ColumnInfo(name = "numQuestions") val listPartsNumQues: String,
    @ColumnInfo(name = "des") val listPartsDes: String,
    @ColumnInfo(name = "ets") var listPartsEts: String,
    @ColumnInfo(name = "test") val listPartsTest: String,
    @ColumnInfo(name = "part") val listPartsPart: String,
)

@Entity(tableName = "part_6")
data class Part6(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val listPartsTitle: String,
    @ColumnInfo(name = "numQuestions") val listPartsNumQues: String,
    @ColumnInfo(name = "des") val listPartsDes: String,
    @ColumnInfo(name = "ets") var listPartsEts: String,
    @ColumnInfo(name = "test") val listPartsTest: String,
    @ColumnInfo(name = "part") val listPartsPart: String,
)

@Entity(tableName = "part_7")
data class Part7(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val listPartsTitle: String,
    @ColumnInfo(name = "numQuestions") val listPartsNumQues: String,
    @ColumnInfo(name = "des") val listPartsDes: String,
    @ColumnInfo(name = "ets") var listPartsEts: String,
    @ColumnInfo(name = "test") val listPartsTest: String,
    @ColumnInfo(name = "part") val listPartsPart: String,
)

@Entity(tableName = "part_listen")
data class Listening(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val listPartsTitle: String,
    @ColumnInfo(name = "numQuestions") val listPartsNumQues: String,
    @ColumnInfo(name = "des") val listPartsDes: String,
    @ColumnInfo(name = "ets") var listPartsEts: String,
    @ColumnInfo(name = "test") val listPartsTest: String,
    @ColumnInfo(name = "part") val listPartsPart: String,
)

@Entity(tableName = "part_read")
data class Reading(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val listPartsTitle: String,
    @ColumnInfo(name = "numQuestions") val listPartsNumQues: String,
    @ColumnInfo(name = "des") val listPartsDes: String,
    @ColumnInfo(name = "ets") var listPartsEts: String,
    @ColumnInfo(name = "test") val listPartsTest: String,
    @ColumnInfo(name = "part") val listPartsPart: String,
)

@Entity(tableName = "full_test")
data class FullTest(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val listPartsTitle: String,
    @ColumnInfo(name = "numQuestions") val listPartsNumQues: String,
    @ColumnInfo(name = "des") val listPartsDes: String,
    @ColumnInfo(name = "ets") var listPartsEts: String,
    @ColumnInfo(name = "test") val listPartsTest: String,
    @ColumnInfo(name = "part") val listPartsPart: String,
)

