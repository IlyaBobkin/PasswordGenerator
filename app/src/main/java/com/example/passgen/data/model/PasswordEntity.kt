package com.example.passgen.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "passwords")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val password: String,
    val entropy: Double,
    val charSet: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isFromFile: Boolean = false,
    val filePath: String? = null
)
