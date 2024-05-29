package com.mira.mira.view.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mira.mira.data.api.ApiConfig
import com.mira.mira.data.model.Article
import kotlinx.coroutines.Dispatchers

class ArticleViewModel : ViewModel() {

    fun getArticles() = liveData(Dispatchers.IO) {
        val response = ApiConfig.apiService.getArticles()
        if (response.success) {
            emit(response.data.posts)
        } else {
            emit(emptyList<Article>())
        }
    }
}
