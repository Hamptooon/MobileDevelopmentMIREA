package ru.mirea.karacheviv.serviceapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.mirea.karacheviv.serviceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var permissionCode = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            Log.d(MainActivity::class.java.simpleName, "Разрешения получены")
        } else {
            Log.d(MainActivity::class.java.simpleName, "Нет разрешений!")
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.FOREGROUND_SERVICE), permissionCode)
        }

        binding.startBtn.setOnClickListener {
            val serviceIntent = Intent(this, PlayerService::class.java)
            ContextCompat.startForegroundService(this, serviceIntent)
        }

        binding.stopBtn.setOnClickListener {
            stopService(Intent(this, PlayerService::class.java))
        }
    }
}