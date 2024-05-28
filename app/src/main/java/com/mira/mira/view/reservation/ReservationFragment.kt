package com.mira.mira.view.reservation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mira.mira.R
import com.mira.mira.databinding.FragmentReservationBinding
import com.mira.mira.view.history.HistoryActivity
import com.mira.mira.view.notification.NotificationActivity

class ReservationFragment : Fragment() {

    private var _binding: FragmentReservationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val reservationViewModel =
            ViewModelProvider(this).get(ReservationViewModel::class.java)

        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        val view = binding.root  // Dapatkan referensi ke root view

        val historyIcon: RelativeLayout = view.findViewById(R.id.history_icon)
        historyIcon.setOnClickListener {
            val intent = Intent(activity, HistoryActivity::class.java)
            startActivity(intent)
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}