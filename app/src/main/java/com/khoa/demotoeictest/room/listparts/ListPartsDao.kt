package com.khoa.demotoeictest.room.listparts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface ListPartsDao {

    @Query("SELECT * FROM part_1")
    fun getAllTitlePart1(): Flow<List<Part1>>

    @Query("SELECT * FROM part_2")
    fun getAllTitlePart2(): Flow<List<Part2>>

    @Query("SELECT * FROM part_3")
    fun getAllTitlePart3(): Flow<List<Part3>>

    @Query("SELECT * FROM part_4")
    fun getAllTitlePart4(): Flow<List<Part4>>

    @Query("SELECT * FROM part_5")
    fun getAllTitlePart5(): Flow<List<Part5>>

    @Query("SELECT * FROM part_6")
    fun getAllTitlePart6(): Flow<List<Part6>>

    @Query("SELECT * FROM part_7")
    fun getAllTitlePart7(): Flow<List<Part7>>

    @Query("SELECT * FROM part_listen")
    fun getAllTitlePartListen(): Flow<List<Listening>>

    @Query("SELECT * FROM part_read")
    fun getAllTitlePartRead(): Flow<List<Reading>>

    @Query("SELECT * FROM full_test")
    fun getAllTitleFullTest(): Flow<List<FullTest>>

    //TODO: Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTitlePart1(part1: List<Part1>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTitlePart2(part2: List<Part2>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTitlePart3(part3: List<Part3>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTitlePart4(part4: List<Part4>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTitlePart5(part5: List<Part5>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTitlePart6(part6: List<Part6>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTitlePart7(part7: List<Part7>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTitlePartListen(listen: List<Listening>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTitlePartRead(read: List<Reading>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTitleFullTest(titleFull: List<FullTest>)

}