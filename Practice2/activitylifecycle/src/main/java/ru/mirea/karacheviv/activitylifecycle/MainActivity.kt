package ru.mirea.karacheviv.activitylifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private var TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

}