package ru.mirea.karacheviv.mireaproject

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var REQUEST_CODE_PERMISSION = 100
    var TAG = RecordFragment::class.java.simpleName
    private var param1: String? = null
    private var param2: String? = null

    private var isWork = false
    private lateinit var recordFilePath : String
    private lateinit var recordButton : Button
    private lateinit var playButton : Button
    private lateinit var recorder: MediaRecorder
    private lateinit var player: MediaPlayer
    var isStartRecording = true
    var isStartPlaying = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RecordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RecordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recordButton = view.findViewById(R.id.recordButton)
        playButton = view.findViewById(R.id.playButton)
        playButton.isEnabled = false
        recordFilePath = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "/audiorecordtest.3gp").absolutePath

        val audioRecordPermissionStatus = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.RECORD_AUDIO
        )
        val storagePermissionStatus = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if(audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED &&  storagePermissionStatus == PackageManager.PERMISSION_GRANTED)
        {
            isWork = true
        }
        else
        {
            ActivityCompat.requestPermissions(
                requireActivity(),
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
                playButton.text = "Stop"
                recordButton.isEnabled = false
                startPlaying()
            }
            else{
                playButton.text = "Play"
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
            player.setOnCompletionListener {
                stopPlaying()
            }
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
            requireActivity().finish()
        }
    }
}