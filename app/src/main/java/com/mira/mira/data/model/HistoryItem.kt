package com.mira.mira.data.model

data class HistoryItem(
    val nama_pasien: String,
    val tanggal_kunjungan: String,
    val jam_kunjungan: String,
    val hari_kunjungan: String,
    val jenis_periksa: String,
    val status: String
)
