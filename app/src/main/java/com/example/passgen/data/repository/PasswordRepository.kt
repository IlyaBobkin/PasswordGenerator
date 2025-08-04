package com.example.passgen.data.repository

import com.example.passgen.domain.model.Password
import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    fun getAll(): Flow<List<Password>>
    suspend fun save(password: Password)
    suspend fun delete(password: Password)
}