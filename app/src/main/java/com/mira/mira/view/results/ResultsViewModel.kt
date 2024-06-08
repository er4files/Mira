package com.mira.mira.view.results

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mira.mira.data.api.MiraApiService
import com.mira.mira.data.model.ResultItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResultsViewModel(private val token: String) : ViewModel() {

    private val _resultsList = MutableLiveData<List<ResultItem>>()
    val results: LiveData<List<ResultItem>> get() = _resultsList

    init {
        fetchResultsFromApi()
    }

    private fun fetchResultsFromApi() {
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

        service.getResultPatients("Bearer $token").enqueue(object : Callback<List<ResultItem>> {
            override fun onResponse(call: Call<List<ResultItem>>, response: Response<List<ResultItem>>) {
                if (response.isSuccessful) {
                    _resultsList.value = response.body()
                } else {
                    _resultsList.value = emptyList()
                    Log.e("ResultViewModel", "Error fetching results: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<ResultItem>>, t: Throwable) {
                _resultsList.value = emptyList()
                Log.e("ResultViewModel", "Error fetching results", t)
            }
        })
    }
}
