package ru.mirea.karacheviv.control_lesson2

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class MyDateDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, dayOfMonth)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        (requireActivity() as MainActivity?)?.showDateInfo(year, month, dayOfMonth)    }
}