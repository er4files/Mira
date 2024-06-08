package com.mira.mira.view.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mira.mira.R
import com.mira.mira.data.api.AuthService
import com.mira.mira.data.api.LoginRequest
import com.mira.mira.data.api.LoginResponse
import com.mira.mira.data.repository.AuthRepository
import com.mira.mira.databinding.ActivityLoginBinding
import com.mira.mira.view.main.MainActivity
import com.mira.mira.view.signup.SignupActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var authService: AuthService
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)

        // Check if user is already logged in
        if (isUserLoggedIn()) {
            navigateToMain()
            return
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("https://identitytoolkit.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)
        authRepository = AuthRepository(authService)

        setupAction()

        binding.signUpTextView.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        val backIcon: ImageView = findViewById(R.id.back_icon)
        backIcon.setOnClickListener {
            finish()
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val token = sharedPreferences.getString("auth_token", "")
        return token?.isNotBlank() ?: false
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (isValidEmail(email) && isValidPassword(password)) {
                binding.progressBar.visibility = View.VISIBLE
                val loginRequest = LoginRequest(email, password)
                authRepository.signInWithEmail(loginRequest, object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        binding.progressBar.visibility = View.GONE
                        if (response.isSuccessful) {
                            val token = response.body()?.idToken ?: ""
                            sharedPreferences.edit().apply {
                                putString("auth_token", token)
                                putString("localId", response.body()?.localId ?: "")
                                apply()
                            }
                            navigateToMain()
                        } else {
                            val errorMessage = when (response.code()) {
                                400 -> "Invalid email or password."
                                403 -> "Access forbidden. Please try again."
                                else -> "Authentication failed. Please try again later."
                            }
                            Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Authentication failed: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this@LoginActivity, "Please enter valid email and password.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
