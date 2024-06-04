package com.mira.mira.view.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.mira.mira.data.model.HistoryItem

class HistoryViewModel : ViewModel() {

    private val _historyList = MutableLiveData<List<HistoryItem>>()
    val historyList: LiveData<List<HistoryItem>> = _historyList

    init {
        fetchHistoryFromFirestore()
    }

    private fun fetchHistoryFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        db.collection("pasien")
            .get()
            .addOnSuccessListener { result ->
                val historyList = mutableListOf<HistoryItem>()
                for (document in result) {
                    val name = document.getString("nama_pasien") ?: ""
                    val date = document.getString("tanggal_kunjungan") ?: ""
                    val time = document.getString("waktu_kunjungan") ?: ""
                    val exam = document.getString("jenis_periksa") ?: ""
                    val status = document.getString("status_kunjungan") ?: "menunggu konfirmasi"
                    val historyItem = HistoryItem(name, date, time, exam, status)
                    historyList.add(historyItem)
                }
                _historyList.value = historyList
            }
            .addOnFailureListener { exception ->
                _historyList.value = emptyList()
                Log.e("HistoryViewModel", "Error fetching results: ${exception.message}")
            }
    }
}
