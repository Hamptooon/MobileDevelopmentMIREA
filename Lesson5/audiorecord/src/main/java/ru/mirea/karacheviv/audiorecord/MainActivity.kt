package ru.mirea.karacheviv.audiorecord

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.mirea.karacheviv.audiorecord.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {
    companion object
    {
        const val REQUEST_CODE_PERMISSION = 100
    }
    var TAG = MainActivity::class.java.simpleName
    private lateinit var binding : ActivityMainBinding
    private var isWork = false
    private lateinit var fileName : String
    private lateinit var recordFilePath : String
    private lateinit var recordButton : Button
    private lateinit var playButton : Button
    private lateinit var recorder: MediaRecorder
    private lateinit var player: MediaPlayer
    var isStartRecording = true
    var isStartPlaying = true






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recordButton = binding.recordButton
        playButton = binding.playButton
        playButton.isEnabled = false
        recordFilePath = File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "/audiorecordtest.3gp").absolutePath

        val audioRecordPermissionStatus = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.RECORD_AUDIO
        )
        val storagePermissionStatus = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if(audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED &&  storagePermissionStatus == PackageManager.PERMISSION_GRANTED)
        {
            isWork = true
        }
        else
        {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION
            )

        }

        recordButton.setOnClickListener{
            if(isStartRecording){
                recordButton.text = "Stop Recording"
                playButton.isEnabled = false
                startRecording();
            }
            else{
                recordButton.text = "Start Recording"
                playButton.isEnabled = true
                stopRecording()
            }
            isStartRecording = !isStartRecording

        }

        playButton.setOnClickListener{
            if(isStartPlaying){
                playButton.text = "Stop playing"
                recordButton.isEnabled = false
                startPlaying()
            }
            else{
                playButton.text = "Start playing"
                recordButton.isEnabled = false
                stopPlaying()
            }
            isStartPlaying = !isStartPlaying
        }




    }
    private fun startRecording() {
        recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setOutputFile(recordFilePath)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        try {
            recorder.prepare()
        } catch (e: IOException) {
            Log.e(TAG, "prepare()	failed")
        }
        recorder.start()
    }

    private fun stopRecording() {
        recorder.stop()
        recorder.release()
    }


    private fun startPlaying() {
        player = MediaPlayer()
        try {
            player.setDataSource(recordFilePath)
            player.prepare()
            player.start()
        } catch (e: IOException) {
            Log.e(TAG, "prepare()	failed")
        }
    }
    private fun stopPlaying() {
        player.release()
    }





    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode)
        {
            REQUEST_CODE_PERMISSION ->
                isWork = grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (!isWork)
        {
            finish()
        }
    }
}