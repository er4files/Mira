package com.mira.mira.view.history

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mira.mira.R
import com.mira.mira.view.adapter.HistoryAdapter

class HistoryActivity : AppCompatActivity() {

    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backIcon: ImageView = findViewById(R.id.back_icon)
        backIcon.setOnClickListener {
            finish()
        }

        // Dapatkan token dari SharedPreferences atau sumber lainnya
        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", "") ?: ""

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this, HistoryViewModelFactory(token)).get(HistoryViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.rc_listhistory)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observasi perubahan data dari ViewModel
        viewModel.historyList.observe(this) { historyList ->
            recyclerView.adapter = HistoryAdapter(historyList)
        }
    }
    class HistoryViewModelFactory(private val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HistoryViewModel(token) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
