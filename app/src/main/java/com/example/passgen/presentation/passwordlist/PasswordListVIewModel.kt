package com.example.passgen.presentation.passwordlist

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passgen.data.repository.PasswordRepository
import com.example.passgen.domain.model.Password
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordListViewModel @Inject constructor(
    private val repository: PasswordRepository
) : ViewModel() {

    val passwords: StateFlow<List<Password>> = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deletePassword(password: Password) {
        viewModelScope.launch {
            repository.delete(password)
        }
    }

    fun copyToClipboard(context: Context, password: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText("Password", password))
    }

    private var currentGroup: String? = null

    fun setCurrentGroup(group: String) {
        currentGroup = group
    }

    fun exportPasswords(context: Context, uri: Uri) {
        val group = currentGroup ?: return
        val listToExport = passwords.value.filter {
            val groupKey = it.filePath ?: "Сгенерированные"
            groupKey == group
        }

        fun exportPasswords(context: Context, uri: Uri, group: String) {
            val toExport = passwords.value.filter {
                if (group == "Сгенерированные") {
                    it.filePath == null
                } else {
                    it.filePath == group
                }
            }

            context.contentResolver.openOutputStream(uri)?.bufferedWriter()?.use { writer ->
                toExport.forEach { pw ->
                    writer.write("${pw.value} | ${"%.2f".format(pw.entropy)} | ${pw.charset}\n")
                }
            }
        }
    }
}