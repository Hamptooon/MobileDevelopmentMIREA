package ru.mirea.karacheviv.intentapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val currentTime = intent.getStringExtra("current_time")
        val myNum = intent.getIntExtra("my_num", 0)
        val text1 = findViewById<TextView>(R.id.textView)
        val msg = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ ЧИСЛО ${myNum * myNum}, а текущее время $currentTime"
        text1.text = msg

    }
}