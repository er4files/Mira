package com.mira.mira.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mira.mira.R
import com.mira.mira.data.model.Article

class ArticleAdapter(private var articleList: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articleList[position]
        holder.titleTextView.text = article.title
        Glide.with(holder.itemView.context)
            .load(article.thumbnail)
            .into(holder.bannerImageView)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    fun updateArticles(articles: List<Article>) {
        this.articleList = articles
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.tv_judul_article)
        val bannerImageView: ImageView = itemView.findViewById(R.id.bannerstory)
    }
}
