package com.mira.mira.view.home

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mira.mira.R
import com.mira.mira.databinding.BottomSheetFormBinding
import com.mira.mira.view.CustomTimePickerDialog
import com.mira.mira.view.formReservation.FormReservationActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var date: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomSheetFormBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        binding.selectDateButton.setOnClickListener {
            val today = Calendar.getInstance()
            val year = today.get(Calendar.YEAR)
            val month = today.get(Calendar.MONTH)
            val day = today.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(context,
                { _, selYear, selMonth, selDay ->
                    val calendar = Calendar.getInstance().apply{
                        set(selYear, selMonth, selDay)
                    }

                    if(calendar.after(today)){
                        val dateFormat = SimpleDateFormat("EEEE, yyyy-MM-dd", Locale.getDefault())
                        date = dateFormat.format(calendar.time)
                        this.binding.dateTextInputEdit.setText(date)
                    }

                }, year, month, day
            )
            datePicker.datePicker.minDate = today.timeInMillis
            datePicker.show()
        }

        binding.selectTimeButton.setOnClickListener {
            if (binding.dateTextInputEdit.text.toString().isNotEmpty()) {
                showTimePickerDialog()
            } else {
                showToast(context, getString(R.string.fragment_reservation_select_date_first))
            }
        }


        binding.reservationButton.setOnClickListener {

            if (binding.dateTextInputEdit.text.toString()
                    .isNotEmpty() && binding.timeTextInputEdit.text.toString().isNotEmpty()
            ) {
                val intent = Intent(activity, FormReservationActivity::class.java)
                intent.putExtra("reservation_date", date)
                intent.putExtra("reservation_time", binding.timeTextInputEdit.text.toString())
                startActivity(intent)
            } else {
                showToast(context, "Please select date and time for the reservation!")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "BottomSheetFragment"
    }
}