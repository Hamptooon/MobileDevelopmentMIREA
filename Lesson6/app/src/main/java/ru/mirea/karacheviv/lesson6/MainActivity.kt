package ru.mirea.karacheviv.lesson6

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.karacheviv.lesson6.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var groupText : EditText;
    private lateinit var numberText : EditText;
    private lateinit var filmText : EditText;

    private lateinit var binding : ActivityMainBinding;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        groupText = binding.groupEditText;
        numberText = binding.numberEditText;
        filmText = binding.filmEditText;

        val sharedPref = getSharedPreferences("mirea_settings", MODE_PRIVATE)
        groupText.setText(sharedPref.getString("GROUP", ""))
        numberText.setText(sharedPref.getInt("NUMBER", 0).toString())
        filmText.setText(sharedPref.getString("FILM", ""))

        binding.saveBtn.setOnClickListener {
            val sharedPrefOnSave = getSharedPreferences("mirea_settings", MODE_PRIVATE)

            val editor = sharedPrefOnSave.edit()

            editor.putString("GROUP", groupText.text.toString())
            editor.putInt("NUMBER", numberText.text.toString().toInt())
            editor.putString("FILM", filmText.text.toString())
            editor.apply()
        }


    }
}