package com.mira.mira.view.chat

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.mira.mira.R
import com.mira.mira.data.api.MiraApiService
import com.mira.mira.data.model.Message
import com.mira.mira.data.model.UserData
import com.mira.mira.databinding.ActivityChatBinding
import com.mira.mira.view.adapter.MessageAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: MessageAdapter
    private lateinit var currentUser: String
    private lateinit var currentUserId: String
    private lateinit var doctorId: String
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve doctor ID from intent
        doctorId = intent.getStringExtra("doctor_id") ?: ""

        // Get current user ID from Firebase Authentication
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        currentUserId = firebaseUser?.uid ?: ""

        // Get current user's name from Firestore or API
        firestore = FirebaseFirestore.getInstance()
        fetchCurrentUser()

        // Set up RecyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = layoutManager

        // Set up FirebaseRecyclerAdapter
        val query = FirebaseDatabase.getInstance("https://mira-team-default-rtdb.asia-southeast1.firebasedatabase.app").reference
            .child("chats")
            .child("$currentUserId-$doctorId")

        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .build()

        adapter = MessageAdapter(options)
        binding.messageRecyclerView.adapter = adapter

        // Set up send button click listener
        binding.sendButton.setOnClickListener {
            sendMessage()
        }

        // Enable send button when there is text in the EditText
        binding.messageEditText.addTextChangedListener {
            binding.sendButton.isEnabled = it.toString().trim().isNotEmpty()
        }

        val backIcon: ImageView = findViewById(R.id.back_icon)
        backIcon.setOnClickListener {
            finish()
        }
    }

    private fun fetchCurrentUser() {
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

        val apiService = retrofit.create(MiraApiService::class.java)
        val token = retrieveTokenFromSharedPreferences()

        apiService.getUserData("Bearer $token").enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    val userData = response.body()
                    userData?.let {
                        currentUser = it.username
                    }
                } else {
                    currentUser = "Anonymous"
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                currentUser = "Anonymous"
            }
        })
    }

    private fun sendMessage() {
        val messageText = binding.messageEditText.text.toString().trim()

        if (messageText.isNotEmpty()) {
            val message = Message(currentUserId, currentUser, messageText, System.currentTimeMillis())

            val messagesRef = FirebaseDatabase.getInstance("https://mira-team-default-rtdb.asia-southeast1.firebasedatabase.app").reference
                .child("chats")
                .child("$currentUserId-$doctorId")

            messagesRef.push().setValue(message)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Message sent successfully
                        Log.d(TAG, "Message sent successfully")
                    } else {
                        // Failed to send message
                        Log.e(TAG, "Failed to send message", task.exception)
                    }
                }

            binding.messageEditText.text.clear()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun retrieveTokenFromSharedPreferences(): String {
        // Retrieve token from SharedPreferences or other source
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", "") ?: ""
    }

    companion object {
        private const val TAG = "ChatActivity"
    }
}
