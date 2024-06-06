package com.mira.mira.view.formReservation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mira.mira.data.api.MiraApiConfig
import com.mira.mira.data.api.response.ReservationAddResponse
import com.mira.mira.data.model.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormReservationViewModel(private val token: String) : ViewModel() {

    private val _message = MutableLiveData<ReservationAddResponse?>()
    val message: MutableLiveData<ReservationAddResponse?> = _message

    internal fun addReservation(reservationData: Reservation) {
        val client = MiraApiConfig.getApiService().addReservation(
            token,
            reservationData.nama_pasien,
            reservationData.alamat,
            reservationData.tanggal_lahir,
            reservationData.gender,
            reservationData.no_hp,
            reservationData.email,
            reservationData.tanggal_kunjungan,
            reservationData.jam_kunjungan,
            reservationData.jenis_periksa
        )
        client.enqueue(object : Callback<ReservationAddResponse> {
            override fun onResponse(
                call: Call<ReservationAddResponse>,
                response: Response<ReservationAddResponse>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _message.value = responseBody
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<ReservationAddResponse>, t: Throwable) {

            }
        })
    }
}