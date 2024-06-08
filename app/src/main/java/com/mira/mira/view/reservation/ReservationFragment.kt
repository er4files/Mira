package com.mira.mira.view.reservation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import com.mira.mira.R
import com.mira.mira.databinding.FragmentReservationBinding
import com.mira.mira.view.CustomTimePickerDialog
import com.mira.mira.view.formReservation.FormReservationActivity
import com.mira.mira.view.history.HistoryActivity
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Locale

class ReservationFragment : Fragment() {

    private var _binding: FragmentReservationBinding? = null
    private lateinit var date : String

    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
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

        val context = requireContext()

        binding.dateTextInputEdit.setOnClickListener{
            val today = Calendar.getInstance()
            val year = today.get(Calendar.YEAR)
            val month = today.get(Calendar.MONTH)
            val day = today.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener{ datePicker, selYear, selMonth, selDay ->
                    val calendar = Calendar.getInstance()
                    calendar.set(selYear, selMonth, selDay)
                    var dateFormat = SimpleDateFormat("yyyy-MM-dd")
                    date = dateFormat.format(calendar.time)

                    dateFormat = SimpleDateFormat("EEEE, yyyy-MM-dd", Locale.getDefault())
                    date = dateFormat.format(calendar.time)
                    this.binding.dateTextInputEdit.setText(date)
                }, year, month, day)
            datePicker.show()
        }

        binding.timeTextInputEdit.setOnClickListener {
            if(binding.dateTextInputEdit.text.toString().isNotEmpty()){
                showTimePickerDialog()
            }else{
                showToast(context, getString(R.string.fragment_reservation_select_date_first))
            }
        }


        binding.reservationButton.setOnClickListener {

            if(binding.dateTextInputEdit.text.toString().isNotEmpty() && binding.timeTextInputEdit.text.toString().isNotEmpty()){
                val intent = Intent(activity, FormReservationActivity::class.java)
                intent.putExtra("reservation_date", date)
                intent.putExtra("reservation_time", binding.timeTextInputEdit.text.toString())
                startActivity(intent)
            }else{
                showToast(context, "Please select date and time for the reservation!")
            }
        }

        return view
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = context?.let {
            CustomTimePickerDialog(
                it,
                { _, selectedHour, selectedMinute ->
                    val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    binding.timeTextInputEdit.setText(selectedTime)
                },
                hour,
                if (minute < 30) 0 else 30,
                true
            )
        }
        if (timePickerDialog != null) {
            timePickerDialog.show(parentFragmentManager, "timePicker")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(context : Context,message : String){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}