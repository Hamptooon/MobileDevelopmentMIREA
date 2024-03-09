package ru.mirea.karacheviv.buttonclicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var textViewStudent : TextView;
    private lateinit var btnWhoAmI : Button;
    private lateinit var btnItIsNotMe : Button;
    private lateinit var checkBoxItsMe : CheckBox;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        textViewStudent = findViewById(R.id.tvOut);
        btnWhoAmI = findViewById(R.id.btnWhoAmI);
        btnItIsNotMe = findViewById(R.id.btnItIsNotMe);
        checkBoxItsMe = findViewById(R.id.checkBox);
        val oclBtnWhoAmI = View.OnClickListener {
            checkBoxItsMe.isChecked = true;
            textViewStudent.text = "Мой номер по списку № 11";
        }
        btnWhoAmI.setOnClickListener(oclBtnWhoAmI);
    }
    fun OnMyButtonClick(view : View){
        Toast.makeText(this, "Извините за ошибку", Toast.LENGTH_SHORT).show();
        textViewStudent.text = "Это не мой номер";
        checkBoxItsMe.isChecked = false;

    }
}