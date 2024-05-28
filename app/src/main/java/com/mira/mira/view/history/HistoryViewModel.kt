package com.mira.mira.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mira.mira.data.model.HistoryItem

class HistoryViewModel : ViewModel() {

    private val _historyList = MutableLiveData<List<HistoryItem>>()
    val historyList: LiveData<List<HistoryItem>> = _historyList

    init {
        val initialData = listOf(
            HistoryItem("Rahmad Era Sugiarto", "01 Jan 1990", "07:00-10:00", "Pemeriksaan Ekstrimitas Atas Pergelangan Tangan", false),
            HistoryItem("John Doe", "02 Feb 1992", "09:00-12:00", "Pemeriksaan Mata", true)

        )
        _historyList.value = initialData
    }

    fun addHistoryItem(historyItem: HistoryItem) {
        val currentList = _historyList.value.orEmpty().toMutableList()
        currentList.add(historyItem)
        _historyList.value = currentList
    }
}
