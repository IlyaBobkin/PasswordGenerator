package com.example.passgen.presentation.loading

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passgen.domain.InitAppUseCase
import com.example.passgen.domain.model.Password
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingScreenViewModel @Inject constructor(
    private val initAppUseCase: InitAppUseCase
) : ViewModel() {
    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> = _isLoaded.asStateFlow()

    val loadedPasswords = MutableStateFlow<List<Password>>(emptyList())

    fun startInit() {
        viewModelScope.launch {
            val passwords = initAppUseCase()
            loadedPasswords.value = passwords
            _isLoaded.value = true
        }
    }

}