package com.mira.mira.view.adapter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        // Set data to views in item_history layout
        holder.nameTextView.text = historyItem.name
        holder.dateTextView.text = historyItem.date
        holder.timeTextView.text = historyItem.time
        holder.examTextView.text = historyItem.exam

        if (historyItem.isCompleted) {
            holder.statusTextView.setBackgroundResource(R.drawable.background_status_gray)
            holder.statusTextView.text = "Selesai"
        } else {
            holder.statusTextView.setBackgroundResource(R.drawable.background_status_green)
            holder.statusTextView.text = "Akan datang"
        }
    }


    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.jtv_namepasien)
        val dateTextView: TextView = itemView.findViewById(R.id.tv_datepasien)
        val timeTextView: TextView = itemView.findViewById(R.id.tv_timepasien)
        val examTextView: TextView = itemView.findViewById(R.id.tv_jenisperiksa)
        val statusTextView: TextView = itemView.findViewById(R.id.tv_statushistory)
    }
}
