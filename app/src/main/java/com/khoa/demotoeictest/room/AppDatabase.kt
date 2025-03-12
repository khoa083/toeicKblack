package com.khoa.demotoeictest.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.khoa.demotoeictest.room.listparts.FullTest
import com.khoa.demotoeictest.room.listparts.ListPartsDao
import com.khoa.demotoeictest.room.listparts.Listening
import com.khoa.demotoeictest.room.listparts.Part1
import com.khoa.demotoeictest.room.listparts.Part2
import com.khoa.demotoeictest.room.listparts.Part3
import com.khoa.demotoeictest.room.listparts.Part4
import com.khoa.demotoeictest.room.listparts.Part5
import com.khoa.demotoeictest.room.listparts.Part6
import com.khoa.demotoeictest.room.listparts.Part7
import com.khoa.demotoeictest.room.listparts.Reading
import com.khoa.demotoeictest.room.vocabs.ListTitleVocab
import com.khoa.demotoeictest.room.vocabs.Vocab600
import com.khoa.demotoeictest.room.vocabs.VocabsDao

@Database(
    entities = [
        Part1::class,
        Part2::class,
        Part3::class,
        Part4::class,
        Part5::class,
        Part6::class,
        Part7::class,
        Listening::class,
        Reading::class,
        FullTest::class,
        ListTitleVocab::class,
        Vocab600::class
    ],
    version = Migrations.DB_VERSION,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun listPartsDao(): ListPartsDao
    abstract fun vocabsDao(): VocabsDao
    
    companion object {
        const val DATABASE_NAME = "kblack_toeic_ver1"
    }

//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "kblack_toeic_ver1"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}