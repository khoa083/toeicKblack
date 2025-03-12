package com.khoa.demotoeictest.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Migrations:
 *
 * Migrating databases depends on the changes made from one version to another.
 * All migrations should be stored in OGMigrations, which will be referenced
 * by the Room database builder. All migrations are SQL commands, implemented the following way:
 *
 * n -> previous DB version
 * m -> next DB version
 *
 * private val MIGRATION_N_M = object : Migration(n, m) {
 *     override fun migrate(database: SupportSQLiteDatabase) {
 *         database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, `name` TEXT, " +
 *         "PRIMARY KEY(`id`))")
 *     }
 *
 * Incrementing without a migration will cause a crash.
 *
 */

object Migrations {
    const val DB_VERSION = 1
    
    // Increment only with a migration
    val MIGRATION_1_2 = object : Migration(1,2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                ""
            )
        }
    }
}