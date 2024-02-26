package ru.mirea.karacheviv.control_lesson1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myTextView =  findViewById<TextView>(R.id.textViewStudent);
        val button = findViewById<Button>(R.id.button);
        myTextView.text = "New text in MIREA!";
        button.text = "MireaButton";
    }

}