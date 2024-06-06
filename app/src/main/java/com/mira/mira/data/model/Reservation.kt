package com.mira.mira.data.model

import java.util.Date

data class Reservation(
    val nama_pasien : String,
    val alamat : String,
    val tanggal_lahir : String,
    val gender : String,
    val no_hp : String,
    val email : String,
    val tanggal_kunjungan : String,
    val jam_kunjungan : String,
    val jenis_periksa : String,
) {
}