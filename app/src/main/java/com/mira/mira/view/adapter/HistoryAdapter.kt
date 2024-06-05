package com.mira.mira.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mira.mira.R
import com.mira.mira.data.model.HistoryItem

class HistoryAdapter(private val historyList: List<HistoryItem>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.nameTextView.text = historyItem.nama_pasien
        holder.dateTextView.text = historyItem.tanggal_kunjungan
        holder.timeTextView.text = historyItem.jam_kunjungan
        holder.dayTextView.text = historyItem.hari_kunjungan
        holder.examTextView.text = historyItem.exam

        when (historyItem.status) {
            "Menunggu Konfirmasi" -> {
                holder.statusTextView.setBackgroundResource(R.drawable.background_status_yellow)
                holder.statusTextView.text = "Menunggu Konfirmasi"
            }
            "Konfirmasi" -> {
                holder.statusTextView.setBackgroundResource(R.drawable.background_status_green)
                holder.statusTextView.text = "Konfirmasi"
            }
            "Selesai" -> {
                holder.statusTextView.setBackgroundResource(R.drawable.background_status_gray)
                holder.statusTextView.text = "Selesai"
            }
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.tv_nama_pasien)
        val dateTextView: TextView = itemView.findViewById(R.id.tv_tanggal_kunjungan)
        val timeTextView: TextView = itemView.findViewById(R.id.tv_waktu_kunjungan)
        val dayTextView: TextView = itemView.findViewById(R.id.tv_hari_kunjungan)
        val examTextView: TextView = itemView.findViewById(R.id.tv_jenis_periksa)
        val statusTextView: TextView = itemView.findViewById(R.id.tv_status_kunjungan)
    }
}
