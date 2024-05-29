package com.mira.mira.view.article

import androidx.lifecycle.ViewModel
import com.mira.mira.data.model.Article

class ArticleViewModel : ViewModel() {

    fun getDummyArticles(): List<Article> {
        return listOf(
            Article("Judul 1", "Deskripsi 1"),
            Article("Judul 2", "Deskripsi 2"),
            Article("Judul 3", "Deskripsi 3")
        )
    }

    fun getTips(): List<Article> {
        return listOf(
            Article("Tips Kesehatan 1", "Deskripsi 1"),
            Article("Tips Kesehatan 2", "Deskripsi 2"),
            Article("Tips Kesehatan 3", "Deskripsi 3")
        )
    }
}
