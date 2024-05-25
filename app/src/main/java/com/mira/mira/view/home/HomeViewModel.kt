package com.mira.mira.view.home

import androidx.lifecycle.ViewModel
import com.mira.mira.data.model.Article

class HomeViewModel : ViewModel() {

    fun getDummyArticles(): List<Article> {
        return listOf(
            Article("Judul 1", "Deskripsi 1"),
            Article("Judul 2", "Deskripsi 2"),
            Article("Judul 3", "Deskripsi 3")
        )
    }
}
