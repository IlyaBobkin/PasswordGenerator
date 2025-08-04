package com.example.passgen.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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
