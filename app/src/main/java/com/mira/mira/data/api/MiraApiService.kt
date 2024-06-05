package com.mira.mira.data.api

import com.mira.mira.data.model.HistoryItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MiraApiService {
    @GET("patients")
    fun getPatients(@Header("Authorization") token: String): Call<List<HistoryItem>>
}
