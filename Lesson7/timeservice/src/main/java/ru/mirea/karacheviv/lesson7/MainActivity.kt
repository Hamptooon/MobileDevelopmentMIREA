package ru.mirea.karacheviv.lesson7

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.karacheviv.lesson7.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var host = "time.nist.gov"
    private var port = 13;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTime.setOnClickListener {
            val timeTask = GetTimeTask()
            timeTask.execute()
        }

    }


    private inner class GetTimeTask : AsyncTask<Void?, Void?, String>() {
        override fun doInBackground(vararg params: Void?): String? {
            var timeResult = ""
            try {
                val socket = Socket(host, port)
                val reader = SocketUtils.getReader(socket)
                reader.readLine() // игнорируем первую строку
                timeResult = reader.readLine() // считываем вторую строку
                Log.d("MainActivityProgram", timeResult)
                socket.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return timeResult
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            val resultStrArr = result.split(" ")
            val date = resultStrArr[1]
            val time = resultStrArr[2]
            binding.timeTextView.text = time
            binding.dateTextView.text = date

        }
    }

}