package com.khoa.demotoeictest.room.vocabs

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabsDao {
    @Query("SELECT * FROM list_title_vocab ")
    fun getAllListTitleVocab(): Flow<List<ListTitleVocab>>

    @Query("SELECT * FROM vocab_600 ")
    fun getAllVocab600(): Flow<List<Vocab600>>

    @Update
    suspend fun updateFavor(favor: Vocab600)
}