package ru.mirea.karacheviv.looper

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.karacheviv.looper.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainThreadHandler: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                Log.d(MainActivity::class.java.simpleName,
                    "Task	execute.	This	is	result:	" + msg.getData()
                        .getString("result")
                )
            }
        }
        val myLooper = MyLooper(mainThreadHandler)
        myLooper.start()
        binding.editText1.setText("Мой номер по списку №10")
        binding.button1.setOnClickListener{
            val age = binding.editText2.text.toString().toIntOrNull()?: 0
            val jobTitle = binding.editText3.text.toString()
            val msg = Message.obtain()
            val bundle = Bundle()
            bundle.putString("KEY", "mirea")
            bundle.putInt("AGE", age)
            bundle.putString("JOB", jobTitle)
            msg.data = bundle
            myLooper.mHandler.sendMessage(msg)
        }
    }
}