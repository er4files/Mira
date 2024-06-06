package com.mira.mira.data.retrofit
import com.mira.mira.data.api.MiraApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://mira-backend-abwswzd4sa-et.a.run.app/"

    val instance: MiraApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(MiraApiService::class.java)
    }
}
