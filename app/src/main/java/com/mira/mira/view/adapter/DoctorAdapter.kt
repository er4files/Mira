package com.mira.mira.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mira.mira.R
import com.mira.mira.data.model.Doctor

class DoctorAdapter(private var doctors: List<Doctor>) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.bind(doctor)
    }

    override fun getItemCount(): Int {
        return doctors.size
    }

    fun updateDoctors(newDoctors: List<Doctor>) {
        doctors = newDoctors
        notifyDataSetChanged()
    }

    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val doctorImage: ImageView = itemView.findViewById(R.id.iv_profiledoctor)
        private val doctorName: TextView = itemView.findViewById(R.id.doctor_name_item)
        private val doctorSpecialization: TextView = itemView.findViewById(R.id.doctor_specialist_item)
        private val doctorRating: RatingBar = itemView.findViewById(R.id.doctor_rating_item)
        private val chatButton: Button = itemView.findViewById(R.id.chat_button)
        private val callButton: Button = itemView.findViewById(R.id.call_button)

        fun bind(doctor: Doctor) {
            Glide.with(itemView.context).load(doctor.profile_picture).into(doctorImage)
            doctorName.text = doctor.nama
            doctorSpecialization.text = doctor.spesialis
            doctorRating.rating = doctor.rating

            chatButton.setOnClickListener {
                // Handle chat action
            }
            callButton.setOnClickListener {
                // Handle call action
            }
        }
    }
}
