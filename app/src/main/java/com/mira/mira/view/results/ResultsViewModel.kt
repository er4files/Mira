package com.mira.mira.view.results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mira.mira.data.model.ResultItem

class ResultsViewModel : ViewModel() {
    private val _results = MutableLiveData<List<ResultItem>>()
    val results: LiveData<List<ResultItem>> get() = _results

    init {
        // Data contoh, ganti dengan logika pengambilan data yang sebenarnya
        _results.value = listOf(
            ResultItem(
                "Rahmad Era Sugiarto",
                "01-01-1990",
                "Pemeriksaan Extrimitas Atas Pergelangan Tangan"
            ),
            ResultItem("John Doe", "12-12-1985", "Pemeriksaan Kepala"),
            ResultItem("Jane Smith", "05-05-1992", "Pemeriksaan Jantung")
        )
    }
}
