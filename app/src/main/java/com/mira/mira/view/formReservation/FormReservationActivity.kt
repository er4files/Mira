package com.mira.mira.view.formReservation

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mira.mira.R
import com.mira.mira.databinding.ActivityFormReservationBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class FormReservationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFormReservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dateTv.text = intent.getStringExtra(RESERVATION_DATE)

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
    }

}