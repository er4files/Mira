package com.mira.mira.view.resultDetail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mira.mira.R
import com.mira.mira.databinding.ActivityFormReservationBinding
import com.mira.mira.databinding.ActivityResultDetailBinding

class ResultDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}