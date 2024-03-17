package ru.mirea.karacheviv.data_thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.mirea.karacheviv.data_thread.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val runn1 = Runnable {
            binding.tvInfo.text = "runn1"
        }
        val runn2 = Runnable {
            binding.tvInfo.text = "runn2"
        }
        val runn3 = Runnable {
            binding.tvInfo.text = "runn3"
        }
        val t = Thread{
            try {
                TimeUnit.SECONDS.sleep(2)
                runOnUiThread(runn1)
                TimeUnit.SECONDS.sleep(1)
                binding.tvInfo.postDelayed(runn3, 2000)
                binding.tvInfo.post(runn2)
                Log.d(MainActivity::class.java.simpleName, "ПРАВИЛЬНО")



            }
            catch (e: Exception){
                Log.d(MainActivity::class.java.simpleName, "ОШИБКА")
                e.printStackTrace()
            }
        }
        t.start()
    }


}