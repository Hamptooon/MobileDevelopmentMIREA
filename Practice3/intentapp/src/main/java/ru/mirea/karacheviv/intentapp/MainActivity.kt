package ru.mirea.karacheviv.intentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun goToSecondActivity(view: View?) {
        val dateInMillis = System.currentTimeMillis()
        val format = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(format)
        val myNum = 10
        val dateString: String = sdf.format(Date(dateInMillis))
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("current_time", dateString)
        intent.putExtra("my_num", myNum)
        startActivity(intent)
    }
}