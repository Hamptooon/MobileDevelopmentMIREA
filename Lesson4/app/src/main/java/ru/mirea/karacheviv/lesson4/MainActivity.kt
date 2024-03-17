package ru.mirea.karacheviv.lesson4

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.karacheviv.lesson4.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextMirea.setText("Мой номер по списку №10")
        binding.buttonMirea.setOnClickListener {
            binding.textViewMirea.text = "Я студент МИРЭА"
        }


    }
}