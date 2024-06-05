package com.mira.mira.view.home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.mira.mira.R
import com.mira.mira.data.api.MiraApiService
import com.mira.mira.data.model.UserData
import com.mira.mira.view.adapter.ArticleAdapter
import com.mira.mira.view.article.ArticleActivity
import com.mira.mira.view.consultation.ConsultationActivity
import com.mira.mira.view.history.HistoryActivity
import com.mira.mira.view.notification.NotificationActivity
import com.mira.mira.view.article.ArticleViewModel
import com.mira.mira.view.formReservation.FormReservationActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var tvUser: TextView
    private lateinit var firestore: FirebaseFirestore
    private lateinit var miraApiService: MiraApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize TextView
        tvUser = view.findViewById(R.id.tv_user)

        // Retrieve token from SharedPreferences
        val token = retrieveTokenFromSharedPreferences()

        // Initialize Retrofit
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://mira-backend-abwswzd4sa-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        miraApiService = retrofit.create(MiraApiService::class.java)

        // Fetch username from API
        miraApiService.getUserData("Bearer $token").enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    val userData = response.body()
                    userData?.let {
                        tvUser.text = it.username
                    }
                } else {
                    tvUser.text = "User"
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                tvUser.text = "User"
            }
        })

        setUpFeatureClickListeners(view)

        // Floating action button
        val fab: FloatingActionButton = view.findViewById(R.id.elevated_button_id)
        TooltipCompat.setTooltipText(fab, "Ada yang bisa saya bantu?")

        return view
    }

    private fun setUpFeatureClickListeners(view: View) {
        // Notification
        val notificationIcon: RelativeLayout = view.findViewById(R.id.notification_icon)
        notificationIcon.setOnClickListener {
            val intent = Intent(activity, NotificationActivity::class.java)
            startActivity(intent)
        }
        // Form Reservation
        val featureFormReservation: LinearLayout = view.findViewById(R.id.feature_formreservation)
        featureFormReservation.setOnClickListener {
            val intent = Intent(activity, FormReservationActivity::class.java)
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

    private fun retrieveTokenFromSharedPreferences(): String {
        // Retrieve token from SharedPreferences or other source
        val sharedPreferences = requireContext().getSharedPreferences("user_session", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", "") ?: ""
    }
}
