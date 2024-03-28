package ru.mirea.karacheviv.camera

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import ru.mirea.karacheviv.camera.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class MainActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_CODE_PERMISSION = 100
        const val CAMERA_REQUEST = 0
    }
    private var isWork = false
    private lateinit var imageUri: Uri
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cameraPermissionStatus = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
        val storagePermissionStatus = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus  == PackageManager.PERMISSION_GRANTED){
            isWork = true
        }
        else{
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION)


        }

        val callback = ActivityResultCallback<ActivityResult>{result ->
            Log.d("KKK", "IMG")
            if(result.resultCode == Activity.RESULT_OK){
                val data = result.data
                binding.imageView.setImageURI(imageUri)
            }

        }

        val cameraActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            callback
        )

        binding.imageView.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (isWork) {
                try {
                    val photoFile = createImageFile()
                    val authorities = applicationContext.packageName + ".fileprovider"
                    imageUri = FileProvider.getUriForFile(
                        this@MainActivity, authorities,
                        photoFile!!
                    )
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    cameraActivityResultLauncher.launch(cameraIntent)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }


    }


    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val imageFileName = "IMAGE_" + timeStamp + "_"
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(imageFileName, ".jpg", storageDirectory)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        }
    }
}