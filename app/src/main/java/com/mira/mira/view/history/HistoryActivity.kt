
package com.mira.mira.view.history

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(HistoryViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.rc_listhistory)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.historyList.observe(this) { historyList ->
            recyclerView.adapter = HistoryAdapter(historyList)
        }
    }
}
