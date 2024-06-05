package com.mira.mira.data.api

import com.mira.mira.data.model.HistoryItem
import com.mira.mira.data.model.ResultItem
import com.mira.mira.data.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MiraApiService {
    @GET("patients")
    fun getHistoryPatients(@Header("Authorization") token: String): Call<List<HistoryItem>>

    @GET("patients")
    fun getResultPatients(@Header("Authorization") token: String): Call<List<ResultItem>>

    @GET("user")
    fun getUserData(@Header("Authorization") token: String): Call<UserData>
}
