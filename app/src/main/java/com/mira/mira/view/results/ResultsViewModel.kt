package com.mira.mira.view.results

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.mira.mira.data.model.ResultItem

class ResultsViewModel : ViewModel() {
    private val _results = MutableLiveData<List<ResultItem>>()
    val results: LiveData<List<ResultItem>> get() = _results

    init {
        fetchResultsFromFirestore()
    }

    private fun fetchResultsFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("pasien")
            .get()
            .addOnSuccessListener { result ->
                val resultList = mutableListOf<ResultItem>()
                for (document in result) {
                    val name = document.getString("nama_pasien") ?: ""
                    val date = document.getString("tanggal_kunjungan") ?: ""
                    val examinationType = document.getString("jenis_periksa") ?: ""
                    val status = document.getBoolean("status_hasil") ?: false
                    val resultItem = ResultItem(name, date, examinationType, status)
                    resultList.add(resultItem)
                }
                _results.value = resultList
            }
            .addOnFailureListener { exception ->
                _results.value = emptyList()
                Log.e("ResultsViewModel", "Error fetching results: ${exception.message}")
            }
    }
}
