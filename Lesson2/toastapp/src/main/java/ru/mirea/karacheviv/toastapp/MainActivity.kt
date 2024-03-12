package ru.mirea.karacheviv.toastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var btn1 : Button
    private lateinit var inpText1 : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1 = findViewById(R.id.button1)
        inpText1 = findViewById(R.id.editText1)
        btn1.setOnClickListener(this::onBtnCountSymClick)
    }

    fun onBtnCountSymClick(view: View){
        Toast.makeText(this, "«СТУДЕНТ № 10 ГРУППА БСБО-10-21 Количество символов - ${inpText1.text.length}", Toast.LENGTH_SHORT).show()
    }


}