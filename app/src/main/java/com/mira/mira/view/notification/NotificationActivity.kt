package com.mira.mira.view.notification

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mira.mira.R
import com.mira.mira.view.adapter.NotificationAdapter



class NotificationActivity : AppCompatActivity() {

    private val viewModel: NotificationViewModel by viewModels()
    private lateinit var adapter: NotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notification)

        adapter = NotificationAdapter(emptyList()) { notification ->
            notification.isRead = true
            viewModel.updateNotification(notification)
        }

        val recyclerView: RecyclerView = findViewById(R.id.rc_listnotification)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val backIcon: ImageView = findViewById(R.id.back_icon)
        backIcon.setOnClickListener {
            finish()
        }

        val markAllReadButton: ImageView = findViewById(R.id.mark_all_read)
        markAllReadButton.setOnClickListener {
            viewModel.markAllAsRead()
        }

        viewModel.notifications.observe(this, Observer { notifications ->
            adapter.updateNotifications(notifications)
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}





