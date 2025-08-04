package com.example.passgen.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passgen.data.model.PasswordEntity

@Database(
    entities = [PasswordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PasswordDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao
}