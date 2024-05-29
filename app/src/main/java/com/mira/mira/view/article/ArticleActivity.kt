package com.mira.mira.view.article

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mira.mira.R
import com.mira.mira.data.model.Article
import com.mira.mira.view.adapter.ArticleAdapter

class ArticleActivity : AppCompatActivity() {
    private lateinit var articleViewModel: ArticleViewModel

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

        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.rc_listnews)
        val articleAdapter = ArticleAdapter(emptyList())
        recyclerView.adapter = articleAdapter

        articleViewModel.getArticles().observe(this, Observer { articles ->
            articleAdapter.updateArticles(articles)
        })

        val tipsRecyclerView = findViewById<RecyclerView>(R.id.rc_listtips)
        val tipsAdapter = ArticleAdapter(getTips())
        tipsRecyclerView.adapter = tipsAdapter
    }

    private fun getTips(): List<Article> {
        return listOf(
            Article("Tips Kesehatan 1", "Deskripsi 1", "", "",""),
            Article("Tips Kesehatan 2", "Deskripsi 2", "", "",""),
            Article("Tips Kesehatan 3", "Deskripsi 3", "", "","")
        )
    }
}
