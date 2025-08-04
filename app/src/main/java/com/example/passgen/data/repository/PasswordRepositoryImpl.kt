package com.example.passgen.data.repository

import com.example.passgen.data.local.PasswordDao
import com.example.passgen.domain.model.Password
import com.example.passgen.domain.model.toDomain
import com.example.passgen.domain.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PasswordRepositoryImpl @Inject constructor(
    private val dao: PasswordDao
) : PasswordRepository {

    override fun getAll(): Flow<List<Password>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override suspend fun save(password: Password) {
        dao.insert(password.toEntity())
    }

    override suspend fun delete(password: Password) {
        dao.delete(password.toEntity())
    }
}