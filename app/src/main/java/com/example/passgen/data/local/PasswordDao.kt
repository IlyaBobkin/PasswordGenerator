package com.example.passgen.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.passgen.data.model.PasswordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Query("SELECT * FROM passwords")
    fun getAll(): Flow<List<PasswordEntity>>

    @Insert
    suspend fun insert(password: PasswordEntity)

    @Delete
    suspend fun delete(password: PasswordEntity)
}