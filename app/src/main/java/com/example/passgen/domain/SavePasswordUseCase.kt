package com.example.passgen.domain

import com.example.passgen.data.repository.PasswordRepository
import com.example.passgen.domain.model.Password
import javax.inject.Inject

class SavePasswordUseCase @Inject constructor(
    private val repository: PasswordRepository
) {
    suspend operator fun invoke(password: Password) {
        repository.save(password)
    }
}