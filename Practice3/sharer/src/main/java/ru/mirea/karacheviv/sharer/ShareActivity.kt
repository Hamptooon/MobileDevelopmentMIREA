package ru.mirea.karacheviv.sharer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ShareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
    }
    fun onSendIntent(view : View){
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("*/*")
        intent.putExtra(Intent.EXTRA_TEXT, "Mirea")
        startActivity(Intent.createChooser(intent, "Выбор за вами!"))
    }
}