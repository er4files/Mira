package com.mira.mira.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("v1/accounts:signInWithPassword?key=AIzaSyBEo_j25kWJzXgr54Fzuza-xSLanbAYOj8")
    fun signInWithEmail(@Body request: LoginRequest): Call<LoginResponse>
}

data class LoginRequest(val email: String, val password: String, val returnSecureToken: Boolean = true)
data class LoginResponse(val idToken: String, val email: String, val refreshToken: String, val expiresIn: String, val localId: String)
