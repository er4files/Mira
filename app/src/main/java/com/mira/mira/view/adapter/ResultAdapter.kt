package com.mira.mira.view.adapter

import android.content.Intent
import android.net.Uri
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

        fun bind(item: ResultItem) {
            nameTextView.text = item.nama_pasien
            dateTextView.text = item.tanggal_kunjungan
            examinationTypeTextView.text = item.jenis_periksa

            if (item.status_hasil) {
                downloadTextView.text = "Download"
                pdfImageView.setImageResource(R.drawable.pdf_status_red)
                pdfImageView.setOnClickListener {
                    val context = pdfImageView.context
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.result))
                    context.startActivity(intent)
                }
            } else {
                downloadTextView.text = "Proses"
                pdfImageView.setImageResource(R.drawable.pdf_status_gray)
                pdfImageView.setOnClickListener(null) // remove click listener if not downloadable
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_results, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    fun updateData(newItems: List<ResultItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
