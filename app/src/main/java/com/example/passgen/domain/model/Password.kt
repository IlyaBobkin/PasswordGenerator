package com.example.passgen.domain.model

import com.example.passgen.data.model.PasswordEntity

data class Password(
    val id: Int,
    val value: String,
    val entropy: Double,
    val charset: String,
    val createdAt: Long,
    val isFromFile: Boolean,
    val filePath: String?
)

fun PasswordEntity.toDomain() = Password(
    id = id,
    value = password,
    entropy = entropy,
    charset = charSet,
    createdAt = createdAt,
    isFromFile = isFromFile,
    filePath = filePath
)

fun Password.toEntity() = PasswordEntity(
    id = id,
    password = value,
    entropy = entropy,
    charSet = charset,
    createdAt = createdAt,
    isFromFile = isFromFile,
    filePath = filePath
)