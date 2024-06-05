package com.mira.mira.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mira.mira.R
import com.mira.mira.data.model.ResultItem

class ResultsAdapter(private var items: List<ResultItem>) :
    RecyclerView.Adapter<ResultsAdapter.ResultViewHolder>() {

    class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tv_nama_pasien)
        val dateTextView: TextView = view.findViewById(R.id.tv_date_reservation)
        val examinationTypeTextView: TextView = view.findViewById(R.id.tv_jenis_periksa)
        val downloadTextView: TextView = view.findViewById(R.id.tv_download)
        val pdfImageView: ImageView = view.findViewById(R.id.iv_pdf)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_results, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.nama_pasien
        holder.dateTextView.text = item.tanggal_kunjungan
        holder.examinationTypeTextView.text = item.jenis_periksa

        if (item.status_hasil) {
            holder.downloadTextView.text = "Download"
            holder.pdfImageView.setImageResource(R.drawable.pdf_status_red)
        } else {
            holder.downloadTextView.text = "Proses"
            holder.pdfImageView.setImageResource(R.drawable.pdf_status_gray)
        }
    }


    fun updateData(newItems: List<ResultItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}