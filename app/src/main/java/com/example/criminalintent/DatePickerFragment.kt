package com.example.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Build
import java.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.util.Date
import java.util.GregorianCalendar

private const val ARG_DATE = "date"
private const val DATE_REQUEST = "date_requested"
private const val  DATE_SELECT = "date_selected"
class DatePickerFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialYear = calendar.get(Calendar.YEAR)
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(ARG_DATE, Date::class.java) as Date
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(ARG_DATE) as Date
        }
        val dateListener = DatePickerDialog.OnDateSetListener{
                _ : DatePicker, year: Int, month: Int, day: Int ->
            val resultDate = GregorianCalendar(year,month,day).time
            setFragmentResult(DATE_REQUEST, bundleOf(DATE_SELECT to resultDate))
        }
        calendar.time = date

        return DatePickerDialog(
            requireContext(),
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }
    companion object {
        fun newInstance(date: Date): DatePickerFragment{
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }

            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }
}