package ru.mirea.karacheviv.musicplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mirea.karacheviv.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.currentTrackTime.text = "0:00"
        binding.trackTime.text = "4:22"
        binding.trackName.text = "When You Know (Where You Come From)"

    }
}