package ru.mirea.karacheviv.thread

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.karacheviv.thread.databinding.ActivityMainBinding
import java.text.Format
import java.util.Arrays


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainThread = Thread.currentThread()
        binding.textView.text = "Имя текущего потока: " + mainThread.name
        mainThread.name = "МОЙ НОМЕР ГРУППЫ: БСБО-10-21, НОМЕР ПО СПИСКУ: 10, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: Легенды Осени"
        binding.textView.append("\nНовое имя потока: " + mainThread.name)
        Log.d(
            MainActivity::class.java.simpleName,
            "Stack:	" + Arrays.toString(mainThread.stackTrace)
        )
        Log.d(MainActivity::class.java.simpleName, "Group:	" + mainThread.threadGroup)
        binding.buttonMirea.setOnClickListener {
            Thread {
                val numberThread = counter++
                Log.d(
                    "ThreadProject",
                    "Запущен поток № $numberThread студентом группы № БСБО-10-21 номер по списку № 10"
                )
                val endTime = System.currentTimeMillis() + 20 * 1000
                while (System.currentTimeMillis() < endTime) {
                    synchronized(this) {
                        try {
                            Thread.sleep(endTime - System.currentTimeMillis())
                        } catch (e: Exception) {
                            throw RuntimeException(e)
                        }
                    }
                }
                Log.d("ThreadProject", "Выполнен поток №	" + numberThread)

            }.start()

        }
    }
}