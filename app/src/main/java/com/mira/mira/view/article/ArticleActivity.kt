package com.mira.mira.view.article

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mira.mira.R
import com.mira.mira.data.model.Article
import com.mira.mira.view.adapter.ArticleAdapter
import com.mira.mira.view.article.ArticleViewModel


class ArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_article)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val backIcon: ImageView = findViewById(R.id.back_icon)
        backIcon.setOnClickListener {
            finish()
        }
        val articleAdapter = ArticleAdapter(getArticles())
        val recyclerView = findViewById<RecyclerView>(R.id.rc_listnews)
        recyclerView.adapter = articleAdapter

        val tipsAdapter = ArticleAdapter(getTips())
        val tipsRecyclerView = findViewById<RecyclerView>(R.id.rc_listtips)
        tipsRecyclerView.adapter = tipsAdapter


    }

    private fun getTips(): List<Article> {
        val articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        return articleViewModel.getTips()
    }

    private fun getArticles(): List<Article> {
        val articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        return articleViewModel.getDummyArticles()
    }
}
