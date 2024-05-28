package com.mira.mira.view.history
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mira.mira.R
import com.mira.mira.data.model.HistoryItem

import com.mira.mira.view.adapter.HistoryAdapter

class HistoryActivity : AppCompatActivity() {
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

        // Sample data
        val historyList: List<HistoryItem> = listOf(
            HistoryItem("Rahmad Era Sugiarto", "01 Jan 1990", "07:00-10:00", "Pemeriksaan Ekstrimitas Atas Pergelangan Tangan", false),
            HistoryItem("John Doe", "02 Feb 1992", "09:00-12:00", "Pemeriksaan Mata", true),
            // Add more history items here
        )

        val recyclerView: RecyclerView = findViewById(R.id.rc_listhistory)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HistoryAdapter(historyList)
    }
}
