package com.mira.mira.view.consultation

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mira.mira.R
import com.mira.mira.data.model.Doctor
import com.mira.mira.view.adapter.DoctorAdapter

class ConsultationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_consultation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backIcon: ImageView = findViewById(R.id.back_icon)
        backIcon.setOnClickListener {
            finish()
        }

        val doctorList = listOf(
            Doctor("Dr. Agus Santoso", "Radiologist", "Jl. Merdeka No. 10, Jakarta", 4.5f, R.drawable.dr_agus),
            Doctor("Dr. Siti Aminah", "Interventional Radiologist", "Jl. Pahlawan No. 20, Bandung", 3.0f, R.drawable.dr_siti),
            Doctor("Dr. Budi Prasetyo", "Medical Imaging Specialist", "Jl. Kemerdekaan No. 30, Surabaya", 4.7f, R.drawable.dr_budi)
        )

        val recyclerView: RecyclerView = findViewById(R.id.rc_listconsultation)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DoctorAdapter(doctorList)
    }
}
