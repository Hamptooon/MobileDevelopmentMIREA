package ru.mirea.karacheviv.systemintentsapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickCall(view : View){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.setData(Uri.parse("tel:89175555555"))
        startActivity(intent)
    }
    fun onClickOpenBrowser(view: View){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("https://www.mirea.ru/"))
        startActivity(intent)

    }
    fun onClickOpenMap(view: View){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("geo:56.144923,37.482913"))
        startActivity(intent)

    }
}