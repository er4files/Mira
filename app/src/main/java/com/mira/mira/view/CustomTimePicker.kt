package com.mira.mira.view

import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.mira.mira.R
import java.util.Calendar

class CustomTimePickerDialog(
    context: Context,
    private val timeSetListener: TimePickerDialog.OnTimeSetListener,
    hourOfDay: Int,
    minute: Int,
    is24HourView: Boolean
) : DialogFragment() {

    private var selectedHour: Int = hourOfDay
    private var selectedMinute: Int = minute
    private val is24HourView = is24HourView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.custom_time_picker, null)
        val timePicker: TimePicker = view.findViewById(R.id.customTimePicker)

        timePicker.setIs24HourView(is24HourView)

        // Initialize with current values
        timePicker.hour = selectedHour
        timePicker.minute = if (selectedMinute < 30) 0 else 30

        // Find the minute picker and set it to show 30-minute intervals
        try {
            val minuteSpinner = timePicker.findViewById<NumberPicker>(
                resources.getIdentifier("minute", "id", "android")
            )
            minuteSpinner.minValue = 0
            minuteSpinner.maxValue = 1
            minuteSpinner.displayedValues = arrayOf("00", "30")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Handle time changes
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            selectedHour = hourOfDay
            selectedMinute = if (minute < 30) 0 else 30
        }

        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                timeSetListener.onTimeSet(timePicker, selectedHour, selectedMinute)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }
}