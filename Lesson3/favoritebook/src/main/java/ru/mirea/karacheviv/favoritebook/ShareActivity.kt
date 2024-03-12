package ru.mirea.karacheviv.favoritebook

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class ShareActivity : AppCompatActivity() {
    private lateinit var inpTextBook : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        inpTextBook = findViewById(R.id.editText1)
        val extras = intent.extras
        if(extras != null){
            val ageView = findViewById<TextView>(R.id.textView2)
            val favoriteBookName  = extras.getString(MainActivity.KEY)
            ageView.text = "Любимая книга разработчика – $favoriteBookName"

        }
    }

    fun onClickSendDataBtn(view: View){
        val data = Intent()
        val inpText = "Название Вашей любимой книги: " +  inpTextBook.text.toString()
        data.putExtra(MainActivity.USER_MESSAGE, inpText)
        setResult(Activity.RESULT_OK, data)
        finish()
    }
}