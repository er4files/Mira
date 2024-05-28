package com.mira.mira.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mira.mira.R
import com.mira.mira.databinding.ActivityMainBinding
import com.mira.mira.view.welcome.WelcomeActivity
import com.mira.mira.view.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))
            .get(MainViewModel::class.java)

        // Observasi session di MainActivity
        viewModel.getSession().observe(this, Observer { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        })

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_reservation,
                R.id.navigation_result,
                R.id.navigation_profile
            )
        )
        val navView = binding.navView
        navView.setupWithNavController(navController)
    }

    // Handle logout di MainActivity
    fun logout() {
        viewModel.logout()
    }
}
