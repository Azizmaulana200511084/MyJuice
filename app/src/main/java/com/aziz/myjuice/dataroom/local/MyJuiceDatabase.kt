package com.aziz.myjuice.dataroom.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MyJuiceEntity::class], version = 1, exportSchema = false)
abstract class MyJuiceDatabase : RoomDatabase() {
    abstract fun MyJuiceDao(): MyJuiceDao
}