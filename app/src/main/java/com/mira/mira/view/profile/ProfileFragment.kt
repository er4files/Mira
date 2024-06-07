package com.mira.mira.view.profile

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.mira.mira.R
import com.mira.mira.data.api.MiraApiService
import com.mira.mira.data.model.UserData
import com.mira.mira.databinding.FragmentProfileBinding
import com.mira.mira.view.history.HistoryActivity
import com.mira.mira.view.main.MainActivity
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var firestore: FirebaseFirestore
    private lateinit var miraApiService: MiraApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        firestore = FirebaseFirestore.getInstance()

        val token = retrieveTokenFromSharedPreferences()

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

        miraApiService.getUserData("Bearer $token").enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful) {
                    val userData = response.body()
                    userData?.let {
                        binding.tvUser.text = it.username
                        binding.tvPhone.text = it.phoneNumber
                        Glide.with(requireContext())
                            .load(it.profile_picture)
                            .placeholder(R.drawable.profil_picture)
                            .error(R.drawable.ic_error)
                            .into(binding.ivProfileUser)
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch user data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed to fetch user data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // Corrected this part
        binding.btnNavigateHistory.setOnClickListener {
            val intent = Intent(activity, HistoryActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEditProfile.setOnClickListener {
            EditProfileDialogFragment.newInstance().show(parentFragmentManager, "EditProfileDialogFragment")
        }

        binding.logoutButton.setOnClickListener {
            (requireActivity() as? MainActivity)?.logout()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retrieveTokenFromSharedPreferences(): String {
        val sharedPreferences = requireContext().getSharedPreferences("user_session", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", "") ?: ""
    }

    fun onProfileUpdated() {
        Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
    }
}
