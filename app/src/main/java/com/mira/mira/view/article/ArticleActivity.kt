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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        val recyclerViewNews = findViewById<RecyclerView>(R.id.rc_listnews)
        val articleAdapterNews = ArticleAdapter(emptyList())
        recyclerViewNews.adapter = articleAdapterNews

        val recyclerViewTips = findViewById<RecyclerView>(R.id.rc_listtips)
        val articleAdapterTips = ArticleAdapter(emptyList())
        recyclerViewTips.adapter = articleAdapterTips

        // Observe articles LiveData and update UI
        articleViewModel.getArticles().observe(this, Observer { articles ->
            articleAdapterNews.updateArticles(articles)
        })

        // Fetch tips asynchronously and update UI
        GlobalScope.launch(Dispatchers.Main) {
            val tips = articleViewModel.getTips()
            articleAdapterTips.updateArticles(tips)
        }
    }
}