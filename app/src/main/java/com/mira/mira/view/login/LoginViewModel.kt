package com.mira.mira.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mira.mira.data.pref.UserModel
import com.mira.mira.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}