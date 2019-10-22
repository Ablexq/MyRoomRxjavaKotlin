package com.xq.myroomrxjavakotlin.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao?

//    companion object {
//        //数据库变动添加Migration
//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL(
//                    "ALTER TABLE user "
//                            + " ADD COLUMN age INTEGER NOT NULL DEFAULT 0"
//                )
//            }
//        }
//    }
}