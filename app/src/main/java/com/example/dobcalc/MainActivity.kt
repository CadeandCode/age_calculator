package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.CadeandCode.dobcalc.R
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null
    private var tvAgeInHours: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        tvAgeInHours = findViewById(R.id.tvAgeInHours)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

    }

    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayofMonth ->

                Toast.makeText(
                    this,
                    "Year was $selectedYear, month was ${selectedMonth + 1}, " +
                            "day of month was $selectedDayofMonth", Toast.LENGTH_LONG
                ).show()

                val selectedDate = "$selectedDayofMonth/${selectedMonth + 1}/$selectedYear"

                tvSelectedDate?.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)
                theDate?.let {

                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {

                        val selectedDateInMinutes = theDate.time / ONE_MIN_IN_MILLIS

                        val currentDateInMinutes = currentDate.time / ONE_MIN_IN_MILLIS

                        val selectedDateInHours = theDate.time / ONE_HOUR_IN_MILLIS

                        val currentDateInHours = currentDate.time / ONE_HOUR_IN_MILLIS

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        val differenceInHours = currentDateInHours - selectedDateInHours

                        tvAgeInMinutes?.text = differenceInMinutes.toString()
                        tvAgeInHours?.text = differenceInHours.toString()
                    }
                }
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - DATE_LIMIT_IN_MILLIS
        datePickerDialog.show()
    }

    companion object {
        private const val ONE_MIN_IN_MILLIS = 60000
        private const val ONE_HOUR_IN_MILLIS = 3600000
        private const val DATE_LIMIT_IN_MILLIS = 86400000
    }

}