package ru.mirea.karacheviv.looper

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class MyLooper(private val mainThreadHandler: Handler) : Thread() {
    lateinit var mHandler: Handler

    override fun run() {
        Log.d("MyLooper", "run")
        Looper.prepare()
        mHandler = object : Handler(Looper.myLooper()!!){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val data = msg.data.getString("KEY")
                val age = msg.data.getInt("AGE")
                val job = msg.data.getString("JOB")
                Log.d("MyLooper get message:", data ?: "")

                val count = data?.length ?: 0
                val message = Message()
                val bundle = Bundle()
                bundle.putString("result", String.format("The number of letters in the word %s is %d\n Возраст: %d, Должность: %s ", data, count, age, job))


                message.data = bundle
                mainThreadHandler.postDelayed({mainThreadHandler.sendMessage(message)}, age.toLong() * 1000)
            }

        }
        Looper.loop()
    }


}