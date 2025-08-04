package com.example.passgen.presentation.generator

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passgen.domain.GeneratePasswordUseCase
import com.example.passgen.domain.SavePasswordUseCase
import com.example.passgen.domain.model.Password
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneratorScreenViewModel @Inject constructor(
    private val generatePasswordUseCase: GeneratePasswordUseCase,
    private val savePasswordUseCase: SavePasswordUseCase
) : ViewModel() {

    var length by mutableIntStateOf(12)
    var useLetters by mutableStateOf(true)
    var useDigits by mutableStateOf(true)
    var useSymbols by mutableStateOf(false)

    var generatedPassword by mutableStateOf("")
    var entropy by mutableDoubleStateOf(0.0)
    var charsetUsed by mutableStateOf("")
    var customSymbols: String by mutableStateOf("")
    private val _saveCompleted = MutableStateFlow(false)
    val saveCompleted: StateFlow<Boolean> = _saveCompleted



    fun generate() {
        val charset = buildCharset()
        charsetUsed = charset
        val (password, ent) = generatePasswordUseCase(length, charset)
        generatedPassword = password
        entropy = ent
    }

    fun savePassword() {
        viewModelScope.launch {
            savePasswordUseCase(
                Password(
                    value = generatedPassword,
                    entropy = entropy,
                    charset = charsetUsed,
                    id = 0,
                    createdAt = System.currentTimeMillis(),
                    isFromFile = false,
                    filePath = null
                )
            )
            _saveCompleted.value = true
        }
    }

    private fun buildCharset(): String {
        var chars = ""
        if (useLetters) chars += "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        if (useDigits) chars += "0123456789"
        if (useSymbols) chars += "!@#\$%^&*()-_=+[]{};:,.<>/?"
        chars += customSymbols
        return chars.ifEmpty { "abc" }
    }

    fun importPasswords(context: Context, uri: Uri) {
        viewModelScope.launch {
            val fileName = getFileName(context, uri) ?: "file_${System.currentTimeMillis()}"
            val inputStream = context.contentResolver.openInputStream(uri)

            val passwords = inputStream?.bufferedReader()?.readLines()
                ?.filter { it.isNotBlank() }
                ?.map { line ->
                    Password(
                        value = line.trim(),
                        entropy = 0.0,
                        charset = "",
                        isFromFile = true,
                        filePath = fileName,
                        createdAt = System.currentTimeMillis(),
                        id = 0
                    )
                } ?: emptyList()

            passwords.forEach { savePasswordUseCase(it) }
            _saveCompleted.value = true
        }
    }
    fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: -1
        cursor?.moveToFirst()
        val name = if (nameIndex >= 0) cursor?.getString(nameIndex) else null
        cursor?.close()
        return name
    }
    fun resetSaveCompleted() {
        _saveCompleted.value = false
    }
}