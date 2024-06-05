package com.mira.mira.data.api

import com.mira.mira.data.model.HistoryItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MiraApiService {
    @GET("patient/{id}/history")
    fun getHistory(
        @Path("id") patientId: String,
        @Header("Authorization") token: String
    ): Call<List<HistoryItem>>
}
