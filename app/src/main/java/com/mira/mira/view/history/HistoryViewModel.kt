package com.mira.mira.view.history

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mira.mira.data.api.MiraApiService
import com.mira.mira.data.model.HistoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val _historyList = MutableLiveData<List<HistoryItem>>()
    val historyList: LiveData<List<HistoryItem>> = _historyList

    init {
        fetchHistoryFromApi(application)
    }

    private fun fetchHistoryFromApi(context: Context) {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = "Bearer ${sharedPreferences.getString("auth_token", "")}"
        val patientId = sharedPreferences.getString("patient_id", "") ?: ""

        val retrofit = Retrofit.Builder()
            .baseUrl("https://mira-backend-abwswzd4sa-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(MiraApiService::class.java)

        apiService.getHistory(patientId, token).enqueue(object : Callback<List<HistoryItem>> {
            override fun onResponse(call: Call<List<HistoryItem>>, response: Response<List<HistoryItem>>) {
                if (response.isSuccessful) {
                    _historyList.value = response.body()
                } else {
                    _historyList.value = emptyList()
                    Log.e("HistoryViewModel", "Error fetching history: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<HistoryItem>>, t: Throwable) {
                _historyList.value = emptyList()
                Log.e("HistoryViewModel", "Error fetching history: ${t.message}")
            }
        })
    }
}
