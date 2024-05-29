package com.mira.mira.data.api

import com.mira.mira.data.model.ApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("/merdeka/sehat/")
    suspend fun getArticles(): ApiResponse
}