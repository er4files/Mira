package com.mira.mira.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mira.mira.R
import com.mira.mira.data.model.Article
import com.mira.mira.view.adapter.ArticleAdapter
import com.mira.mira.view.article.ArticleActivity
import com.mira.mira.view.history.HistoryActivity
import com.mira.mira.view.notification.NotificationActivity
import com.mira.mira.view.article.ArticleViewModel
import com.mira.mira.view.consultation.ConsultationActivity

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleViewModel: ArticleViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.rc_listnews)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        articleAdapter = ArticleAdapter(emptyList())
        recyclerView.adapter = articleAdapter

        // Initialize ViewModel
        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        articleViewModel.getArticles().observe(viewLifecycleOwner, Observer { articles ->
            // limit 3 article
            articleAdapter.updateArticles(articles.take(3))
        })

        setUpFeatureClickListeners(view)

        return view
    }

    private fun setUpFeatureClickListeners(view: View) {
        // Notification
        val notificationIcon: RelativeLayout = view.findViewById(R.id.notification_icon)
        notificationIcon.setOnClickListener {
            val intent = Intent(activity, NotificationActivity::class.java)
            startActivity(intent)
        }

        // History
        val featureHistory: LinearLayout = view.findViewById(R.id.feature_history)
        featureHistory.setOnClickListener {
            val intent = Intent(activity, HistoryActivity::class.java)
            startActivity(intent)
        }

        // Consultation Doctor
        val featureConsultation: LinearLayout = view.findViewById(R.id.feature_doctorconsultation)
        featureConsultation.setOnClickListener {
            val intent = Intent(activity, ConsultationActivity::class.java)
            startActivity(intent)
        }

        // Article Health
        val featureArticle: LinearLayout = view.findViewById(R.id.feature_article)
        featureArticle.setOnClickListener {
            val intent = Intent(activity, ArticleActivity::class.java)
            startActivity(intent)
        }
    }
}
