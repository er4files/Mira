package com.mira.mira.data.api

import com.mira.mira.data.model.ApiResponse
import retrofit2.http.GET

interface ApiService {
    @GET("antara/lifestyle/")
    suspend fun getArticles(): ApiResponse

    @GET("tempo/cantik/")
    suspend fun getTips(): ApiResponse
}