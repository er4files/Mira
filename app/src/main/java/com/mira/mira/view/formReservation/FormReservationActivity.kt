package com.mira.mira.view.formReservation

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mira.mira.R
import com.mira.mira.data.model.Reservation
import com.mira.mira.databinding.ActivityFormReservationBinding
import com.mira.mira.view.CustomTimePickerDialog
import com.mira.mira.view.history.HistoryActivity
import com.mira.mira.view.history.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.Calendar


class FormReservationActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFormReservationBinding

    private var reservationTypes  : Array<String> = arrayOf("Tumor Otak", "Kanker")
    private lateinit var date : String
    private lateinit var time : String

    private lateinit var viewModel: FormReservationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("auth_token", "") ?: ""

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this, FormReservationViewModelFactory(token)).get(FormReservationViewModel::class.java)

        var displayDate = intent.getStringExtra(RESERVATION_DATE)
        binding.dateTv.text = displayDate

        time = intent.getStringExtra(RESERVATION_TIME).toString()
        binding.timeTv.text = time

        val dateSplit = displayDate.toString().split(',')
        date = dateSplit[1]
        date = date.replace(" ", "")
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

                    var dateFormat = SimpleDateFormat("yyyy-MM-dd")
                    var str = dateFormat.format(calendar.time)
                    this.binding.editTextDate.setText(str)
                }, year, month, day)
            datePicker.show()
        }

        binding.reservationButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val dateBirth = binding.editTextDate.text.toString()
            val gender = getRadioButton()
            val address = binding.addressEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val type = binding.spinnerType.selectedItem.toString()

            if(name.isNotEmpty() && dateBirth.isNotEmpty() && gender.isNotEmpty() && address.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && type.isNotEmpty()){
                val reservation = Reservation(name, address, dateBirth, gender, phone, email, date, time, type)
                viewModel.addReservation(reservation)
                finish()
            }
        }

            viewModel.message.observe(this@FormReservationActivity){
                if (it != null) {
                    showToast(it.message)
                }
            }

        val backIcon: ImageView = findViewById(R.id.back_icon)
        backIcon.setOnClickListener {
            finish()
        }

        viewModel.isLoading.observe(this@FormReservationActivity){
            showLoading(it)
        }
    }

    private fun getRadioButton() : String{
        for (i in 0 until binding.genderRadioGroup.childCount){
            val radioButton = binding.genderRadioGroup.getChildAt(i) as RadioButton
            if(radioButton.isChecked){
                return radioButton.text.toString()
            }
        }
        return ""
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showToast(message : String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        showLoading(false)
    }


    companion object{
        const val RESERVATION_DATE = "reservation_date"
        const val RESERVATION_TIME = "reservation_time"
    }

    class FormReservationViewModelFactory(private val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FormReservationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FormReservationViewModel(token) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}