package ru.mirea.karacheviv.control_lesson2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onClickTimeDialog(view : View){
        val timeDialogFragment = MyTimeDialogFragment()
        timeDialogFragment.show(supportFragmentManager, "")
    }

    fun onClickDateDialog(view : View){
        val dateDialogFragment = MyDateDialogFragment()
        dateDialogFragment.show(supportFragmentManager, "Выберите дату")
    }

    fun showTimeInfo(hours : Int, minutes : Int){
        val msg = "Выбранное время: ${getNumWithTrailingZeroes(hours)}:${getNumWithTrailingZeroes(minutes)}"
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()
    }
    private fun getNumWithTrailingZeroes(num : Int) : String{
        return if(num > 10) num.toString() else "0$num"
    }

    fun showDateInfo(year: Int, month: Int, dayOfMonth: Int) {
        val msg = "Выбранная дата: ${getNumWithTrailingZeroes(dayOfMonth)}.${getNumWithTrailingZeroes(month + 1)}.$year";
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show()

    }
    fun onClickProgressDialog(view : View){
        val progressDialogFragment = MyProgressDialogFragment()
        progressDialogFragment.show(supportFragmentManager, "")
        Snackbar.make(findViewById(android.R.id.content), "Progress bar started", Snackbar.LENGTH_LONG).show()
    }
}