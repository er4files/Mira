package com.mira.mira.view.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mira.mira.R
import com.mira.mira.databinding.ActivityMainBinding
import com.mira.mira.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)

        // Check user login status
        if (!isUserLoggedIn()) {
            navigateToWelcome()
            return
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    fun logout() {
        clearSession()
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isUserLoggedIn(): Boolean {
        val tempId = sharedPreferences.getString("localId", "")
        return tempId?.isNotBlank() ?: false
    }

    private fun navigateToWelcome() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }
}
