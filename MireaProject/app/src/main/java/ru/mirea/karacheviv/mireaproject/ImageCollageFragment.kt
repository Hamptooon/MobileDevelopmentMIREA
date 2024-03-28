package ru.mirea.karacheviv.mireaproject

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.allViews
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImageCollageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageCollageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var REQUEST_CODE_PERMISSION = 100
    private var CAMERA_REQUEST = 0
    private var isWork = false
    private lateinit var imageUri: Uri
    private lateinit var currentImageView: ImageView




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
        return inflater.inflate(R.layout.fragment_image_collage, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImageCollageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageCollageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val views = view.allViews
        val imageViews = ArrayList<ImageView>()
        for (viewItem in views){
            if(viewItem is ImageView){
                Log.d("TTT", viewItem.id.toString())
                imageViews.add(viewItem)
            }
        }
        setOnClickListenersForImageViews(imageViews)

        val cameraPermissionStatus = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA);
        val storagePermissionStatus = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus  == PackageManager.PERMISSION_GRANTED){
            isWork = true
        }
        else{
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_CODE_PERMISSION)


        }

//        val callback = ActivityResultCallback<ActivityResult>{ result ->
//            Log.d("KKK", "IMG")
//            if(result.resultCode == Activity.RESULT_OK){
//                val data = result.data
//                binding.imageView.setImageURI(imageUri)
//            }
//
//        }
//
//        val cameraActivityResultLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult(),
//            callback
//        )

//        binding.imageView.setOnClickListener {
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            if (isWork) {
//                try {
//                    val photoFile = createImageFile()
//                    val authorities = applicationContext.packageName + ".fileprovider"
//                    imageUri = FileProvider.getUriForFile(
//                        this@MainActivity, authorities,
//                        photoFile!!
//                    )
//                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//                    cameraActivityResultLauncher.launch(cameraIntent)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        }

    }


    private fun setOnClickListenersForImageViews(imageViews: ArrayList<ImageView>) {
        for (imageView in imageViews) {
            imageView.setOnClickListener {
                Log.d("---", imageView.scaleType.toString())

                takePhoto(imageView)
            }
        }
    }

    // Метод для обработки нажатия на ImageView и запуска камеры
    private fun takePhoto(imageView: ImageView) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (isWork) {
            try {
                val photoFile = createImageFile()
                val authorities = requireContext().packageName + ".fileprovider"
                imageUri = FileProvider.getUriForFile(
                    requireContext(), authorities,
                    photoFile!!
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
                // Сохраните ссылку на ImageView, куда будет установлена фотография
                currentImageView = imageView
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val imageFileName = "IMAGE_" + timeStamp + "_"
        val storageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            // Устанавливаем изображение в соответствующий ImageView
            currentImageView.setImageURI(imageUri)
        }
    }





}