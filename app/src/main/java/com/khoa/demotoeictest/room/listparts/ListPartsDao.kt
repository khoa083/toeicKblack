package com.khoa.demotoeictest.room.listparts

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ListPartsDao {
    @Query("SELECT * FROM part_1")
    fun getAllPart1(): Flow<List<Part1>>

    @Query("SELECT * FROM part_2")
    fun getAllPart2(): Flow<List<Part2>>

    @Query("SELECT * FROM part_3")
    fun getAllPart3(): Flow<List<Part3>>

    @Query("SELECT * FROM part_4")
    fun getAllPart4(): Flow<List<Part4>>

    @Query("SELECT * FROM part_5")
    fun getAllPart5(): Flow<List<Part5>>

    @Query("SELECT * FROM part_6")
    fun getAllPart6(): Flow<List<Part6>>

    @Query("SELECT * FROM part_7")
    fun getAllPart7(): Flow<List<Part7>>

    @Query("SELECT * FROM part_listen")
    fun getAllPartListen(): Flow<List<Listening>>

    @Query("SELECT * FROM part_read")
    fun getAllPartRead(): Flow<List<Reading>>

    @Query("SELECT * FROM full_test")
    fun getAllFullTest(): Flow<List<FullTest>>
}