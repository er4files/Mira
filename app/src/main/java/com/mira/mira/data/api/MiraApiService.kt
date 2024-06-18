package com.mira.mira.data.api

import com.mira.mira.data.api.response.ReservationAddResponse
import com.mira.mira.data.model.Doctor
import com.mira.mira.data.model.HistoryItem
import com.mira.mira.data.model.Notification
import com.mira.mira.data.model.ResultItem
import com.mira.mira.data.model.UserData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface MiraApiService {
    @GET("patients")
    fun getHistoryPatients(@Header("Authorization") token: String): Call<List<HistoryItem>>

    @GET("patients")
    fun getResultPatients(@Header("Authorization") token: String): Call<List<ResultItem>>

    @GET("user")
    fun getUserData(@Header("Authorization") token: String): Call<UserData>
    @Multipart
    @PATCH("user")
    fun updateUserData(
        @Header("Authorization") token: String,
        @Part("username") username: RequestBody,
        @Part("phoneNumber") phone: RequestBody,
        @Part profile_picture: MultipartBody.Part?
    ): Call<UserData>

    @GET("/patient/{id}/notification")
    fun getNotifications(
        @Path("id") patientId: String,
        @Header("Authorization") token: String
    ): Call<List<Notification>>

    @GET("doctors")
    fun getDoctors(@Header("Authorization") token: String): Call<List<Doctor>>

    @FormUrlEncoded
    @POST("/add")
    fun addReservation(
        @Header("Authorization") token: String,
        @Field("nama_pasien") nama_pasien: String,
        @Field("alamat") alamat : String,
        @Field("tanggal_lahir") tanggal_lahir : String,
        @Field("gender") gender : String,
        @Field("no_hp") no_hp : String,
        @Field("email") email : String,
        @Field("tanggal_kunjungan") tanggal_kunjungan : String,
        @Field("jam_kunjungan") jam_kunjungan : String,
        @Field("jenis_periksa") jenis_periksa : String
    ) : Call<ReservationAddResponse>

    @GET("/predict/{id}")
    fun getResult(@Header("Authorization") token: String,
                  @Path("id") resultId : String
                  ): Call<List<Doctor>>
}
