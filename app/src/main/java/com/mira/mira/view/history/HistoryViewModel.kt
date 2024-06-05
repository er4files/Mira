package com.mira.mira.view.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mira.mira.data.api.MiraApiService
import com.mira.mira.data.model.HistoryItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryViewModel(private val token: String) : ViewModel() {

    private val _historyList = MutableLiveData<List<HistoryItem>>()
    val historyList: LiveData<List<HistoryItem>> = _historyList

    init {
        fetchHistoryFromApi()
    }

    private fun fetchHistoryFromApi() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://mira-backend-abwswzd4sa-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        val service = retrofit.create(MiraApiService::class.java)

        service.getHistoryPatients("Bearer $token").enqueue(object : Callback<List<HistoryItem>> {
            override fun onResponse(call: Call<List<HistoryItem>>, response: Response<List<HistoryItem>>) {
                if (response.isSuccessful) {
                    _historyList.value = response.body()
                } else {
                    _historyList.value = emptyList()
                    Log.e("HistoryViewModel", "Error fetching results: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<HistoryItem>>, t: Throwable) {
                _historyList.value = emptyList()
                Log.e("HistoryViewModel", "Error fetching results: ${t.message}")
            }
        })
    }
}
