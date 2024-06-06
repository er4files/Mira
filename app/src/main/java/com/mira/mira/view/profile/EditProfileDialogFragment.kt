package com.mira.mira.view.profile

import android.app.Activity.RESULT_OK
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.mira.mira.R
import com.mira.mira.data.api.MiraApiService
import com.mira.mira.data.model.UserData
import com.mira.mira.databinding.FragmentEditProfileBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class EditProfileDialogFragment : DialogFragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var miraApiService: MiraApiService
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

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

        binding.btnSelectImage.setOnClickListener {
            selectImage()
        }

        binding.btnSave.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val phone = binding.etPhone.text.toString()
            updateProfile(token, username, phone, selectedImageUri)
        }

        return binding.root
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            val bitmap = getBitmapFromUri(selectedImageUri!!)
            binding.ivSelectedImage.setImageBitmap(bitmap)
            binding.ivSelectedImage.visibility = View.VISIBLE
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        var inputStream: InputStream? = null
        try {
            inputStream = requireContext().contentResolver.openInputStream(uri)
            return BitmapFactory.decodeStream(inputStream)
        } finally {
            inputStream?.close()
        }
    }

    private fun updateProfile(token: String, username: String, phone: String, imageUri: Uri?) {
        val usernameBody = RequestBody.create("text/plain".toMediaTypeOrNull(), username)
        val phoneBody = RequestBody.create("text/plain".toMediaTypeOrNull(), phone)

        val imageBody: MultipartBody.Part? = imageUri?.let {
            val file = File(requireContext().cacheDir, "profile_picture.jpg")
            val inputStream = requireContext().contentResolver.openInputStream(it)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            outputStream.close()
            inputStream?.close()

            val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
            MultipartBody.Part.createFormData("profile_picture", file.name, requestBody)
        }

        miraApiService.updateUserData("Bearer $token", usernameBody, phoneBody, imageBody)
            .enqueue(object : Callback<UserData> {
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful) {
                        Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        (parentFragment as? ProfileFragment)?.onProfileUpdated()
                        dismiss() // Tutup dialog
                    } else {
                        Toast.makeText(requireContext(), "Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Toast.makeText(requireContext(), "Failed to update profile: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retrieveTokenFromSharedPreferences(): String {
        val sharedPreferences = requireContext().getSharedPreferences("user_session", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", "") ?: ""
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1000

        fun newInstance(): EditProfileDialogFragment {
            return EditProfileDialogFragment()
        }
    }
}
