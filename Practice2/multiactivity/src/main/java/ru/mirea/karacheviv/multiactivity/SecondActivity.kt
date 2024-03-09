package ru.mirea.karacheviv.multiactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    private var TAG = SecondActivity::class.java.simpleName

    private lateinit var text1 : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        text1 = findViewById(R.id.text1)
        val intent = intent
        text1.text = intent.getStringExtra("key")
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
}