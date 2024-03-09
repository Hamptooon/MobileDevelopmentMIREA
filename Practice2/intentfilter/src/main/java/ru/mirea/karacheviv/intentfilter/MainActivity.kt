package ru.mirea.karacheviv.intentfilter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var btn2 : Button
    private lateinit var btn3 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn2 = findViewById(R.id.button2)
        btn3 = findViewById(R.id.button3)
        val oclBtn2 = View.OnClickListener {
            val address = Uri.parse("https://www.mirea.ru/");
            val intentOpenLink = Intent(Intent.ACTION_VIEW, address)
            startActivity(intentOpenLink)
        }
        btn2.setOnClickListener(oclBtn2)
        btn3.setOnClickListener(this::SendStudentInfo)
    }

    fun SendStudentInfo(view: View){
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA")
        shareIntent.putExtra(Intent.EXTRA_TEXT, "КАРАЧЕВ ИГОРЬ ВЛАДИСЛАВОВИЧ")
        startActivity(Intent.createChooser(shareIntent, "МОИ ФИО"))
    }
}