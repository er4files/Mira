package com.mira.mira.view.consultation

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mira.mira.R
import com.mira.mira.data.api.MiraApiService
import com.mira.mira.data.model.Doctor
import com.mira.mira.view.adapter.DoctorAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConsultationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var doctorAdapter: DoctorAdapter
    private lateinit var miraApiService: MiraApiService

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

        recyclerView = findViewById(R.id.rc_listconsultation)
        recyclerView.layoutManager = LinearLayoutManager(this)
        doctorAdapter = DoctorAdapter(emptyList())
        recyclerView.adapter = doctorAdapter

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

        // Fetch doctors from API
        miraApiService.getDoctors("Bearer $token").enqueue(object : Callback<List<Doctor>> {
            override fun onResponse(call: Call<List<Doctor>>, response: Response<List<Doctor>>) {
                if (response.isSuccessful) {
                    response.body()?.let { doctors ->
                        val doctorsWithRatings = doctors.map { doctor ->
                            Doctor(
                                no_hp = doctor.no_hp,
                                spesialis = doctor.spesialis,
                                profile_picture = doctor.profile_picture,
                                id = doctor.id,
                                email = doctor.email,
                                nama = doctor.nama,
                                rating = 4.0f // default rating
                            )
                        }
                        doctorAdapter.updateDoctors(doctorsWithRatings)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ConsultationActivity", "Error fetching doctors: $errorBody")
                }
            }

            override fun onFailure(call: Call<List<Doctor>>, t: Throwable) {
                Log.e("ConsultationActivity", "Error fetching doctors: ${t.message}")
            }
        })
    }

    private fun retrieveTokenFromSharedPreferences(): String {
        // Retrieve token from SharedPreferences or other source
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", "") ?: ""
    }
}
