package com.example.passgen.domain

import com.example.passgen.data.repository.PasswordRepository
import com.example.passgen.domain.model.Password
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class InitAppUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    suspend operator fun invoke(): List<Password> {
        // Получаем пароли из Room (первое значение из Flow)
        return repository.getAll().first()
    }
}