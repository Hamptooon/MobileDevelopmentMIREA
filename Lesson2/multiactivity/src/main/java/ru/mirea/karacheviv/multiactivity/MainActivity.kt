package ru.mirea.karacheviv.multiactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private var TAG = MainActivity::class.java.simpleName

    private lateinit var btn1 : Button
    private lateinit var inpText1 : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1 = findViewById(R.id.button)
        inpText1 = findViewById(R.id.editTextText)

        Log.i(TAG, "OnCreate()")
    }
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "OnStart()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"onDestroy()")
    }

    fun OnClickNewActivity(view : View){
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("key", inpText1.text.toString())
        startActivity(intent)

    }
}