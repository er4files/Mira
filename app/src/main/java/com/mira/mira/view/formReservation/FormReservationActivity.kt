package com.mira.mira.view.formReservation

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.mira.mira.databinding.ActivityFormReservationBinding
import java.text.SimpleDateFormat
import java.util.Calendar


class FormReservationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFormReservationBinding

    private var reservationTypes  : Array<String> = arrayOf("Tumor Otak", "Kanker")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val date = intent.getStringExtra(RESERVATION_DATE)

        binding.dateTv.text = date
        binding.timeTv.text = intent.getStringExtra(RESERVATION_TIME)

        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, reservationTypes)
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.spinnerType.adapter = adapter

        binding.editTextDate.setOnClickListener{
            val today = Calendar.getInstance()
            val year = today.get(Calendar.YEAR)
            val month = today.get(Calendar.MONTH)
            val day = today.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ datePicker, selYear, selMonth, selDay ->
                    val calendar = Calendar.getInstance()
                    calendar.set(selYear, selMonth, selDay)

                    var dateFormat = SimpleDateFormat("dd-MM-yyyy")
                    var str = dateFormat.format(calendar.time)
                    this.binding.editTextDate.setText(str)
                }, year, month, day)
            datePicker.show()
        }
    }

    companion object{
        const val RESERVATION_DATE = "reservation_date"
        const val RESERVATION_TIME = "reservation_time"
    }

}