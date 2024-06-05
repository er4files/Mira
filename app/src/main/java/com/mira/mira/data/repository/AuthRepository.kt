package com.mira.mira.data.repository

import com.mira.mira.data.api.AuthService
import com.mira.mira.data.api.LoginRequest
import com.mira.mira.data.api.LoginResponse
import retrofit2.Callback

class AuthRepository(private val authService: AuthService) {
    fun signInWithEmail(request: LoginRequest, callback: Callback<LoginResponse>) {
        authService.signInWithEmail(request).enqueue(callback)
    }
}
